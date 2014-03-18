package com.su.funycard.bean;

/**
 * 
 * @author sfshine 2013-4-12 上午9:22:29 就是Elelment
 */

// class Element {
// /* 元素id 元素服务器路径 元素文件名 元素上传日期 元素所属分类 元素评价 价格 所属用户uid*/
// public $id = 0;
// public $sid = "";
// public $path = "";
// public $name = "";
// public $uploadtime = 0;
// public $catagory = "";
// public $evaluate = 0;
// public $price = 0;
// public $uid = 0;
// }

public class PicBean {
	private int id;
	private String sid;
	private float uploadtime;
	private String evaluate;
	private String uid;
	private String catagory;
	private String name;
	private int image;
	private String imageurl;

	public PicBean() {

	}

	public PicBean(int id, String sid, float uploadtime, String evaluate,
			String uid, String catagory, String name, int image, String imageurl) {
		this.id = id;
		this.sid = sid;
		this.uploadtime = uploadtime;
		this.evaluate = evaluate;
		this.uid = uid;
		this.catagory = catagory;
		this.name = name;
		this.image = image;
		this.imageurl = imageurl;
	}

	public PicBean(int i, String string, String string2, String string3) {
		// TODO Auto-generated constructor stub
	}

	public PicBean(int i, String string, String string2, int a0000113022601) {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public float getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(float uploadtime) {
		this.uploadtime = uploadtime;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

}
