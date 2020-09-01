package com.study.httpframework.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.gyf.barlibrary.ImmersionBar;
import com.study.httpframework.R;
import com.study.httpframework.util.ImageLoaderUtil;
import com.study.httpframework.util.LoadingDialog;
import com.study.httpframework.util.ToastUtils;
import com.study.httpframework.widget.CommonEmptyView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public abstract class BaseTitleBarActivity extends AppCompatActivity {

    protected Context mContext;
    protected ViewFlipper mContentView;
    protected RelativeLayout mHeadLayout;
    protected ImageView mIvLeft;
    protected TextView mTitle;
    protected TextView mHeadRightText;
    protected CommonEmptyView commonEmpty;
    private LoadingDialog mDialog;
    private ImageView ivRight;
    private View viewLine;
    protected View viewStatusBarWhite;
    private Dialog mNativeLoadingDialog;
    //是否需要监听网络 true 需重写onNetWorkChange
    private boolean isNeedTrackNetWork = false;
    private NetWorkChangeReceiver netWorkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_base);
        mContext = this;
        mDialog = new LoadingDialog(this);
        // 初始化公共头部
        mContentView = (ViewFlipper) findViewById(R.id.layout_container);
        mHeadLayout = (RelativeLayout) findViewById(R.id.layout_head);
        mHeadRightText = (TextView) findViewById(R.id.text_right);
        mIvLeft = (ImageView) findViewById(R.id.btn_left);
        viewLine = (View) findViewById(R.id.view_line);
        viewStatusBarWhite = (View) findViewById(R.id.view_status_bar_white);
        ivRight = (ImageView) findViewById(R.id.iv_right);
        mTitle = (TextView) findViewById(R.id.tv_title);
        commonEmpty = (CommonEmptyView) findViewById(R.id.common_empty);
        LayoutInflater.from(this).inflate(getLayoutId(), mContentView);
