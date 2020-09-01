package com.study.httpframework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.study.httpframework.R;


/**
 * 用于显示界面的 loading、错误信息提示等状态。
 * <p>
 * </p>
 */
public class CommonEmptyView extends FrameLayout {
    private TextView mTitleTextView;


    private TextView mDetailTextView;
    protected Button mButton;
    private ImageView emptyViewIv;
    private ProgressBar mLoadingView;
    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int NODATA_ENABLE_CLICK = 5;
    public static final int NO_LOGIN = 6;
    public static final int NO_INTERNET = 7;
    public static final int NO_ADDRESS = 8;
    public static final int NO_BOOK_DOCTOR = 9;

    public CommonEmptyView(Context context) {
        this(context,null);
    }

    public CommonEmptyView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

	public CommonEmptyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        init();
	}

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.common_empty_view, this, true);
        emptyViewIv = (ImageView)findViewById(R.id.empty_view_iv);
        mTitleTextView = (TextView)findViewById(R.id.empty_view_title);
        mDetailTextView = (TextView)findViewById(R.id.empty_view_detail);
        mButton = (Button)findViewById(R.id.empty_view_button);
        mLoadingView = (ProgressBar)findViewById(R.id.empty_view_loading);

    }

    public void setErrorType(int type){
        setVisibility(View.VISIBLE);
        switch (type) {
            case NETWORK_ERROR:
                setTitleText("网络异常，请刷新重试");
                mLoadingView.setVisibility(GONE);
                setImageShowing(true,R.drawable.default_img);
                mButton.setVisibility(VISIBLE);
                break;
            case NETWORK_LOADING:
                setTitleText("数据加载中...");
                mLoadingView.setVisibility(VISIBLE);
                setImageShowing(false,R.drawable.default_img);
                mButton.setVisibility(GONE);
                break;
            case NODATA:
                setTitleText("暂无内容...");
                mLoadingView.setVisibility(GONE);
                setImageShowing(false,R.drawable.default_img);
                mButton.setVisibility(GONE);
                break;
            case HIDE_LAYOUT:
                hide();
                break;
            case NODATA_ENABLE_CLICK:
                break;
            case NO_LOGIN:
                break;
            case NO_INTERNET:
                setTitleText("请检查网络连接");
                mLoadingView.setVisibility(GONE);
                setImageShowing(true,R.drawable.default_img);
                mButton.setVisibility(VISIBLE);
                break;
        }
    }

    public void setNoData(String title, int resId){
        setTitleText(title);
        mLoadingView.setVisibility(GONE);
        setImageShowing(true,resId);
    }

    public void setNoData(String title, int resId, String text, OnClickListener onClickListener){
        setTitleText(title);
        mLoadingView.setVisibility(GONE);
        setImageShowing(true,resId);
        mButton.setText(text);

        mButton.setVisibility(text != null ? VISIBLE : GONE);
        mButton.setOnClickListener(onClickListener);
    }

    /**
     * 隐藏emptyView
     */
    public void hide() {
        setVisibility(GONE);
        setImageShowing(false,R.drawable.default_img);
        setTitleText(null);
        setDetailText(null);
        setButton(null, null);
        emptyViewIv.setVisibility(GONE);
        mLoadingView.setVisibility(GONE);
    }

    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    public void setImageShowing(boolean show,int resId) {
        emptyViewIv.setVisibility(show ? VISIBLE : GONE);
        if(show){
            emptyViewIv.setImageResource(resId);
        }
    }

    public void setTitleText(String text) {
        mTitleTextView.setText(text);
        mTitleTextView.setVisibility(text != null ? VISIBLE : GONE);
    }

    public void setDetailText(String text) {
        mDetailTextView.setText(text);
        mDetailTextView.setVisibility(text != null ? VISIBLE : GONE);
    }

    public void setTitleColor(int color) {
    	mTitleTextView.setTextColor(color);
    }

    public void setDetailColor(int color) {
    	mDetailTextView.setTextColor(color);
    }

    public void setButton(String text, OnClickListener onClickListener) {
        mButton.setText(text);

        mButton.setVisibility(text != null ? VISIBLE : GONE);
        mButton.setOnClickListener(onClickListener);
    }
}
