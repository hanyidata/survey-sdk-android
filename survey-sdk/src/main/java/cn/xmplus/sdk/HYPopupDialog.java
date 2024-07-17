package cn.xmplus.sdk;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xmplus.sdk.callback.SurveyFunction;
import cn.xmplus.sdk.data.SurveyStartRequest;
import cn.xmplus.sdk.data.SurveyStartResponse;
import cn.xmplus.sdk.service.HYSurveyService;

public class HYPopupDialog extends Dialog {
    private String surveyId;
    private String channelId;
    private String clientId;
    private final JSONObject parameters;
    private JSONObject options;

    private HYSurveyView survey;
    private JSONObject config;
    private JSONObject surveyJson;

//    private ScrollView scrollView;
    private LinearLayout contentView;
    private int contentHeight = 0;
    private Context context;
    private SurveyFunction onCancel = null;
    private SurveyFunction onSubmit = null;
    private SurveyFunction onLoad = null;

    private int appPaddingWidth = 0;
    private int appBorderRadiusPx = 0;
    private int embedHeight = 0;
    private String embedVerticalAlign = "CENTER";
    private String embedHeightMode = "AUTO";
    private Boolean embedBackGround = false;

    // 标记是否关闭，调用close方法会设置成true，一旦设置成true会关闭当前打开的dialog
    static Boolean _close = false;

    // 标记最近的dialog实例
    static HYPopupDialog _lastInstance = null;

    // 标记最近一次的tracking view
    static View _trackingView = null;

    /**
     * 根据sid cid弹出问卷
     * @param context
     * @param surveyId
     * @param channelId
     * @param parameters
     * @param options
     * @param config
     * @param onCancel
     * @param onSubmit
     * @param onLoad
     */
    public HYPopupDialog(Context context, String surveyId, String channelId, String clientId, JSONObject surveyJson, JSONObject parameters, JSONObject options, JSONObject config, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onLoad)  {
        super(context);
        this.context = context;
        this.surveyId = surveyId;
        this.channelId = channelId;
        this.clientId = clientId;
        this.parameters = parameters;
        this.options = options;
        this.config = config;
        this.onCancel = onCancel;
        this.onSubmit = onSubmit;
        this.onLoad = onLoad;
        this.surveyJson = surveyJson;

        // global config
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        boolean clickDismiss = options.optBoolean("clickDismiss", false);
        this.setCanceledOnTouchOutside(clickDismiss);
        this.setCancelable(clickDismiss);

        // 弹窗场景下，圆角根据垂直对齐方式设置
        embedVerticalAlign = config.optString("embedVerticalAlign", "CENTER");
        try {
            this.options.put("borderRadiusMode", embedVerticalAlign);
        } catch (JSONException e) {
        }

        // 设置 OnDismissListener 处理取消事件
        setOnDismissListener(dialog -> {
            HYPopupDialog._lastInstance = null;
            HYPopupDialog._close = false;
        });
    }



    /**
     * Make Dialog by sid and cid
     * @deprecated
     * please replace the first parameter context to view, since we will track the view status
     * before popup dialog.
     *
     * @param context
     * @param surveyId
     * @param channelId
     * @param parameters
     * @param options
     * @param onCancel
     * @param onSubmit
     * @param onError
     */
    @Deprecated()
    public static void makeDialog(Context context, String surveyId, String channelId, JSONObject parameters, JSONObject options, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onError) {
        _makeDialog(null, context, surveyId, channelId,  "", parameters, options, onCancel, onSubmit, onError, null);
    }

    /**
     * Make Dialog sid and cid
     * @deprecated
     * please replace the first parameter context to view, since we will track the view status
     * before popup dialog.
     *
     * @param context
     * @param surveyId
     * @param channelId
     * @param parameters
     * @param options
     * @param onCancel
     * @param onSubmit
     * @param onError
     * @param onLoad
     */
    @Deprecated()
    public static void makeDialog(Context context, String surveyId, String channelId, JSONObject parameters, JSONObject options, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onError, SurveyFunction onLoad)  {
        _makeDialog(null, context, surveyId, channelId, "", parameters, options, onCancel, onSubmit, onError, onLoad);
    }

    /**
     * Make Dialog sid and cid
     *
     * @param view
     * @param surveyId
     * @param channelId
     * @param parameters
     * @param options
     * @param onCancel
     * @param onSubmit
     * @param onError
     */
    public static void makeDialog(View view, String surveyId, String channelId, JSONObject parameters, JSONObject options, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onError) {
        _makeDialog(view, view.getContext(), surveyId, channelId, "", parameters, options, onCancel, onSubmit, onError, null);
    }

