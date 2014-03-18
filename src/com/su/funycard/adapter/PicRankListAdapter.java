package com.su.funycard.adapter;

import java.util.List;

import com.su.funycardpro.R;
import com.su.funycard.bean.PicBean;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PicRankListAdapter extends BaseAdapter {
	Context context;
	List<PicBean> picBeans;

	public PicRankListAdapter(Context context, List<PicBean> picBeans) {
		this.context = context;
		this.picBeans = picBeans;
	}

	@Override
	public int getCount() {
		return picBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_picrank, null);

		}
		PicBean picBean = picBeans.get(position);
		TextView tv_picname = (TextView) convertView
				.findViewById(R.id.tv_picname);
		TextView tv_picauthor = (TextView) convertView
				.findViewById(R.id.tv_picauthor);
		ImageView img_pic = (ImageView) convertView.findViewById(R.id.img_pic);
		tv_picauthor.setText(picBean.getUid() + "");
		tv_picname.setText(picBean.getName());

		return convertView;
	}

	public void addItem(PicBean picBean) {
		picBeans.add(picBean);
		notifyDataSetChanged();
	}

	public void addItems(List<PicBean> picbeans) {
		picBeans.addAll(picbeans);
		notifyDataSetChanged();

	}

}