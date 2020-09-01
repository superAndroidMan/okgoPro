package com.study.httpframework.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by superman on 2017/8/2.
 */

public abstract class BaseRecycleListAdapter<T> extends RecyclerView.Adapter {

    protected List<T> mList = new ArrayList<T>();
    protected Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;

    public BaseRecycleListAdapter(List<T> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(getLayoutId(), parent, false);
        return createHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mList.size() > position){
            if (holder != null) {
                holder.itemView.setTag(holder);
                holder.itemView.setOnLongClickListener(onLongClickListener);
                holder.itemView.setOnClickListener(onClickListener);
            }
            onBindDefaultViewHolder(holder, mList.get(position), position);
        }
    }

    public List<T> getList() {
        return mList;
    }

    public void appendToList(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
    }

    public void append(T t) {
        if (t == null) {
            return;
        }
        mList.add(t);
    }

    public void appendToTop(T item) {
        if (item == null) {
            return;
        }
        mList.add(0, item);
    }

    public void appendToTopList(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(0, list);
    }


    public void remove(int position) {
        if (position < mList.size() - 1 && position >= 0) {
            mList.remove(position);
        }
    }

    public void clear() {
        mList.clear();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public abstract int getLayoutId();

    public abstract RecyclerView.ViewHolder createHolder(View view);

    protected abstract void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, T bean, int position);

    /**
     * 添加项点击事件
     *
     * @param onItemClickListener the RecyclerView item click listener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 添加项点长击事件
     *
     * @param onItemLongClickListener the RecyclerView item long click listener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 可以共用同一个listener，相对高效
     */
    public static abstract class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            onClick(holder.getAdapterPosition(), holder.getItemId());
        }

        public abstract void onClick(int position, long itemId);
    }

    /**
     * 可以共用同一个listener，相对高效
     */
    public static abstract class OnLongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            return onLongClick(holder.getAdapterPosition(), holder.getItemId());
        }

        public abstract boolean onLongClick(int position, long itemId);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, long itemId);
    }

    public interface OnItemLongClickListener {
        void onLongClick(int position, long itemId);
    }


}
