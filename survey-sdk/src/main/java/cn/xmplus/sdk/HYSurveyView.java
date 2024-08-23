package cn.xmplus.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Locale;

import cn.xmplus.sdk.callback.SurveyFunction;
import cn.xmplus.sdk.data.SurveyStartRequest;
import cn.xmplus.sdk.data.SurveyStartResponse;
import cn.xmplus.sdk.service.HYSurveyService;





/**
 * Survey View
 */
public class HYSurveyView extends LinearLayout {
//    private HYRoundWebView webView;
    private WebView webView;
    private final String surveyId;
    private final String channelId;
    private final JSONObject parameters;
    private final JSONObject options;
    private JSONObject config = new JSONObject();
    private JSONObject surveyJson = null;
    private String clientId = null;

    private Boolean finished = false;
    private Integer previousHeight = 0;
    private Boolean halfscreen = false;
    private Boolean closed = false;
    private int backgroundColor = Color.WHITE;

    private final Boolean debug;
    private final Boolean bord;
    public Boolean isDialogMode = false;

    private final Integer delay;
    private String borderRadiusMode = "CENTER";

    private Integer appBorderRadiusPx = 0;
    private Integer appPaddingWidth = 0;
    private final String server;

    private final String version = "";
    public final String ua = "";

    private SurveyFunction onSubmit = null;
    private SurveyFunction onCancel = null;

    private SurveyFunction onSize = null;
    private SurveyFunction onClose = null;
    private SurveyFunction onLoad = null;

    private JSONObject mergedConfig = new JSONObject();
    private Boolean inputFocused = false;
    private long lastClickTime = 0;

    public HYSurveyView(Context context, String surveyId, String channelId, JSONObject parameters, JSONObject options) {
        this(context, surveyId, channelId, parameters, options, new JSONObject(), null, null);
    }
    public HYSurveyView(Context context, String surveyId, String channelId, JSONObject parameters, JSONObject options, JSONObject config) {
        this(context, surveyId, channelId, parameters, options, new JSONObject(), null, null);
    }

    public HYSurveyView(Context context, String surveyId, String channelId, JSONObject parameters, JSONObject options, JSONObject config, JSONObject surveyJson, String clientId) {
        super(context);

        this.surveyId = surveyId;
        this.surveyJson = surveyJson;
        this.clientId = clientId;
        this.channelId = channelId;
        this.parameters = parameters;
        this.options = options;
        this.config = config;

        DisplayMetrics displayMetrics = this.getContext().getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        this.debug = options.optBoolean("debug", false);
        this.bord = options.optBoolean("bord", false);
        this.isDialogMode = options.optBoolean("isDialogMode", false);
        this.delay = options.optInt("delay", 1000);
        this.halfscreen = options.optBoolean("halfscreen", false);
        this.server = options.optString("server", "production");

        this.borderRadiusMode = config.optString("borderRadiusMode", "CENTER");
        this.appPaddingWidth = Util.parsePx(context, config.optString("appPaddingWidth", "0px"), screenWidth);

        setGravity(Gravity.TOP);

        this.setup();
    }

    /**
     * 根据sid cid显示问卷
     * @param context
     * @param surveyId
     * @param channelId
     * @param parameters
     * @param options
     * @param onError
     */
    public static void makeViewAsync(Context context, String surveyId, String channelId, JSONObject parameters, JSONObject options, SurveyFunction onReady, SurveyFunction onError)  {
        if (onReady == null) {
            Log.e("surveySDK", "onReady not setup");
            return;
        }

        if (!HYGlobalConfig.check()) {
            Log.d("surveySDK", "access code is not ready or invalid");
            onError.accept("access code is not ready or invalid");
            return;
        }

        String server = options.optString("server", HYGlobalConfig.getServer());

        SurveyStartRequest request = new SurveyStartRequest(server, surveyId, channelId, null, parameters);
        new HYSurveyService((SurveyStartResponse response) -> {
            if (response.getError() == null) {
                String sid = response.getSurveyId();
                String cid = response.getChannelId();
                JSONObject survey = response.getSurvey();
                HYSurveyView view = new HYSurveyView(context, sid, cid, parameters, options, response.getChannelConfig(), survey, response.getClientId());
                onReady.accept(view);
            } else {
                Log.e("surveySDK", String.format("survey popup failed %s", response.getError()));
                if (onError != null) {
                    onError.accept(response.getError());
                }
            }
        }).execute(request);
    }

