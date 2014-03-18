package com.su.funycard.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 简单的xml数据操作类
 * 
 * @author sfshine
 * 
 */

public class DataStoreUtil {

	public static String datastore = "data";

	// 加密后存储 默认共存储26张
	public static int encode(int code) {
		int source = (code - 26) * (-27);
		return source;

	}

	public static int decode(int source) {
		int code = source / (-27) + 26;
		return code;

	}

	public static void putElecount(Context context, int count) {

		SharedPreferences settings = context.getSharedPreferences(datastore,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("test", encode(count));
		editor.commit();
	}

	public static int getElecount(Context context) {
		SharedPreferences settings = context.getSharedPreferences(datastore,
				Context.MODE_PRIVATE);
		int arg = settings.getInt("test", 0);
		arg = decode(arg);
		return arg;
	}

	public static void put(Context context, String key, String value) {

		SharedPreferences settings = context.getSharedPreferences(datastore,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);

		editor.commit();
	}

	public static String getString(Context context, String key) {
		SharedPreferences settings = context.getSharedPreferences(datastore,
				Context.MODE_PRIVATE);

		String arg = settings.getString(key, "0");
		return arg;
	}

	public static void put(Context context, String key, Boolean value) {
		SharedPreferences settings = context.getSharedPreferences(datastore,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);

		editor.commit();
	}

	public static Boolean getBoolean(Context context, String key) {
		SharedPreferences settings = context.getSharedPreferences(datastore,
				Context.MODE_PRIVATE);
		Boolean arg = settings.getBoolean(key, true);
		return arg;
	}

	public static void clear(Context context) {
		SharedPreferences settings = context.getSharedPreferences(datastore,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}
}