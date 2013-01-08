package com.tgram.wifisdemo;


import java.util.Date;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.tgram.wifisdemo.sqlite.dao.DaoWifiInfo;
import com.tgram.wifisdemo.sqlite.model.ModelWifiInfo;
import com.tgram.wifisdemo.util.BDLocationManager;
import com.tgram.wifisdemo.util.WifiHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	
	private static final String[] distances={"5","10","20","30","40","50","100"};

	public static String IP;             //本机IP
	public static String MAC;            //本机MAC

	Spinner spinner = null;
	Button btn=null;
	Button scan_btn = null;
	EditText ip_txt=null;
	EditText mac_txt=null;
	TextView scan_txt = null;

	private WifiHelper mWifiHelper;
	
	private DaoWifiInfo mDaoWifiInfo;
	private ModelWifiInfo mModelWifiInfo;
	//默认是5米
	private static long distance = 5;
	
	 private ArrayAdapter<String> adapter;  
	 
	 private boolean isFirstLoc = true;  //是否是第一次定位

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initVarible();
		startBDLocationClient();
	}


	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopBDLocationClient();
	}


	private void initView(){
		btn=(Button)findViewById(R.id.btn);
		ip_txt=(EditText)findViewById(R.id.ip_txt);
		mac_txt=(EditText)findViewById(R.id.mac_txt);
		scan_btn = (Button) findViewById(R.id.scan_btn);
		scan_txt = (TextView) findViewById(R.id.scan_txt);
		initSpinner();
	}

	
	private void initSpinner(){
		spinner = (Spinner) findViewById(R.id.Spinner01);
		//将可选内容与ArrayAdapter连接起来  
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				distances);  
		//设置下拉列表的风格  
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		//将adapter 添加到spinner中  
		spinner.setAdapter(adapter);  
		//添加事件Spinner事件监听    
		spinner.setOnItemSelectedListener(new SpinnerSelectedListener());  
		//设置默认值  
		spinner.setVisibility(View.VISIBLE);  
	}
	

	private void initVarible(){
		mWifiHelper = new WifiHelper(this);
		initBDLocationManager();
		mDaoWifiInfo = new DaoWifiInfo(this);
	}
	
	
	 //使用数组形式操作  
	class SpinnerSelectedListener implements OnItemSelectedListener{  

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
				long arg3) {  
			distance = Integer.parseInt(distances[arg2]);
		}  

		public void onNothingSelected(AdapterView<?> arg0) {  
		}  
	} 

	//单位是M
	private static final double EARTH_RADIUS = 6378137;
	public double getDistance(double lat1, double lng1, double lat2, double lng2){
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double radLatDiffer = radLat1 - radLat2;
		double radLngDiffer = rad(lng1) - rad(lng2);
		double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(radLatDiffer/2),2) + 
				Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(radLngDiffer/2),2)));
		distance = distance * EARTH_RADIUS;
		distance = Math.round(distance * 10000) / 10000;
		return distance;
	}
	
	private double rad(double d){
		return d * Math.PI / 180.0;
	}
	
	
	public void onclick(View v){
		switch (v.getId()) {
		case R.id.btn:
//			IP = getLocalIpAddress();  //获取本机IP
//			MAC = getLocalMacAddress();//获取本机MAC
//			IP = intToIP(mWifiHelper.getIP());
//			MAC = mWifiHelper.getMac();
//			ip_txt.setText(IP);
//			mac_txt.setText(MAC);
			requestLocation();
			break;
		case R.id.scan_btn:
			mWifiHelper.startScan();
			scan_txt.setText(mWifiHelper.lookupScanInfo());
			break;
		}
	}

	//    public String getLocalIpAddress() {
	//		try {
	//			for (Enumeration<NetworkInterface> en = NetworkInterface
	//					.getNetworkInterfaces(); en.hasMoreElements();) {
	//				NetworkInterface intf = en.nextElement();
	//				for (Enumeration<InetAddress> enumIpAddr = intf
	//						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	//					InetAddress inetAddress = enumIpAddr.nextElement();
	//					if (!inetAddress.isLoopbackAddress()) {
	//						return inetAddress.getHostAddress().toString();
	//					}
	//				}
	//			}
	//		} catch (SocketException ex) {
	//			Log.e("WifiPreference IpAddress", ex.toString());
	//		}
	//		return null;
	//	}

	public String getLocalIpAddress() {
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return intToIP(info.getIpAddress());
	}

	public String getLocalMacAddress() {
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	private String intToIP(int ip){
		return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "."
				+ ((ip >> 24) & 0xFF);
	}
	
	private BDLocationManager mBDLocationManager;
	/**
	 * 初始化百度定位
	 */
	private void initBDLocationManager(){
		mBDLocationManager = new BDLocationManager(this,
				new MyLocationListener());
	}
	
	/**
	 * 打开百度定位客户端
	 */
	private void startBDLocationClient(){
		if(mBDLocationManager != null){
			mBDLocationManager.startBDLocationClient();
		}
	}
	
	/**
	 * 关闭百度定位客户端
	 */
	private void stopBDLocationClient(){
		if(mBDLocationManager != null){
			mBDLocationManager.stopBDLocationClient();
		}
	}
	/**
	 * 定位
	 */
	private void requestLocation(){ 
		if(mBDLocationManager != null){
			mBDLocationManager.requestLocation();
		}
	}
	
	
	
	
	class MyLocationListener implements BDLocationListener{
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			doLocationChangedEvent(location);
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {
			// TODO Auto-generated method stub
		}
	}
	
	
	
	private double lastLongitude = 0.0f;
	private double lastLatitude = 0.0f;
	
	
	/**
	 * 位置改变的处理
	 * @param location
	 */
	private void doLocationChangedEvent(BDLocation location) {
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		Log.d(TAG, "---------------->onLocationChanged lng,lat : "
				+longitude+ "," +latitude);
		ip_txt.setText("Longitude: "+longitude);
		mac_txt.setText("Latitude: "+latitude);
		//位置改变
		
//		if(longitude != lastLongitude
//				|| latitude != lastLatitude){  
//			insert(longitude, latitude);
//			lastLongitude = longitude;
//			lastLatitude = latitude;
//		}
		
		//第一次定位或者两次位置超过distance时搜索网路列表
		if(isFirstLoc
				||(getDistance(lastLatitude, lastLongitude,latitude, longitude) > distance)){
			Log.d(TAG, "---------------->need insert ");
			insert(longitude, latitude);
			lastLongitude = longitude;
			lastLatitude = latitude;
			isFirstLoc = false;
		}
	
	}
	
	/**
	 * 将WIFI信息插入数据库
	 * @param longitude
	 * @param latitude
	 */
	private void insert(double longitude,double latitude){
		mWifiHelper.startScan();
		mModelWifiInfo = new ModelWifiInfo();
		List<ScanResult> wifiList = mWifiHelper.getWifiList();
		if(wifiList != null){
			for(ScanResult sr:wifiList){
				mModelWifiInfo.setSsid(sr.SSID);
				mModelWifiInfo.setBssid(sr.BSSID);
				mModelWifiInfo.setCapabilities(sr.capabilities);
				mModelWifiInfo.setLevel(sr.level);
				mModelWifiInfo.setFrequency(sr.frequency);
				mModelWifiInfo.setCreateDate(new Date().toGMTString());
				mModelWifiInfo.setLongitude(String.valueOf(longitude));
				mModelWifiInfo.setLatitude(String.valueOf(latitude));
				try {
					mDaoWifiInfo.save(mModelWifiInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
