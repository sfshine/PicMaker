package com.su.funycardpro;

import com.su.funycard.util.DataStoreUtil;
import com.su.funycardpro.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

public class SubMenuActivity extends Activity {

	private String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submenu);
		if (DataStoreUtil.getBoolean(this, "shotcutpro1")) {
			addShortcut(this);
			DataStoreUtil.put(this, "shotcutpro1", false);
		}

	}

	public void btnmyele(View view) {
		Intent intent = new Intent(SubMenuActivity.this, MyEleActivity.class);

		startActivity(intent);
	}

	public void btnmake(View view) {
		Intent intent = new Intent(SubMenuActivity.this, MakeCardActivity.class);

		startActivity(intent);
	}

	public void btneleonline(View view) {
		Intent intent = new Intent(SubMenuActivity.this,
				CatagorisOnlineActivity.class);

		startActivity(intent);
	}

	public void btnmycard(View view) {
		Intent intent = new Intent(SubMenuActivity.this, MyCardActivity.class);

		startActivity(intent);
	}

	public void btn_upload(View view) {
		Intent intent = new Intent(SubMenuActivity.this, UploadActivity.class);

		startActivity(intent);
	}

	public static void delShortcut(Context cx) {
		Intent shortcut = new Intent(
				"com.android.launcher.action.UNINSTALL_SHORTCUT");

		String title = "XtickerX";
		try {
			final PackageManager pm = cx.getPackageManager();
			title = pm.getApplicationLabel(
					pm.getApplicationInfo(cx.getPackageName(),
							PackageManager.GET_META_DATA)).toString();
		} catch (Exception e) {
		}

		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		Intent shortcutIntent = cx.getPackageManager()
				.getLaunchIntentForPackage(cx.getPackageName());
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		cx.sendBroadcast(shortcut);
	}

	public static void addShortcut(Context cx) {
		// delShortcut(cx);
		Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");

		Intent shortcutIntent = cx.getPackageManager()
				.getLaunchIntentForPackage(cx.getPackageName());
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		String title = "XtickerX";
		try {
			final PackageManager pm = cx.getPackageManager();
			title = pm.getApplicationLabel(
					pm.getApplicationInfo(cx.getPackageName(),
							PackageManager.GET_META_DATA)).toString();
		} catch (Exception e) {
		}

		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);

		shortcut.putExtra("duplicate", false);

		Parcelable iconResource = Intent.ShortcutIconResource.fromContext(cx,
				R.drawable.ic_launcher);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);

		cx.sendBroadcast(shortcut);
	}

}
