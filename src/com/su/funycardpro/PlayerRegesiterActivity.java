package com.su.funycardpro;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

import com.su.funycard.bean.UserBean;
import com.su.funycard.net.UserService;
import com.su.funycard.util.AsynTaskVoid;
import com.su.funycard.util.DataStoreUtil;
import com.su.funycard.util.Str;
import com.su.funycardpro.R;

public class PlayerRegesiterActivity extends BaseActivity {
	private EditText et_email, et_pwd, et_confirmpwd;
	private UserBean userBean;
	private CheckBox checkbox1;
	private CheckBox checkbox2;
	private Button btnok;
	private LinearLayout ll_register;
	private LinearLayout ll_forgot;
	private boolean forgotmode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_playeregester);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void findViewById() {
		et_email = (EditText) findViewById(R.id.et_email);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		et_confirmpwd = (EditText) findViewById(R.id.et_confirmpwd);
		checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
		checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
		btnok = (Button) findViewById(R.id.btnok);
		btnok.setEnabled(false);
		btnok.setBackgroundResource(R.drawable.bkg_grey_stroke);

		ll_register = (LinearLayout) findViewById(R.id.ll_register);
		ll_forgot = (LinearLayout) findViewById(R.id.ll_forgot);
		forgotmode = this.getIntent().getBooleanExtra("forgot", false);
		if (forgotmode) {
			ll_register.setVisibility(View.GONE);
			ll_forgot.setVisibility(View.VISIBLE);
			btnok.setEnabled(true);
			btnok.setBackgroundColor(0xFF4BCCEC);
			btnok.setTextColor(0xFF000000);
		}

	}

	@Override
	protected void setListener() {
		checkbox1.setOnCheckedChangeListener(onCheckedChangeListener);
		checkbox2.setOnCheckedChangeListener(onCheckedChangeListener);

	}

	public OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (!isChecked) {
				return;
			}
			String url = null;
			switch (buttonView.getId()) {
			case R.id.checkbox1:
				url = "http://xtickerx.com/site/rules.html";
				break;
			case R.id.checkbox2:
				url = "http://xtickerx.com/site/terms.html";
				break;

			default:
				break;
			}
			Intent intent = new Intent(PlayerRegesiterActivity.this,
					WebViewActivity.class);
			intent.putExtra("URL", url);
			startActivity(intent);
			if (checkbox1.isChecked() && checkbox2.isChecked()) {
				btnok.setEnabled(true);
				btnok.setBackgroundColor(0xFF4BCCEC);
				btnok.setTextColor(0xFF000000);
			} else {
				btnok.setEnabled(false);
				btnok.setBackgroundResource(R.drawable.bkg_grey_stroke);
				btnok.setTextColor(0xFFDDDDDD);
			}

		}
	};

	@Override
	protected void loadData() {

	}

	public void back(View view) {
		finish();

	}

	public void clean(View view) {
		et_email.setText("");
		et_pwd.setText("");
		et_confirmpwd.setText("");

	}

	public void ok(View view) {
		if (forgotmode) {
			String email = et_email.getText().toString().trim();

		} else {
			String email = et_email.getText().toString().trim();
			String pwd = et_pwd.getText().toString().trim();
			String confirmpwd = et_confirmpwd.getText().toString().trim();
			if (!isEmail(email)) {
				Toast("Please input correct email!");
			} else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)
					|| TextUtils.isEmpty(confirmpwd)) {
				Toast("Please input all of the blanks!");

			} else if (!pwd.equals(confirmpwd)) {
				Toast("Twice of the inputting password is not identical");
			}

			else {
				userBean = new UserBean();
				userBean.setEmail(email);
				userBean.setPwd(Str.md5(pwd));
				startRegister();

			}
		}

	}

	public boolean isEmail(String email) {

		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		return matcher.matches();

	}

	public void startRegister() {
		new AsynTaskVoid() {
			protected String doInBackground(Void... params) {

				return new UserService().addUser(userBean).trim();

			};

			protected void onPostExecute(String result) {
				if (result.contains("Duplicate")) {
					Toast("The Email has exists!");
				} else if (result.contains("failed")) {
					Toast("Create Faileds");
				} else {

					DataStoreUtil.put(PlayerRegesiterActivity.this, "email",
							userBean.getEmail());
					DataStoreUtil.put(PlayerRegesiterActivity.this, "pwd",
							Str.md5(userBean.getPwd()));
					DataStoreUtil.put(PlayerRegesiterActivity.this, "id",
							result);
					XtickerApp.userBean = userBean;
					XtickerApp.userBean.setId(result);
					Toast("Success!your id is" + result);
				}

			};

		}.execute();

	}
}
