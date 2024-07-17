package cn.xmplus.demo

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cn.xmplus.sdk.HYPopupDialog
import cn.xmplus.sdk.HYSurveyView
import org.json.JSONObject

class MainActivityNew : AppCompatActivity() {

    private var survey: HYSurveyView? = null;
    private var padding: Int = 0;
    private var bord: Boolean = true;
    private var debug: Boolean = true;
    private var halfscreen: Boolean = false;
    private var delay: Int = 1000;
    private var accessCode: String = "";
    private var euid: String = "";

    // TEST
    private var surveyId: String = "6625464840488960";
    private var channelId: String = "6625466922633216";
    private var sendId: String = "5TesHfArNnmu5pPn";
    private var server: String = "https://test.xmplus.cn/api/survey";

    private var editorSurveyId: EditText? = null
    private var editorChannelId: EditText? = null
    private var editorSendId: EditText? = null
    private var editorLang: EditText? = null

    private var testCheckBox: CheckBox? = null
    private var prodCheckBox: CheckBox? = null
    private var halfScreenCheckBox: CheckBox? = null;
    private var dismissCheckBox: CheckBox? = null;
    private var popupButton: Button? = null;
    private var embeddedButton: Button? = null;
    private var container: LinearLayout? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // 创建 ScrollView 作为根布局
        val scrollView = ScrollView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        // 创建 LinearLayout 作为 ScrollView 的子布局
        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
            }
        }
        rootLayout.setBackgroundColor(Color.argb(60, 255, 0, 0))

        // 创建函数来生成每一行的布局
        fun createRow(labelText: String, inputText: String = "", isCheckBox: Boolean = false): Pair<LinearLayout, View> {
            val inputView: View

            val rowLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, 8)
                }

                val label = TextView(this@MainActivityNew).apply {
                    text = labelText
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                }
                addView(label)

                inputView = if (isCheckBox) {
                    CheckBox(this@MainActivityNew).apply {
                        text = inputText
                        setTextColor(Color.WHITE) // 设置文字颜色
                    }.also { addView(it) }
                } else {
                    EditText(this@MainActivityNew).apply {
                        setText(inputText)
                        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3f)
                    }.also { addView(it) }
                }
            }

            return Pair(rowLayout, inputView)
        }

        // 添加各个输入行
        val sendIdPan = createRow("SENDID", sendId)
        rootLayout.addView(sendIdPan.first)
        editorSendId = sendIdPan.second as EditText;

        val sidPan = createRow("SID", surveyId)
        rootLayout.addView(sidPan.first)
        editorSurveyId = sidPan.second as EditText;

        val cidPan = createRow("CID", channelId)
        rootLayout.addView(cidPan.first)
        editorChannelId = cidPan.second as EditText;

        val langPan = createRow("LANG", "zh-cn")
        rootLayout.addView(langPan.first)
        editorLang = langPan.second as EditText;

        val serverRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 8)
            }

            val serverLabel = TextView(this@MainActivityNew).apply {
                text = "SERVER"
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }
            addView(serverLabel)

            testCheckBox = CheckBox(this@MainActivityNew).apply {
                text = "TEST"
                isChecked = server.startsWith("https://test.xmplus")
            }
            addView(testCheckBox)

            prodCheckBox = CheckBox(this@MainActivityNew).apply {
                text = "PROD"
                isChecked = server.startsWith("https://www.xmplus")
            }
            addView(prodCheckBox)
        }
        rootLayout.addView(serverRow)

//        rootLayout.addView(createRow("EUID"))

        val optRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 8)
            }

            val optLabel = TextView(this@MainActivityNew).apply {
                text = "Opt"
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }
            addView(optLabel)

            halfScreenCheckBox = CheckBox(this@MainActivityNew).apply {
                text = "HalfScreen"
            }
            addView(halfScreenCheckBox)

            dismissCheckBox = CheckBox(this@MainActivityNew).apply {
                text = "Dismiss"
            }
            addView(dismissCheckBox)
        }
        rootLayout.addView(optRow)

        // 添加按钮
        embeddedButton = Button(this).apply {
            this.text = "内置"
            setBackgroundColor(Color.LTGRAY)
            setTextColor(Color.BLACK)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 8)
            }
        }
        embeddedButton?.setOnClickListener { onNested(it) };
        rootLayout.addView(embeddedButton)

        popupButton = Button(this).apply {
            this.text = "弹出"
            setBackgroundColor(Color.LTGRAY)
            setTextColor(Color.BLACK)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 8)
            }
        }
        popupButton?.setOnClickListener { onPopup(it) };
        rootLayout.addView(popupButton)

        // 将 rootLayout 添加到 ScrollView 中
        scrollView.addView(rootLayout)

        // 设置 Activity 的内容视图为 ScrollView
        setContentView(scrollView)
    }

    private fun onPopup(view: View?) {
//        alert("popup");
        var parameters = buildParameters();
        var options = buildOptions();
        var sid: String = editorSurveyId!!.text.toString();
        var cid: String = editorChannelId!!.text.toString();

//        survey
        HYPopupDialog.makeDialog(
            this, sid , cid, parameters, options,
            {
//                onCancel(null);
            },
            {
//                onSubmit(null);
            },
            {
                Log.d("surveyExample ", "发生错误 $it")
            }, {
                Log.d("surveyExample", "onLoad")
            }
        );
    }

    private fun onNested(view: View?) {
        alert("nest");
    }

    private fun getSever(): String {
        if (testCheckBox!!.isChecked) {
            return "https://test.xmplus.cn/api/survey";
        } else if (prodCheckBox!!.isChecked) {
            return "https://www.xmplus.cn/api/survey";
        }
        return "";
    }

    private fun buildParameters(): JSONObject {
        var parameters = JSONObject();
        if (euid.isNotEmpty()) {
            parameters.put("externalUserId", euid);
        }
        return parameters;
    }

    private fun buildOptions():JSONObject {
        var options = JSONObject();
        options.put("lang", editorLang!!.text);
        options.put("debug", debug);
        options.put("bord", bord);
//        options.put("delay", delay);
        options.put("server", getSever());
        options.put("delay", delay);
        options.put("halfscreen", halfScreenCheckBox!!.isChecked);
        options.put("clickDismiss", dismissCheckBox!!.isChecked);

//        var container:LinearLayout = findViewById(R.id.container)
//        val displayMetrics: DisplayMetrics = this.getResources().getDisplayMetrics()
        return options;
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

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // 在配置变化时执行需要的操作
        // 你可以在这里处理屏幕旋转后的逻辑
    }
}
