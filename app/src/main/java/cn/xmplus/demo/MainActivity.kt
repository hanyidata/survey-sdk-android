package cn.xmplus.demo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
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
//    private var delay: Int = 3000;
    private var accessCode: String = "";
//    private var accessCode: String = "1128430492441440256";

    private var overrideOps: Boolean = false;
    // JLTEST
    private var surveyId: String = "4445329530320896";
    private var channelId: String = "4446931357162496";
    private var server: String = "https://jltest.xmplus.cn/api/survey";

//    // UAT
//    private var surveyId: String = "4475002070663168";
//    private var channelId: String = "4475389028433920";
//    private var server: String = "https://mktcs-uat.lynkco-test.com/api/survey";

//    private var surveyId: String = "4538358709728256";
//    private var channelId: String = "4538360831580160";
//    private var server: String = "https://mktcs.lynkco.com/api/survey";

//    // TEST
//    private var surveyId: String = "3937853687522304";
//    private var channelId: String = "3937854297465856";
//    private var server: String = "https://test.xmplus.cn/api/survey";

    fun handleClickEmbed(view: View) {

        var sid: String = if (!overrideOps) findViewById<EditText>(R.id.editTextSurveyId).text.toString() else surveyId;
        var cid: String = if (!overrideOps) findViewById<EditText>(R.id.editTextChannelId).text.toString() else channelId;
        var ser: String = if (!overrideOps) getServer() else server;
        var code: String = if (!overrideOps) findViewById<EditText>(R.id.editTextAccessCode).text.toString() else accessCode;

        var parameters = JSONObject();
        parameters.put("accessCode", code);

        var options = JSONObject();
        options.put("debug", debug);
        options.put("bord", bord);
//        options.put("delay", delay);
        options.put("padding", padding);
        options.put("server", ser);

        var container:LinearLayout = findViewById(R.id.container)
//        container.setPadding(10);

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
        var ser: String = if (!overrideOps) getServer() else server;
        var code: String = if (!overrideOps) findViewById<EditText>(R.id.editTextAccessCode).text.toString() else accessCode;

        var parameters = JSONObject();
        parameters.put("accessCode", code);

        var options = JSONObject();
        options.put("debug", debug);
        options.put("bord", bord);
//        options.put("delay", delay);
        options.put("padding", padding);
        options.put("server", ser);
        var root = findViewById<View>(android.R.id.content)
        HYPopupDialog.makeDialog(
            root.context, sid, cid, parameters, options,
            {
//                alert("取消");
            },
            {
//                alert("提交");
            },
            {
                alert(it.toString());
            },
        );
    }

    fun onSubmit(param: Any?) {
//        alert("已经提交")
    }

    fun onCancel(param: Any?) {
//        alert("取消了作答")
//        var container:LinearLayout = findViewById(R.id.container)
//        container.layoutParams.height = 0;
    }

    fun onSize(param: Any?) {
//        alert("高度变化");
        var container:LinearLayout = findViewById(R.id.container)
        container.layoutParams.height = (param as Int);
        println(param);
    }

    fun onClose(param: Any?) {
//        alert("关闭")
//        var container:LinearLayout = findViewById(R.id.container)
//        container.layoutParams.height = 0;
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
        findViewById<CheckBox>(R.id.checkBoxJLTEST).setOnClickListener { onCheckboxClick(it) };
        findViewById<CheckBox>(R.id.checkBoxUAT).setOnClickListener { onCheckboxClick(it) };
        findViewById<CheckBox>(R.id.checkBoxPROD).setOnClickListener { onCheckboxClick(it) };
    }

    private fun getServer(): String {
        if (findViewById<CheckBox>(R.id.checkBoxJLTEST).isChecked) {
            return "https://jltest.xmplus.cn/api/survey"
        } else if (findViewById<CheckBox>(R.id.checkBoxUAT).isChecked) {
            return "https://mktcs-uat.lynkco-test.com/api/survey"
        } else if (findViewById<CheckBox>(R.id.checkBoxPROD).isChecked) {
            return "https://mktcs.lynkco.com/api/survey"
        }
        return "";
    }

    private fun onCheckboxClick(view: View?) {
        Log.d("example", "checkbox");
        if (view!!.id == R.id.checkBoxJLTEST) {
            findViewById<CheckBox>(R.id.checkBoxUAT).isChecked = false;
            findViewById<CheckBox>(R.id.checkBoxPROD).isChecked = false;
        } else if (view!!.id == R.id.checkBoxUAT) {
            findViewById<CheckBox>(R.id.checkBoxJLTEST).isChecked = false;
            findViewById<CheckBox>(R.id.checkBoxPROD).isChecked = false;
        } else if (view!!.id == R.id.checkBoxPROD) {
            findViewById<CheckBox>(R.id.checkBoxUAT).isChecked = false;
            findViewById<CheckBox>(R.id.checkBoxJLTEST).isChecked = false;
        }

    }


}