    /**
     * 根据发送id显示问卷
     * @param context
     * @param sendId
     * @param parameters
     * @param options
     * @param onReady
     * @param onError
     */
    public static void makeViewAsyncBySendId(Context context, String sendId, JSONObject parameters, JSONObject options, SurveyFunction onReady, SurveyFunction onError)  {
        if (onReady == null) {
            Log.e("surveySDK", "onReady not setup");
            return;
        }
        String server = options.optString("server", HYGlobalConfig.getServer());
        if (!HYGlobalConfig.check()) {
            Log.d("surveySDK", "access code is not ready or invalid");
            onError.accept("access code is not ready or invalid");
            return;
        }
        SurveyStartRequest request = new SurveyStartRequest(server, null, null, sendId, parameters);
        new HYSurveyService((SurveyStartResponse response) -> {
            if (response.getError() == null) {
                String sid = response.getSurveyId();
                String cid = response.getChannelId();
                JSONObject survey = response.getSurvey();
                HYSurveyView view = new HYSurveyView(context, sid, cid, parameters, options, response.getChannelConfig(), survey, response.getClientId());
                onReady.accept(view);
            } else {
                Log.e("surveySDK", String.format("survey popup failed %s", response.getError()));
                if (onError != null) {
                    onError.accept(response.getError());
                }
            }
        }).execute(request);
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
    public void setOnLoad(SurveyFunction callback) {
        this.onLoad = callback;
    }

    /**
     * setup webview
     */
    private void setup() {
        if (!isDialogMode) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(this.appPaddingWidth, 0, this.appPaddingWidth, 0); // 左、上、右、下边距
            this.setLayoutParams(layoutParams);
        } else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            setLayoutParams(layoutParams);
        }

        Boolean showBackground = surveyJson.optBoolean("showBackground", false);
        if (showBackground && surveyJson != null && surveyJson.has("style")) {
            JSONObject style = surveyJson.optJSONObject("style");
            if (style != null && style.has("backgroundColor")) {
                String color = style.optString("backgroundColor");
                backgroundColor = Util.colorFromHex(color);
            }
        }

        webView = new WebView(this.getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.addJavascriptInterface(this, "surveyProxy");
        webView.getSettings().setUserAgentString(ua);
        webView.setVerticalScrollBarEnabled(true);
        webView.setClipToOutline(true);
        webView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        webView.setFocusableInTouchMode(true);
        webView.setFocusable(true);
        webView.getSettings().setNeedInitialFocus(false);

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
        webView.setBackgroundColor(Color.TRANSPARENT);

        if (config.length() > 0) {
            applyConfig();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }

        webView.loadUrl("file:///android_asset/index.html#pages/bridge");
        this.addView(webView);

        Log.v("surveySDK", "init with version: " + version);
    }

    /**
     *
     */
    private void  applyConfig() {

    }

    public String getVersion() {
        return version;
    }