    /**
     * Make Dialog
     *
     * @param view
     * @param surveyId
     * @param channelId
     * @param parameters
     * @param options
     * @param onCancel
     * @param onSubmit
     * @param onError
     * @param onLoad
     */
    public static void makeDialog(View view, String surveyId, String channelId, JSONObject parameters, JSONObject options, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onError, SurveyFunction onLoad) {
        _makeDialog(view, view.getContext(), surveyId, channelId, "", parameters, options, onCancel, onSubmit, onError, onLoad);
    }

    /**
     * Make Dialog by sendId
     *
     * @param view
     * @param sendId
     * @param parameters
     * @param options
     * @param onCancel
     * @param onSubmit
     * @param onError
     */
    public static void makeDialogBySendId(View view, String sendId, JSONObject parameters, JSONObject options, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onError) {
        _makeDialog(view, view.getContext(), "", "", sendId, parameters, options, onCancel, onSubmit, onError, null);
    }

    /**
     * Make Dialog by sendId
     *
     * @param view
     * @param parameters
     * @param options
     * @param onCancel
     * @param onSubmit
     * @param onError
     * @param onLoad
     */
    public static void makeDialogBySendId(View view, String sendId, JSONObject parameters, JSONObject options, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onError, SurveyFunction onLoad) {
        _makeDialog(view, view.getContext(), "", "", sendId, parameters, options, onCancel, onSubmit, onError, onLoad);
    }

    /**
     * internal make dialog
     * @param view
     * @param context
     * @param surveyId
     * @param channelId
     * @param sendId
     * @param parameters
     * @param options
     * @param onCancel
     * @param onSubmit
     * @param onError
     * @param onLoad
     */
    private static void _makeDialog(View view, Context context, String surveyId, String channelId, String sendId, JSONObject parameters, JSONObject options, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onError, SurveyFunction onLoad) {
        String server = options.optString("server", "https://www.xmplus.cn/api/survey");
        JSONObject mergeOption = options;
        try {
            mergeOption.put("isDialogMode", true);
        } catch (JSONException e) {
            Log.d("surveySDK", e.getMessage());
        }

        if (HYPopupDialog._lastInstance != null) {
            if (onError != null) {
                onError.accept("skip popup already have dialog");
            }
            return;
        }

        HYPopupDialog._trackingView = view;
        HYPopupDialog._close = false;

        // sid + channel id
        SurveyStartRequest request = new SurveyStartRequest(server, surveyId, channelId, sendId, parameters);
        new HYSurveyService((SurveyStartResponse response) -> {
            if (response.getError() == null) {
                String sid = response.getSurveyId();
                String cid = response.getChannelId();
                JSONObject config = response.getChannelConfig();
                JSONObject survey = response.getSurvey();
                HYPopupDialog._showDialog(context, sid, cid, response.getClientId(), survey, parameters, mergeOption, config, onCancel, onSubmit, onLoad, onError);
            } else {
                Log.e("surveySDK", String.format("survey popup failed %s", response.getError()));
                if (onError != null) {
                    onError.accept(response.getError());
                }
            }
        }).execute(request);
    }

    private static void _showDialog(Context context, String surveyId, String channelId, String clientId, JSONObject survey, JSONObject parameters, JSONObject options, JSONObject config, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onLoad, SurveyFunction onError) {
        if (!HYPopupDialog.canPopup()) {
            Log.e("surveySDK", "survey skip popup");
            if (onError != null) {
                onError.accept("popup skipped");
            }
            return;
        }
        HYPopupDialog._lastInstance = new HYPopupDialog(context, surveyId, channelId, clientId, survey, parameters, options, config, onCancel, onSubmit, onLoad);
        HYPopupDialog._lastInstance.show();
    }

    /**
     * close popup
     */
    public static void close() {
        HYPopupDialog._close = true;
        if (HYPopupDialog._lastInstance != null) {
            try {
                HYPopupDialog._lastInstance.dismiss();
            } catch (Exception ex) {
                Log.e("surveySDK", String.format("survey pop close failed %s", ex.getMessage()));
            }
            HYPopupDialog._lastInstance = null;
        }
    }

