package com.study.httpframework.util;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.study.httpframework.AppContext;
import com.study.httpframework.R;


public class ToastUtils {
	private static Toast toast;

	public static void showToastCenter(String message) {
		if ( message != null && !message.equalsIgnoreCase("") ) {
			View view = LayoutInflater.from(AppContext.mInstance).inflate(
					R.layout.view_toast_center, null);
			((TextView) view.findViewById(R.id.title_tv)).setText(message);
			if(toast == null){
				toast = new Toast(AppContext.mInstance);
			}
			toast.setView(view);
			toast.setGravity(Gravity.TOP, 0, DensityUtil.dip2px(270));
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.show();
		}
	}

}
