package com.tgram.wifisdemo.sqlite.dbdictionary;

import android.provider.BaseColumns;

/**
 * 数据库的数据字段
 * @author chunjiang.shieh
 *
 */
public class DBDictionary {
	
	
	/**
	 * WifiInfo 表的表字段
	 * @author chunjiang.shieh
	 *
	 */
	public static final class ColumnsWifiInfo implements BaseColumns{
		public final static String TABLE_NAME = "wifi_info";

		public final static String COLUMN_SSID = "ssid"; 
		public final static String COLUMN_BSSID= "bssid"; //物理地址
		public final static String COLUMN_CAPABILITIES = "capabilities";//Wifi支持的功能
		public final static String COLUMN_LEVEL = "level"; //信号强度
		public final static String COLUMN_FREQUENCY = "frequency";  //频率
		public final static String COLUMN_CREATEDATE = "createDate";//数据插入时间
		public final static String COLUMN_LONGITUDE = "longitude";
		public final static String COLUMN_LATITUDE = "latitude";
	}
	
		
}
