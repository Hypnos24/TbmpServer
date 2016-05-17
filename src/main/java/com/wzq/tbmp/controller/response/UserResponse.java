package com.wzq.tbmp.controller.response;

import com.wzq.tbmp.pojo.ServerUser;

public class UserResponse extends ResponseInfo{
	private static final long serialVersionUID = -1609744437451465709L;
	
	private String userId;
	private String username;
	private String nickName;
	private long lastLoginTime;
	private int accountType;
	private String headImg;
	private String birthday;
	private int sex;
	
	public UserResponse(){
		
	}
	public UserResponse(String respCode, String respDesc, ServerUser user ){
		super(respCode, respDesc);
		this.userId = user.getUserId();
		this.username = user.getUsername();
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	

}
