package cn.xmplus.sdk;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.*;

public class Util {
    static Pattern PATTERN_PX =  Pattern.compile("\\d+px");
    static Pattern PATTERN_PERC =  Pattern.compile("\\d+%");

    public static int dpFromPx(final Context context, final double px) {
        return (int) Math.round(px / context.getResources().getDisplayMetrics().density);
    }

    public static int pxFromDp(final Context context, final float dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
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
}
