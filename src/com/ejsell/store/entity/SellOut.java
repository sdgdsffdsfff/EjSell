package com.ejsell.store.entity;

/**
 * �������ⵥ
 * 
 * @author �½�
 *
 */
public class SellOut {

	private Long id;
	private String model;// ���
	private String color;// ��ɫ
	private String size;// ����
	private int amount;// ����
	private double price;// ����
	
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
