package com.study.httpframework.http;

import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;


public abstract class CommonCallBack<T> implements Callback<T> {
    private String TAG = getClass().getSimpleName();

    @Override
    public void onStart(Request<T, ? extends Request> request) {
    }

    @Override
    public void onCacheSuccess(Response<T> response) {
    }

    @Override
    public void uploadProgress(Progress progress) {
    }

    @Override
    public void downloadProgress(Progress progress) {
    }

    @Override
    public void onError(Response<T> response) {

    }
}
