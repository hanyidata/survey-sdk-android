package cn.xmplus.sdk.data;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SurveyStartRequest {
    private String server;
    private String surveyId;
    private String channelId;
    private String sendId;

    private JSONObject parameters = new JSONObject();

    public SurveyStartRequest(String server, String surveyId, String channelId, String sendId, JSONObject parameters) {
        this.server = server;
        this.surveyId = surveyId;
        this.channelId = channelId;
        this.sendId = sendId;
        this.parameters = parameters;
    }

    public String getServer() {
        return server;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getSendId() {
        return sendId;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public JSONObject getParameters() {
        return parameters;
    }
}
