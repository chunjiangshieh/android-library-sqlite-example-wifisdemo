package com.tgram.wifisdemo.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tgram.wifisdemo.sqlite.dbdictionary.DBDictionary.ColumnsWifiInfo;
import com.xcj.android.sqlite.DatabaseHelper;

public class MyDatabaseHelper extends DatabaseHelper {
	
	public static final String DB_NAME = "wifiinfo.db";
	public static final int DB_VERSION = 1;
	
	
	public MyDatabaseHelper(Context context, String dbName, int dbVersion) {
		super(context, dbName, dbVersion);
	}

	@Override
	protected boolean isCreateDB() {
		return true;  //TRUE 执行SQL语句
	}

	@Override
	protected void createTables(SQLiteDatabase db) {
		StringBuilder buf = new StringBuilder();
		buf.append("CREATE TABLE ");
		buf.append(ColumnsWifiInfo.TABLE_NAME);
		buf.append(" (");
		buf.append(ColumnsWifiInfo._ID);
		buf.append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
		buf.append(ColumnsWifiInfo.COLUMN_SSID);
		buf.append(" TEXT NOT NULL,");
		buf.append(ColumnsWifiInfo.COLUMN_BSSID);
		buf.append(" TEXT NOT NULL,");
		buf.append(ColumnsWifiInfo.COLUMN_CAPABILITIES);
		buf.append(" TEXT,");
		buf.append(ColumnsWifiInfo.COLUMN_LEVEL);
		buf.append(" INTEGER,");
		buf.append(ColumnsWifiInfo.COLUMN_FREQUENCY);
		buf.append(" INTEGER,");
		buf.append(ColumnsWifiInfo.COLUMN_CREATEDATE);
		buf.append(" TEXT,");
		buf.append(ColumnsWifiInfo.COLUMN_LONGITUDE);
		buf.append(" TEXT,");
		buf.append(ColumnsWifiInfo.COLUMN_LATITUDE);
		buf.append(" TEXT);");
		db.execSQL(buf.toString());
	}

	@Override
	protected void dropTables(SQLiteDatabase db) {
		String sql = "DROP TABLE IF EXISTS " + ColumnsWifiInfo.TABLE_NAME;
		db.execSQL(sql);
	}

	@Override
	protected String getDBPath() {
		return null;
	}

}
