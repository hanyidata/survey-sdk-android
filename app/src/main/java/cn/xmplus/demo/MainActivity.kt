package cn.xmplus.demo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cn.xmplus.sdk.HYGlobalConfig
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
//    private var accessCode: String = "";
//    private var euid: String = "";
//    private var accessCode: String = "1128430492441440256";

    // JLTEST
//    private var surveyId: String = "4445329530320896";
//    private var channelId: String = "4446931357162496";
//    private var server: String = "https://jltest.xmplus.cn/api/survey";

//    // UAT
//    private var surveyId: String = "4475002070663168";
//    private var channelId: String = "4475389028433920";
//    private var server: String = "https://mktcs-uat.lynkco-test.com/api/survey";

//    private var surveyId: String = "4538358709728256";
//    private var channelId: String = "4538360831580160";
//    private var server: String = "https://mktcs.lynkco.com/api/survey";

    // TEST
//    private var sendId: String = "BddfddRImjktRzRk";
    private var accessCode: String = "";
    private var euid: String = "";
    private var parameter: String = ""
//    private var parameter: String = "{\"externalUserId\":\"152205\",\"parameters\":{\"cancelTime\":\"2014年2月1日\",\"orderNo\":\"888888888\",\"orderPrice\":\"1,500\"}}"
    // TEST
    private var surveyId: String = "6829192408645632";
    private var channelId: String = "6880930353772544";
    private var sendId: String = "";
    private var serverId: Int = R.id.checkBoxPROD
    val SERVERMAP: Map<Int, String> = mapOf(
        R.id.checkBoxJLT to "https://jltest.xmplus.cn/api/survey",
        R.id.checkBoxJLU to "https://mktcs-uat.lynkco-test.com/api/survey",
        R.id.checkBoxJLP to "https://mktcs.lynkco.com/api/survey",
        R.id.checkBoxTEST to "https://test.xmplus.cn/api/survey",
        R.id.checkBoxPROD to "https://www.xmplus.cn/api/survey"
    )
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
//        HYPopupDialog.close();
        // 创建Intent并启动ProductListActivity
        SurveyOptions.sid = findViewById<EditText>(R.id.editTextSurveyId).text.toString();
        SurveyOptions.cid = findViewById<EditText>(R.id.editTextChannelId).text.toString();
        SurveyOptions.sdid = findViewById<EditText>(R.id.editTextSendId).text.toString();
        SurveyOptions.options = buildOptions();
        SurveyOptions.parameters = getParam();

        val intent = Intent(this, DemoListActivity::class.java)
        startActivity(intent)
    }

    fun getParam(): JSONObject {
        var text: String = findViewById<EditText>(R.id.editTextEUID).text.toString();
        if (text.isEmpty()) {
            return JSONObject();
        }
        try {
            return JSONObject(text)
        } catch (ex: java.lang.Exception) {
            alert("invalid json params")
            return JSONObject();
        }
    }

    fun handleClickEmbed(view: View) {

        var sid: String = findViewById<EditText>(R.id.editTextSurveyId).text.toString();
        var cid: String = findViewById<EditText>(R.id.editTextChannelId).text.toString();
        var ser: String = getServer();
//        var code: String = findViewById<EditText>(R.id.editTextParams).text.toString();
//        var euid: String = findViewById<EditText>(R.id.editTextEUID).text.toString();
        var sdid: String = findViewById<EditText>(R.id.editTextSendId).text.toString();
        var lang: String = findViewById<EditText>(R.id.editTextLang).text.toString();

        var parameters = getParam();
        var options = buildOptions();
        if (parameters == null) {
            return
        }

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

    fun buildOptions(): JSONObject {
        var ser: String = getServer();
        var lang: String = findViewById<EditText>(R.id.editTextLang).text.toString();

        var options = JSONObject();
        options.put("language", lang);
        options.put("lang", lang);
        options.put("clickDismiss", findViewById<CheckBox>(R.id.checkBoxDismiss).isChecked);
        options.put("debug", debug);
        options.put("bord", bord);
//        options.put("delay", delay);
        options.put("padding", padding);
        options.put("server", ser);
        options.put("delay", delay);
        options.put("halfscreen", findViewById<CheckBox>(R.id.checkBoxHalfScreen).isChecked);
        return options;
    }

    fun handleClickPopup(view: View) {
        var sid: String = findViewById<EditText>(R.id.editTextSurveyId).text.toString();
        var cid: String = findViewById<EditText>(R.id.editTextChannelId).text.toString();
        var sdid: String = findViewById<EditText>(R.id.editTextSendId).text.toString();

        var parameters = getParam();
        if (parameters == null) {
            return
        }
        var options = buildOptions()

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
                    alert("发生错误 $it");
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

        findViewById<CheckBox>(serverId).isChecked = true;
        excludeServerCheckbox(serverId);

        findViewById<CheckBox>(R.id.checkBoxProjectLynkco).isChecked = HYGlobalConfig.getProject() == "lynkco";

        findViewById<EditText>(R.id.editTextSurveyId).setText(surveyId);
        findViewById<EditText>(R.id.editTextChannelId).setText(channelId);
        findViewById<CheckBox>(R.id.checkBoxHalfScreen).isChecked = halfscreen;
        findViewById<EditText>(R.id.editTextSendId).setText(sendId);

        findViewById<EditText>(R.id.editTextEUID).setText(parameter);

        findViewById<CheckBox>(R.id.checkBoxTEST).setOnClickListener { onCheckboxClick(it) };
        findViewById<CheckBox>(R.id.checkBoxPROD).setOnClickListener { onCheckboxClick(it) };
        findViewById<CheckBox>(R.id.checkBoxJLT).setOnClickListener { onCheckboxClick(it) };
        findViewById<CheckBox>(R.id.checkBoxJLU).setOnClickListener { onCheckboxClick(it) };
        findViewById<CheckBox>(R.id.checkBoxJLP).setOnClickListener { onCheckboxClick(it) };

        findViewById<CheckBox>(R.id.checkBoxForceAuth).setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean -> (onAuthCheckboxClick(compoundButton, b)) };

        findViewById<CheckBox>(R.id.checkBoxProjectLynkco).setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean -> (onProjectCheck(compoundButton, b)) };

    }

    private fun getServer(): String {
        return SERVERMAP[serverId]!!
    }

    private fun excludeServerCheckbox(excludeId: Int) {
        var ids = arrayOf(R.id.checkBoxTEST, R.id.checkBoxPROD, R.id.checkBoxJLP, R.id.checkBoxJLT, R.id.checkBoxJLU)
        for (id in ids) {
            if (id != excludeId) {
                findViewById<CheckBox>(id).isChecked = false;
            }
        }
    }
    private fun onCheckboxClick(view: View?) {
        Log.d("example", "checkbox");
        serverId = view!!.id;
        excludeServerCheckbox(view!!.id)
    }
    private fun onProjectCheck(view: CompoundButton?, checked: Boolean) {
        var project = "";
        if (checked) {
            project = "lynkco"
        }
        HYGlobalConfig.setProject(project);
    }

    private fun onAuthCheckboxClick(view: CompoundButton?, checked: Boolean) {
        var param = getParam();
        if (checked) {
            if (param != null && param.has("accessCode")) {
                HYGlobalConfig.setup(getServer(), param.getString("accessCode"), checked);
            } else {
                alert("missing access code")
            }
        } else {
            HYGlobalConfig.resetAuthCache()
        }
    }
}
