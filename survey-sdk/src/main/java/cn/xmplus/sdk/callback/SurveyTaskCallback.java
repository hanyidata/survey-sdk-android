package cn.xmplus.sdk.callback;

import org.json.JSONObject;

@FunctionalInterface
public interface SurveyTaskCallback {
    void onConfigReady(JSONObject config, String error);
}
