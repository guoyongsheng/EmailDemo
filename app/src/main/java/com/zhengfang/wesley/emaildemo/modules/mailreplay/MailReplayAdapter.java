package com.zhengfang.wesley.emaildemo.modules.mailreplay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhengfang.wesley.emaildemo.R;
import com.zhengfang.wesley.emaildemo.entitiy.Attachment;
import com.zhengfang.wesley.emaildemo.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 2016/10/12.
 * 附件适配器
 */
class MailReplayAdapter extends RecyclerView.Adapter<MailReplayAdapter.ViewHolder> {

    private List<Attachment> list = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public MailReplayAdapter(Context context) {
        this.context = context;
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.item_mail_replay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder == null || list == null || list.size() <= position) {
            return;
        }

        Attachment attachment = list.get(position);
        String path = attachment.getFilePath();
        String name = attachment.getFileName();
        String length = attachment.getFileSize();

        GlideUtils.loadImage(path, holder.iv_image, context);
        holder.tv_image_name.setText(name);
        holder.tv_image_length.setText(length);

        holder.ll_layuot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
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

    //天机item
    void addItem(Attachment affInfos) {
        list.add(affInfos);
        notifyItemInserted(list.size());
        // notifyDataSetChanged();
    }

    List<Attachment> getDataSource() {
        return list;
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    void removeItem(int position) {
        if(list != null && list.size() > position){
            list.remove(position);
            notifyItemRemoved(position);
        }
    }


    //静态内部类
    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_image;//图片
        private TextView tv_image_name; //图片名
        private TextView tv_image_length; //图片大小
        private LinearLayout ll_layuot;

        ViewHolder(View itemView) {
            super(itemView);

            iv_image = (ImageView) itemView.findViewById(R.id.item_mail_replay_image);
            tv_image_name = (TextView) itemView.findViewById(R.id.item_mail_replay_image_name);
            tv_image_length = (TextView) itemView.findViewById(R.id.item_mail_replay_image_length);
            ll_layuot = (LinearLayout) itemView.findViewById(R.id.item_mail_linear);
        }
    }
}
