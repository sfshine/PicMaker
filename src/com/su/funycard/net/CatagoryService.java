package com.su.funycard.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.su.funycard.bean.CatBean;
import com.su.funycard.common.Constant;
import com.su.funycard.util.HttpUtil;
import com.su.funycard.util.HttpUtil.MLog;

public class CatagoryService {
	private final String GETCATAGORY = Constant.MOBILEACTION + "getcatagory.php";

	/**
	 * 获取类别
	 * 
	 * @return
	 */
	public List<CatBean> getCatagory() {
		String result = HttpUtil.get(GETCATAGORY);
		try {
			JSONArray jsonArray = new JSONArray(result);
			List<CatBean> catBeans = new ArrayList<CatBean>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				int id = jsonObject.optInt("id");

				String catagory = jsonObject.optString("catagory");

				CatBean catBean = new CatBean(id, catagory);
				catBeans.add(catBean);

			}
			for (int i = 0; i < catBeans.size(); i++) {
				MLog.e("" + catBeans.get(i).getCat());

			}
			return catBeans;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;

	}

}
