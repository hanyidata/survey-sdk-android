package cn.xmplus.sdk.service;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.xmplus.sdk.callback.SurveyTaskCallback;

import java.util.Arrays;
import java.util.List;

/**
 * Survey Service to fetch config
 */


public class HYSurveyService extends HYSurveyBaseService<SurveyTaskCallback> {

    public  HYSurveyService(SurveyTaskCallback callback) {
        super(callback);
    }

    @Override
    protected String getUrl(String... strings) {
        String server = strings[0];
        String surveyId = strings[1];
        String channelId = strings[2];
        String accessCode = strings[3];
        String externalUserId =  strings.length > 3 ? strings[4] : "";
        String _url = String.format("%s/surveys/%s/embed?channelId=%s&accessCode=%s&externalUserId=%s", server, surveyId, channelId, accessCode, externalUserId);
        return _url;
    }

    @Override
    protected void onPostExecute(List<Object> result) {
        if (this.parameters.size() <= 3 || result == null || result.size() != 2) {
            taskCallback.onConfigReady(null, null, null, "系统错误");
            return;
        }
        String surveyId = this.parameters.get(1);
        String channelId = this.parameters.get(2);
        JSONObject config = (JSONObject) result.get(0);
        String error = (String) result.get(1);
        if (config != null) {
            if (config.optString("channelStatus").equals("PAUSE") || config.optString("surveyStatus").equals("STOPPED")) {
                Log.w("surveySDK", "survey stop or system issue");
                taskCallback.onConfigReady(surveyId, channelId, null,"问卷停用");
            } else if (config.optBoolean("doNotDisturb")) {
                Log.w("surveySDK", "survey 免打扰屏蔽");
                taskCallback.onConfigReady(surveyId, channelId, null,"免打扰屏蔽");
            } else {
                taskCallback.onConfigReady(surveyId, channelId, config, null);
            }
        } else {
            taskCallback.onConfigReady(surveyId, channelId, null, error);
        }
    }
}
