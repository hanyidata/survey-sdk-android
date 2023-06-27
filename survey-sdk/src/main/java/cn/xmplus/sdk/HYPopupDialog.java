package cn.xmplus.sdk;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xmplus.sdk.callback.SurveyFunction;
import cn.xmplus.sdk.service.HYSurveyService;

public class HYPopupDialog extends Dialog {
    private final String surveyId;
    private final String channelId;
    private final JSONObject parameters;
    private final JSONObject options;

    private HYSurveyView survey;
    private JSONObject config;

    private LinearLayout contentView;
    private int contentHeight = 1;
    private Context context;
    private SurveyFunction onCancel = null;
    private SurveyFunction onSubmit = null;

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
    }

    /**
     *
     * @param context
     * @param surveyId
     * @param channelId
     * @param parameters
     * @param options
     */
    public static void makeDialog(Context context, String surveyId, String channelId, JSONObject parameters, JSONObject options, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onError)  {
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
        }).execute(server, surveyId, channelId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // survey channel config apply here
        DisplayMetrics displayMetrics = this.getContext().getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // global config
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        setCanceledOnTouchOutside(false);

        String embedVerticalAlign = config.optString("embedVerticalAlign", "CENTER");
        boolean embedBackGround = config.optBoolean("embedBackGround", false);
        int appBorderRadiusPx = Util.parsePx(config.optString("appBorderRadius", "0px"), screenWidth);
        int appPaddingWidth = Util.parsePx(config.optString("appPaddingWidth", "0px"), screenWidth);

        appBorderRadiusPx = Util.pxFromDp(context, appBorderRadiusPx);
        appPaddingWidth = Util.pxFromDp(context, appPaddingWidth);
        GradientDrawable gradientDrawable = new GradientDrawable();
        if (embedBackGround) {
            gradientDrawable.setAlpha(150);
            gradientDrawable.setColor(Color.BLACK);
        } else {
            getWindow().setDimAmount(0f);
            gradientDrawable.setColor(Color.TRANSPARENT);
        }
        switch (embedVerticalAlign) {
            case "CENTER":
                gradientDrawable.setCornerRadius(appBorderRadiusPx);
                getWindow().setGravity(Gravity.CENTER);
                break;
            case "TOP":
                getWindow().setGravity(Gravity.TOP);
                gradientDrawable.setCornerRadii(new float[] {0,0,0,0, appBorderRadiusPx, appBorderRadiusPx, appBorderRadiusPx, appBorderRadiusPx});
                break;
            case "BOTTOM":
                getWindow().setGravity(Gravity.BOTTOM);
                gradientDrawable.setCornerRadii(new float[] {appBorderRadiusPx, appBorderRadiusPx, appBorderRadiusPx, appBorderRadiusPx, 0,0,0,0});
                break;
        }
        getWindow().setBackgroundDrawable(gradientDrawable);

        // content view
        contentView = new LinearLayout(context);

        contentView.setGravity(Gravity.CENTER);
        contentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // survey
        try {
            this.survey = new HYSurveyView(context, this.surveyId, this.channelId, this.parameters, this.options);
            this.survey.setGravity(Gravity.CENTER);
            this.survey.setLayoutParams(new LinearLayout.LayoutParams(screenWidth - appPaddingWidth * 2, ViewGroup.LayoutParams.MATCH_PARENT));
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
//                this.updateLayout();
            });
            contentView.addView(this.survey);
            setContentView(contentView);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
