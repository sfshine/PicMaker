package com.su.funycardpro;

import com.su.funycard.bean.UserBean;
import com.su.funycard.common.Constant;
import com.su.funycard.net.UserService;
import com.su.funycard.util.DataStoreUtil;
import com.su.funycardpro.R;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class XtickerApp extends Application {
	static Context context;
	public static UserBean userBean = new UserBean();

	// 在整个应用第一次被创建出来的时候 执行
	// 在应用程序对应的进程 第一次创建出来的时候执行
	@Override
	public void onCreate() {
		super.onCreate();
		context = this.getApplicationContext();
		// // 把自定义的异常处理类设置 给主线程
		// MyCrashHandler myCrashHandler = MyCrashHandler.getInstance();
		// myCrashHandler.init(getApplicationContext());
		// Thread.currentThread().setUncaughtExceptionHandler(myCrashHandler);
		Constant.ELECOUNTS = DataStoreUtil.getElecount(context);
		if (getUser()) {
			updateEleSpace();
		}

	}

	private void updateEleSpace() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				int elespace = new UserService().queryElespace(10 + "");
				if (elespace != -1) {

					DataStoreUtil.putElecount(context, elespace);
					Constant.ELECOUNTS = DataStoreUtil.getElecount(context);
					Log.e("TAG", Constant.ELECOUNTS + "'");
				}
			}
		}).start();

	}

	private boolean getUser() {
		String id = DataStoreUtil.getString(context, "id");
		String email = DataStoreUtil.getString(context, "email");
		String pwd = DataStoreUtil.getString(context, "pwd");
		if (id.equals("0")) {
			Toast.makeText(context, "Your have not registered", 10).show();
			return false;
		}
		userBean.setId("1");
		userBean.setEmail(email);
		userBean.setPwd(pwd);
		return true;

	}

	static public Context getContext() {
		return context;

	}

}