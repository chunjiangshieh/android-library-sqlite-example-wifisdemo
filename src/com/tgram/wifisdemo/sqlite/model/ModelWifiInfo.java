package com.tgram.wifisdemo.sqlite.model;
/**
 * WifiInfo的模型类
 * @author chunjiang.shieh
 *
 */
public class ModelWifiInfo {
	
	private long _id;
	private String ssid;
	private String bssid;
	private String capabilities;
	private int level;
	private int frequency;
	private String createDate;
	private String longitude;
	private String latitude;
	
	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bssid) {
		this.bssid = bssid;
	}
	public String getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(String capabilities) {
		this.capabilities = capabilities;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	

}
