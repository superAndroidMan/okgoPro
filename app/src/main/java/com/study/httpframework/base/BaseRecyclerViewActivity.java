package com.study.httpframework.base;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.study.httpframework.R;
import com.study.httpframework.widget.CommonEmptyView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 基本列表类，重写getLayoutId()自定义界面
 * Created by superman
 * on 2016/4/12.
 */
@SuppressWarnings("unused")
public abstract class BaseRecyclerViewActivity<T> extends BaseTitleBarActivity implements OnRefreshLoadMoreListener, OnItemClickListener {
    protected BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    protected RecyclerView mRecyclerView;
    protected boolean mIsRefresh;
    private CommonEmptyView commonEmpty;
    private TextView bottomItem;
    private LinearLayout llBottom;
    private boolean needLoadMore = true;
    public SmartRefreshLayout refreshLayout;
    protected int mCurrentPage = 1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recycler_view_activity;
    }

    @Override
    public void initView() {
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        commonEmpty = (CommonEmptyView) findViewById(R.id.common_empty);
        bottomItem = (TextView) findViewById(R.id.bottom_item);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
    }

    @Override
    public void initData() {
        setTitle(getCustomTitle());
        mAdapter = getRecyclerAdapter();
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                autoRefresh();
            }
        });
        refreshLayout.setOnRefreshLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    public void autoRefresh() {
        refreshLayout.autoRefresh();
    }

    protected abstract String getCustomTitle();

    @Override
    public void onStop() {
        super.onStop();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (isNeedLoadMore()) {
            mCurrentPage++;
            mIsRefresh = false;
            requestData();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        mIsRefresh = true;
        requestData();
    }

    protected abstract void requestData();

    protected void onRequestSuccess(int code) {

    }

    protected void onRequestFinish() {
        onComplete();
    }

    protected void onComplete() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        refreshLayout.setNoMoreData(false);//恢复上拉状态
        mIsRefresh = false;
    }

    protected void setListData(List<T> data) {
        final int size = data == null ? 0 : data.size();
        //is refresh
        if (mIsRefresh) {
            //cache the time
            mAdapter.setNewData(data);
            refreshLayout.finishRefresh();
            if (isNeedLoadMore())
                refreshLayout.setNoMoreData(false);//恢复上拉状态
        } else {
            if (size > 0) {
                mAdapter.addData(data);
                refreshLayout.finishLoadMore();
            }
        }
        if (size == 0) {
            refreshLayout.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
        }
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    protected abstract BaseQuickAdapter<T, BaseViewHolder> getRecyclerAdapter();

    public void setErrorType(int type) {
        if (type == CommonEmptyView.HIDE_LAYOUT) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
        }
        commonEmpty.setErrorType(type);
    }

    public void setErrorType(int type, String buttonString, View.OnClickListener onClickListener) {
        if (type == CommonEmptyView.HIDE_LAYOUT) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
        }
        commonEmpty.setErrorType(type);
        commonEmpty.setButton(buttonString, onClickListener);
    }

    public void hideEmpty() {
        commonEmpty.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void setEmptyType(String title, int ResId) {
        commonEmpty.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        commonEmpty.setNoData(title, ResId);
    }

    public void setEmptyType(String title, int ResId, String emptyButton, View.OnClickListener onClickListener) {
        commonEmpty.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        commonEmpty.setNoData(title, ResId);
        commonEmpty.setButton(emptyButton, onClickListener);
    }

    public LinearLayout getLlBottom() {
        return llBottom;
    }

    public void setBottomItemText(String content) {
        bottomItem.setText(content);
        llBottom.setVisibility(View.VISIBLE);
    }

    public void setBottomItemGone() {
        llBottom.setVisibility(View.GONE);
    }

    public void hideBottomItemText() {
        llBottom.setVisibility(View.GONE);
    }

    public TextView getBottomItem() {
        return bottomItem;
    }

    public void onButtomItemClick(View view) {

    }

    public boolean isNeedLoadMore() {
        return needLoadMore;
    }

    public void setNeedLoadMore(boolean needLoadMore) {
        this.needLoadMore = needLoadMore;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

}
