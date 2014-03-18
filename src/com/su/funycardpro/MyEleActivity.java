package com.su.funycardpro;

/**
 * 我收藏的的卡片
 */
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.su.funycard.adapter.EleFileGridAdapter;
import com.su.funycard.bean.PicFileBean;
import com.su.funycard.common.Constant;
import com.su.funycard.util.FileUtil;
import com.su.funycardpro.R;
import com.su.funycardpro.MyCardActivity.ComparatorUser;

public class MyEleActivity extends BaseActivity {
	private static final String TAG = "MyCardActivity";
	private GridView gridView;
	private List<PicFileBean> pictureFiles;
	private EleFileGridAdapter picFileGridAdapter;
	private String isselect;
	private LinearLayout ll_image;
	private Button btn_llimg_back;
	private Button btn_llimg_delete;
	private ImageView imageView;
	private String currentEle = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_myelements);
		super.onCreate(savedInstanceState);
		isselect = this.getIntent().getStringExtra("isselect");

	}

	@Override
	protected void findViewById() {
		gridView = (GridView) findViewById(R.id.gridview);
		ll_image = (LinearLayout) findViewById(R.id.ll_image);

		btn_llimg_delete = (Button) findViewById(R.id.btn_llimg_delete);
		imageView = (ImageView) findViewById(R.id.imageView);
	}

	protected void setListener() {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				if (isselect != null) {
					Intent newdata = new Intent(MyEleActivity.this,
							MakeCardActivity.class);
					newdata.putExtra("picpath", pictureFiles.get(position)
							.getPath());
					setResult(-1, newdata);

					finish();
				} else {
					if (ll_image.getVisibility() == View.GONE) {
						ll_image.setVisibility(View.VISIBLE);
						ll_image.startAnimation(AnimationUtils.loadAnimation(
								MyEleActivity.this, R.anim.pushin));

					}
					imageView.setImageDrawable(((ImageView) v
							.findViewById(R.id.image)).getDrawable());
					currentEle = pictureFiles.get(position).getPath();
				}

			}
		});

		btn_llimg_delete.setOnClickListener(btnlistener);

	}

	private OnClickListener btnlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.btn_llimg_delete:
				ll_image.setVisibility(View.GONE);

				File file = new File(currentEle);
				file.delete();
				loadData();
				break;
			default:
				break;
			}

		}
	};

	@Override
	protected void loadData() {
		new AsyncTask<Void, Void, Void>() {

			protected Void doInBackground(Void... params) {
				pictureFiles = FileUtil.getImageNames(Constant.ELEPATH);
				ComparatorUser comparator = new ComparatorUser();
				Collections.sort(pictureFiles, comparator);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				picFileGridAdapter = new EleFileGridAdapter(context,
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
