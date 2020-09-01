package com.study.httpframework.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by superman on 2017/4/19.
 */

public class DensityUtil {
    public static DensityUtil Instance;

    private DensityUtil() {
    }

    public static DensityUtil getInstance() {
        if (Instance == null) {
            Instance = new DensityUtil();
        }
        return Instance;
    }

    public static int dip2px(float dpValu) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValu,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static void changeViewSize(Activity context, View view, float spValue) {
        WindowManager wm = context.getWindowManager();//获取屏幕宽高
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        ViewGroup.LayoutParams para = view.getLayoutParams();//获取drawerlayout的布局
        para.width = (int) (width * 0.7);//修改宽度
        para.height = height;//修改高度
        view.setLayoutParams(para); //设置修改后的布局。
    }


    /**
     * check if sdcard exist
     *
     * @return
     */
    public static boolean isSdcardExist() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    public static float px2sp(float pxVal) {
        return (pxVal / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }


    /**
     * 测量文字高度
     *
     * @param paint
     * @return
     */
    public static float measureTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (Math.abs(fontMetrics.ascent) - fontMetrics.descent);
    }

    public int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
