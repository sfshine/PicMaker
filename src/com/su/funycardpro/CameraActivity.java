package com.su.funycardpro;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import com.su.funycardpro.R;

public class CameraActivity extends Activity {

	private String localTempImgFileName = "cameratemp";
	private final int CAPTURE_CODE = 100;

	private Uri uri;
	private File dir;
	private File file;

	private ProgressDialog progressDialog;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		dir = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/DCIM/");// 设置临时文件的存放目录
		if (!dir.exists()) {
			dir.mkdir();
		}
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		file = new File(dir, localTempImgFileName);
		uri = Uri.fromFile(file);
		intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(intent, CAPTURE_CODE);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("测试", resultCode + "");
		if (resultCode != RESULT_OK) {
			finish();
			return;
		}
		switch (requestCode) {
		case CAPTURE_CODE:
			Intent newdata = new Intent(CameraActivity.this,
					MakeCardActivity.class);

			newdata.putExtra("picpath", file.getAbsolutePath());
			setResult(-1, newdata);

			finish();
			break;

		default:
			break;
		}

	}

}
