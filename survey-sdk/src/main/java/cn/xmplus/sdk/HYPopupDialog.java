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
import cn.xmplus.sdk.service.HYSurveyService;

public class HYPopupDialog extends Dialog {
    private final String surveyId;
    private final String channelId;
    private final JSONObject parameters;
    private JSONObject options;

    private HYSurveyView survey;
    private JSONObject config;

    private LinearLayout contentView;
    private int contentHeight = 0;
    private Context context;
    private SurveyFunction onCancel = null;
    private SurveyFunction onSubmit = null;

    private int appPaddingWidth = 0;
    private int appBorderRadiusPx = 0;
    private int embedHeight = 0;
    private String embedVerticalAlign = "CENTER";
    private String embedHeightMode = "AUTO";
    private Boolean embedBackGround = false;

    public HYPopupDialog(Context context, String surveyId, String channelId, JSONObject parameters, JSONObject options, JSONObject config, SurveyFunction onCancel, SurveyFunction onSubmit)  {
        super(context);
        this.context = context;
        this.surveyId = surveyId;
        this.channelId = channelId;
        this.parameters = parameters;
        this.options = options;
        this.config = config;
        this.onCancel = onCancel;
        this.onSubmit = onSubmit;

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
     *
     * @param context
     * @param surveyId
     * @param channelId
     * @param accessCode
     * @param parameters
     * @param options
     */
    public static void makeDialog(Context context, String surveyId, String channelId, String accessCode, JSONObject parameters, JSONObject options, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onError)  {
        String server = options.optString("server", "https://www.xmplus.cn/api/survey");
        JSONObject mergeOption = options;
        try {
            mergeOption.put("ignorePadding", true);
        } catch (JSONException e) {
        }
        new HYSurveyService((JSONObject config, String error) -> {
            if (config != null) {
                HYPopupDialog dialog = new HYPopupDialog(context, surveyId, channelId, parameters, mergeOption, config, onCancel, onSubmit);
                dialog.show();
            } else {
                Log.e("surveySDK", String.format("survey popup failed %s", error));
                if (onError != null) {
                    onError.accept(error);
                }
            }
        }).execute(server, surveyId, channelId, accessCode);
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
        contentView = new LinearLayout(context);
        if (options.optBoolean("bord", false)) {
            gradientDrawable.setStroke(5, Color.RED);
            contentView.setPadding(10, 10, 10, 10);
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
                break;
        }

        // survey
        try {
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
            contentView.addView(this.survey);
            setContentView(contentView, new FrameLayout.LayoutParams(screenWidth - appPaddingWidth * 2, 1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateLayout() {
        DisplayMetrics displayMetrics = this.getContext().getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int newWidth = screenWidth - appPaddingWidth * 2;
        int newHeight = 0;
        switch (embedHeightMode) {
            case "AUTO":
                newHeight = Math.min(this.contentHeight, embedHeight);
                break;
            case "FIX":
                newHeight = embedHeight;
                break;
        }
        contentView.setLayoutParams(new FrameLayout.LayoutParams(newWidth, newHeight));
    }
}
