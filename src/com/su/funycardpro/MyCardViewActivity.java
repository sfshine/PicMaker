package com.su.funycardpro;

/**
 * 我购买的元素
 */
import java.io.File;
import java.io.FileInputStream;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.su.funycard.uiwidget.PicLayer;
import com.su.funycard.util.BitmapUtil;
import com.su.funycardpro.R;

public class MyCardViewActivity extends BaseActivity {
	private static final String TAG = "MyCardViewActivity";
	private PicLayer img_mycard;
	private String img_path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_mycard_view);
		super.onCreate(savedInstanceState);

	};

	public void btnpeople(View view) {

	}

	@Override
	protected void findViewById() {
		img_mycard = (PicLayer) findViewById(R.id.img_mycard);
		Button btn_share = (Button) findViewById(R.id.btn_share);
		Button btn_delete = (Button) findViewById(R.id.btn_delete);
		Button btn_detail = (Button) findViewById(R.id.btn_detail);
		btn_share.setOnClickListener(listener);
		btn_delete.setOnClickListener(listener);
		btn_detail.setOnClickListener(listener);

	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			File file = new File(img_path);
			switch (v.getId()) {
			case R.id.btn_share:
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
				shareIntent.setType("image/jpeg");
				startActivity(Intent.createChooser(shareIntent, "Share:"));
				break;
			case R.id.btn_delete:
				showDelDialog(file);
				break;
			case R.id.btn_detail:
				showDetailDialog(file);
				break;

			default:
				break;
			}

		}
	};

	private void showDetailDialog(final File file) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(img_path, options);

		Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("File Details:");
		dialog.setMessage("Length:" + file.length() / 1024 + "k\n" + "Size:"
				+ options.outWidth + "*" + options.outHeight);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		dialog.show();

	}

	private void showDelDialog(final File file) {
		Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("Are You Sure To Delete?");
		dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				file.delete();
				dialog.dismiss();
				setResult(123);
				finish();
				Toast.makeText(MyCardViewActivity.this, "Deleted", 100).show();

			}
		});
		dialog.show();

	}

	@Override
	protected void setListener() {

	}

	@Override
	protected void loadData() {
		img_path = this.getIntent().getStringExtra("img_path");
		Bitmap bitmap = BitmapUtil.getBitmapFromFileJust(img_path);
		img_mycard.setImageBitmap(bitmap);
		// bitmap.recycle();

	}
}
