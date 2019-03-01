package com.weima.aishangyi.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 轮播图广告实体
 * @author cgy
 *
 */
public class LunboEntity {
	@SerializedName("id")
	private int id;
	@SerializedName("type")
	private int type;
	@SerializedName("title")
	private String title;
	@SerializedName("detail")
	private String detail;
	@SerializedName("operateId")
	private String operateId;
	public String getOperateId() {
		return operateId;
	}
	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@SerializedName("imageUrl")
	private String imageUrl;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
