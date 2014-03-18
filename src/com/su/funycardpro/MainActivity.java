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

public class MainActivity extends Activity {

	private String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (DataStoreUtil.getBoolean(this, "shotcut")) {
			addShortcut(this);
			DataStoreUtil.put(this, "shotcut", false);
		}

	}

	public void btn_upload(View view) {
		Intent intent = new Intent(MainActivity.this, UploadActivity.class);
		startActivity(intent);
	}

	public void btnsubmenu(View view) {
		Intent intent = new Intent(MainActivity.this, SubMenuActivity.class);
		startActivity(intent);
	}

	public void btnmakecard(View view) {
		Intent intent = new Intent(MainActivity.this, MakeCardActivity.class);
		startActivity(intent);
	}

	public void btnmycards(View view) {
		Intent intent = new Intent(MainActivity.this, MyCardActivity.class);
		startActivity(intent);
	}

	public void btneleonline(View view) {
		Intent intent = new Intent(MainActivity.this,
				CatagorisOnlineActivity.class);
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
		delShortcut(cx);
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
