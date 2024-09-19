package cn.xmplus.demo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
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
    data class ServerInfo(val name: String, val url: String, val orgCode: String)

    private var survey: HYSurveyView? = null;
    private var padding: Int = 0;
    private var bord: Boolean = true;
    private var debug: Boolean = true;
    private var halfscreen: Boolean = false;
    private var delay: Int = 1000;
//    private var parameter: String = "{\"accessCode\": \"1284536534859235328\"}"
//    private var parameter: String = "{\"externalUserId\":\"152205\",\"parameters\":{\"cancelTime\":\"2014年2月1日\",\"orderNo\":\"888888888\",\"orderPrice\":\"1,500\"}}"
    // TEST
//    private var parameter: String = ""
    private var parameter: String = "{\"externalUserId\": \"w\"}"

    private var surveyId: String = "6960496112091136";
    private var channelId: String = "6960496869556224";
    private var sendId: String = "";

    var ServerInfoList = listOf<ServerInfo>(
        ServerInfo("xmp_www", "https://www.xmplus.cn/api/survey", ""),
        ServerInfo("xmp_test", "https://test.xmplus.cn/api/survey", ""),
        ServerInfo("galaxy_uat", "https://galaxy-h5-test.geely-test.com/api/survey", "galaxy_cem"),
        ServerInfo("galaxy_prd", "https://galaxy-h5.geely.com/api/survey", "galaxy_cem"),
        ServerInfo("lynkco_uat", "https://mktcs-uat.lynkco-test.com/api/survey", "lynkco_cem"),
        ServerInfo("lynkco_prd", "https://mktcs.lynkco.com/api/survey", "lynkco_cem"),
    )

    val defaultServer = "galaxy_uat" // 你可以根据业务逻辑定义默认值
    private var selectedServer: ServerInfo =
        ServerInfoList.find { x -> x.name == defaultServer }!!;

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
        var sdid: String = findViewById<EditText>(R.id.editTextSendId).text.toString();

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
        var lang: String = findViewById<EditText>(R.id.editTextLang).text.toString();
        var options = JSONObject();
        options.put("language", lang);
        options.put("lang", lang);
        options.put("clickDismiss", findViewById<CheckBox>(R.id.checkBoxDismiss).isChecked);
        options.put("showClose", findViewById<CheckBox>(R.id.checkBoxShowClose).isChecked);
        options.put("debug", debug);
        options.put("bord", bord);
//        options.put("delay", delay);
        options.put("padding", padding);
//        options.put("server", ser);
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
        Log.d("surveyExample", "onSize $param")
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
        title = String.format("SurveySDK (%s)", cn.xmplus.sdk.BuildConfig.SDK_VERSION)

        Log.d("ServerInfo", "Selected: ${selectedServer.name}, URL: ${selectedServer.url}  org: ${selectedServer.orgCode}")
        HYGlobalConfig.setup(selectedServer.url);

        findViewById<EditText>(R.id.editTextSurveyId).setText(surveyId);
        findViewById<EditText>(R.id.editTextChannelId).setText(channelId);
        findViewById<CheckBox>(R.id.checkBoxHalfScreen).isChecked = halfscreen;
        findViewById<EditText>(R.id.editTextSendId).setText(sendId);

//        findViewById<EditText>(R.id.editTextOrgCode).setText(orgCode);

        findViewById<EditText>(R.id.editTextEUID).setText(parameter);


        val spinner: Spinner = findViewById(R.id.serverList)

        // 手动创建选项列表
        val options = listOf("TEST", "PROD", "JLT")

        // 创建 ArrayAdapter 并设置给 Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ServerInfoList.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // 找到默认服务器在列表中的索引
        val defaultPosition = ServerInfoList.indexOfFirst { it.name == defaultServer }
        // 设置默认选中的服务器
        if (defaultPosition != -1) {
            spinner.setSelection(defaultPosition)
        }

        // 设置选择监听器
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedServer = ServerInfoList[position]
                // 处理选中的服务器，例如显示服务器信息
                Log.d("ServerInfo", "Selected: ${selectedServer.name}, URL: ${selectedServer.url}  org: ${selectedServer.orgCode}")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 未选择时的处理
            }
        }

        findViewById<CheckBox>(R.id.checkBoxForceAuth).setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean -> (onAuthCheckboxClick(compoundButton, b)) };
    }

    private fun onAuthCheckboxClick(view: CompoundButton?, checked: Boolean) {
        var param = getParam();
        if (checked) {
            if (param != null && param.has("accessCode")) {
                HYGlobalConfig.setup(selectedServer.url,  selectedServer.orgCode, param.getString("accessCode"), checked);
            } else {
                alert("missing access code")
            }
        } else {
            HYGlobalConfig.resetAuthCache()
        }
    }
}
