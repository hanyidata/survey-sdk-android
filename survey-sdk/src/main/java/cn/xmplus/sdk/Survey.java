package cn.xmplus.sdk;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class Survey extends LinearLayout {


    private WebView webView;
    private Long surveyId;
    private Long channelId;
    private JSONObject options;
    private JSONObject parameters;

    private Boolean finished = false;
    private Boolean debug = false;
    private Integer delay = 3000;

    public Survey(Context context, Long surveyId, Long channelId, JSONObject parameters, JSONObject options) throws JSONException {
        super(context);
        this.surveyId = surveyId;
        this.channelId = channelId;
        this.parameters = parameters;
        this.options = options;

        this.debug = this.options.has("debug") && this.options.getBoolean("debug");
        this.delay = this.options.has("delay") ? this.options.getInt("delay") : 3000;

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

//        Integer padding = 10;
//        ShapeDrawable shapedrawable = new ShapeDrawable();
//        shapedrawable.setShape(new RectShape());
//        shapedrawable.getPaint().setColor(Color.RED);
//        shapedrawable.getPaint().setStrokeWidth(10f);
//        shapedrawable.setPadding(padding, padding, padding, padding);
//        shapedrawable.getPaint().setStyle(Paint.Style.STROKE);

        webView = new WebView(this.getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.addJavascriptInterface(this, "surveyProxy");

        webView.loadUrl("file:///android_asset/index.html");
        this.addView(webView);

//        this.setBackground(shapedrawable);
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
            Log.v(TAG, event.toString());

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
                                Log.v(TAG, data.toString());
                                String script = String.format("document.dispatchEvent(new CustomEvent('command', { detail: %s}))", data);
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
                                LayoutParams layoutParams = (LayoutParams) container.getLayoutParams();
                                Log.v(TAG, "change height to " + dp);
                                container.setLayoutParams(new LayoutParams(layoutParams.width, dp));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "close":
                            LayoutParams layoutParams = (LayoutParams) container.getLayoutParams();
                            container.setLayoutParams(new LayoutParams(layoutParams.width, 0));
                            break;
                        case "submit":
                            finished = true;
                            break;
                        default:
                            break;
                    }
                }
            });
        } catch (Throwable t) {
            Log.e(TAG, "Could not parse malformed JSON: \"" + message + "\"" + t.getMessage());
        }
    }

}

