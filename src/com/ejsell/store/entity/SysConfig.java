package com.ejsell.store.entity;

/**
 * 系统配置类
 * 
 * @author 陈捷
 *
 */
public class SysConfig {

	private Long id;
	private String name;// 名称
	private String val;// 值

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
