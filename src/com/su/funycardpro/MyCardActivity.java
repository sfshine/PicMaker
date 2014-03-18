package com.su.funycardpro;

/**
 * 我制作的卡片
 */
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.su.funycard.adapter.CardFileGridAdapter;
import com.su.funycard.bean.PicFileBean;
import com.su.funycard.common.Constant;
import com.su.funycard.util.FileUtil;
import com.su.funycardpro.R;

public class MyCardActivity extends BaseActivity {
	private static final String TAG = "MyCardActivity";
	private GridView gridView;
	private List<PicFileBean> pictureFiles;
	private CardFileGridAdapter picFileGridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_mycard);
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void findViewById() {
		gridView = (GridView) findViewById(R.id.gridview);

	}

	@Override
	protected void setListener() {
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent intent = new Intent(context, MyCardViewActivity.class);
				intent.putExtra("img_path", pictureFiles.get(position)
						.getPath());
				startActivityForResult(intent, 00000);

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 123) {
			loadData();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	@Override
	protected void loadData() {
		new AsyncTask<Void, Void, Void>() {

			protected Void doInBackground(Void... params) {
				pictureFiles = FileUtil.getImageNames(Constant.CARDPATH);

				ComparatorUser comparator = new ComparatorUser();
				Collections.sort(pictureFiles, comparator);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				picFileGridAdapter = new CardFileGridAdapter(context,
						pictureFiles);
				gridView.setAdapter(picFileGridAdapter);
				super.onPostExecute(result);
			}
		}.execute();

	};

	public class ComparatorUser implements Comparator<PicFileBean> {

		@Override
		public int compare(PicFileBean lhs, PicFileBean rhs) {
			int flag = lhs.getName().compareTo(rhs.getName());

			return -flag;
		}

	}

}
