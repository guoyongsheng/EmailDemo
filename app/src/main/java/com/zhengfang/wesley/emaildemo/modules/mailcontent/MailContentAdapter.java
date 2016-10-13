package com.zhengfang.wesley.emaildemo.modules.mailcontent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhengfang.wesley.emaildemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 2016/10/12.
 * 适配器
 */
public class MailContentAdapter extends RecyclerView.Adapter<MailContentAdapter.ViewHolder> {

    private List<String> list = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public MailContentAdapter(Context context) {
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
        View view = inflater.inflate(R.layout.item_mail_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(list == null || list.size() <= position || holder == null){
            return;
        }

        String name = list.get(position);
        holder.tv_name.setText(name);
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
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

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    void setDataSource(ArrayList<String> attachments) {
        this.list = attachments;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.item_mail_content_name);
        }
    }
}
