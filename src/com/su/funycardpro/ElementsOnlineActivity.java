package com.su.funycardpro;

/**
 * 我购买/收藏的元素
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.su.funycard.adapter.CardFileGridAdapter;
import com.su.funycard.adapter.PicGridAdapter;
import com.su.funycard.bean.PicBean;
import com.su.funycard.bean.PicFileBean;
import com.su.funycard.common.Constant;
import com.su.funycard.net.ElementService;
import com.su.funycard.uiwidget.MyGridView;
import com.su.funycard.util.AsynTaskVoid;
import com.su.funycard.util.FileUtil;
import com.su.funycard.util.HttpUtil.MLog;
import com.su.funycard.util.Str;
import com.su.funycardpro.R;

public class ElementsOnlineActivity extends BaseActivity {
	private static final String TAG = "MyCardActivity";

	private GridView grid_pic;

	private View loadMoreView;

	private Button loadMoreButton;
	private LinearLayout ll_image;

	private MyGridView mGridView;
	private int page = 0;
	private Button moreButton;
	private ImageView imageView;

	private PicGridAdapter adapter;

	private List<PicBean> picBeans;

	private Button btn_llimg_getit;
	private int currentEle = 0;

	private String catid;
	private String catname;

	private TextView tv_eletitle;

	private Button btn_mkcard;

	private Button btn_myele;

	private ElementService elementService;

	private Button btn_order_like;

	private Button btn_order_new;

	private Button btn_order_star;
	private String order = "uploadtime";

	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_elementsonline);
		catid = this.getIntent().getStringExtra("catid");
		catname = this.getIntent().getStringExtra("catname");
		elementService = new ElementService();

		MLog.e("catBeans.get(position).getId()" + catid);
		super.onCreate(savedInstanceState);

	}

	private void loadData(int i) {
		new AsynTaskVoid() {
			@Override
			protected String doInBackground(Void... params) {
				// MLog.e("" + page);
				picBeans = elementService.getElements(catid, page, 12, order);
				return null;
			}

			protected void onPreExecute() {
				showProDialog();
			};

			protected void onPostExecute(String result) {
				dismissProDialog();
				if (picBeans == null) {
					Toast("This is the last page");
					return;
				}
				if (picBeans.size() < 12) {
					moreButton.setVisibility(View.GONE);
				} else {
					moreButton.setVisibility(View.VISIBLE);
				}
				if (page == 0) {

					adapter = new PicGridAdapter(ElementsOnlineActivity.this,
							picBeans);

					mGridView.setAdapter(adapter);

				} else {
					adapter.addItems(picBeans);

				}
				page += 12;
			};
		}.execute();

		// if (count > 23) {
		// moreButton.setVisibility(View.GONE);
		// }

	}

	@Override
	protected void findViewById() {
		mGridView = (MyGridView) findViewById(R.id.gridview);
		moreButton = (Button) findViewById(R.id.moreButton);
		imageView = (ImageView) findViewById(R.id.imageView);
		ll_image = (LinearLayout) findViewById(R.id.ll_image);

		btn_llimg_getit = (Button) findViewById(R.id.btn_llimg_getit);

		btn_order_like = (Button) findViewById(R.id.btn_order_like);
		btn_order_new = (Button) findViewById(R.id.btn_order_new);
		btn_order_star = (Button) findViewById(R.id.btn_order_star);

		tv_eletitle = (TextView) findViewById(R.id.tv_eletitle);
		if (catname != null) {
			tv_eletitle.setText(catname);
		}
		// ll_image.setAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.pushin));

	}

	@Override
	protected void setListener() {
		btn_llimg_getit.setOnClickListener(clicklistener);

		moreButton.setOnClickListener(clicklistener);

		btn_order_like.setOnClickListener(clicklistener);
		btn_order_new.setOnClickListener(clicklistener);
		btn_order_star.setOnClickListener(clicklistener);

		btn_llimg_getit.setOnClickListener(clicklistener);

		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (ll_image.getVisibility() == View.GONE) {
					ll_image.setVisibility(View.VISIBLE);
					ll_image.startAnimation(AnimationUtils.loadAnimation(
							ElementsOnlineActivity.this, R.anim.pushin));

				}
				imageView.setImageDrawable(((ImageView) arg1
						.findViewById(R.id.img_picgrid)).getDrawable());
				currentEle = arg2;
				sid = ((PicBean) adapter.getItem(currentEle)).getSid();

			}
		});

	}

	private String sid = "0";
	private View.OnClickListener clicklistener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.moreButton:
				loadData(page);
				// MLog.e("" + page);
				break;
			case R.id.btn_llimg_getit:
				getEle();
				break;
			case R.id.btn_order_like:
				adapter.clear();
				order = "good";
				page = 0;
				loadData();

				break;
			case R.id.btn_order_new:
				adapter.clear();
				order = "uploadtime";
				page = 0;
				loadData();
				break;
			case R.id.btn_order_star:

				break;

			default:
				break;
			}

		}

	};

	private void getEle() {
		// 保存选中的图片 其实就是 移动图片到指定地方
		// Constant.MAINFOLDER + Str.md5(url);
		String url = ((PicBean) adapter.getItem(currentEle)).getImageurl();
		FileUtil.copyFile(Constant.ELEOLPATH + Str.md5(url), Constant.ELEPATH
				+ getData() + Str.md5(url));
		Toast("Added Success!");

	}

	@Override
	protected void loadData() {
		loadData(page);

	}

	private String getData() {
		SimpleDateFormat sdf = new SimpleDateFormat("",
				Locale.SIMPLIFIED_CHINESE);
		sdf.applyPattern("MMddHHmmss");
		return sdf.format(new Date());

	}

}