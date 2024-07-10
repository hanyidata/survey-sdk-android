package cn.xmplus.sdk.data;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SurveyStartResponse {
    private String surveyId;
    private String error;
    private String channelId;
    private JSONObject survey = new JSONObject();
    private JSONObject embed = new JSONObject();
    private JSONObject channel = new JSONObject();

    public SurveyStartResponse(String error) {
        this.error = error;
    }

    public SurveyStartResponse(String _surveyId, String _channelId, JSONObject survey, JSONObject embed, JSONObject channel) {
        this.surveyId = _surveyId;
        this.channelId = _channelId;
        this.survey = survey;
        this.embed = embed;
        this.channel = channel;
    }

    public static SurveyStartResponse fromJson(JSONObject surveyJson) {
        String surveyId = surveyJson.optString("id");
        JSONObject embed = surveyJson.optJSONObject("embed");
        JSONObject channel = surveyJson.optJSONObject("channel");
        String channelId = channel.optString("id", null);
        return new SurveyStartResponse(surveyId, channelId,  surveyJson, embed, channel);
    }

    public String getSurveyId() {
        return surveyId;
    }

    public String getChannelId() {
        return channelId;
    }
    public String getError() {
        return error;
    }

    public JSONObject getChannel() {
        return channel;
    }

    public JSONObject getEmbed() {
        return embed;
    }

    public JSONObject getSurvey() {
        return survey;
    }
}
