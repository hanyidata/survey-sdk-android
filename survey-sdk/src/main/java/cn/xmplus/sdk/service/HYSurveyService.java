package cn.xmplus.sdk.service;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import cn.xmplus.sdk.HYGlobalConfig;
import cn.xmplus.sdk.callback.SurveyTaskCallback;
import cn.xmplus.sdk.data.SurveyStartRequest;
import cn.xmplus.sdk.data.SurveyStartResponse;


/**
 * Survey Service to union start
 */
public class HYSurveyService extends AsyncTask<SurveyStartRequest, Void, SurveyStartResponse> {
    protected SurveyTaskCallback taskCallback;
    protected List<String> parameters = new ArrayList<>();
    static List<String> systemParametersWhiteList = Arrays.asList("externalUserId", "departmentCode", "externalCompanyId", "customerName", "customerGender");

    public HYSurveyService(SurveyTaskCallback callback) {
        this.taskCallback = callback;
    }

    @Override
    protected SurveyStartResponse doInBackground(SurveyStartRequest... params) {
        SurveyStartRequest param = params[0];
        String clientId = UUID.randomUUID().toString();

        String _url = String.format("%s/surveys/union-start", param.getServer());
        HashMap<String, Object> data = new HashMap<>();
        String accessCode = param.getParameters().optString("accessCode", HYGlobalConfig.getAccessCode());

        // accessCode 优先使用参数，fallback到全局, 如果和全局一样忽律二次传入避免再次发生服务调用
        if (accessCode != null && !accessCode.isEmpty() && !accessCode.equals(HYGlobalConfig.getAccessCode())) {
            HashMap<String, Object> additionData = new HashMap<>();
            additionData.put("accessCode", accessCode);
            data.put("additionData", additionData);
        }

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
        Log.d("surveySDK", String.format("union start %s %s", _url, clientId));

        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            urlConnection.setRequestProperty("Accept", "application/json");

            // Write the JSON data to the output stream
            try (BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream())) {
                JSONObject jsonInput = new JSONObject(data);
                String jsonInputString = jsonInput.toString();
                // Write the JSON data to the output stream
                byte[] input = jsonInputString.getBytes("utf-8");
                Log.d("surveySDK", String.format("doInBackground: post data %s %s", _url, jsonInputString));
                out.write(input, 0, input.length);
                out.flush();
            }

            int statusCode = urlConnection.getResponseCode();
            JSONObject json = null;
            JSONObject deepData = null;

            BufferedReader in;
            if (statusCode == 200) {
                in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
            }

            // Read the response
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine.trim());
            }
            json = new JSONObject(response.toString());
            deepData = json.optJSONObject("data");
            if (json == null) {
                return new SurveyStartResponse("系统错误");
            }
            if (deepData != null) {
                return SurveyStartResponse.fromJson(clientId, deepData);
            } else {
                String message = json.optString("message", "系统错误");
                Log.d("surveySDK", String.format("union start failed %s", message));
                return new SurveyStartResponse(message);
            }
        } catch (Exception e) {
            Log.d("surveySDK", String.format("union start system failed %s", e.getMessage()));
            e.printStackTrace();
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
