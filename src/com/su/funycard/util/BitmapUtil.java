package com.su.funycard.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.su.funycard.common.Constant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

public class BitmapUtil {

	/**
	 * 从本读读取图片的bitmap 添加opt参数
	 * 
	 * @param uri
	 * @return
	 */
	static public Bitmap getBitmapFromFile(String uri, int opsize) {
		/**
		 * 如果大于500就缩小2倍
		 */
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(uri, options);
		options.inJustDecodeBounds = false;
		options.inSampleSize = options.outHeight > 800 ? options.outHeight / 800
				: 1;
		options.inSampleSize = options.outHeight > 500 ? options.outHeight / 500
				: 1;
		bitmap = BitmapFactory.decodeFile(uri, options);
		// bitmap = rotateBmp(bitmap, 90);
		return bitmap;
	}

	/**
	 * 镜像旋转bitmap
	 * 
	 * @param b
	 * @param degrees
	 * @return
	 */
	public static Bitmap horizonticalRotateBmp(Bitmap bitmap) {

		Matrix m = new Matrix();
		m.setScale(-1, 1);
		Log.e("test", "");
		try {
			Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), m, true);
			if (bitmap != b2) {
				bitmap.recycle();
				bitmap = b2;
			}
		} catch (OutOfMemoryError ex) {

		}
		return bitmap;
	}

	/**
	 * 旋转bitmap
	 * 
	 * @param b
	 * @param degrees
	 * @return
	 */
	public static Bitmap rotateBmp(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2,
					(float) b.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					b.recycle();
					b = b2;
				}
			} catch (OutOfMemoryError ex) {

			}
		}
		return b;
	}

	/**
	 * 从本读读取图片的bitmap
	 * 
	 * @param uri
	 * @return
	 */
	static public Bitmap getBitmapFromFile(String uri) {
		return getBitmapFromFile(uri, 3);
	}

	static public Bitmap getBitmapFromFileJust(String uri) {
		return BitmapFactory.decodeFile(uri);
	}

	/**
	 * drawable转bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	static public Bitmap drawableToBitmap(Drawable drawable) {
		/*
		 * Drawable转化为Bitmap
		 */
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 保持bitmap到sdcard
	 * 
	 * @param bitmap
	 * @param bitName
	 */

	static public void saveMyBitmap(Bitmap bitmap, String bitName) {
		File filedir = new File(Constant.CARDPATH);
		if (!filedir.isDirectory()) {
			filedir.mkdirs();
		}
		try {
			File f = new File(Constant.CARDPATH + bitName + ".png");
			f.createNewFile();
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);

			bitmap.compress(Bitmap.CompressFormat.PNG, 50, fOut);
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 合并两个bitmap
	 * 
	 * @param firstBitmap
	 * @param secondBitmap
	 * @return
	 */

	static public Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
		Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(),
				firstBitmap.getHeight(), firstBitmap.getConfig());
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(firstBitmap, new Matrix(), null);
		canvas.drawBitmap(secondBitmap, 0, 0, null);
		return bitmap;
	}

}
