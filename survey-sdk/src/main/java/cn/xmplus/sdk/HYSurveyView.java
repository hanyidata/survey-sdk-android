package cn.xmplus.sdk;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Survey View
 */
public class HYSurveyView extends LinearLayout {
    private WebView webView;
    private final String surveyId;
    private final String channelId;
    private final JSONObject parameters;

    private Boolean finished = false;
    private final Boolean debug;
    private final Integer delay;

    private final String version = "";
    public final String ua = "";

    private SurveyFunction onSubmit = null;
    private SurveyFunction onCancel = null;

    private SurveyFunction onSize = null;
    private SurveyFunction onClose = null;

    public HYSurveyView(Context context, String surveyId, String channelId, JSONObject parameters, JSONObject options) throws JSONException {
        super(context);

        this.surveyId = surveyId;
        this.channelId = channelId;
        this.parameters = parameters;

        this.debug = options.has("debug") && options.getBoolean("debug");
        this.delay = options.has("delay") ? options.getInt("delay") : 3000;

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        this.setup();
    }

    public void setOnSubmit(SurveyFunction callback) {
        this.onSubmit = callback;
    }
    public void setOnCancel(SurveyFunction callback) {
        this.onCancel = callback;
    }

    public void setOnClose(SurveyFunction callback) {
        this.onClose = callback;
    }
    public void setOnSize(SurveyFunction callback) {
        this.onSize = callback;
    }

    /**
     * setup webview
     */
    private void setup() {
        webView = new WebView(this.getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.addJavascriptInterface(this, "surveyProxy");
        webView.getSettings().setUserAgentString(ua);
        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public boolean onConsoleMessage(ConsoleMessage cm) {
                if (debug) {
                    Log.d("surveySDK", cm.message());
                }
                return true;
            }
        });

        webView.loadUrl("file:///android_asset/index.html#pages/bridge");
        this.addView(webView);

        Log.v("surveySDK", "init with version: " + version);
    }

    public String getVersion() {
        return version;
    }

    public String getBuild() {
        String build = "";
        return build;
    }

    public void show() {
        if (this.finished) {
            this.webView.reload();
        } else {
            webView.evaluateJavascript("document.dispatchEvent(new CustomEvent('show'))", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                }
            });
        }
    }

    public static int dpFromPx(final Context context, final double px) {
        return (int) Math.round(px / context.getResources().getDisplayMetrics().density);
    }

    public static int pxFromDp(final Context context, final float dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }

    @JavascriptInterface
    public void postMessage(String message) {
        try {
            JSONObject event = new JSONObject(message);
            Log.v("surveySDK", event.toString());

            String type = event.getString("type");
            JSONObject value = event.has("value") ? event.getJSONObject("value") : null;
            ViewGroup container = this;

            webView.post(new Runnable() {
                @Override
                public void run() {
                    switch (type) {
                        case "init":
                            JSONObject data = new JSONObject();
                            try {
                                data.put("surveyId", surveyId);
                                data.put("channelId", channelId);
                                data.put("delay", delay);
                                data.put("parameters", parameters);
                                Log.v("surveySDK", data.toString());
                                String script = String.format("document.dispatchEvent(new CustomEvent('init', { detail: %s}))", data);
                                webView.evaluateJavascript(script, new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String value) {
                                    }
                                });

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "size":
                            try {
                                int dp = value.getInt("height");
                                int px = pxFromDp(getContext(), dp);
                                ViewGroup.LayoutParams layoutParams = container.getLayoutParams();
                                Log.v("surveySDK", "change height to " + px);
                                container.setLayoutParams(new LayoutParams(layoutParams.width, px));
                                if (onSize != null) {
                                    onSize.accept(px);
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "cancel":
                            if (onCancel != null) {
                                onCancel.accept(null);
                            }
                            break;
                        case "close":
                            LayoutParams layoutParams = (LayoutParams) container.getLayoutParams();
                            container.setLayoutParams(new LayoutParams(layoutParams.width, 0));
                            if (onClose != null) {
                                onClose.accept(null);
                            }
                            break;
                        case "submit":
                            finished = true;
                            if (onSubmit != null) {
                                onSubmit.accept(null);
                            }
                            break;
                        default:
                            break;
                    }
                }
            });
        } catch (Throwable t) {
            Log.e("surveySDK", "Could not parse malformed JSON: \"" + message + "\"" + t.getMessage());
        }
    }

}

