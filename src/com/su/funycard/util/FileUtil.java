// 2012-4-18下午07:49:44

package com.su.funycard.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.su.funycard.bean.PicFileBean;

public class FileUtil {

	// 复制文件
	public static void copyFile(String ssourceFile, String stargetFile) {
		String folderpath = stargetFile.substring(0,
				stargetFile.lastIndexOf("/"));
		FileUtil.createFolder(folderpath);

		File sourceFile = new File(ssourceFile);
		File targetFile = new File(stargetFile);

		try {
			FileInputStream input = new FileInputStream(sourceFile);

			BufferedInputStream inBuff = new BufferedInputStream(input);

			// 新建文件输出流并对它进行缓冲
			FileOutputStream output = new FileOutputStream(targetFile);
			BufferedOutputStream outBuff = new BufferedOutputStream(output);

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();

			// 关闭流
			inBuff.close();
			outBuff.close();
			output.close();
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getMyEleCounts(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			return file.list().length;
		}
		return 0;

	}

	public static List<PicFileBean> getImageNames(String folderPath) {
		List<PicFileBean> pictureFiles = new ArrayList<PicFileBean>();
		File file = new File(folderPath);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File file02 = new File(folderPath + "/" + filelist[i]);

				if (!file02.isDirectory()) {
					// if (isImageFile(file02.getName())) {
					PicFileBean pictureFile = new PicFileBean(filelist[i],
							file02.getAbsolutePath());
					pictureFiles.add(pictureFile);
					// }
				}
			}

		}
		return pictureFiles;

	}

	private static boolean isImageFile(String fileName) {
		String fileEnd = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		if (fileEnd.equalsIgnoreCase("jpg")) {
			return true;
		} else if (fileEnd.equalsIgnoreCase("png")) {
			return true;
		} else if (fileEnd.equalsIgnoreCase("bmp")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 复制asset文件夹下的文件到sd卡上
	 * 
	 * @param context
	 *            上下文
	 * @param assetDir
	 *            asset指定文件夹的路径
	 * @param dir
	 *            sd卡指定文件夹的路径
	 */
	public static void copyAssets(Context context, String assetDir, String dir) {
		String[] files;
		try {
			files = context.getResources().getAssets().list(assetDir);
		} catch (IOException e1) {
			return;
		}
		File mWorkingPath = new File(dir);
		// if this directory does not exists, make one.
		if (!mWorkingPath.exists()) {
			if (!mWorkingPath.mkdirs()) {

			}
		}
		for (int i = 0; i < files.length; i++) {
			try {
				String fileName = files[i];
				// we make sure file name not contains '.' to be a folder.
				if (!fileName.contains(".")) {
					if (0 == assetDir.length()) {
						// 这里使用的递归
						copyAssets(context, fileName, dir + fileName + "/");
					} else {
						copyAssets(context, assetDir + "/" + fileName, dir
								+ fileName + "/");
					}
					continue;
				}
				File outFile = new File(mWorkingPath, fileName);
				if (outFile.exists()) {
					continue;
				}
				InputStream in = null;
				if (0 != assetDir.length()) {
					in = context.getAssets().open(assetDir + "/" + fileName);
				} else {
					in = context.getAssets().open(fileName);
				}
				OutputStream out = new FileOutputStream(outFile);

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean existFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}

	}

	public static void createFolder(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹

		} catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();

		}
	}

	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

}
