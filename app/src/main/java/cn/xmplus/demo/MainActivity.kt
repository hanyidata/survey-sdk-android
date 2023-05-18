package cn.xmplus.demo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.xmplus.sdk.HYSurveyView
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private var survey: HYSurveyView? = null;

    fun handleClick(view: View) {
        this.setup()
    }

    fun setup() {
        if (this.survey == null) {
            var sidInput: EditText =  findViewById(R.id.sid);
            var cidInput: EditText =  findViewById(R.id.cid);
            var euidInput: EditText =  findViewById(R.id.euid);
            var container:LinearLayout = findViewById(R.id.container)

//            container.
            var sid = sidInput.text.toString().toLong();
            var cid = cidInput.text.toString().toLong();
            var parameters = JSONObject();
            parameters.put("externalUserId", euidInput.text.toString());
            var options = JSONObject();
            options.put("debug", true);
            this.survey =
                HYSurveyView(this, sid, cid, parameters, options);

            container.addView(this.survey)
            this.survey?.setOnSubmit(::onSubmit);
            this.survey?.setOnCancel(::onCancel);
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