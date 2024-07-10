package cn.xmplus.sdk.service;

import android.os.AsyncTask;
import android.util.JsonWriter;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import cn.xmplus.sdk.callback.SurveyTaskCallback2;
import cn.xmplus.sdk.data.SurveyStartRequest;
import cn.xmplus.sdk.data.SurveyStartResponse;
import kotlin.NotImplementedError;


/**
 * Survey Service to union start
 */
public class HYSurveyBaseService2 extends AsyncTask<SurveyStartRequest, Void, SurveyStartResponse> {
    protected SurveyTaskCallback2 taskCallback;
    protected List<String> parameters = new ArrayList<>();
    static List<String> systemParametersWhiteList = Arrays.asList("externalUserId", "departmentCode", "externalCompanyId", "customerName", "customerGender");

    public HYSurveyBaseService2(SurveyTaskCallback2 callback) {
        this.taskCallback = callback;
    }

    @Override
    protected SurveyStartResponse doInBackground(SurveyStartRequest... params) {
        SurveyStartRequest param = params[0];
        String clientId = UUID.randomUUID().toString();

        String _url = String.format("%s/surveys/union-start", param.getServer());
        HashMap<String, Object> data = new HashMap<>();
        // 优先使用sendId
        if (param.getSendId() != null && !param.getSendId().isEmpty()) {
            data.put("sendToken", param.getSendId());
        } else {
            if (param.getSurveyId() != null && !param.getSurveyId().isEmpty()) {
                data.put("surveyId", param.getSurveyId());
            }
            if (param.getChannelId() != null && !param.getChannelId().isEmpty()) {
                data.put("channelId", param.getChannelId());
            }
        }

        // 处理系统变量
        if (param.getParameters() != null) {
            Iterator<String> keys = param.getParameters().keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (systemParametersWhiteList.contains(key)) {
                    data.put(key, param.getParameters().opt(key));
                }
            };
            if (param.getParameters().has("parameters")) {
                data.put("parameters", param.getParameters().opt("parameters"));
            }
        }

        data.put("clientId", clientId);
        data.put("collectorMethod", "APP");

        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            urlConnection.setRequestProperty("Accept", "application/json");

            // Write the JSON data to the output stream
            try (BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream())) {
                JSONObject jsonInput = new JSONObject(data);
                String jsonInputString = jsonInput.toString();
                // Write the JSON data to the output stream
                byte[] input = jsonInputString.getBytes("utf-8");
                Log.d("surveySDK", String.format("doInBackground: post data %s", jsonInputString));
                out.write(input, 0, input.length);
                out.flush();
            }

            int statusCode = urlConnection.getResponseCode();
            JSONObject json = null;
            JSONObject deepData = null;

            // Read the response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = in.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                json = new JSONObject(response.toString());
                deepData = json.optJSONObject("data");
            }
            if (json == null) {
                return new SurveyStartResponse("系统错误");
            }
//            String surveyStatus = json.getString("status");
//            boolean doNotDisturb = json.getBoolean("doNotDisturb");
            if (statusCode != 200) {
                return new SurveyStartResponse(json.optString("message", "系统错误"));
            }
            if (deepData != null) {
                return SurveyStartResponse.fromJson(deepData);
            } else {
                return new SurveyStartResponse("系统错误");
            }
        } catch (Exception e) {
            return new SurveyStartResponse("网络错误");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(SurveyStartResponse response) {
        taskCallback.onSurveyReady(response);
    }
}
