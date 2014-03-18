// 2012-4-18下午07:47:44

package com.su.funycard.bean;

public class PicFileBean {
	private String name;
	private String path;

	public PicFileBean(String name, String path) {
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
