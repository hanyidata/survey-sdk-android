package cn.xmplus.demo

import android.app.Application
import android.content.res.Resources
import android.util.DisplayMetrics
import cn.xmplus.sdk.HYConfig
import me.jessyan.autosize.AutoSizeConfig

class TestApplication() : Application(){
    override fun onCreate() {
        super.onCreate()
        //屏幕适配
        val metrics: DisplayMetrics = Resources.getSystem().getDisplayMetrics()
        HYConfig.setDensity(metrics.density);
        AutoSizeConfig.getInstance()
            .setLog(true)
            .setExcludeFontScale(true) // App 内的字体大小不跟随系统设置中字体大小的改变
            .setUseDeviceSize(false)
            .init(this)
    }
}
