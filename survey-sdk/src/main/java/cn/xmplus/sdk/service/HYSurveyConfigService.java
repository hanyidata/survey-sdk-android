package cn.xmplus.sdk.service;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import cn.xmplus.sdk.HYGlobalConfig;
import cn.xmplus.sdk.callback.SurveyAuthCheckCallback;

/**
 * Survey Global Config Service
 */
public class HYSurveyConfigService extends AsyncTask<String, Void, List<Object>> {
    private SurveyAuthCheckCallback taskCallback;

    public HYSurveyConfigService(SurveyAuthCheckCallback callback) {
        this.taskCallback = callback;
    }

    @Override
    protected List<Object> doInBackground(String... strings) {
        String server = strings[0];
        String accessCode = strings[1];

        try {
            String _url = String.format("%s/surveys/init-access-code/%s", server, accessCode);
            if (!HYGlobalConfig.getOrgCode().isEmpty()) {
                _url = String.format("%s/surveys/init-access-code/%s/%s", server, HYGlobalConfig.getOrgCode(), accessCode);
            }
            URL url = new URL(_url);
            Log.d("surveySDK", String.format("pre check access code for %s", _url));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(HYGlobalConfig.getConnectionTimeout());
            urlConnection.setReadTimeout(HYGlobalConfig.getReadTimeout());
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
            Log.d("surveySDK", String.format("pre check result %s", response.toString()));
            if (code == 200) {
                boolean passed = response.optBoolean("data", false);
                return Arrays.asList(passed, null);
            } else {
                return Arrays.asList(false, response.optString("message", "系统错误"));
            }
        } catch (Exception e) {
            Log.e("surveySDK", String.format("survey check request got error %s", e.getMessage()));
            return Arrays.asList(null, "系统错误");
        }
    }

    @Override
    protected void onPostExecute(List<Object> result) {
        if (result == null || result.size() != 2) {
            taskCallback.onAuthChecked(false, "系统错误");
            return;
        }
        // 检查 result.get(0) 是否为 null，并且是否是 Boolean 类型
        Boolean authResult = (result.get(0) instanceof Boolean) ? (Boolean) result.get(0) : null;
        String message = (String) result.get(1);

        if (authResult == null) {
            taskCallback.onAuthChecked(false, "认证失败");
            return;
        }

        taskCallback.onAuthChecked(authResult, message);
    }
}