//        StatusBarUtil.setStatusBarColor(this,R.color.white);
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        ButterKnife.bind(this);
        initView();
        initData();
        //此方法与统计分析sdk中统计日活的方法无关！请务必调用此方法！
        PushAgent.getInstance(this).onAppStart();
        if (isNeedTrackNetWork) {
            netWorkChangeReceiver = new NetWorkChangeReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netWorkChangeReceiver, filter);//注册
        }
    }

    protected abstract void initData();

    protected abstract int getLayoutId();

    protected abstract void initView();

    /**
     * 设置头部是否可见
     *
     * @param visibility
     */
    public void setHeadVisibility(int visibility) {
        mHeadLayout.setVisibility(visibility);
    }

    /**
     * 设置左边是否可见
     *
     * @param visibility
     */
    public void setHeadLeftButtonVisibility(int visibility) {
        mIvLeft.setVisibility(visibility);
    }

    /**
     * 设置标题
     */
    public void setTitle(int titleId) {
        setTitle(getString(titleId), false);
    }

    /**
     * 设置标题
     */
    public void setTitle(int titleId, boolean flag) {
        setTitle(getString(titleId), flag);
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            super.setTitle("");
        }
        setTitle(title, false);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title, boolean flag) {
        mTitle.setText(title);
    }

    /**
     * 点击左按钮
     */
    public void onHeadLeftButtonClick(View v) {
        finish();
    }

    public void setIvRightResources(String path) {
        ImageLoaderUtil.getInstance().displayCommonImage(path, ivRight);
    }

    public void setIvRightResources(int path) {
        ivRight.setImageResource(path);
        ivRight.setVisibility(View.VISIBLE);
    }

    public void setIvRightHide() {
        ivRight.setVisibility(View.GONE);
    }

    public void setmHeadRightText(String headRightText) {
        mHeadRightText.setText(headRightText);
        mHeadRightText.setVisibility(View.VISIBLE);
    }

    public TextView getmHeadRightText() {
        return mHeadRightText;
    }

    public void setmHeadRightTextColor(int colorId) {
        mHeadRightText.setTextColor(getResources().getColor(colorId));
    }

    public void setmHeadRightTextVisibity(int visibility) {
        mHeadRightText.setVisibility(visibility);
    }

    public void setmHeadLayoutColor(int colorId) {
        mHeadLayout.setBackgroundColor(getResources().getColor(colorId));
    }

    public void setmIvLeftResources(int resId) {
        mIvLeft.setImageResource(resId);
    }

    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    public void setErrorType(int type) {
        if (type == CommonEmptyView.HIDE_LAYOUT) {
            mContentView.setVisibility(View.VISIBLE);
        } else {
            mContentView.setVisibility(View.GONE);
        }
        commonEmpty.setErrorType(type);
    }

    public void setErrorType(int type, String buttonString, View.OnClickListener onClickListener) {
        if (type == CommonEmptyView.HIDE_LAYOUT) {
            mContentView.setVisibility(View.VISIBLE);
        } else {
            mContentView.setVisibility(View.GONE);
        }
        commonEmpty.setErrorType(type);
        commonEmpty.setButton(buttonString, onClickListener);
    }

    public void setEmptyType(String title, int ResId) {
        commonEmpty.setNoData(title, ResId);
    }


    public void hideEmpty() {
        commonEmpty.setVisibility(View.GONE);
        mContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showSoftKeyBoard(View view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mInputMethodManager.showSoftInput(view, 0);
        mInputMethodManager.showSoftInputFromInputMethod(view.getWindowToken(), 0);
    }

    public static void hideSoftKeyboard(View view) {
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus() && event.getAction() == MotionEvent.ACTION_UP) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    /**
     * 右文字点击
     *
     * @param view
     */
    public void onRightTextViewClick(View view) {

    }

    public void ShowWaitDialog(int message) {
        if (!isFinishing() && mDialog != null && !mDialog.isShowing()) {
            mDialog.setText(getString(message));
            mDialog.show();
        }
    }

    public void showToast(String text) {
        ToastUtils.showToastCenter(text);
    }

    public void showWaitDialog(String message) {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.setText(message);
            mDialog.show();
        }
    }

    public void showLine(boolean isShow) {
        viewLine.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void HideWaitDialog() {
        if (mDialog != null)
            mDialog.dismiss();
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .fitsSystemWindows(true)
                .navigationBarColor(R.color.white) //导航栏颜色，不写默认黑色
                .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                .init();
    }

    /**
     * 修改状态栏颜色
     *
     * @param color
     */
    protected void setImmersionBarColor(int color) {
        ImmersionBar.with(this)
                .statusBarColor(color)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .fitsSystemWindows(true)
                .navigationBarColor(R.color.white) //导航栏颜色，不写默认黑色
                .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                .init();
    }

    /**
     * 修改状态栏颜色
     *
     * @param color
     * @param isBlackText 状态栏字体颜色是否黑色
     */
    protected void setImmersionBarColor(int color, boolean isBlackText) {
        ImmersionBar.with(this)
                .statusBarColor(color)
                .statusBarDarkFont(isBlackText)   //状态栏字体是深色，不写默认为亮色
                .fitsSystemWindows(true)
                .navigationBarColor(R.color.white) //导航栏颜色，不写默认黑色
                .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                .init();
    }

    /**
     * 修改状态栏颜色
     *
     * @param color
     * @param isBlackText    状态栏字体颜色是否黑色
     * @param isShowKeyBoard 键盘是否顶起布局
     */
    protected void setImmersionBarColor(int color, boolean isBlackText, boolean isShowKeyBoard) {
        ImmersionBar.with(this)
                .statusBarColor(color)
                .statusBarDarkFont(isBlackText)   //状态栏字体是深色，不写默认为亮色
                .fitsSystemWindows(true)
                .navigationBarColor(R.color.white) //导航栏颜色，不写默认黑色
                .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                .keyboardEnable(isShowKeyBoard)
                .init();
    }

    /**
     * 透明状态栏
     *
     * @param isBlackText 状态栏字体颜色是否黑色
     */
    protected void setImmersionBarTransparent(boolean isBlackText) {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .fitsSystemWindows(false)
                .statusBarDarkFont(isBlackText)
                .fullScreen(false)
                .navigationBarColor(R.color.white) //导航栏颜色，不写默认黑色
                .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                .init();
    }

    @Override
    protected void onDestroy() {
        if (mNativeLoadingDialog != null) {
            mNativeLoadingDialog.dismiss();
        }
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        if (netWorkChangeReceiver != null) {
            unregisterReceiver(netWorkChangeReceiver);
        }
    }

    /**
     * 右图标点击
     *
     * @param view
     */
    public void onHeadRightIvClick(View view) {

    }

    public void onNetWorkChange(boolean isAvailable) {

    }

    public void setNeedTrackNetWork(boolean needTrackNetWork) {
        isNeedTrackNetWork = needTrackNetWork;
    }

    class NetWorkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()
                    || activeNetworkInfo.isFailover()) {
                onNetWorkChange(false);
            } else {
                onNetWorkChange(true);
            }
        }
    }

}
