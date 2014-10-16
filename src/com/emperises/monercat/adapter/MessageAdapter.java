package com.emperises.monercat.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emperises.monercat.R;
public class MessageAdapter extends BaseAdapter {
    private List<MessageModel> listModel;
    private Context context;
    private ViewHolder holder;
    private int headerImageResId;
    private String headerImageResPath;
    class ViewHolder {
        TextView messageA;
        TextView dateA;
        TextView messageB;
        TextView dateB;
        ImageView headerB;
        LinearLayout layoutB;
        LinearLayout layoutA;

    }

    public MessageAdapter(Context context, List<MessageModel> listModel) {
        this.context = context;
        this.listModel = listModel;
        holder = new ViewHolder();
    }

    @Override
    public int getCount() {
        return listModel.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.list_message_item,
            null);
        holder.messageA = (TextView) convertView.findViewById(R.id.messageA);
        holder.dateA = (TextView) convertView.findViewById(R.id.dateA);
        holder.messageB = (TextView) convertView.findViewById(R.id.messageB);
        holder.dateB = (TextView) convertView.findViewById(R.id.dateB);
        holder.layoutB = (LinearLayout) convertView.findViewById(R.id.layoutB);
        holder.layoutA = (LinearLayout) convertView.findViewById(R.id.layoutA);
        holder.headerB = (ImageView) convertView.findViewById(R.id.user_image);

        if (listModel.get(position).isA()) {
            holder.layoutB.setVisibility(View.GONE);
            holder.messageA.setText(listModel.get(position).getMessage());
            holder.dateA.setText(listModel.get(position).getDate());
        } else {
            holder.layoutA.setVisibility(View.GONE);
            holder.messageB.setText(listModel.get(position).getMessage());
            holder.dateB.setText(listModel.get(position).getDate());
//            holder.headerB.setImageResource(headerImageResId);
            //TODO:头像变更为做处理
        }
        return convertView;
    }

	public int getHeaderImageResId() {
		return headerImageResId;
	}

	public void setHeaderImageResId(int headerImageResId) {
		this.headerImageResId = headerImageResId;
	}

	public String getHeaderImageResPath() {
		return headerImageResPath;
	}

	public void setHeaderImageResPath(String headerImageResPath) {
		this.headerImageResPath = headerImageResPath;
	}

}
