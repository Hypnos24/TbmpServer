package com.wzq.tbmp.controller.response;

import java.io.Serializable;

public class ResponseInfo implements Serializable {
	private static final long serialVersionUID = 3814575800489648411L;
	private String respCode;
	private String respDesc;

	public ResponseInfo() {
		
	}

	public ResponseInfo(String respCode, String respDesc) {
		this.respCode = respCode;
		this.respDesc = respDesc;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

}
