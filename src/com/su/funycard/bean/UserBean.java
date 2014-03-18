package com.su.funycard.bean;

public class UserBean {
	// [{"id":"10","email":"emai111111laa","pwd":"qqyue1","createtime":"4654","acount":"0","elespace":"9457",
	// "designspace":"9457"}]
	private String id;
	private String email;
	private String pwd;
	private String createtime;
	private int elespace;
	private int designspace;

	public UserBean(String id, String email, String pwd, String createtime,
			int elespace, int designspace) {
		super();
		this.id = id;
		this.email = email;
		this.pwd = pwd;
		this.createtime = createtime;
		this.elespace = elespace;
		this.designspace = designspace;
	}

	public UserBean() {
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", email=" + email + ", pwd=" + pwd
				+ ", createtime=" + createtime + ", elespace=" + elespace
				+ ", designspace=" + designspace + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public int getElespace() {
		return elespace;
	}

	public void setElespace(int elespace) {
		this.elespace = elespace;
	}

	public int getDesignspace() {
		return designspace;
	}

	public void setDesignspace(int designspace) {
		this.designspace = designspace;
	}

}
