package com.asuper.angelgroup.moduel.login.bean;

import java.io.Serializable;

public class RelationItem implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String id;// 关系id
	private String relationName;// 关系名称
	
	public RelationItem() {
	}

	public RelationItem(String id, String relationName) {
		super();
		this.id = id;
		this.relationName = relationName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

}
