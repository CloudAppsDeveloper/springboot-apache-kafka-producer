package com.cinch.kafka.orders.model;

import java.io.Serializable;


public class Orders implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Long id;
	private String orderName;
	private String orderDescription;
	private Double orderValue;
	private int orderQuantity;
	private int orderNumber;
	private String timestamp;
	private String offsetNum;
	private int partitionNum;
	private String groupId;
	
	public Orders(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}

	public Double getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Double orderValue) {
		this.orderValue = orderValue;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	

	public String getOffsetNum() {
		return offsetNum;
	}

	public void setOffsetNum(String offsetNum) {
		this.offsetNum = offsetNum;
	}

	public int getPartitionNum() {
		return partitionNum;
	}

	public void setPartitionNum(int partition) {
		this.partitionNum = partition;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", orderName=" + orderName + ", orderDescription=" + orderDescription
				+ ", orderValue=" + orderValue + ", orderQuantity=" + orderQuantity + ", orderNumber=" + orderNumber
				+ ", timestamp=" + timestamp + ", offsetNum=" + offsetNum + ", partitionNum=" + partitionNum
				+ ", groupId=" + groupId + "]";
	}


	

}
