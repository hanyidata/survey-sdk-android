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
        List<Object> result = null;
        try {
            URL url = new URL(String.format("%s/surveys/%s/embed?channelId=%s&accessCode=%s", server, surveyId, channelId, accessCode));
            Log.d("surveySDK", String.format("download config from %s", server));
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

        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(List<Object> result) {
        if (result == null || result.size() != 2) {
            if (taskCallback != null) {
                taskCallback.onConfigReady(null, "系统错误");
            }
            return;
        }
        JSONObject config = (JSONObject) result.get(0);
        String error = (String) result.get(1);
        if (config != null && (config.optString("channelStatus").equals("STOPPED") || config.optString("surveyStatus").equals("STOPPED"))) {
            Log.w("surveySDK", "survey stop or system issue");
            if (taskCallback != null) {
                taskCallback.onConfigReady(null,"问卷停用");
            }
            return;
        }
        if (taskCallback != null) {
            taskCallback.onConfigReady(config, error);
        }
    }
}
