package com.weima.aishangyi.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 接口基类
 * @author cgy
 *
 */
public class CommonEntity {
	@SerializedName("code")
	private String code;//返回状态码(200表示成功)

	@SerializedName("message")
	private String message;//返回描述

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
