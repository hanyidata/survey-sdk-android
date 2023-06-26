package cn.xmplus.sdk.service;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.xmplus.sdk.callback.SurveyTaskCallback;

/**
 * Survey Service to fetch config
 */


public class HYSurveyService extends AsyncTask<String, Void, JSONObject> {
    private SurveyTaskCallback taskCallback;

    public  HYSurveyService(SurveyTaskCallback callback) {
        this.taskCallback = callback;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String server = strings[0];
        String surveyId = strings[1];
        String channelId = strings[2];
        try {
            URL url = new URL(String.format("%s/surveys/%s/embed?channelId=%s", server, surveyId, channelId));
            Log.d("surveySDK", String.format("download config from %s", server));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int status = urlConnection.getResponseCode();
            if (status != 200) {
                urlConnection.disconnect();
                return null;
            }

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
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
            JSONObject config = response.optJSONObject("data");
            return config;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(JSONObject config) {
        if (config == null || config.optString("channelStatus").equals("STOPPED") || config.optString("surveyStatus").equals("STOPPED")) {
            Log.w("surveySDK", "survey stop or system issue");
            if (taskCallback != null) {
                taskCallback.onConfigReady(null,"问卷停用");
            }
        }  else if (taskCallback != null) {
            taskCallback.onConfigReady(config, null);
        }
    }
}
