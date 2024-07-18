package cn.xmplus.demo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
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
    private var bord: Boolean = true;
    private var debug: Boolean = true;
    private var halfscreen: Boolean = false;
    private var delay: Int = 1000;
    private var accessCode: String = "";
    private var euid: String = "";

    // TEST
    private var surveyId: String = "6616146990215168";
    private var channelId: String = "6616147424260096";
    private var sendId: String = "";
    private var server: String = "https://test.xmplus.cn/api/survey";

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.d("demo", "onConfigurationChanged")
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 竖屏
        }
        // 处理屏幕方向变化的逻辑
    }

    fun handleCloseDialog(view: View)  {
        HYPopupDialog.close();
    }

    fun handleClickEmbed(view: View) {

        var sid: String = findViewById<EditText>(R.id.editTextSurveyId).text.toString();
        var cid: String = findViewById<EditText>(R.id.editTextChannelId).text.toString();
        var ser: String = getServer();
        var code: String = findViewById<EditText>(R.id.editTextAccessCode).text.toString();
        var euid: String = findViewById<EditText>(R.id.editTextEUID).text.toString();
        var sdid: String = findViewById<EditText>(R.id.editTextSendId).text.toString();
        var lang: String = findViewById<EditText>(R.id.editTextLang).text.toString();

        var parameters = JSONObject();
        parameters.put("accessCode", code);
        if (euid.isNotEmpty()) {
            parameters.put("externalUserId", euid);
        }

        var options = JSONObject();
        options.put("lang", lang);
        options.put("debug", debug);
        options.put("bord", bord);
//        options.put("delay", delay);
        options.put("padding", padding);
        options.put("server", ser);
        options.put("delay", delay);
        options.put("halfscreen", findViewById<CheckBox>(R.id.checkBoxHalfScreen).isChecked);
        options.put("project", getProject());

        var container:LinearLayout = findViewById(R.id.container)
        val displayMetrics: DisplayMetrics = this.getResources().getDisplayMetrics()
        val screenWidth = displayMetrics.widthPixels
        if (findViewById<CheckBox>(R.id.checkBoxHalfScreen).isChecked) {
            container.layoutParams = LinearLayout.LayoutParams(screenWidth / 2, 0);
        } else {
            container.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        }

        if (sdid.length > 0) {
            HYSurveyView.makeViewAsyncBySendId(this, sdid, parameters, options, {
                this.survey = it as HYSurveyView?;
                container.addView(this.survey)
                this.survey?.setOnSubmit(::onSubmit);
                this.survey?.setOnCancel(::onCancel);
                this.survey?.setOnClose(::onClose);
                this.survey?.setOnLoad(::onLoad);
                this.survey?.setOnSize(::onSize);

//            this.survey?.setBackgroundColor(Color.RED);

            }, {
                alert(it.toString());
            })
        } else {
            HYSurveyView.makeViewAsync(this, sid, cid, parameters, options, {
                this.survey = it as HYSurveyView?;
                container.addView(this.survey)
                this.survey?.setOnSubmit(::onSubmit);
                this.survey?.setOnCancel(::onCancel);
                this.survey?.setOnClose(::onClose);
                this.survey?.setOnLoad(::onLoad);
                this.survey?.setOnSize(::onSize);

//            this.survey?.setBackgroundColor(Color.RED);

            }, {
                alert(it.toString());
            })
        }

    }

    fun handleClickPopup(view: View) {
        var sid: String = findViewById<EditText>(R.id.editTextSurveyId).text.toString();
        var cid: String = findViewById<EditText>(R.id.editTextChannelId).text.toString();
        var ser: String = getServer();
        var code: String = findViewById<EditText>(R.id.editTextAccessCode).text.toString();
        var euid: String = findViewById<EditText>(R.id.editTextEUID).text.toString();
        var sdid: String = findViewById<EditText>(R.id.editTextSendId).text.toString();
        var lang: String = findViewById<EditText>(R.id.editTextLang).text.toString();

        var parameters = JSONObject();
        parameters.put("accessCode", code);
        if (euid.isNotEmpty()) {
            parameters.put("externalUserId", euid);
        }

        var options = JSONObject();
        options.put("lang", lang);
        options.put("clickDismiss", findViewById<CheckBox>(R.id.checkBoxLynkco).isChecked);
        options.put("debug", debug);
        options.put("bord", bord);
//        options.put("delay", delay);
        options.put("padding", padding);
        options.put("server", ser);
        options.put("delay", delay);
//        options.put("halfscreen", findViewById<CheckBox>(R.id.checkBoxHalfScreen).isChecked);
        options.put("project", getProject());

        var root = findViewById<View>(android.R.id.content)
        if (sdid.length > 0) {
            HYPopupDialog.makeDialogBySendId(
                root, sdid, parameters, options,
                {
                    onCancel(null);
                },
                {
                    onSubmit(null);
                },
                {
                    alert("发生错误 $it");
                }, {
                    Log.d("surveyExample", "onLoad")
                }
            );
        } else {
            HYPopupDialog.makeDialog(
                root, sid, cid, parameters, options,
                {
                    onCancel(null);
                },
                {
                    onSubmit(null);
                },
                {
                    Log.d("surveyExample ", "发生错误 $it")
                }, {
                    Log.d("surveyExample", "onLoad")
                }
            );

//            HYPopupDialog.makeDialog(
//                root, sid, cid, parameters, options,
//                {
//                    onCancel(null);
//                },
//                {
//                    onSubmit(null);
//                },
//                {
//                    Log.d("surveyExample ", "发生错误 $it")
//                }, {
//                    Log.d("surveyExample", "onLoad")
//                }
//            );

        }

    }



    fun onSubmit(param: Any?) {
        alert("已经提交")
    }

    fun onLoad(param: Any?) {
        Log.d("surveyExample", "onLoad")
    }


    fun onCancel(param: Any?) {
        var container:LinearLayout = findViewById(R.id.container)
        container.layoutParams.height = 0;
        alert("取消了作答")
    }

    fun onSize(param: Any?) {
//        alert("高度变化");
        Log.d("surveyExample", "onSize")
        var container:LinearLayout = findViewById(R.id.container)
        container.layoutParams.height = (param as Int);
        println(param);
    }

    fun onClose(param: Any?) {
//        alert("关闭")
        var container:LinearLayout = findViewById(R.id.container)
        container.layoutParams.height = 0;

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

        if (server == "https://test.xmplus.cn/api/survey") {
            findViewById<CheckBox>(R.id.checkBoxTEST).isChecked = true;
//            findViewById<CheckBox>(R.id.checkBoxUAT).isChecked = false;
            findViewById<CheckBox>(R.id.checkBoxPROD).isChecked = false;
//            findViewById<CheckBox>(R.id.checkBoxJPROD).isChecked = false;
        } else if (server == "https://mktcs-uat.lynkco-test.com/api/survey") {
//            findViewById<CheckBox>(R.id.checkBoxUAT).isChecked = true;
            findViewById<CheckBox>(R.id.checkBoxPROD).isChecked = false;
            findViewById<CheckBox>(R.id.checkBoxTEST).isChecked = false;
            findViewById<CheckBox>(R.id.checkBoxPROD).isChecked = false;
        } else if (server == "https://mktcs.lynkco.com/api/survey") {
//            findViewById<CheckBox>(R.id.checkBoxJPROD).isChecked = true;
            findViewById<CheckBox>(R.id.checkBoxPROD).isChecked = false;
//            findViewById<CheckBox>(R.id.checkBoxUAT).isChecked = false;
            findViewById<CheckBox>(R.id.checkBoxTEST).isChecked = false;
        } else if (server == "https://www.xmplus.cn/api/survey") {
            findViewById<CheckBox>(R.id.checkBoxPROD).isChecked = true;
//            findViewById<CheckBox>(R.id.checkBoxUAT).isChecked = false;
            findViewById<CheckBox>(R.id.checkBoxTEST).isChecked = false;
//            findViewById<CheckBox>(R.id.checkBoxJPROD).isChecked = false;
        }

        findViewById<EditText>(R.id.editTextSurveyId).setText(surveyId);
        findViewById<EditText>(R.id.editTextChannelId).setText(channelId);
        findViewById<EditText>(R.id.editTextAccessCode).setText(accessCode);
        findViewById<CheckBox>(R.id.checkBoxHalfScreen).isChecked = halfscreen;
        findViewById<EditText>(R.id.editTextEUID).setText(euid);
        findViewById<EditText>(R.id.editTextSendId).setText(sendId);


//        handleClickPopup(window.decorView);
        findViewById<CheckBox>(R.id.checkBoxTEST).setOnClickListener { onCheckboxClick(it) };
//        findViewById<CheckBox>(R.id.checkBoxUAT).setOnClickListener { onCheckboxClick(it) };
        findViewById<CheckBox>(R.id.checkBoxPROD).setOnClickListener { onCheckboxClick(it) };

//        handleClickEmbed(view = findViewById<EditText>(R.id.editTextSurveyId));
    }

    private fun getServer(): String {
        if (findViewById<CheckBox>(R.id.checkBoxTEST).isChecked) {
            return "https://test.xmplus.cn/api/survey"
//        } else if (findViewById<CheckBox>(R.id.checkBoxUAT).isChecked) {
//            return "https://mktcs-uat.lynkco-test.com/api/survey"
//        } else if (findViewById<CheckBox>(R.id.checkBoxJPROD).isChecked) {
//            return "https://mktcs.lynkco.com/api/survey"
        } else if (findViewById<CheckBox>(R.id.checkBoxPROD).isChecked) {
            return "https://www.xmplus.cn/api/survey"
        }
        return "";
    }

    private fun getProject(): String? {
//        if (findViewById<CheckBox>(R.id.checkBoxLynkco).isChecked) {
//            return "lynkco";
//        }
        return null;
    }

    private fun onCheckboxClick(view: View?) {
        Log.d("example", "checkbox");
        if (view!!.id == R.id.checkBoxTEST) {
//            findViewById<CheckBox>(R.id.checkBoxUAT).isChecked = false;
            findViewById<CheckBox>(R.id.checkBoxPROD).isChecked = false;
//        } else if (view!!.id == R.id.checkBoxUAT) {
//            findViewById<CheckBox>(R.id.checkBoxTEST).isChecked = false;
//            findViewById<CheckBox>(R.id.checkBoxPROD).isChecked = false;
        } else if (view!!.id == R.id.checkBoxPROD) {
//            findViewById<CheckBox>(R.id.checkBoxUAT).isChecked = false;
            findViewById<CheckBox>(R.id.checkBoxTEST).isChecked = false;
        }

    }


}
