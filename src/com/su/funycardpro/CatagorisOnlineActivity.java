package com.su.funycardpro;

import java.util.ArrayList;
import java.util.List;

import com.su.funycard.bean.CatBean;
import com.su.funycard.net.CatagoryService;
import com.su.funycard.util.AsynTaskVoid;
import com.su.funycard.util.HttpUtil.MLog;
import com.su.funycardpro.R;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class CatagorisOnlineActivity extends BaseActivity {
	private GridView grid_cat;
	private List<CatBean> catBeans = new ArrayList<CatBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_catsonline);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void findViewById() {
		grid_cat = (GridView) findViewById(R.id.grid_cat);
		grid_cat.setGravity(Gravity.CENTER);
		grid_cat.setNumColumns(2);
	//	grid_cat.setVerticalScrollBarEnabled(true);
		grid_cat.setPadding(80, 80, 80, 80);

	}

	@Override
	protected void setListener() {
		grid_cat.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(CatagorisOnlineActivity.this,
						ElementsOnlineActivity.class);
				intent.putExtra("catid", catBeans.get(position).getId() + "");
				intent.putExtra("catname", catBeans.get(position).getCat());

				startActivity(intent);

			}
		});

	}

	@Override
	protected void loadData() {
		new AsynTaskVoid() {

			protected String doInBackground(Void... params) {
				catBeans = new CatagoryService().getCatagory();
				return null;

			};

			protected void onPostExecute(String result) {
				if (catBeans == null) {
					return;
				}
				grid_cat.setAdapter(new CatAdapter());

			};
		}.execute();

	}

	private class CatAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return catBeans.size();
		}

		@Override
		public Object getItem(int position) {

			return catBeans.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(CatagorisOnlineActivity.this);
			textView.setGravity(Gravity.CENTER);
			textView.setText(catBeans.get(position).getCat());
			textView.setBackgroundResource(R.drawable.bkg_grey_stroke);
			textView.setHeight(60);
			textView.setTextColor(Color.BLACK);
			return textView;
		}

	}
}
