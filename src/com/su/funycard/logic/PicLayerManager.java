package com.su.funycard.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.su.funycardpro.R;
import com.su.funycard.uiwidget.PicLayer;
import com.su.funycard.util.BitmapUtil;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

/**
 * Use a list to manager all picture layers on the "canvas"(the layout)
 * 
 * 
 * @author sfshine
 * @email cngaoshuai@163.com
 */
public class PicLayerManager {
	List<PicLayer> picLayers = new ArrayList<PicLayer>();
	Context context;
	ViewGroup layout;

	public PicLayerManager(Context context, ViewGroup layout) {
		this.context = context;
		this.layout = layout;
	}

	public int getTopLayerNumber() {
		return picLayers.size() - 1;

	}

	public PicLayer getTopLayer() {
		return picLayers.get(getTopLayerNumber());
	}

	public void horizonticalReverce() {

	}

	public void addImage(int resid) {
		PicLayer newImage = new PicLayer(context);
		newImage.setImageResource(resid);
		picLayers.add(newImage);
		addView2Layout(newImage);

	}

	public void addImage(Bitmap bitmap) {
		PicLayer newImage = new PicLayer(context);
		newImage.setImageBitmap(bitmap);
		picLayers.add(newImage);
		addView2Layout(newImage);

	}

	public void addText(String text) {
		PicLayer newText = new PicLayer(context);
		newText.drawText(text);
		picLayers.add(newText);
		addView2Layout(newText);
	}

	public void addText(String text, int color) {
		PicLayer newText = new PicLayer(context);
		newText.drawText(text, color);
		picLayers.add(newText);
		addView2Layout(newText);
	}

	public void setTextColor(int id, int color) {
		picLayers.get(id).setTextColor(color);
	}

	public void setTextColor(PicLayer picLayer, int color) {
		picLayer.setTextColor(color);
	}

	PicLayerRestoreCache picLayerRestoreCache = new PicLayerRestoreCache();

	public void removeLastPicLayer() {
		int id = getTopLayerNumber();
		if (id == -1) {
			return;
		}
		layout.removeView(picLayers.get(id));
		picLayerRestoreCache.pushPicLayer(picLayers.get(id));
		picLayers.remove(id);
	}

	public void restorePicLayer() {
		PicLayer picLayer = picLayerRestoreCache.popPicLayer();
		if (picLayer != null) {
			addView2Layout(picLayer);
			picLayers.add(picLayer);
		}

	}

	public void clearRestoreCache() {
		picLayerRestoreCache.clear();
	}

	static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd_HHmmss");

	public static String getCurrentTime() {
		Date date = new Date();
		sdf_date.format(date);
		return sdf_date.format(date);
	}

	public void save() {

		Bitmap result = picLayers.get(0).getNewBitmap(1);
		boolean flag = true;// just to step over the first layer that has add to
							// the result.
		for (PicLayer picLayer : picLayers) {

			if (picLayer != null && !flag) {
				result = BitmapUtil.mergeBitmap(result,
						picLayer.getNewBitmap(1));
			}
			flag = false;
		}

		Canvas canvas = new Canvas(result);
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.x_logo);
		canvas.drawBitmap(bitmap, result.getWidth() - 170,
				result.getHeight() - 60, null);
		String time = "XtickerX_" + getCurrentTime();
		Log.e("time", time);
		BitmapUtil.saveMyBitmap(result, time);
	}

	/**
	 * add the layer to the layout
	 * 
	 * @param context
	 * @param view
	 */
	private void addView2Layout(View view) {
		LayoutParams l = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		layout.addView(view, l);
	}

}
