package com.su.funycardpro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.su.funycard.logic.PicLayerManager;
import com.su.funycard.util.BitmapUtil;
import com.su.funycard.util.DataStoreUtil;
import com.su.funycardpro.R;

public class MakeCardActivity extends Activity implements OnClickListener {
	private static final String TAG = "MakeCardActivity";

	private Context context;
	private PicLayerManager picLayerManager;
	private FrameLayout framemain;
	private final int SELECTPIC = 1122;
	private final int SELECTIMGTOADD = 1123;
	private RelativeLayout fontcolormenu;
	private RelativeLayout fontsizemenu;
	private LinearLayout ll_tools;
	private boolean tips = true;

	private RelativeLayout getpicmenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_makecard);
		context = this;
		int type = this.getIntent().getIntExtra("type", 3);

		if (type == 0) {
			Intent intent = new Intent(context, CameraActivity.class);
			startActivityForResult(intent, SELECTPIC);
		} else if (type == 1) {
			Intent intent = new Intent(context, GalleryActivity.class);
			startActivityForResult(intent, SELECTPIC);
		}

		framemain = (FrameLayout) findViewById(R.id.framemain);
		picLayerManager = new PicLayerManager(context, framemain);
		ll_tools = (LinearLayout) findViewById(R.id.ll_tools);

		// frame2btn.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View v) {
		// showSimpleChoiceDialog();
		// }
		// });
		TextView reverceimg = (TextView) findViewById(R.id.reverceimg);
		TextView savetv = (TextView) findViewById(R.id.savetv);
		TextView btnmyele = (TextView) findViewById(R.id.btnmyele);
		TextView addtexttv = (TextView) findViewById(R.id.addtexttv);
		TextView tv_fontcolormenu = (TextView) findViewById(R.id.tv_fontcolormenu);
		TextView tv_fontsizemenu = (TextView) findViewById(R.id.tv_fontsizemenu);

		TextView tv_restoreimg = (TextView) findViewById(R.id.tv_restoreimg);
		TextView tv_back = (TextView) findViewById(R.id.tv_back);
		TextView delimgtv = (TextView) findViewById(R.id.delimgtv);
		TextView mycards = (TextView) findViewById(R.id.mycards);
		TextView btneleonline = (TextView) findViewById(R.id.btneleonline);
		TextView btngetpic = (TextView) findViewById(R.id.btngetpic);

		btngetpic.setOnClickListener(this);
		btneleonline.setOnClickListener(this);
		delimgtv.setOnClickListener(this);
		savetv.setOnClickListener(this);
		btnmyele.setOnClickListener(this);
		addtexttv.setOnClickListener(this);
		tv_restoreimg.setOnClickListener(this);
		tv_fontsizemenu.setOnClickListener(this);
		tv_fontcolormenu.setOnClickListener(this);
		mycards.setOnClickListener(this);
		tv_back.setOnClickListener(this);
		reverceimg.setOnClickListener(this);
		initFontMenu();

	}

	public void btnmake_camera(View view) {
		Log.e(TAG, "camera");
		Intent intent = new Intent(context, CameraActivity.class);
		startActivityForResult(intent, SELECTPIC);

	}

	public void btnmake_album(View view) {
		Intent intent = new Intent(context, GalleryActivity.class);
		startActivityForResult(intent, SELECTPIC);
	}

	public void btn_hreverce(View view) {

		picLayerManager.getTopLayer().horizonticalReverce();

	}

	private void showMsgDialog() {
		if (tips && DataStoreUtil.getBoolean(context, "isshowinstruction")) {

			final Dialog dialog = new Dialog(this, R.style.Translucent_NoTitle);
			dialog.setContentView(R.layout.common_dialog);
			Button common_dialog_btn_cancel = (Button) dialog
					.findViewById(R.id.common_dialog_btn_cancel);
			Button common_dialog_btn_ok = (Button) dialog
					.findViewById(R.id.common_dialog_btn_ok);
			common_dialog_btn_ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DataStoreUtil.put(context, "isshowinstruction", false);
					dialog.dismiss();

				}
			});
			common_dialog_btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();

				}
			});
			dialog.show();
			tips = false;

		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != RESULT_OK) {
			if (requestCode == SELECTPIC) {
				finish();
			}

			return;
		}
		switch (requestCode) {
		case SELECTPIC:
			Log.e(TAG, data.getStringExtra("picpath" + ""));
			String picpath = data.getStringExtra("picpath");
			picLayerManager.addImage(BitmapUtil.getBitmapFromFile(picpath));

			showMsgDialog();
			break;
		case SELECTIMGTOADD:
			String imgtoadd = data.getStringExtra("picpath");
			picLayerManager.addImage(BitmapUtil.getBitmapFromFile(imgtoadd));

			showMsgDialog();
			break;
		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		try {

			switch (v.getId()) {

			case R.id.btneleonline:
				Intent intent0 = new Intent(context,
						CatagorisOnlineActivity.class);
				startActivity(intent0);

				break;
			case R.id.mycards:
				Intent intent1 = new Intent(context, MyCardActivity.class);

				startActivity(intent1);

				break;

			case R.id.btnmyele:

				Intent intent = new Intent(context, MyEleActivity.class);
				intent.putExtra("isselect", "true");
				startActivityForResult(intent, SELECTIMGTOADD);
				hideFontMenu();

				break;
			case R.id.reverceimg:
				picLayerManager.getTopLayer().horizonticalReverce();

			case R.id.tv_restoreimg:
				picLayerManager.restorePicLayer();
				break;
			case R.id.addtexttv:
				showAddText();

				break;

			case R.id.btngetpic:

				if (getpicmenu.getVisibility() == View.VISIBLE) {
					getpicmenu.setVisibility(View.GONE);
				} else {
					hideFontMenu();
					getpicmenu.setVisibility(View.VISIBLE);
				}
				break;

			case R.id.tv_fontcolormenu:

				if (fontcolormenu.getVisibility() == View.VISIBLE) {
					fontcolormenu.setVisibility(View.GONE);
				} else {
					hideFontMenu();
					fontcolormenu.setVisibility(View.VISIBLE);
				}

				break;
			case R.id.tv_fontsizemenu:
				if (fontsizemenu.getVisibility() == View.VISIBLE) {
					fontsizemenu.setVisibility(View.GONE);
				} else {
					hideFontMenu();
					fontsizemenu.setVisibility(View.VISIBLE);
				}

				break;

			case R.id.delimgtv:
				picLayerManager.removeLastPicLayer();
				if (picLayerManager.getTopLayerNumber() == -1) {

					picLayerManager.clearRestoreCache();
				}
				hideFontMenu();
				break;
			case R.id.savetv:
				picLayerManager.save();
				Toast.makeText(context, "Saved in the Share", 1).show();
				hideFontMenu();
				break;
			case R.id.tv_back:
				// finish();
				Intent intent2 = new Intent(MakeCardActivity.this,
						SubMenuActivity.class);
				startActivity(intent2);
				break;
			default:
				break;
			}
		} catch (Exception e) {

		}

	}

	private void showAddText() {
		final EditText et = new EditText(context);
		et.setSingleLine(false);
		AlertDialog.Builder ad1 = new AlertDialog.Builder(context);
		ad1.setTitle("Add Text");
		ad1.setMessage("Please input your text:");
		ad1.setIcon(android.R.drawable.ic_dialog_info);
		ad1.setView(et);
		ad1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {
				picLayerManager.addText(et.getText().toString());

			}
		});
		ad1.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {

			}
		});
		ad1.show();// 显示对话框
	}

	private void hideFontMenu() {

		fontsizemenu.setVisibility(View.GONE);
		fontcolormenu.setVisibility(View.GONE);
		getpicmenu.setVisibility(View.GONE);

	}

	private void initFontMenu() {
		initFontColorMenu();
		initFontSizeMenu();
		initGetpicmenu();

	}

	private void initFontColorMenu() {
		fontcolormenu = (RelativeLayout) findViewById(R.id.fontcolormenu);

		TextView btn_color_1 = (TextView) findViewById(R.id.btn_color_1);
		btn_color_1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextColor(Color.RED);
			}
		});
		TextView btn_color_2 = (TextView) findViewById(R.id.btn_color_2);
		btn_color_2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextColor(0xffff6600);
			}
		});
		TextView btn_color_3 = (TextView) findViewById(R.id.btn_color_3);
		btn_color_3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextColor(Color.YELLOW);
			}
		});
		TextView btn_color_4 = (TextView) findViewById(R.id.btn_color_4);
		btn_color_4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextColor(0xff006633);
			}
		});
		TextView btn_color_5 = (TextView) findViewById(R.id.btn_color_5);
		btn_color_5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextColor(0xffccff66);
			}
		});
		TextView btn_color_6 = (TextView) findViewById(R.id.btn_color_6);
		btn_color_6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextColor(0xff003399);
			}
		});
		TextView btn_color_7 = (TextView) findViewById(R.id.btn_color_7);
		btn_color_7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextColor(0xff330066);
			}
		});
		TextView btn_color_8 = (TextView) findViewById(R.id.btn_color_8);
		btn_color_8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextColor(0xffcc99cc);
			}
		});

		TextView btn_color_9 = (TextView) findViewById(R.id.btn_color_9);
		btn_color_9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextColor(Color.WHITE);
			}
		});
		TextView btn_color_10 = (TextView) findViewById(R.id.btn_color_10);
		btn_color_10.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextColor(Color.BLACK);
			}
		});
	}

	private void initGetpicmenu() {
		getpicmenu = (RelativeLayout) findViewById(R.id.getpicmenu);
		TextView btn_camera = (TextView) findViewById(R.id.btn_camera);
		TextView btn_gallery = (TextView) findViewById(R.id.btn_gallery);
		TextView btn_close = (TextView) findViewById(R.id.btn_close);
		btn_camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btnmake_camera(null);

			}
		});
		btn_gallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btnmake_album(null);

			}
		});
		btn_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideFontMenu();

			}
		});

	}

	private void initFontSizeMenu() {
		fontsizemenu = (RelativeLayout) findViewById(R.id.fontsizemenu);

		TextView btn_fontsize_1 = (TextView) findViewById(R.id.btn_fontsize_1);
		btn_fontsize_1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextSize(20f);
			}
		});
		TextView btn_fontsize_2 = (TextView) findViewById(R.id.btn_fontsize_2);
		btn_fontsize_2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextSize(40f);
			}
		});
		TextView btn_fontsize_3 = (TextView) findViewById(R.id.btn_fontsize_3);
		btn_fontsize_3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextSize(60f);
			}
		});
		TextView btn_fontsize_4 = (TextView) findViewById(R.id.btn_fontsize_4);
		btn_fontsize_4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextSize(80f);
			}
		});
		TextView btn_fontsize_5 = (TextView) findViewById(R.id.btn_fontsize_5);
		btn_fontsize_5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				picLayerManager.getTopLayer().setTextSize(100f);
			}
		});

	}
}
