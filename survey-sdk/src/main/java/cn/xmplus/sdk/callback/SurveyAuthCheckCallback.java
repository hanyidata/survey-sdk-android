package cn.xmplus.sdk.callback;

@FunctionalInterface
public interface SurveyAuthCheckCallback {
    void onAuthChecked(boolean pass, String error);
}