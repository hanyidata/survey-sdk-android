package cn.xmplus.sdk.service;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.xmplus.sdk.callback.SurveyTaskCallback;
import kotlin.NotImplementedError;

/**
 * Survey Service to sender config (发送管理)
 */


public class HYSurveyBaseService<T> extends AsyncTask<String, Void, List<Object>> {
    protected T taskCallback;
    protected List<String> parameters = new ArrayList<>();

    public HYSurveyBaseService(T callback) {
        this.taskCallback = callback;
    }

    protected String getUrl(String... strings) {
        return "";
    }

    @Override
    protected List<Object> doInBackground(String... strings) {
        String server = strings[0];
        String sendId = strings[1];
        this.parameters = Arrays.asList(strings);

        try {
            String _url = this.getUrl(strings);
            URL url = new URL(_url);
            Log.d("surveySDK", String.format("fetch send info from %s send id", server, sendId));
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
        throw new NotImplementedError();
    }
}
