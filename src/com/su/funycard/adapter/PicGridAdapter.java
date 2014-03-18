package com.su.funycard.adapter;

import java.util.List;

import com.su.funycardpro.R;
import com.su.funycard.bean.PicBean;
import com.su.funycard.util.AsyncImageLoader;
import com.su.funycard.util.BitmapUtil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PicGridAdapter extends BaseAdapter {
	Context mContext;
	List<PicBean> picBeans;
	AsyncImageLoader asyncImageLoader;

	public PicGridAdapter(Context context, List<PicBean> picBeans) {
		this.mContext = context;
		this.picBeans = picBeans;
		this.asyncImageLoader = new AsyncImageLoader();
	}

	public void clear() {
		picBeans.clear();
	}

	@Override
	public int getCount() {
		return picBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return picBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_picgrid, null);
		}
		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.img_picgrid);

		asyncImageLoader.setAsyDrawableFromurl(picBeans.get(position)
				.getImageurl(), imageView);
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