    /**
     * check can popup, only return true when
     *  - user not asking close, aka _close != true
     *  - trackingView is visible
     * @return
     */
    private static  boolean canPopup() {
        if (HYPopupDialog._lastInstance != null || HYPopupDialog._close) {
            return false;
        }

        // no tracking view, still use context case.
        if (HYPopupDialog._trackingView == null) {
            return true;
        }

        return HYPopupDialog._trackingView.getVisibility() == View.VISIBLE && HYPopupDialog._trackingView.isAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // survey channel config apply here
        DisplayMetrics displayMetrics = this.getContext().getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        embedVerticalAlign = config.optString("embedVerticalAlign", "CENTER");
        embedHeightMode = config.optString("embedHeightMode", "AUTO");
        embedBackGround = config.optBoolean("embedBackGround", false);
        appBorderRadiusPx = Util.parsePx(context, config.optString("appBorderRadius", "0px"), screenWidth);
        appPaddingWidth = Util.parsePx(context, config.optString("appPaddingWidth", "0px"), screenWidth);
        embedHeight = Util.parsePx(context, config.optString("embedHeight", "0px"), screenHeight);

        contentView = new LinearLayout(context);
        contentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        Window window = getWindow();
        if (window != null) {
            // 获取Dialog的Window对象，并设置dimAmount为0，完全去除背景暗化效果
            window.setDimAmount(0f);
        }

        switch (embedVerticalAlign) {
            case "CENTER":
                getWindow().setGravity(Gravity.CENTER);
                contentView.setGravity(Gravity.CENTER);
                break;
            case "TOP":
                getWindow().setGravity(Gravity.TOP);
                contentView.setGravity(Gravity.TOP);
                break;
            case "BOTTOM":
                getWindow().setGravity(Gravity.BOTTOM);
                contentView.setGravity(Gravity.BOTTOM);
                if (window != null) {
                    // 设置从底部弹出动画
                    window.setWindowAnimations(android.R.style.Animation_Dialog);
                    WindowManager.LayoutParams layoutParams = window.getAttributes();
                    layoutParams.gravity = Gravity.BOTTOM;
                    layoutParams.y = 0;
                    window.setAttributes(layoutParams);
                }
                break;
        }

        // survey
        this.survey = new HYSurveyView(context, this.surveyId, this.channelId, this.parameters, this.options, this.config, this.surveyJson, this.clientId);
        this.survey.setOnCancel((Object param) -> {
            if (this.onCancel != null) {
                this.onCancel.accept(null);
            }
            this.close();
        });
        this.survey.setOnClose((Object param) -> {
            this.close();
        });
        this.survey.setOnSubmit((Object param) -> {
            if (this.onSubmit != null) {
                this.onSubmit.accept(null);
            }
        });
        this.survey.setOnSize((Object param) -> {
            this.contentHeight = (int)param;
            this.updateLayout();
        });
        this.survey.setOnLoad((Object param) -> {
            Log.d("surveySDK", "popup dialog received onLoad");
            contentView.post(() -> {
//                contentView.setVisibility(View.VISIBLE);
                if (this.embedBackGround) {
                    if (getWindow() != null) {
                        // 获取Dialog的Window对象，并设置dimAmount为0，完全去除背景暗化效果
                        getWindow().setDimAmount(0.6f);
                    }
                }
                ViewGroup.LayoutParams layout = contentView.getLayoutParams();
                layout.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                contentView.setLayoutParams(layout);
                contentView.requestLayout();

            });

            if (this.onLoad != null) {
                this.onLoad.accept(param);
            }
        });
        contentView.addView(this.survey);
//        contentView.setVisibility(View.INVISIBLE);
//        setContentView(contentView, new FrameLayout.LayoutParams(screenWidth - appPaddingWidth * 2, ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(contentView, new FrameLayout.LayoutParams(screenWidth - appPaddingWidth * 2, 0));
    }

    private void updateLayout() {
        DisplayMetrics displayMetrics = this.getContext().getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int newWidth = screenWidth - appPaddingWidth * 2;
        int newHeight = (int) Math.min(this.contentHeight, displayMetrics.heightPixels);
//        int newHeight = this.contentHeight;
        newHeight = displayMetrics.heightPixels / 2;
        Log.d("surveySDK", "update height " + newHeight);
        switch (embedHeightMode) {
            case "AUTO":
                newHeight = Math.min(this.contentHeight, embedHeight);
                break;
            case "FIX":
                newHeight = embedHeight;
                break;
        }
//        contentView.setLayoutParams(new FrameLayout.LayoutParams(newWidth, newHeight));
//        scrollView.setLayoutParams(new FrameLayout.LayoutParams(newWidth, newHeight));
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.copyFrom(getWindow().getAttributes());
//        layoutParams.width = newWidth;
//        layoutParams.height = newHeight;
//        getWindow().setAttributes(layoutParams);

        ViewGroup.LayoutParams layoutParamsA = contentView.getLayoutParams();
        layoutParamsA.height = newHeight;
        contentView.setLayoutParams(layoutParamsA);

        // 请求重新布局
        contentView.requestLayout();
        contentView.invalidate();
    }
}
