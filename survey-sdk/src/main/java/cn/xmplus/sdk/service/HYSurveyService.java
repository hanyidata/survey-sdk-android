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


public class HYSurveyService extends AsyncTask<String, Void, List<Object>> {
    private SurveyTaskCallback taskCallback;

    public  HYSurveyService(SurveyTaskCallback callback) {
        this.taskCallback = callback;
    }

    @Override
    protected List<Object> doInBackground(String... strings) {
        String server = strings[0];
        String surveyId = strings[1];
        String channelId = strings[2];
        String accessCode = strings[3];
        String externalUserId =  strings.length > 3 ? strings[4] : "";
        try {
            String _url = String.format("%s/surveys/%s/embed?channelId=%s&accessCode=%s&externalUserId=%s", server, surveyId, channelId, accessCode, externalUserId);
            if (accessCode != null && !accessCode.isEmpty()) {
                _url = _url + String.format("&accessCode=%s", accessCode);
            }
            if (externalUserId != null && !externalUserId.isEmpty()) {
                _url = _url + String.format("&externalUserId=%s", externalUserId);
            }
            URL url = new URL(_url);
            Log.d("surveySDK", String.format("download config from %s %s %s", server, surveyId, channelId));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int status = urlConnection.getResponseCode();
            if (status >= 500) {
                urlConnection.disconnect();
                return Arrays.asList(null, "系统错误");
            }

            InputStream in = null;
            if (status >= 200 && status < 400) {
                in = urlConnection.getInputStream();
            } else {
                in = urlConnection.getErrorStream();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line+"\n");
            }
            String str = sb.toString();
            reader.close();
            urlConnection.disconnect();
            JSONObject response = new JSONObject(str);
            int code = response.getInt("code");
            if (code == 200) {
                JSONObject config = response.optJSONObject("data");
                return Arrays.asList(config, null);
            } else {
                return Arrays.asList(null, response.optString("message", "系统错误"));
            }

        } catch (Exception e) {
            Log.e("surveySDK", String.format("survey check request got error %s", e.getMessage()));
            return Arrays.asList(null, "系统错误");
        }
    }

    @Override
    protected void onPostExecute(List<Object> result) {
        if (result == null || result.size() != 2) {
            taskCallback.onConfigReady(null, "系统错误");
            return;
        }
        JSONObject config = (JSONObject) result.get(0);
        String error = (String) result.get(1);
        if (config != null) {
            if (config.optString("channelStatus").equals("PAUSE") || config.optString("surveyStatus").equals("STOPPED")) {
                Log.w("surveySDK", "survey stop or system issue");
                taskCallback.onConfigReady(null,"问卷停用");
            } else if (config.optBoolean("doNotDisturb")) {
                Log.w("surveySDK", "survey 免打扰屏蔽");
                taskCallback.onConfigReady(null,"免打扰屏蔽");
            } else {
                taskCallback.onConfigReady(config, null);
            }
        } else {
            taskCallback.onConfigReady(null, error);
        }
    }
}
