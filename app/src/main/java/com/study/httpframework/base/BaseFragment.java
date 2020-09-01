package com.study.httpframework.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.SimpleImmersionFragment;
import com.lzy.okgo.OkGo;
import com.study.httpframework.R;
import com.study.httpframework.util.LoadingDialog;
import com.study.httpframework.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

public abstract class BaseFragment extends SimpleImmersionFragment {
    protected View mRoot;
    protected Bundle mBundle;
    private LoadingDialog mDialog;
    public Activity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = new LoadingDialog(getActivity());
        mBundle = getArguments();
        initBundle(mBundle);
        mContext = getActivity();
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .fitsSystemWindows(true)
                .navigationBarColor(R.color.white) //导航栏颜色，不写默认黑色
                .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                .init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mRoot);
            initView(mRoot);
            initData();
        }
        return mRoot;
    }

    public abstract void initView(View view);

    public abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        OkGo.getInstance().cancelAll();
        mRoot = null;
        mBundle = null;
    }

    public void refresh() {
    }

    protected void initBundle(Bundle bundle) {

    }

    protected abstract int getLayoutId();

    public void showToast(String text) {
        ToastUtils.showToastCenter(text);
    }

    public void showWaitDialog() {
        showWaitDialog(R.string.loading);
    }

    public void showWaitDialog(int message) {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.setText(getString(message));
            mDialog.show();
        }
    }

    public void showWaitDialog(String message) {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.setText(message);
            mDialog.show();
        }
    }

    public void hideWaitDialog() {
        if (mDialog != null)
            mDialog.dismiss();
    }

    public void hideSoftKeyboard(View view) {
        if (view == null)
            return;
        View focusView = null;
        if (view instanceof EditText)
            focusView = view;
        Context context = view.getContext();
        if (context != null && context instanceof Activity) {
            Activity activity = ((Activity) context);
            focusView = activity.getCurrentFocus();
        }

        if (focusView != null) {
            if (focusView.isFocused()) {
                focusView.clearFocus();
            }
            InputMethodManager manager = (InputMethodManager) focusView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(focusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            manager.hideSoftInputFromInputMethod(focusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
