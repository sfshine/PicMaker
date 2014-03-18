// 2012-4-18下午07:47:44

package com.su.funycard.bean;

public class CatBean {
	private int id;
	private String cat;

	public CatBean(int id, String cat) {

		this.id = id;
		this.cat = cat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

}
