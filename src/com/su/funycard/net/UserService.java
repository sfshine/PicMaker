package com.su.funycard.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.su.funycard.bean.CatBean;
import com.su.funycard.bean.UserBean;
import com.su.funycard.common.Constant;
import com.su.funycard.util.HttpUtil;
import com.su.funycard.util.HttpUtil.MLog;

public class UserService {
	private final String MGRUSERACTION = Constant.MOBILEACTION
			+ "mgruseraction.php";

	/**
	 * 检查用户
	 */
	public String checkUser(UserBean userBean) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("op", "login");
		map.put("email", userBean.getEmail());
		map.put("pwd", userBean.getPwd());
		String result = HttpUtil.post(MGRUSERACTION, map);
		return result;
	}

	/**
	 * 注册用户
	 */
	public String addUser(UserBean userBean) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("op", "adduser");
		map.put("email", userBean.getEmail());
		map.put("pwd", userBean.getPwd());
		String result = HttpUtil.post(MGRUSERACTION, map);
		return result;
	}

	/**
	 * 增加用户元素空间
	 */
	public String addElespace(int elespace, String uid) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("elespace", elespace + "");
		map.put("uid", uid);
		String result = HttpUtil.post(MGRUSERACTION, map);
		return result;
	}

	/**
	 * 增加用户设计空间
	 */
	public String addDesignspace(int designspace, String uid) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("designspace", designspace + "");
		map.put("uid", uid);
		String result = HttpUtil.post(MGRUSERACTION, map);
		return result;
	}

	/**
	 * 查询用户所有信息
	 */
	public UserBean queryUserbyID(String uid) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("query", 3 + "");
		map.put("uid", uid);
		String result = HttpUtil.post(MGRUSERACTION, map);
		try {
			JSONObject jsonObject = new JSONArray(result).getJSONObject(0);
			// [{"id":"10","email":"emai111111laa","pwd":"qqyue1","createtime":"4654","acount":"0","elespace":"9457",
			// "designspace":"9457"}]

			String id = jsonObject.getString("id");
			String email = jsonObject.getString("email");
			String pwd = jsonObject.getString("pwd");
			String createtime = jsonObject.getString("createtime");
			int elespace = jsonObject.getInt("elespace");
			int designspace = jsonObject.getInt("designspace");

			UserBean userBean = new UserBean(id, email, pwd, createtime,
					elespace, designspace);
			Log.e("TAG", userBean.toString());
			return userBean;

		} catch (Exception e) {

		}

		return null;
	}

	/**
	 * 查询用户设计空间
	 */
	public int queryDesignspace(String uid) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("query", 2 + "");
		map.put("uid", uid);
		String result = HttpUtil.post(MGRUSERACTION, map);
		try {
			JSONArray jsonArray = new JSONArray(result);
			int res = jsonArray.getJSONObject(0).getInt("designspace");
			return res;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 * 查询用户元素空间
	 */
	public int queryElespace(String uid) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("query", 1 + "");
		map.put("uid", uid);
		String result = HttpUtil.post(MGRUSERACTION, map);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			int res = jsonArray.getJSONObject(0).getInt("elespace");
			return res;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}
}
