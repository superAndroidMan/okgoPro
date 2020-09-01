/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.study.httpframework.http;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.request.base.Request;
import com.study.httpframework.AppContext;
import com.study.httpframework.util.SPUtils;
import com.study.httpframework.util.ToastUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.util.Stack;

import okhttp3.Response;

public abstract class JsonCallback<T> extends CommonCallBack<T> {

    private Type type;
    private Class<T> clazz;
    private String TAG = getClass().getSimpleName();

    public JsonCallback() {
    }

    public JsonCallback(Type type) {
        this.type = type;
    }

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        String token = AppContext.getmInstance().getProperty("token");
        if (TextUtils.isEmpty(token)) {
            token = (String) SPUtils.get("token", "");
        } else {
            SPUtils.put("token", token);
        }
        if (!TextUtils.isEmpty(token)) {
            request.headers("token", token);
        }
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        Throwable exception = response.getException();
        String code = exception.getMessage();
        String message;
        if (exception.getCause() == null) {
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
//                intent.setClass(AppContext.getmInstance(), FirstLoginActivity.class);
                AppContext.getmInstance().startActivity(intent);
                Stack<Activity> activities = AppContext.getmInstance().getmActivityList();
                for (Activity activity : activities) {

                }
            } else if ("5000".equals(code)) {
                Log.e(TAG, "onError: 重复提交");
            } else {
                ToastUtils.showToastCenter(message);
            }
        } else {
            ToastUtils.showToastCenter("网络不给力，请稍后重试！");
        }
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     */
    @Override
    public T convertResponse(Response response) throws Throwable {

        if (type == null) {
            if (clazz == null) {
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                JsonConvert<T> convert = new JsonConvert<>(clazz);
                return convert.convertResponse(response);
            }
        }

        JsonConvert<T> convert = new JsonConvert<>(type);
        return convert.convertResponse(response);
    }
}
