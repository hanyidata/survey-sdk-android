package cn.xmplus.sdk.callback;

import cn.xmplus.sdk.data.SurveyStartResponse;

@FunctionalInterface
public interface SurveyTaskCallback {
    void onSurveyReady(SurveyStartResponse response);
}
