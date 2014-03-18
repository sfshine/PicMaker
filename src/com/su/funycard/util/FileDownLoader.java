package com.su.funycard.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.R.drawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

/**
 * 图片下载工具
 */
public class FileDownLoader {
	/**
	 * 从网址获取drawable
	 * 
	 * @param url
	 * @return
	 */

	public static Drawable getDrawableFromUrl(String url) {

		URL m;
		InputStream i = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Drawable d = null;
		if (i != null) {
			d = Drawable.createFromStream(i, "src");
		}
		return d;
	}

	/**
	 * 下载图片到本地 如果存在這個文件將不會下載 如果不存在對應文件夾 創建對應文件夾
	 * 
	 * @param urlPath
	 * @param savePath
	 * @throws Exception
	 */
	public static void downloadImg(String urlPath, String savePath)
			throws Exception {
		// 获取图片
		if (!FileUtil.existFile(savePath)) {
			String folderpath = savePath
					.substring(0, savePath.lastIndexOf("/"));
			FileUtil.createFolder(folderpath);
			String str = java.net.URLEncoder.encode(urlPath, "UTF-8");
			str = str.replaceAll("%2F", "/");
			str = str.replaceAll("%3A", ":");
			URL url = new URL(str);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3 * 1000);
			if (conn.getResponseCode() == 200) {
				InputStream inSream = conn.getInputStream();
				File file = new File(savePath);
				FileOutputStream outStream = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = inSream.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				outStream.close();
				inSream.close();
			}
		}
	}
}