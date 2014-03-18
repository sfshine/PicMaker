// 2012-4-18下午07:46:57

package com.su.funycard.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.su.funycardpro.R;
import com.su.funycard.bean.PicFileBean;
import com.su.funycard.util.AsyncImageLoader;

public class CardFileGridAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<PicFileBean> pictures;
	private Context context;

	public CardFileGridAdapter(Context context, List<PicFileBean> pictures) {
		super();
		this.pictures = pictures;
		this.context = context;
		inflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		return pictures.size();

	}

	@Override
	public Object getItem(int position) {
		return pictures.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_mycard, null);
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.name.setText(pictures.get(position).getName());
		AsyncImageLoader asyncImageLoader = new AsyncImageLoader(context);
		asyncImageLoader.setAsyDrawable(pictures.get(position).getPath(),
				viewHolder.image);

		// viewHolder.image.setImageBitmap(BitmapFactory.decodeFile(pictures.get(
		// position).getPath()));
		return convertView;
	}

	public void addItems(List<PicFileBean> picFileBeans) {
		pictures.addAll(picFileBeans);
		notifyDataSetChanged();

	}

	private class ViewHolder {
		public TextView name;
		public ImageView image;
	}
}
