package com.study.httpframework.http;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.study.httpframework.AppContext;
import com.study.httpframework.util.ToastUtils;

import java.net.SocketException;
import java.util.Stack;

public class ExceptionHandler {


    private static String TAG = "ExceptionHandler";

    public static void handleException(Throwable exception){

        String code = exception.getMessage();
        String message;
        if(exception.getCause() == null){
            message = "网络不给力，请稍后重试！";
        } else {
            message = exception.getCause().getMessage();
        }
        if (exception instanceof JsonSyntaxException) {
            exception.printStackTrace();
        } else if (exception instanceof SocketException) {
            exception.printStackTrace();
        } else if (exception instanceof IllegalStateException) {
            if ("4000".equals(code)) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AppContext.getmInstance().startActivity(intent);
                Stack<Activity> activities = AppContext.getmInstance().getmActivityList();
                for (Activity activity : activities) {
//                    boolean isMainLogin = activity instanceof FirstLoginActivity;
//                    if (!isMainLogin) {
//                        activity.finish();
//                    }
                }
            } else if("5000".equals(code)){
                Log.e(TAG, "onError: 重复提交" );
            } else {
                ToastUtils.showToastCenter(message);
            }
        } else {
            ToastUtils.showToastCenter("网络不给力，请稍后重试！");
        }

    }

} 