package com.emperises.monercat.database;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.domain.model.ZcmUser;

public interface DatabaseInterface {

	SQLiteDatabase getDatabase();
	void createTableDatabaseForClass(Class<?> classz);
	void createTableDatabaseForListClass(List<Class<?>> classz);
	void insertDataForObjs(List<DomainObject> objs) ;
	Cursor queryCursor(Class<?> cls,String[] columns , String where, String[] selectionArgs, String orderBy , String limit);
	Object queryDatabaseForClass(Class<?> classz , String[] columns , String where ,String[] selectionArgs, String orderBy , String limit);
	Object queryDatabaseForClass(Class<?> classz , String where ,String[] selectionArgs, String orderBy , String limit);
	List<Object> queryDatabaseForClassToList(List<Class<?>> classz , String[] columns , String where, String[] selectionArgs, String orderBy,String limit) ;
	Object queryDatabaseForClass(Class<?> classz , String[] columns , String where ,String[] selectionArgs);
	Object queryDatabaseForClass(Class<?> classz , String where ,String[] selectionArgs) ;
	void update(String tabName ,Object obj, String whereClause , String[] whereArgs) ;
	void saveMyInfo(DomainObject myInfoObj,Context context);
	ZcmUser getMyInfo();
}
