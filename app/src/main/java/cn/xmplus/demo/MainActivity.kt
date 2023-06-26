package cn.xmplus.demo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.xmplus.sdk.HYPopupDialog
import cn.xmplus.sdk.HYSurveyView
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private var survey: HYSurveyView? = null;
    var sid = "4445329530320896"
    var cid = "4446931357162496";
    var parameters = JSONObject();
    var options = JSONObject();

    init {
//        parameters.put("externalUserId", euidInput.text.toString());
        options.put("debug", true);
        options.put("server", "https://jltest.xmplus.cn/api/survey");
//        options.put("server", "https://test.xmplus.cn/api/survey");
    }

    fun handleClickEmbed(view: View) {
        this.setup()
    }

    fun handleClickPopup(view: View) {
//        this.setup()
        var root = findViewById<View>(android.R.id.content)
        HYPopupDialog.makeDialog(root.context, sid, cid, parameters, options,  {
            alert("取消");
        }, {
            alert("提交");
        }, {
            alert(it.toString());
        },
        );
    }


    fun setup() {

        if (this.survey == null) {
            var container:LinearLayout = findViewById(R.id.container)

            this.survey =
                HYSurveyView(this, sid, cid, parameters, options);

            container.addView(this.survey)
            this.survey?.setOnSubmit(::onSubmit);
            this.survey?.setOnCancel(::onCancel);
            this.survey?.setOnClose(::onClose);
            this.survey?.setOnSize(::onSize);
            var versionText: TextView = findViewById(R.id.version)
            versionText.text = String.format("v:%s (%s)", survey?.version, survey?.build)
        } else {
            this.survey?.show();
        }
    }

    fun onSubmit(param: Any?) {
        alert("已经提交")
    }

    fun onCancel(param: Any?) {
        alert("取消了作答")
    }

    fun onSize(param: Any?) {
//        alert("高度变化");
        println(param);
    }

    fun onClose(param: Any?) {
        alert("关闭")
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
    }
}