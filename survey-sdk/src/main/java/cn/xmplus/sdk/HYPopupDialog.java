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
import android.widget.LinearLayout;
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
    public static void buildDialog(Context context, String surveyId, String channelId, JSONObject parameters, JSONObject options, SurveyFunction onCancel, SurveyFunction onSubmit, SurveyFunction onError) {
        String server = options.optString("server", "https://www.xmplus.cn/api/survey");
        new HYSurveyService((JSONObject config, String error) -> {
            if (config != null) {
                HYPopupDialog dialog = new HYPopupDialog(context, surveyId, channelId, parameters, options, config, onCancel, onSubmit);
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
        // global config
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);

        // survey channel config apply here
        DisplayMetrics displayMetrics = this.getContext().getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        String embedVerticalAlign = config.optString("embedVerticalAlign", "CENTER");
        boolean embedBackGround = config.optBoolean("embedBackGround", false);
        int appBorderRadiusPx = Util.parsePx(config.optString("appBorderRadius", "0px"), screenWidth);
        int appPaddingWidth = Util.parsePx(config.optString("appPaddingWidth", "0px"), screenWidth);

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
        contentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        GradientDrawable border = new GradientDrawable();
//        border.setColor(Color.RED); //white background
//        border.setStroke(5, Color.RED); //black border with full opacity
//        contentView.setBackground(border);
//        contentView.setPadding(10,10,10,10);

        // survey
        try {
            this.survey = new HYSurveyView(context, this.surveyId, this.channelId, this.parameters, this.options);

            this.survey.setLayoutParams((new LinearLayout.LayoutParams(screenWidth - appPaddingWidth * 2, -1)));
            this.survey.setOnCancel((Object param) -> {
                if (this.onCancel != null) {
                    this.onCancel.accept(null);
                }
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
