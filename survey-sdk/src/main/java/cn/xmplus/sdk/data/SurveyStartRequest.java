package cn.xmplus.sdk.data;

import java.util.HashMap;
import java.util.Map;

public class SurveyStartRequest {
    private String server;
    private String surveyId;
    private String channelId;
    private String sendId;

    private Map<String, Object> parameters = new HashMap<>();

    public SurveyStartRequest(String server, String surveyId, String channelId, String sendId) {
        this.server = server;
        this.surveyId = surveyId;
        this.channelId = channelId;
        this.sendId = sendId;
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
}
