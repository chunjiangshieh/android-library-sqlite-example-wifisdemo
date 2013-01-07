package com.tgram.wifisdemo.util;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import android.content.Context;

/**
 * 百度定位服务的管理类
 * @author chunjiang.shieh
 *
 */
public class BDLocationManager {
	
//	private static BDLocationManager mInstance;
	
//	private static BDLocationManager getInstance(){
//		if(mInstance != null){
//			mInstance = new BDLocationManager();
//		}
//		return mInstance;
//	}
	//5秒获取一次
	public static final int SCANSPAN_TIME = 5000;  //单位ms
	
	private static final String PRODNAME_DEFAULT = "prodName_BDLocationManager";
	
	public static final String COORTYPE_GCJ02 = "gcj02";  //国测局经纬度坐标系
	
	public static final String COORTYPE_BD09 = "bd09";  //百度墨卡托坐标系
	
	public static final String COORTYPE_BD09LL = "bd09ll";  //百度经纬度坐标系  默认
	
	
	/**
	 * 百度定位客户端是否开启的标志位
	 */
	private boolean isStart;
	
	/**
	 * 百度定位的客户端
	 */
	private LocationClient mLocationClient;
	
	/**
	 * 百度定位客户端的设置信息(定位方式)
	 */
	private LocationClientOption mLocationClientOption;
	
	
//	private Context mContext;
	
	/**
	 * 
	 * @param context
	 * @param bdLocationListener
	 */
	public BDLocationManager(Context context,BDLocationListener bdLocationListener){
		mLocationClient = new LocationClient(context);
		mLocationClient.registerLocationListener(bdLocationListener);
	}
	
	
	/**
	 * 初始化定位方式
	 * @param locationClientOption
	 */
	public void initLocationClientOption(LocationClientOption locationClientOption){
		this.mLocationClientOption = locationClientOption;
		mLocationClient.setLocOption(mLocationClientOption);
	}
	
	/**
	 * 打开百度定位的客户端
	 */
	public void startBDLocationClient(){
		if(mLocationClient != null){
			initDefaultLocationClientOption();
			mLocationClient.start();
			isStart = true;
		}
	}
	
	/**
	 * 关闭百度定位的客户端
	 */
	public void stopBDLocationClient(){
		if(mLocationClient != null){
			mLocationClient.stop();
			isStart = false;
		}
	}
	
	/**
	 * ScanSpan:当不设此项，或者所设的整数值小于1000（ms）时，采用一次定位模式
	 */
	private void initDefaultLocationClientOption(){
		if(mLocationClientOption == null){
			mLocationClientOption = new LocationClientOption();
			mLocationClientOption.setOpenGps(true);
			mLocationClientOption.setCoorType(COORTYPE_BD09LL);	//设置坐标类型为bd09ll
			mLocationClientOption.setPriority(LocationClientOption.GpsFirst);	//设置GPS优先
			mLocationClientOption.setProdName(PRODNAME_DEFAULT);	//设置产品线名称
			mLocationClientOption.setScanSpan(SCANSPAN_TIME);
			mLocationClientOption.disableCache(true);  //静止启用缓存定位
			mLocationClient.setLocOption(mLocationClientOption);
		}
	}


	public boolean isStart() {
		return isStart;
	}


	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	
	
	public void requestLocation(){
		if(mLocationClient != null){
			mLocationClient.requestLocation();
		}
	}
	
	
	public void requestPoi(){
		if(mLocationClient != null){
			mLocationClient.requestPoi();
		}
	}

}
