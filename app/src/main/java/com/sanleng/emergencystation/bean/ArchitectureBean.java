package com.sanleng.emergencystation.bean;

/**
 * 建筑信息
 *
 * @author qiaoshi
 *
 */
public class ArchitectureBean {
	private String name;
	private String id;
	private String mac;
	private String type;
	private String address;
	private String channel_two;
	private String channel_one;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChannel_two() {
		return channel_two;
	}

	public void setChannel_two(String channel_two) {
		this.channel_two = channel_two;
	}

	public String getChannel_one() {
		return channel_one;
	}

	public void setChannel_one(String channel_one) {
		this.channel_one = channel_one;
	}
}
