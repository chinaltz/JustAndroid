package com.litingzhe.justandroid.main.model;

/**
 * 店铺信息
 */
public class StoreInfo
{
	protected String Id;
	protected String name;
	protected boolean isChoosed;
    private boolean isEdtor;

	public boolean isEdtor() {
		return isEdtor;
	}

	public void setIsEdtor(boolean isEdtor) {
		this.isEdtor = isEdtor;
	}

	public StoreInfo(String id, String name) {
		Id = id;
		this.name = name;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChoosed() {
		return isChoosed;
	}

	public void setChoosed(boolean isChoosed) {
		this.isChoosed = isChoosed;
	}
}
