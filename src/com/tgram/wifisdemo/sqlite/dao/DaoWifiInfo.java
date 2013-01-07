package com.tgram.wifisdemo.sqlite.dao;

import android.content.Context;

import com.tgram.wifisdemo.sqlite.MyDatabaseHelper;
import com.tgram.wifisdemo.sqlite.dbdictionary.DBDictionary.ColumnsWifiInfo;
import com.tgram.wifisdemo.sqlite.model.ModelWifiInfo;
import com.xcj.android.sqlite.DatabaseHelper;
import com.xcj.android.sqlite.SimpleDao;

public class DaoWifiInfo extends SimpleDao<ModelWifiInfo, Integer> {

	
    public DaoWifiInfo(Context context) {  
        this(ColumnsWifiInfo.TABLE_NAME, context);  
    }
    
    
	public DaoWifiInfo(String tableName, Context context) {
		super(tableName, context);
	}

	@Override
	protected DatabaseHelper getDatabaseHelper(Context context) {
		return new MyDatabaseHelper(context, 
				MyDatabaseHelper.DB_NAME, 
				MyDatabaseHelper.DB_VERSION);
	}

}
