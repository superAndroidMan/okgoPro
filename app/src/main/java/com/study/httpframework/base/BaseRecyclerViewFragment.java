package com.study.httpframework.base;

import android.os.Handler;
import android.view.View;


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
public abstract class BaseRecyclerViewFragment<T> extends BaseFragment implements OnRefreshLoadMoreListener, OnItemClickListener {
    protected BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    protected RecyclerView mRecyclerView;
    protected boolean mIsRefresh = true;
    private CommonEmptyView commonEmpty;
    protected int mCurrentPage = 1;
    private String TAG = getClass().getSimpleName();
    protected boolean isFirstLoad = true;
    public SmartRefreshLayout refreshLayout;
    private boolean needLoadMore = true;
    protected LinearLayoutManager linearLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recycler_view;
    }

    @Override
    public void initView(View root) {
        refreshLayout = (SmartRefreshLayout) root.findViewById(R.id.refreshLayout);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        commonEmpty = (CommonEmptyView) root.findViewById(R.id.common_empty);
    }

    @Override
    public void initData() {
        mAdapter = getRecyclerAdapter();
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh();
            }
        });
        refreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (refreshLayout != null)
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
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
        refresh();
    }

    @Override
    public void refresh() {
        super.refresh();
        mCurrentPage = 1;
        mIsRefresh = true;
        requestData();
    }


    protected void requestData() {
    }

    protected void onRequestStart() {

    }

    protected void onRequestSuccess(int code) {

    }

    protected void onRequestFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onComplete();
            }
        },500);
    }

    protected void onComplete() {
        refreshLayout.finishRefresh();
        refreshLayout.setNoMoreData(false);//恢复上拉状态
        mIsRefresh = false;
    }

    protected void setListData(List<T> data) {
        //is refresh
        if (mIsRefresh) {
            //cache the time
            mAdapter.setList(data);
            refreshLayout.finishRefresh();
            if (isNeedLoadMore())
                refreshLayout.setNoMoreData(false);//恢复上拉状态
        } else {
            mAdapter.addData(data);
            refreshLayout.finishLoadMore();
        }
        if (data!= null && data.size() == 0) {
            refreshLayout.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
        }
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        return linearLayoutManager;
    }

    protected abstract BaseQuickAdapter<T,BaseViewHolder> getRecyclerAdapter();

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

    public boolean isNeedLoadMore() {
        return needLoadMore;
    }

    public void setNeedLoadMore(boolean needLoadMore) {
        this.needLoadMore = needLoadMore;
        refreshLayout.setEnableLoadMore(isNeedLoadMore());
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

    }

}
