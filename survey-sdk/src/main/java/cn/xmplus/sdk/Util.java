package cn.xmplus.sdk;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.*;

public class Util {
    static Pattern PATTERN_PX =  Pattern.compile("\\d+px");
    static Pattern PATTERN_PERC =  Pattern.compile("\\d+%");

    public static int dpFromPx(final Context context, final double px) {
        Float density = HYConfig.getDensity() != null ? HYConfig.getDensity() : context.getResources().getDisplayMetrics().density;
        return (int) Math.round(px / density);
    }

    public static int pxFromDp(final Context context, final float dp) {
        Float density = HYConfig.getDensity() != null ? HYConfig.getDensity() : context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    /**
     * 解析px或者%， 注意这里的px是指产品页面的设置像素，在app端其实是dp
     * @param value
     * @param max
     * @return
     */
    public static int parsePx(final Context context, String value, int max) {
        try {
            if (PATTERN_PX.matcher(value).matches()) {
                return pxFromDp(context, new DecimalFormat("0px").parse(value).intValue());
            } else if (PATTERN_PERC.matcher(value).matches()) {
                Float hp = new DecimalFormat("0%").parse(value).floatValue();
                return (int) (hp * max);
            }
        } catch (ParseException ex) {
            return 0;
        }
        return 0;
    }

    public static int colorFromHex(String hex) {
        hex = hex.replace("#", "").trim();
        int r, g, b, a;

        if (hex.length() == 3) {
            r = Integer.parseInt(hex.substring(0, 1) + hex.substring(0, 1), 16);
            g = Integer.parseInt(hex.substring(1, 2) + hex.substring(1, 2), 16);
            b = Integer.parseInt(hex.substring(2, 3) + hex.substring(2, 3), 16);
            a = 255;
        } else if (hex.length() == 6) {
            r = Integer.parseInt(hex.substring(0, 2), 16);
            g = Integer.parseInt(hex.substring(2, 4), 16);
            b = Integer.parseInt(hex.substring(4, 6), 16);
            a = 255;
        } else if (hex.length() == 7) {
            r = Integer.parseInt(hex.substring(0, 2), 16);
            g = Integer.parseInt(hex.substring(2, 4), 16);
            b = Integer.parseInt(hex.substring(4, 6), 16);
            a = Integer.parseInt(hex.substring(6, 7) + hex.substring(6, 7), 16);
        } else if (hex.length() == 8) {
            r = Integer.parseInt(hex.substring(0, 2), 16);
            g = Integer.parseInt(hex.substring(2, 4), 16);
            b = Integer.parseInt(hex.substring(4, 6), 16);
            a = Integer.parseInt(hex.substring(6, 8), 16);
        } else {
            Log.d("surveySDK", "colorFromHex: invalid");
            return Color.WHITE;
        }
        return Color.argb(a, r, g, b);
    }
}
