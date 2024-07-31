package cn.xmplus.sdk;

import android.util.Log;

import cn.xmplus.sdk.service.HYSurveyConfigService;

public class HYGlobalConfig {
    private static String accessCode;
    private static int connectionTimeout = 5000;
    private static int readTimeout = 5000;

    private static String server = "https://www.xmplus.cn/api/survey";
    private static String project = "";

    private static boolean authRequired = false;
    private static boolean verified = false;

    public static String getAccessCode() {
        return accessCode;
    }

    public static String getServer() { return server; }
    public static String getProject() { return project; }

    public static int getConnectionTimeout() { return connectionTimeout; }
    public static int getReadTimeout() { return readTimeout; }

    public static void setConnectionTimeout(int val) { connectionTimeout = val; }
    public static void setReadTimeout(int val) { readTimeout = val; }

    /**
     * global server setup
     * @param _server
     */
    public static void setup(String _server) {
        server = _server;
    }

    /**
     * global server project
     * @param _project
     */
    public static void setProject(String _project) {
        project = _project;
    }

    /**
     * global auth config
     * @param _server, the server url
     * @param _access_code, the third party access code pass global
     * @param _auth_required, true means survey will require access code, survey will not be displayed until setup a valid code
     */
    public static void setup(String _server, String _access_code, boolean _auth_required) {
        server = _server;
        accessCode = _access_code;
        authRequired = _auth_required;

        if (authRequired && !accessCode.isEmpty()) {
            Log.d("surveySDK", "auth required on will pre check access code");
            new HYSurveyConfigService((boolean pass, String error) -> {
                Log.d("surveySDK", String.format("access code pre check pass? %s  error: %s", pass, error));
                verified = pass;
            }).execute(server, accessCode);
        }
    }

    /**
     * reset global
     */
    public static void resetAuthCache() {
        accessCode = "";
        authRequired = false;
    }

    /**
     * pre check, if auth required and access code is not verified will be rejected
     * @return
     */
    public static boolean check() {
        if (authRequired && !verified) {
            return false;
        }
        return true;
    }
}