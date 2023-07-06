package cn.xmplus.demo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import cn.xmplus.sdk.HYPopupDialog
import cn.xmplus.sdk.HYSurveyView
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var survey: HYSurveyView? = null;
    private var padding: Int = 0;
    private var bord: Boolean = false;
    private var debug: Boolean = true;
    private var delay: Int = 3000;
    private var accessCode: String = "";
//    private var accessCode: String = "1126224225836916736";
    private var externalUserId: String = "";

    private var overrideOps: Boolean = true;
    // JLTEST
    private var surveyId: String = "4467958136180736";
    private var channelId: String = "4508090776587264";
    private var server: String = "https://jltest.xmplus.cn/api/survey";

//    // UAT
//    private var surveyId: String = "4475020361170944";
//    private var channelId: String = "4496490408345600";
//    private var server: String = "https://mktcs-uat.lynkco-test.com/api/survey";

//    // TEST
//    private var surveyId: String = "3937853687522304";
//    private var channelId: String = "3937854297465856";
//    private var server: String = "https://test.xmplus.cn/api/survey";

    fun handleClickEmbed(view: View) {

        var sid: String = if (!overrideOps) findViewById<EditText>(R.id.editTextSurveyId).text.toString() else surveyId;
        var cid: String = if (!overrideOps) findViewById<EditText>(R.id.editTextChannelId).text.toString() else channelId;
        var server: String = if (!overrideOps) findViewById<EditText>(R.id.editTextServer).text.toString() else server;
        var parameters = JSONObject();
        parameters.put("accessCode", accessCode);
        parameters.put("externalUserId", externalUserId);

        var options = JSONObject();
        options.put("debug", debug);
        options.put("bord", bord);
        options.put("delay", delay);
        options.put("padding", padding);
        options.put("server", server);

        var container:LinearLayout = findViewById(R.id.container)

        HYSurveyView.makeViewAsync(this, sid, cid, parameters, options, {
            this.survey = it as HYSurveyView?;
            container.addView(this.survey)
            this.survey?.setOnSubmit(::onSubmit);
            this.survey?.setOnCancel(::onCancel);
            this.survey?.setOnClose(::onClose);
            this.survey?.setOnSize(::onSize);
        }, {
            alert(it.toString());
        })
    }

    fun handleClickPopup(view: View) {
        var sid: String = if (!overrideOps) findViewById<EditText>(R.id.editTextSurveyId).text.toString() else surveyId;
        var cid: String = if (!overrideOps) findViewById<EditText>(R.id.editTextChannelId).text.toString() else channelId;
        var server: String = if (!overrideOps) findViewById<EditText>(R.id.editTextServer).text.toString() else server;
        var parameters = JSONObject();
        parameters.put("accessCode", accessCode);
        parameters.put("externalUserId", externalUserId);

        var options = JSONObject();
        options.put("debug", debug);
        options.put("bord", bord);
        options.put("delay", delay);
        options.put("padding", padding);
        options.put("server", server);
        var root = findViewById<View>(android.R.id.content)
        HYPopupDialog.makeDialog(root.context, sid, cid, parameters, options,  {
//                alert("取消");
            }, {
//                alert("提交");
            }, {
                alert(it.toString());
            },
        );
    }

    fun onSubmit(param: Any?) {
//        alert("已经提交")
    }

    fun onCancel(param: Any?) {
//        alert("取消了作答")
    }

    fun onSize(param: Any?) {
//        alert("高度变化");
        println(param);
    }

    fun onClose(param: Any?) {
//        alert("关闭")
    }

    fun alert(message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                //do things
            })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        handleClickPopup(window.decorView);
    }
}