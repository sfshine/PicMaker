package com.su.funycardpro;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.su.funycardpro.R;

public class GalleryActivity extends Activity {
	public final int GETPICFROMGALLERY = 104;
	private Uri uri;
	private String photoTemp;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("TAG", "  onCreate===========");
		getPicFromGallery();
	}

	private void getPicFromGallery() {

		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, GETPICFROMGALLERY);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			finish();
			return;
		}

		switch (requestCode) {
		case GETPICFROMGALLERY:
			uri = data.getData();
			try {

				ContentResolver cr = this.getContentResolver();
				Cursor c = cr.query(uri, null, null, null, null);
				c.moveToFirst();
				photoTemp = c.getString(c.getColumnIndex("_data"));
				Intent newdata = new Intent(GalleryActivity.this,
						MakeCardActivity.class);
				newdata.putExtra("picpath", photoTemp);
				setResult(-1, newdata);

			} catch (Exception e) {

			}

			break;
		default:
			break;
		}
		Log.e("TAG", "  onActivityResult===========");

		finish();
	}

}
