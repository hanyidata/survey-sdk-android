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
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xmplus.sdk.callback.SurveyFunction;
import cn.xmplus.sdk.service.HYSurveySendService;
import cn.xmplus.sdk.service.HYSurveyService;

public class HYPopupDialog extends Dialog {
    private String surveyId;
    private String channelId;
    private final JSONObject parameters;
    private JSONObject options;

    private HYSurveyView survey;
    private JSONObject config;

    private ScrollView scrollView;
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
    public HYPopupDialog(Context context, String surveyId, String channelId, JSONObject parameters, JSONObject options, JSONObject config, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onLoad)  {
        super(context);
        this.context = context;
        this.surveyId = surveyId;
        this.channelId = channelId;
        this.parameters = parameters;
        this.options = options;
        this.config = config;
        this.onCancel = onCancel;
        this.onSubmit = onSubmit;
        this.onLoad = onLoad;

        // global config
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 弹窗场景下，圆角根据垂直对齐方式设置
        embedVerticalAlign = config.optString("embedVerticalAlign", "CENTER");
        try {
            this.options.put("borderRadiusMode", embedVerticalAlign);
        } catch (JSONException e) {
        }
        setCanceledOnTouchOutside(false);
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
        String accessCode = parameters.optString("accessCode", "");
        String externalUserId = parameters.optString("externalUserId", "");
        JSONObject mergeOption = options;
        try {
            mergeOption.put("isDialogMode", true);
        } catch (JSONException e) {
            Log.d("surveySDK", e.getMessage());
        }

        HYPopupDialog._trackingView = view;
        HYPopupDialog._lastInstance = null;
        HYPopupDialog._close = false;

        if (sendId.isEmpty()) {
            // sid + channel id
            new HYSurveyService((String sid, String cid, JSONObject config, String error) -> {
                if (config != null) {
                    HYPopupDialog._showDialog(context, surveyId, channelId, parameters, mergeOption, config, onCancel, onSubmit, onLoad, onError);
                } else {
                    Log.e("surveySDK", String.format("survey popup failed %s", error));
                    if (onError != null) {
                        onError.accept(error);
                    }
                }
            }).execute(server, surveyId, channelId, accessCode, externalUserId);
        } else {
            // send id
            new HYSurveySendService((String sid, String cid, JSONObject config, String error) -> {
                if (config != null) {
                    HYPopupDialog._showDialog(context, sid, cid, parameters, mergeOption, config, onCancel, onSubmit, onLoad, onError);
                } else {
                    Log.e("surveySDK", String.format("survey popup failed %s", error));
                    if (onError != null) {
                        onError.accept(error);
                    }
                }
            }).execute(server, sendId, accessCode, externalUserId);
        }
    }

    private static void _showDialog(Context context, String surveyId, String channelId, JSONObject parameters, JSONObject options, JSONObject config, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onLoad, SurveyFunction onError) {
        if (!HYPopupDialog.canPopup()) {
            Log.e("surveySDK", "survey skip popup");
            if (onError != null) {
                onError.accept("popup skipped");
            }
            return;
        }
        HYPopupDialog._lastInstance = new HYPopupDialog(context, surveyId, channelId, parameters, options, config, onCancel, onSubmit, onLoad);
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
        }
    }

    /**
     * check can popup, only return true when
     *  - user not asking close, aka _close != true
     *  - trackingView is visible
     * @return
     */
    private static  boolean canPopup() {
        if (HYPopupDialog._close) {
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

        GradientDrawable gradientDrawable = new GradientDrawable();
        if (!embedBackGround) {
            getWindow().setDimAmount(0f);
        }

        // content view
        scrollView = new ScrollView(context);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        contentView = new LinearLayout(context);
        if (options.optBoolean("bord", false)) {
            gradientDrawable.setStroke(5, Color.RED);
            contentView.setPadding(10, 10, 10, 10);
        }
        scrollView.addView(contentView);

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
                break;
        }

        // survey
        this.survey = new HYSurveyView(context, this.surveyId, this.channelId, this.parameters, this.options, this.config);
        this.survey.setOnCancel((Object param) -> {
            if (this.onCancel != null) {
                this.onCancel.accept(null);
            }
            this.dismiss();
        });
        this.survey.setOnClose((Object param) -> {
            this.dismiss();
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
            if (this.onLoad != null) {
                this.onLoad.accept(param);
            }
        });
        contentView.addView(this.survey);
        setContentView(scrollView, new FrameLayout.LayoutParams(screenWidth - appPaddingWidth * 2, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    private void updateLayout() {
        DisplayMetrics displayMetrics = this.getContext().getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int newWidth = screenWidth - appPaddingWidth * 2;
        int newHeight = (int) Math.min(this.contentHeight, displayMetrics.heightPixels);
//        int newHeight = this.contentHeight;
        Log.d("surveySDK", "update height " + newHeight);
        switch (embedHeightMode) {
            case "AUTO":
                newHeight = Math.min(this.contentHeight, embedHeight);
                break;
            case "FIX":
                newHeight = embedHeight;
                break;
        }
        scrollView.setLayoutParams(new FrameLayout.LayoutParams(newWidth, newHeight));
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(getWindow().getAttributes());
        layoutParams.width = newWidth;
        layoutParams.height = newHeight;
        getWindow().setAttributes(layoutParams);
    }
}
