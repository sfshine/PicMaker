package com.su.funycardpro;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.su.funycard.bean.UserBean;
import com.su.funycard.net.UserService;
import com.su.funycard.util.AsynTaskVoid;
import com.su.funycard.util.DataStoreUtil;
import com.su.funycard.util.Str;
import com.su.funycardpro.R;

public class PlayerLoginActivity extends BaseActivity {
	private EditText et_email, et_pwd;
	private UserBean userBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_playerlogin);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void findViewById() {
		et_email = (EditText) findViewById(R.id.et_email);
		et_pwd = (EditText) findViewById(R.id.et_pwd);

	}

	@Override
	protected void setListener() {

	}

	@Override
	protected void loadData() {

	}

	public void back(View view) {
		finish();

	}

	public void ok(View view) {
		String email = et_email.getText().toString().trim();
		String pwd = et_pwd.getText().toString().trim();

		if (!isEmail(email)) {
			Toast("Please input correct email!");
		} else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)) {
			Toast("Please input all of the blanks!");

		} else {
			userBean = new UserBean();
			userBean.setEmail(email);
			userBean.setPwd(Str.md5(pwd));
			startLogin();

		}

	}

	public boolean isEmail(String email) {

		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		return matcher.matches();

	}

	public void register(View view) {
		Intent intent = new Intent(PlayerLoginActivity.this,
				PlayerRegesiterActivity.class);
		startActivity(intent);
		finish();

	}

	public void startLogin() {
		new AsynTaskVoid() {
			protected String doInBackground(Void... params) {

				return new UserService().checkUser(userBean).trim();

			};

			protected void onPostExecute(String result) {
				if (result.contains("failed")) {
					Toast("failed");
				} else {
					DataStoreUtil.put(PlayerLoginActivity.this, "email",
							userBean.getEmail());
					DataStoreUtil.put(PlayerLoginActivity.this, "pwd",
							Str.md5(userBean.getPwd()));
					DataStoreUtil.put(PlayerLoginActivity.this, "id", result);
					XtickerApp.userBean = userBean;
					XtickerApp.userBean.setId(result);

					Toast("success");
					finish();
				}

			};

		}.execute();

	}
}
