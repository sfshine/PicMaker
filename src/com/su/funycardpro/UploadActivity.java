package com.su.funycardpro;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.su.funycard.bean.CatBean;
import com.su.funycard.bean.PicBean;
import com.su.funycard.net.CatagoryService;
import com.su.funycard.net.ElementService;
import com.su.funycard.util.AsynTaskVoid;
import com.su.funycard.util.HttpUtil.MLog;
import com.su.funycardpro.R;

public class UploadActivity extends BaseActivity {
	private Button btn_chose_file, btn_chose_cat, btn_upload;
	private List<CatBean> catBeans = new ArrayList<CatBean>();
	private boolean islogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_upload);
		islogin = !(XtickerApp.userBean.getId() == null || XtickerApp.userBean
				.getId().length() == 0);

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void findViewById() {
		btn_chose_cat = (Button) findViewById(R.id.btn_chose_cat);
		btn_chose_file = (Button) findViewById(R.id.btn_chose_file);
		btn_upload = (Button) findViewById(R.id.btn_upload);

	}

	@Override
	protected void setListener() {
		btn_chose_cat.setOnClickListener(btnlistener);
		btn_chose_file.setOnClickListener(btnlistener);
		btn_upload.setOnClickListener(btnlistener);

	}

	private final int SELECTPIC = 1122;
	private OnClickListener btnlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_chose_file:
				Intent intent = new Intent(context, GalleryActivity.class);
				// Intent intent = new Intent(context,
				// FileManagerActivity.class);
				startActivityForResult(intent, SELECTPIC);
				break;
			case R.id.btn_chose_cat:
				showCatsChoiceDialog();
				break;

			case R.id.btn_upload:
				// if (!islogin) {
				//
				// Intent intent2 = new Intent(UploadActivity.this,
				// PlayerLoginActivity.class);
				// startActivity(intent2);
				// } else
				if (picpath == null) {
					break;
				}

				else {
					upload();
				}
				break;

			default:
				break;
			}

		}
	};

	private int catid = -1;

	private void showCatsChoiceDialog() {
		if (catBeans.size() == 0) {
			return;
		}
		final String[] cats = getCats(catBeans);
		Builder dialog = new AlertDialog.Builder(context);

		dialog.setItems(cats, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				catid = catBeans.get(which).getId();
				btn_chose_cat.setText(cats[which]);
			}
		});
		dialog.show();

	}

	private String[] getCats(List<CatBean> catBeans) {
		String[] cats = new String[catBeans.size()];
		for (int i = 0; i < catBeans.size(); i++) {
			cats[i] = catBeans.get(i).getCat();

		}
		return cats;
	}

	private String picpath;

	private void upload() {
		new AsynTaskVoid() {
			protected void onPreExecute() {
				showProDialog();
			};

			protected String doInBackground(Void... params) {
				File file = new File(picpath);
				if (file.length() > 1024 * 100) {
					return "File Too Big";
				}
				if (catid == -1) {
					return "Not Successful, Select a Category";
				}

				PicBean picBean = new PicBean();
				picBean.setImageurl(picpath);
				picBean.setUid(XtickerApp.userBean.getId());
				picBean.setCatagory(catid + "");
				String mresult = new ElementService().uploadElements(picBean);
				return mresult;

			};

			protected void onPostExecute(String result) {
				dismissProDialog();
				if (result != null && result.contains("success")) {
					result = "Upload Success";
				}
				Toast(result);

			};
		}.execute();

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != RESULT_OK) {
			// finish();
			return;
		}
		switch (requestCode) {
		case SELECTPIC:

			picpath = data.getStringExtra("picpath");

			if (picpath != null) {
				btn_chose_file.setText(picpath);
			}

			break;

		default:
			break;
		}

	}

	@Override
	protected void loadData() {
		new AsynTaskVoid() {

			protected String doInBackground(Void... params) {
				catBeans = new CatagoryService().getCatagory();
				return null;

			};

			protected void onPostExecute(String result) {

			};
		}.execute();

	}

}
