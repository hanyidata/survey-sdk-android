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

    fun handleClickEmbed(view: View) {

        var sid: String = findViewById<EditText>(R.id.editTextSurveyId).text.toString();
        var cid: String = findViewById<EditText>(R.id.editTextChannelId).text.toString();
        var parameters = JSONObject();
        var options = JSONObject();
        options.put("debug", debug);
        options.put("bord", bord);
        options.put("delay", delay);
        options.put("padding", padding);
        options.put("server", findViewById<EditText>(R.id.editTextServer).text.toString());

        var container:LinearLayout = findViewById(R.id.container)

        this.survey =
            HYSurveyView(this, sid, cid, parameters, options);

        container.addView(this.survey)
        this.survey?.setOnSubmit(::onSubmit);
        this.survey?.setOnCancel(::onCancel);
        this.survey?.setOnClose(::onClose);
        this.survey?.setOnSize(::onSize);
    }

    fun handleClickPopup(view: View) {
        var sid: String = findViewById<EditText>(R.id.editTextSurveyId).text.toString();
        var cid: String = findViewById<EditText>(R.id.editTextChannelId).text.toString();
        var parameters = JSONObject();
        var options = JSONObject();
        options.put("debug", debug);
        options.put("bord", bord);
        options.put("delay", delay);
        options.put("padding", padding);
        options.put("server", findViewById<EditText>(R.id.editTextServer).text.toString());
        var root = findViewById<View>(android.R.id.content)
        HYPopupDialog.makeDialog(root.context, sid, cid, parameters, options,  {
//                alert("取消");
            }, {
//                alert("提交");
            }, {
//                alert(it.toString());
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