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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Survey View
 */
public class HYSurveyView extends LinearLayout {
    private WebView webView;
    private Long surveyId;
    private Long channelId;
    private JSONObject options;
    private JSONObject parameters;

    private Boolean finished = false;
    private Boolean debug = false;
    private Integer delay = 3000;

    private String version = "";
    private String build = "";
    public String ua = "";

    private SurveyFunction onSubmit = null;
    private SurveyFunction onCancel = null;

    public HYSurveyView(Context context, Long surveyId, Long channelId, JSONObject parameters, JSONObject options) throws JSONException {
        super(context);
        this.loadVersion(context);

        this.surveyId = surveyId;
        this.channelId = channelId;
        this.parameters = parameters;
        this.options = options;

        this.debug = this.options.has("debug") && this.options.getBoolean("debug");
        this.delay = this.options.has("delay") ? this.options.getInt("delay") : 3000;

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

        webView.loadUrl("file:///android_asset/index.html");
        this.addView(webView);

        Log.v("surveySDK", "init with version: " + version);
    }

    public String getVersion() {
        return version;
    }

    public String getBuild() {
        return build;
    }

    /**
     * load version info
     */
    private void loadVersion(Context context) {
        try {
            InputStream io = context.getResources().getAssets().open("version.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(io));
            String versionStr = br.lines().map(line -> line + "\n").collect(Collectors.joining());
            JSONObject object = new JSONObject(versionStr);
            this.version = object.getString("version");
            this.build = object.getString("build");
            this.ua = String.format("surveySDK/%s (Android) %s", version, build);
        } catch (Exception ex) {
            Log.e("surveySDK", "loadVersion: " + ex.getMessage());
        }
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
                                int height = value.getInt("height");
                                int dp = pxFromDp(getContext(), height);
                                ViewGroup.LayoutParams layoutParams = container.getLayoutParams();
                                Log.v("surveySDK", "change height to " + dp);
                                container.setLayoutParams(new LayoutParams(layoutParams.width, dp));
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

