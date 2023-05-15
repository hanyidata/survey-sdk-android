package cn.xmplus.demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import cn.xmplus.sdk.Survey
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var survey: Survey? = null;

    fun handleClick(view: View) {
        this.setup()
    }

    fun setup() {
        if (this.survey == null) {
            var sidInput: EditText =  findViewById(R.id.sid);
            var cidInput: EditText =  findViewById(R.id.cid);
            var euidInput: EditText =  findViewById(R.id.euid);
            var container:LinearLayout = findViewById(R.id.container)

            var sid = sidInput.text.toString().toLong();
            var cid = cidInput.text.toString().toLong();
            var parameters = JSONObject();
            parameters.put("externalUserId", euidInput.text.toString());
            var options = JSONObject();
            options.put("debug", true);
            this.survey = Survey(this, sid, cid, parameters,options);
            container.addView(this.survey)
        } else {
            this.survey?.show();
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}