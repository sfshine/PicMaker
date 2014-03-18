package com.su.funycardpro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import com.su.funycardpro.R;

public abstract class BaseActivity extends Activity {
	private static final String TAG = "MyCardActivity";
	protected Context context;

	private Dialog dialog;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		context = this;
		findViewById();
		setListener();
		loadData();
	}

	protected abstract void findViewById();

	protected abstract void setListener();

	protected abstract void loadData();

	public void Toast(String text) {
		android.widget.Toast.makeText(this, text, 10).show();
	}

	public void showProDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Please Wait...");

		}
		progressDialog.show();

		// Builder builder = new AlertDialog.Builder(this);
		// dialog = builder.create();
		// ProgressBar progressBar = new ProgressBar(this);
		// LayoutParams params = new LayoutParams(100, 100);
		// dialog.addContentView(progressBar, params);
		// dialog.show();

	}

	public void dismissProDialog() {
		progressDialog.dismiss();
		// dialog.dismiss();

	}
}
