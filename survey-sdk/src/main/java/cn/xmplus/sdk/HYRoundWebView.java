package cn.xmplus.sdk;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

public class HYRoundWebView extends WebView {
    private float topRadius;
    private float bottomRadius;
//    private OnScrollChangeListener onScrollChangeListener;

    public HYRoundWebView(Context context) {
        super(context);
    }

    public HYRoundWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HYRoundWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCornerRadius(float topRadius, float bottomRadius) {
        this.topRadius = topRadius;
        this.bottomRadius = bottomRadius;
        invalidate();
    }

//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
////        Log.d("surveySDK", "onScrollChanged");
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        float[] radii = new float[]{
                topRadius, topRadius, // 左上角
                topRadius, topRadius, // 右上角
                bottomRadius, bottomRadius, // 右下角
                bottomRadius, bottomRadius  // 左下角
        };
        path.addRoundRect(0, 0, getWidth(), getHeight(), radii, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
