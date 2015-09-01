package com.ejsell.store.entity;

/**
 * 销售发货
 * 
 * @author 陈捷
 *
 */
public class SellOut {

	private Long id;
	private String model;// 款号
	private String color;// 颜色
	private String size;// 尺寸
	private int amount;// 数量
	private double price;// 单价

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
