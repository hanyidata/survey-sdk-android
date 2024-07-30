package cn.xmplus.sdk;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class CustomOutlineProvider extends ViewOutlineProvider {
    private final float radius;
    private final int mode;

    public static final int TOP_ONLY = 0;
    public static final int BOTTOM_ONLY = 1;
    public static final int BOTH = 2;

    public CustomOutlineProvider(float radius, int mode) {
        this.radius = radius;
        this.mode = mode;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        int width = view.getWidth();
        int height = view.getHeight();
        switch (mode) {
            case TOP_ONLY:
                outline.setRoundRect(0, 0, width, height + (int) radius, radius);
                break;
            case BOTTOM_ONLY:
                outline.setRoundRect(0, - (int) radius, width, height, radius);
                break;
            case BOTH:
                outline.setRoundRect(0, 0, width, height, radius);
                break;
        }
    }
}
