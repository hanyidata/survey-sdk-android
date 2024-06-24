package cn.xmplus.sdk.callback;

import org.json.JSONObject;

@FunctionalInterface
public interface SurveyTaskCallback {
    void onConfigReady(String sid, String cid, JSONObject config, String error);
}
