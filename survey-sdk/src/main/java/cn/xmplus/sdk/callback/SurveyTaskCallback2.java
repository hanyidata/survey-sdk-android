package cn.xmplus.sdk.callback;

import org.json.JSONObject;

import cn.xmplus.sdk.data.SurveyStartResponse;

@FunctionalInterface
public interface SurveyTaskCallback2 {
    void onSurveyReady(SurveyStartResponse response);
}