    public String getBuild() {
        String build = "";
        return build;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        Log.d("surveySDK", "detached survey view");
        if (webView != null) {
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.removeAllViews();
            webView.destroy();
            webView = null;
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

    private void close() {
        if (this.closed) {
            Log.v("surveySDK", "already close");
            return;
        }
        Log.v("surveySDK", "closeSurveyView");
        webView.setVisibility(GONE);
        this.removeView(webView);
        this.setVisibility(GONE);
        this.setLayoutParams(new LayoutParams(0, 0));
        this.closed = true;
    }


    @JavascriptInterface
    public void postMessage(String message) {
        try {
            JSONObject event = new JSONObject(message);
            if (debug) {
                Log.v("surveySDK", event.toString());
            }

            String type = event.getString("type");
            JSONObject value = event.has("value") ? event.getJSONObject("value") : null;
            JSONObject configure = event.has("configure") && !event.get("configure").equals(null) ? event.getJSONObject("configure") : new JSONObject();
            ViewGroup container = this;

            if (type.equals("load")) {
                mergedConfig = new JSONObject();
                for (Iterator<String> it = configure.keys(); it.hasNext(); ) {
                    String key = it.next();
                    mergedConfig.putOpt(key, configure.get(key));
                }
                for (Iterator<String> it = options.keys(); it.hasNext(); ) {
                    String key = it.next();
                    mergedConfig.putOpt(key, options.get(key));
                }
            }

            webView.post(new Runnable() {
                @Override
                public void run() {
                    DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
                    if (webView == null) {
                        Log.v("surveySDK", "webview missing, skip the post msg process");
                        return;
                    }
                    switch (type) {
                        case "init":
                            JSONObject data = new JSONObject();
                            String languageTag = null;
                            String userLang = options.optString("language", "");
                            if (!userLang.isEmpty()) {
                                languageTag = userLang;
                            } else {
                                Locale current = Locale.getDefault();
                                String language = current.getLanguage(); // zh
                                String country = current.getCountry();   // CN
                                languageTag = language + "-" + country.toLowerCase(); // zh-cn
                            }
                            try {
                                data.put("surveyId", surveyId);
                                data.put("channelId", channelId);
                                data.put("delay", delay);
                                data.put("language", languageTag);
                                data.put("halfscreen", halfscreen);
                                data.put("showType", isDialogMode ? "dialog" : "embedded");
                                data.put("server", server);
                                data.put("parameters", parameters);
                                data.put("borderRadiusMode", borderRadiusMode);
                                if (!HYGlobalConfig.getProject().isEmpty()) {
                                    data.put("project", HYGlobalConfig.getProject());
                                }
                                if (clientId != null) {
                                    data.put("clientId", clientId);
                                }
                                Log.v("surveySDK", data.toString());
                                if (surveyJson != null) {
                                    data.put("survey", surveyJson);
                                }
                                String script = String.format("document.dispatchEvent(new CustomEvent('init', { detail: %s}))", data);
                                webView.evaluateJavascript(script, new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String value) {
                                    }
                                });

                            } catch (JSONException e) {
                                Log.e("surveySDK", "init error \"" + message + "\"" + e.getMessage());
                            }
                            break;
                        case "input-focus":
                            Log.v("surveySDK", "input focus gain");
                            if (System.currentTimeMillis() - lastClickTime < 100) {
                                // 忽略这次点击，因为距离上次点击不足一秒
                                return;
                            }
                            lastClickTime = System.currentTimeMillis();
                            if (!inputFocused) {
                                webView.setFocusableInTouchMode(true);
                            }
                            webView.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                Boolean res = imm.showSoftInput(webView, InputMethodManager.SHOW_IMPLICIT);
                                Log.v("surveySDK", "show soft input " + res);
                            }
                            break;
                        case "input-blur":
                            Log.v("surveySDK", "input focus lost");
//                            webView.clearFocus();
//                            webView.setFocusableInTouchMode(false);
                            break;
                        case "load":
                            int screenWidth = displayMetrics.widthPixels;
                            appBorderRadiusPx = Util.parsePx(getContext(), mergedConfig.optString("appBorderRadius", "0px"), screenWidth);
                            appPaddingWidth = Util.parsePx(getContext(), mergedConfig.optString("appPaddingWidth", "0px"), screenWidth);
                            String embedVerticalAlign = mergedConfig.optString("embedVerticalAlign", "CENTER");

                            if (!isDialogMode) {
                                // 如果非弹窗，这里要调整宽度
//                                setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT - appPaddingWidth * 2, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            GradientDrawable drawable = new GradientDrawable();
                            drawable.setShape(GradientDrawable.RECTANGLE);
                            drawable.setColor(backgroundColor); // 设置背景颜色
                            float[] radii = new float[8];
                            int mode = CustomOutlineProvider.BOTH;
                            radii[0] = appBorderRadiusPx; // Top left radius
                            radii[1] = appBorderRadiusPx; // Top left radius
                            radii[2] = appBorderRadiusPx; // Top right radius
                            radii[3] = appBorderRadiusPx; // Top right radius
                            radii[4] = appBorderRadiusPx; // Bottom right radius
                            radii[5] = appBorderRadiusPx; // Bottom right radius
                            radii[6] = appBorderRadiusPx; // Bottom left radius
                            radii[7] = appBorderRadiusPx; // Bottom left radius
                            if (isDialogMode) {
                                switch (embedVerticalAlign) {
                                    case "TOP":
                                        radii[0] = 0; // Top left radius
                                        radii[1] = 0; // Top left radius
                                        radii[2] = 0; // Top right radius
                                        radii[3] = 0; // Top right radius
                                        radii[4] = appBorderRadiusPx; // Bottom right radius
                                        radii[5] = appBorderRadiusPx; // Bottom right radius
                                        radii[6] = appBorderRadiusPx; // Bottom left radius
                                        radii[7] = appBorderRadiusPx; // Bottom left radius
                                        mode = CustomOutlineProvider.BOTTOM_ONLY;
                                        break;
                                    case "BOTTOM":
                                        // 设置只有顶部左右两角是圆角
                                        radii[0] = appBorderRadiusPx; // Top left radius
                                        radii[1] = appBorderRadiusPx; // Top left radius
                                        radii[2] = appBorderRadiusPx; // Top right radius
                                        radii[3] = appBorderRadiusPx; // Top right radius
                                        radii[4] = 0; // Bottom right radius
                                        radii[5] = 0; // Bottom right radius
                                        radii[6] = 0; // Bottom left radius
                                        radii[7] = 0; // Bottom left radius
                                        mode = CustomOutlineProvider.TOP_ONLY;
                                        break;
                                }
                            }
                            drawable.setCornerRadii(radii);
                            webView.setBackground(drawable);
                            webView.setOutlineProvider(new CustomOutlineProvider(appBorderRadiusPx, mode));

                            if (onLoad != null) {
                                onLoad.accept(mergedConfig);
                            }
                            break;
                        case "size":
                            try {
                                int dp = value.getInt("height");
                                int px = Util.pxFromDp(getContext(), dp);
                                int height = px;
                                int sw = displayMetrics.widthPixels;

                                if (previousHeight != height) {
                                    Log.v("surveySDK", "change height to " + height);
                                    if (!isDialogMode) {
                                        ViewGroup.LayoutParams layout = container.getLayoutParams();
                                        layout.height = height;
                                        container.setLayoutParams(layout);
                                    }
                                    if (onSize != null) {
                                        onSize.accept(px);
                                    }
                                    previousHeight = height;
                                }
                            } catch (JSONException e) {
                            }
                            break;
                        case "cancel":
                            Log.v("surveySDK", "survey canceled");
                            close();
                            if (onCancel != null) {
                                onCancel.accept(null);
                            }
                            break;
                        case "close":
                            close();
                            Log.v("surveySDK", "survey closed");
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

