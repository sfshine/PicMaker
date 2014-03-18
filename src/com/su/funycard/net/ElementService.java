package com.su.funycard.net;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.su.funycard.bean.PicBean;
import com.su.funycard.common.Constant;
import com.su.funycard.util.DataStoreUtil;
import com.su.funycard.util.HttpUtil;

public class ElementService {

	private final String UPLOADURL = Constant.HOST
			+ "/xtickerxsev/action/uploadaction.php";
	private final String GETELEMENTSURL = Constant.MOBILEACTION
			+ "getelementaction.php";
	private final String MGRELEMENTACTION = Constant.MOBILEACTION
			+ "mgrelementaction.php";

	/**
	 * 更新bad评论
	 * 
	 */

	public String votebad(String sid) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("sid", sid);
		hashMap.put("vote", "bad");
		return HttpUtil.post(MGRELEMENTACTION, hashMap);

	}

	/**
	 * 更新good评论
	 * 
	 */

	public String votegood(String sid) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("sid", sid);
		hashMap.put("vote", "good");
		return HttpUtil.post(MGRELEMENTACTION, hashMap);

	}

	/**
	 * 获取元素通过类别
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<PicBean> getElements(String catid, int start, int end,
			String order) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("start", start + "");
		map.put("end", end + "");
		map.put("catid", catid);
		map.put("order", order);

		String result = HttpUtil.get(GETELEMENTSURL, map);
		try {
			JSONArray jsonArray = new JSONArray(result);
			List<PicBean> picBeans = new ArrayList<PicBean>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				int id = jsonObject.optInt("id");
				String sid = jsonObject.optString("sid");
				String imageurl = Constant.HOST
						+ jsonObject.optString("imageurl");

				String catagory = jsonObject.optString("catagory");
				String evaluate = jsonObject.optString("evaluate");
				String uid = jsonObject.optString("uid");
				float uploadtime = jsonObject.optLong("uploadtime");
				PicBean picBean = new PicBean(id, sid, uploadtime, evaluate,
						uid, catagory, "", -1, imageurl);
				picBeans.add(picBean);

			}

			return picBeans;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 获取元素
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<PicBean> getElements(int start, int end) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("start", start + "");
		map.put("end", end + "");
		String result = HttpUtil.get(GETELEMENTSURL, map);
		try {
			JSONArray jsonArray = new JSONArray(result);
			List<PicBean> picBeans = new ArrayList<PicBean>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				int id = jsonObject.optInt("id");
				String sid = jsonObject.optString("sid");
				String imageurl = Constant.HOST
						+ jsonObject.optString("imageurl");

				String catagory = jsonObject.optString("catagory");
				String evaluate = jsonObject.optString("evaluate");
				String uid = jsonObject.optString("uid");
				float uploadtime = jsonObject.optLong("uploadtime");
				PicBean picBean = new PicBean(id, sid, uploadtime, evaluate,
						uid, catagory, "", -1, imageurl);
				picBeans.add(picBean);

			}

			return picBeans;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;

	}

	public String uploadElements(PicBean picBean) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", picBean.getUid() + "");
		map.put("catagory", picBean.getCatagory());

		HashMap<String, File> files = new HashMap<String, File>();
		files.put("element", new File(picBean.getImageurl()));

		return HttpUtil.post(UPLOADURL, map, files);

	}
}
