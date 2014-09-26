package com.emperises.monercat.database;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "moneycat.db";
	private static final int DATABASE_VERSION = 1;
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, factory, version);
	}
	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	/**
	 * 通过一个对象列表创建数据库
	 * 每个对象对应一张表，对象中的字段将被作为数据库的字段创建
	 * 如果对象为null,只创建字段，否则通过对象中字段的值创建对应数据库字段的值
	 * @param context
	 * @param objs
	 */
	private List<Class<?>> mObjs;
	public DatabaseHelper(Context context , List<Class<?>> objs) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mObjs = objs;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		createDatabaseForObjs(db);
	}

	//通过反射创建数据库字段与表
	private void createDatabaseForObjs(SQLiteDatabase db){
//		List<Class<?>> os = new ArrayList<Class<?>>();
//		os.add(ADInfo.class);
//		os.add(UpdateInfo.class);
		if(mObjs != null){
			DatabaseUtil.createTableDatabaseForListClass(mObjs, db);
		}
//		List<DomainObject> objs = new ArrayList<DomainObject>();
//		ADInfo info = new ADInfo();
//		info.setAdAward("Award");
//		info.setAdDescription("des");
//		info.setAdIcon("iconpath");
//		info.setAdId("id");
//		info.setAdImage("image");
//		info.setAdSource("aaa");
//		info.setAdTtile("title");
//		info.setAdType("type");
//		UpdateInfo u = new UpdateInfo();
//		u.setDownloadUrl("http://");
//		u.setMessage("message");
//		u.setTitle("title");
//		u.setVersionCode("version code");
//		objs.add(info);
//		objs.add(u);
//		try {
//			DatabaseUtil.insertDataForObjs(objs, db);
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
