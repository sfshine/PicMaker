package tests;

import com.su.funycard.bean.PicBean;
import com.su.funycard.bean.UserBean;
import com.su.funycard.net.CatagoryService;
import com.su.funycard.net.ElementService;
import com.su.funycard.net.UserService;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestCase extends AndroidTestCase {
	public void testGetElems() {
		new ElementService().getElements(0, 100);

	}

	// 2013050118563595
	public void testVotbad() {
		new ElementService().votebad("2013050118563595");

	}

	public void testVotgood() {
		new ElementService().votegood("2013050118563595");

	}

	public void testUploadElems() {
		PicBean picBean = new PicBean();
		picBean.setUid("12345");
		picBean.setCatagory("others");
		picBean.setImageurl("/sdcard/1.png");
		new ElementService().uploadElements(picBean);

	}

	public void testGetCats() {
		new CatagoryService().getCatagory();

	}

	public void testAddEleSpace() {
		new UserService().addElespace(100, 10 + "");

	}

	public void testAddDesignSpace() {
		new UserService().addDesignspace(100, 10 + "");

	}

	public void testqueryDesignspace() {
		new UserService().queryDesignspace(10 + "");

	}

	public void testqueryElespace() {

		new UserService().queryElespace(10 + "");

	}

	public void testqueryUser() {
		Log.e("TAG", new UserService().queryUserbyID(10 + "").getEmail());

	}

	public void testaddUser() {
		UserBean userBean = new UserBean();
		userBean.setEmail("test@qq.com");
		userBean.setPwd("123");
		Log.e("TAG", new UserService().addUser(userBean));

	}

	public void testcheckUser() {
		UserBean userBean = new UserBean();
		userBean.setEmail("test@qq.com");
		userBean.setPwd("123");
		Log.e("TAG", new UserService().checkUser(userBean));

	}
}
