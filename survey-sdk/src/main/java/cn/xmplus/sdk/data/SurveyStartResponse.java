package cn.xmplus.sdk.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SurveyStartResponse {
    private String surveyId;
    private String error;
    private String channelId;
    private String clientId;
    private JSONObject survey = new JSONObject();
    private JSONObject channel = new JSONObject();
    private JSONObject channelConfig = null;

    public SurveyStartResponse(String error) {
        this.error = error;
    }

    public SurveyStartResponse(String _surveyId, String _channelId, String _clientId, JSONObject survey, JSONObject channel) {
        this.surveyId = _surveyId;
        this.channelId = _channelId;
        this.clientId = _clientId;
        this.survey = survey;
        this.channel = channel;
        if (channel.has("configure")) {
            try {
                this.channelConfig = new JSONObject(channel.optString("configure", "{}"));
            } catch (JSONException e) {
                Log.e("surveySDK", "unexpected survey channel config");
            }
        }
    }

    public static SurveyStartResponse fromJson(String clientId, JSONObject surveyJson) {
        String surveyId = surveyJson.optString("id");
        JSONObject channel = surveyJson.optJSONObject("channel");
        String channelId = channel.optString("id", null);
        return new SurveyStartResponse(surveyId, channelId, clientId, surveyJson, channel);
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

    public String getClientId() {
        return clientId;
    }

    public JSONObject getChannelConfig() {
        return channelConfig;
    }

    public JSONObject getSurvey() {
        return survey;
    }
}
