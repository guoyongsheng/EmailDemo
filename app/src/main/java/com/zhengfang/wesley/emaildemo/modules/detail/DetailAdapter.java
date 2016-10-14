package com.zhengfang.wesley.emaildemo.modules.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhengfang.wesley.emaildemo.R;
import com.zhengfang.wesley.emaildemo.entitiy.Email;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 2016/10/9.
 * 详情适配器
 */
class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    private List<Email> list = new ArrayList<>();
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private boolean isRefreshing;

    DetailAdapter(Context context) {
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (list == null || list.size() <= position) {
            return;
        }

        Email email = list.get(position);
        String email_from = email.getFrom();
        String email_subject = email.getSubject();
        String email_time = email.getSentdata();

        holder.tv_email_from.setText(email_from);
        holder.tv_email_subject.setText(email_subject);
        holder.tv_email_time.setText(email_time);

        //点击事件
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(list.get(position), position);
            }
        });

        holder.rl_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                onItemClickListener.showDialogDelete(list.get(position).getMessageID(), position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    void addAll(List<Email> list) {
        if (isRefreshing) {
            this.list.clear();
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    void removeItem(int position) {
        if (list != null && list.size() > position) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    void setIsRefreshing(boolean isRefreshing) {
        this.isRefreshing = isRefreshing;
    }


    /**
     * 静态内部类
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_email_from; //邮件来源
        private TextView tv_email_subject; //邮件主题
        private TextView tv_email_time; //邮件发送日期
        private RelativeLayout rl_item; //整个布局

        ViewHolder(View itemView) {
            super(itemView);


            tv_email_from = (TextView) itemView.findViewById(R.id.item_detail_from);
            tv_email_subject = (TextView) itemView.findViewById(R.id.item_detail_subject);
            tv_email_time = (TextView) itemView.findViewById(R.id.item_detail_time);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.item_detail);
        }
    }
}
