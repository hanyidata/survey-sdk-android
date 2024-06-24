package cn.xmplus.sdk.service;

import android.util.Log;

import org.json.JSONObject;


import java.util.List;

import cn.xmplus.sdk.callback.SurveyTaskCallback;

/**
 * Survey Service to sender config (发送管理)
 */


public class HYSurveySendService extends HYSurveyBaseService<SurveyTaskCallback> {

    public HYSurveySendService(SurveyTaskCallback callback) {
        super(callback);
    }

    @Override
    protected String getUrl(String... strings) {
        String server = strings[0];
        String sendId = strings[1];
        String _url = String.format("%s/surveys/send-manage/%s", server, sendId);
        return _url;
    }

    /**
     * response
     *  -data
     *      surveyId:
     *      channelId:
     * @param result
     */
    @Override
    protected void onPostExecute(List<Object> result) {
        if (result == null || result.size() != 2) {
            taskCallback.onConfigReady(null, null, null,"系统错误");
            return;
        }
        JSONObject config = (JSONObject) result.get(0);
        String error = (String) result.get(1);
        if (config != null) {
            String sid = config.optString("surveyId", null);
            String cid = config.optString("channelId", null);

            if (sid != null && cid != null) {
                // server, sendId, accessCode, externalUserId
                String server = this.parameters.get(0);
                String accessCode = this.parameters.get(2);
                String externalUserId =  this.parameters.size() > 2 ? this.parameters.get(3) : "";
                Log.w("surveySDK", "survey send use sid: " + sid + " cid: " + cid);
                new HYSurveyService(taskCallback).execute(server, sid, cid, accessCode, externalUserId);
            } else {
                Log.w("surveySDK", "survey error missing sid or cid");
                taskCallback.onConfigReady(sid, cid, null, "系统错误");
            }
        } else {
            taskCallback.onConfigReady(null, null, null, error);
        }
    }
}
