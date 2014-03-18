package com.su.funycardpro;

/**
 * 我购买的元素
 */
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.su.funycard.bean.UserBean;
import com.su.funycard.net.UserService;
import com.su.funycard.util.AsynTaskVoid;
import com.su.funycard.util.DataStoreUtil;
import com.su.funycardpro.R;

public class MyProfileUserActivity extends BaseActivity {
	private static final String TAG = "MyCardActivity";
	private UserBean userBean;
	private TextView tvEmail, tvCreatetime, tvElespace, tvDesignSpace;
	private Button btn_logout;
	private LinearLayout ll_myprofile;
	private LinearLayout ll_firstpage;
	private TextView tv_register;
	private TextView tv_login;
	private TextView tv_forogotpassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_myprofileuser);

		super.onCreate(savedInstanceState);

	};

	@Override
	protected void findViewById() {
		tvEmail = (TextView) findViewById(R.id.tv_email);
		tvCreatetime = (TextView) findViewById(R.id.tv_createtime);
		tvElespace = (TextView) findViewById(R.id.tv_elespace);
		tvDesignSpace = (TextView) findViewById(R.id.tv_designspace);
		ll_myprofile = (LinearLayout) findViewById(R.id.ll_myprofile);
		ll_firstpage = (LinearLayout) findViewById(R.id.ll_firstpage);

		tv_register = (TextView) findViewById(R.id.tv_register);
		tv_login = (TextView) findViewById(R.id.tv_login);
		btn_logout = (Button) findViewById(R.id.btn_logout);
		tv_forogotpassword = (TextView) findViewById(R.id.tv_forogotpassword);

	}

	public void logout() {
		DataStoreUtil.put(MyProfileUserActivity.this, "id", "0");
		btn_logout.setVisibility(View.GONE);
		ll_myprofile.setVisibility(View.GONE);
		ll_firstpage.setVisibility(View.VISIBLE);
		XtickerApp.userBean = new UserBean();

	}

	@Override
	protected void setListener() {
		tv_forogotpassword.setOnClickListener(clickListener);
		tv_login.setOnClickListener(clickListener);
		tv_register.setOnClickListener(clickListener);
		btn_logout.setOnClickListener(clickListener);

	}

	public OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = null;
			switch (v.getId()) {
			case R.id.btn_logout:
				logout();
				break;
			case R.id.tv_register:
				intent = new Intent(MyProfileUserActivity.this,
						PlayerRegesiterActivity.class);
				startActivityForResult(intent, 1111);

				break;
			case R.id.tv_login:
				intent = new Intent(MyProfileUserActivity.this,
						PlayerLoginActivity.class);
				startActivityForResult(intent, 1111);
				break;
			case R.id.tv_forogotpassword:
				intent = new Intent(MyProfileUserActivity.this,
						PlayerRegesiterActivity.class);
				intent.putExtra("forgot", true);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void loadData() {
		String id = XtickerApp.userBean.getId();
		if (id != null && !id.equals("0")) {
			ll_myprofile.setVisibility(View.VISIBLE);
			ll_firstpage.setVisibility(View.GONE);
			btn_logout.setVisibility(View.VISIBLE);
			btn_logout.setText("logout");
			new AsynTaskVoid() {
				protected String doInBackground(Void... params) {
					userBean = new UserService()
							.queryUserbyID(XtickerApp.userBean.getId());
					return null;
				};

				protected void onPostExecute(String result) {
					if (userBean != null) {
						tvEmail.setText("Email: " + userBean.getEmail());
						tvCreatetime.setText("CreactTime: "
								+ userBean.getCreatetime());
						tvDesignSpace.setText("DesignSpace: "
								+ userBean.getDesignspace() + "");
						tvElespace.setText("ElementSpace: "
								+ userBean.getElespace() + "");
					}

				};

			}.execute();
		} else {
			ll_myprofile.setVisibility(View.GONE);
			btn_logout.setText("login");
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		loadData();
		super.onActivityResult(requestCode, resultCode, data);

	}

	@SuppressLint("SimpleDateFormat")
	public static String formatTime(long timestamp) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年 MM月dd日");

		Date date = new Date(timestamp * 1000);

		sdf.format(date);

		return sdf.format(date);
	}
}
