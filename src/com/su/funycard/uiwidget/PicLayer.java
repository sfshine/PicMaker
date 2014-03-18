package com.su.funycard.uiwidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.su.funycardpro.R;
import com.su.funycard.util.BitmapUtil;

/**
 * 稍加修改 可以在xml布局中直接使用.
 * 
 * @author sfshine
 * @email cngaoshuai@163.com
 */
public class PicLayer extends ImageView {

	float x_down = 0;
	float y_down = 0;
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;
	float oldRotation = 0;
	Matrix matrix = new Matrix();
	Matrix matrix1 = new Matrix();
	Matrix savedMatrix = new Matrix();

	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	private static final String TAG = "TouchImageView";
	int mode = NONE;

	boolean matrixCheck = false;

	int widthScreen;
	int heightScreen;
	DisplayMetrics dm;
	Bitmap gintama;
	private Paint textPaint;
	private String text;

	public PicLayer(Context context) {
		super(context);
		gintama = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);

		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		widthScreen = dm.widthPixels;
		heightScreen = dm.heightPixels;

		matrix = new Matrix();
	}

	public PicLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		dm = new DisplayMetrics();
		gintama = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);

		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		widthScreen = dm.widthPixels;
		heightScreen = dm.heightPixels;
		matrix = new Matrix();

	}

	public void horizonticalReverce() {
		gintama = BitmapUtil.horizonticalRotateBmp(gintama);
		invalidate();
	}

	public void setImageBitmap(Bitmap bitmap) {
		gintama = bitmap;
		center(true, true);
		// invalidate();

	}

	public void setImageResource(int res) {
		gintama = BitmapFactory.decodeResource(getResources(), res);
		center(true, true);
		// invalidate();

	}

	public void drawText(String text) {

		drawText(text, 50.0f, Typeface.DEFAULT_BOLD, Color.BLACK);

	}

	public void drawText(String text, int color) {

		drawText(text, 50.0f, Typeface.DEFAULT_BOLD, color);

	}

	public void drawText(String text, float size, Typeface typeface, int color) {

		Bitmap bitmap = Bitmap.createBitmap(widthScreen, heightScreen,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap); // 新建画布
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
		textPaint.setTextSize(size);// 字体大小
		textPaint.setTypeface(typeface);// 采用默认的宽度
		textPaint.setColor(color);// 采用的颜色
		this.text = text;
		// canvas.drawText(text, widthScreen / 4, 150, textPaint);
		// drawMultiLineText(text, widthScreen / 4, 150, textPaint,
		// canvas);
		drawMultiLineText(text, 20, 150, textPaint, canvas);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		gintama = bitmap;
		center(true, true);

	}

	public void setTextColor(int color) {
		if (textPaint != null) {

			Bitmap bitmap = Bitmap.createBitmap(widthScreen, heightScreen,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap); // 新建画布
			textPaint.setColor(color);// 采用的颜色
			drawMultiLineText(text, 20, 150, textPaint, canvas);
			// canvas.drawText(text, widthScreen / 4, 150,
			// textPaint);
			canvas.save(Canvas.ALL_SAVE_FLAG);
			canvas.restore();
			gintama = bitmap;
			invalidate();
		}

	}

	public void setTextSize(float size) {
		if (textPaint != null) {
			Bitmap bitmap = Bitmap.createBitmap(widthScreen, heightScreen,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap); // 新建画布
			textPaint.setTextSize(size);
			drawMultiLineText(text, 20, 150, textPaint, canvas);
			// canvas.drawText(text, widthScreen / 4, 150,
			// textPaint);
			canvas.save(Canvas.ALL_SAVE_FLAG);
			canvas.restore();
			gintama = bitmap;
			invalidate();
		}

	}

	void drawMultiLineText(String str, float x, float y, Paint paint,
			Canvas canvas) {
		String[] lines = str.split("\n");
		float txtSize = -paint.ascent() + paint.descent();

		if (paint.getStyle() == Style.FILL_AND_STROKE
				|| paint.getStyle() == Style.STROKE) {
			txtSize += paint.getStrokeWidth(); // add stroke width to the text
		}
		float lineSpace = txtSize * 0.1f; // default line spacing
		for (int i = 0; i < lines.length; ++i) {
			canvas.drawText(lines[i], x, y + (txtSize + lineSpace) * i, paint);
		}
	}

	protected void onDraw(Canvas canvas) {
		if (gintama == null) {
			return;
		}
		canvas.save();
		canvas.drawBitmap(gintama, matrix, null);
		canvas.restore();

	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mode = DRAG;
			x_down = event.getX();
			y_down = event.getY();
			savedMatrix.set(matrix);
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mode = ZOOM;
			oldDist = spacing(event);
			oldRotation = rotation(event);
			savedMatrix.set(matrix);
			midPoint(mid, event);
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == ZOOM) {
				matrix1.set(savedMatrix);
				float rotation = rotation(event) - oldRotation;
				float newDist = spacing(event);
				float scale = newDist / oldDist;
				matrix1.postScale(scale, scale, mid.x, mid.y);// 縮放
				matrix1.postRotate(rotation, mid.x, mid.y);// 旋轉
				// matrixCheck = matrixCheck();
				if (matrixCheck == false) {
					matrix.set(matrix1);
					invalidate();
				}
			} else if (mode == DRAG) {
				matrix1.set(savedMatrix);
				matrix1.postTranslate(event.getX() - x_down, event.getY()
						- y_down);// 平移
				// matrixCheck = matrixCheck();

				if (matrixCheck == false) {
					matrix.set(matrix1);
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			break;
		}
		return true;
	}

	private boolean matrixCheck() {
		float[] f = new float[9];
		matrix1.getValues(f);
		// 图片4个顶点的坐标
		float x1 = f[0] * 0 + f[1] * 0 + f[2];
		float y1 = f[3] * 0 + f[4] * 0 + f[5];
		float x2 = f[0] * gintama.getWidth() + f[1] * 0 + f[2];
		float y2 = f[3] * gintama.getWidth() + f[4] * 0 + f[5];
		float x3 = f[0] * 0 + f[1] * gintama.getHeight() + f[2];
		float y3 = f[3] * 0 + f[4] * gintama.getHeight() + f[5];
		float x4 = f[0] * gintama.getWidth() + f[1] * gintama.getHeight()
				+ f[2];
		float y4 = f[3] * gintama.getWidth() + f[4] * gintama.getHeight()
				+ f[5];
		// 图片现宽度
		double width = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		// 缩放比率判断
		if (width < widthScreen / 3 || width > widthScreen * 3) {
			return true;
		}
		// 出界判断
		if ((x1 < widthScreen / 3 && x2 < widthScreen / 3
				&& x3 < widthScreen / 3 && x4 < widthScreen / 3)
				|| (x1 > widthScreen * 2 / 3 && x2 > widthScreen * 2 / 3
						&& x3 > widthScreen * 2 / 3 && x4 > widthScreen * 2 / 3)
				|| (y1 < heightScreen / 3 && y2 < heightScreen / 3
						&& y3 < heightScreen / 3 && y4 < heightScreen / 3)
				|| (y1 > heightScreen * 2 / 3 && y2 > heightScreen * 2 / 3
						&& y3 > heightScreen * 2 / 3 && y4 > heightScreen * 2 / 3)) {
			return true;
		}
		return false;
	}

	// 触碰两点间距离
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	// 取手势中心点
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	// 取旋转角度
	private float rotation(MotionEvent event) {
		double delta_x = (event.getX(0) - event.getX(1));
		double delta_y = (event.getY(0) - event.getY(1));
		double radians = Math.atan2(delta_y, delta_x);
		return (float) Math.toDegrees(radians);
	}

	/**
	 * 横向、纵向居中
	 */
	protected void center(boolean horizontal, boolean vertical) {
		if (gintama == null) {
			return;
		}

		Matrix m = new Matrix();
		m.set(matrix);
		RectF rect = new RectF(0, 0, gintama.getWidth(), gintama.getHeight());
		m.mapRect(rect);

		float height = rect.height();
		float width = rect.width();

		float deltaX = 0, deltaY = 0;

		if (vertical) {

			// 图片小于屏幕大小，则居中显示。大于屏幕，上方留空则往上移，下方留空则往下移

			if (height < heightScreen) {
				deltaY = (heightScreen - height) / 2 - rect.top;
				Log.e(TAG, "" + deltaY);
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < heightScreen) {
				deltaY = this.getHeight() - rect.bottom;
			}
		}

		if (horizontal) {

			if (width < widthScreen) {
				deltaX = (widthScreen - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < widthScreen) {
				deltaX = widthScreen - rect.right;
			}
		}
		matrix.postTranslate(deltaX, deltaY);
	}

	/**
	 * 返回处理后图片的bitmap int times:以屏幕的几倍输出
	 * 
	 * @return
	 */
	public Bitmap getNewBitmap(int times) {

		Matrix matrixToSave = new Matrix(matrix);
		matrixToSave.postScale(times, times);
		Bitmap bitmap = Bitmap.createBitmap(widthScreen * times, heightScreen
				* times, Config.ARGB_8888); // 背景图片
		Canvas canvas = new Canvas(bitmap); // 新建画布
		canvas.drawBitmap(gintama, matrixToSave, null); // 画图片
		canvas.save(Canvas.ALL_SAVE_FLAG); // 保存画布
		canvas.restore();

		return bitmap;
	}

}