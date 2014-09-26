package com.emperises.monercat.database;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.domain.model.ZcmUser;

public class DatabaseImpl implements DatabaseInterface {

	private SQLiteDatabase db;
	private Context context;
	public DatabaseImpl(Context context, List<Class<?>> objs) {
		DatabaseHelper mDatabaseHelper = new DatabaseHelper(context, objs);
		this.db = mDatabaseHelper.getWritableDatabase();
		this.context = context;
	}

	@Override
	public SQLiteDatabase getDatabase() {
		return db;
	}

	@Override
	public void createTableDatabaseForClass(Class<?> classz) {
		DatabaseUtil.createTableDatabaseForClass(classz, db);
	}

	@Override
	public void createTableDatabaseForListClass(List<Class<?>> classz) {
		DatabaseUtil.createTableDatabaseForListClass(classz, db);
	}

	@Override
	public void insertDataForObjs(List<DomainObject> objs) {
		try {
			DatabaseUtil.insertDataForObjs(objs, db);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Cursor queryCursor(Class<?> cls, String[] columns, String where,
			String[] selectionArgs, String orderBy, String limit) {

		return DatabaseUtil.queryCursor(cls, db, columns, where, selectionArgs,
				orderBy, limit);
	}

	@Override
	public List<Object> queryDatabaseForClass(Class<?> classz, String[] columns,
			String where, String[] selectionArgs, String orderBy, String limit) {

		try {
			return DatabaseUtil.queryDatabaseForClass(classz, db, columns,
					where, selectionArgs, orderBy, limit);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Object> queryDatabaseForClass(Class<?> classz, String where,
			String[] selectionArgs, String orderBy, String limit) {

		try {
			return DatabaseUtil.queryDatabaseForClass(classz, db, where,
					selectionArgs, orderBy, limit);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Object> queryDatabaseForClassToList(List<Class<?>> classz,
			String[] columns, String where, String[] selectionArgs,
			String orderBy, String limit) {

		try {
			return DatabaseUtil.queryDatabaseForClassToList(classz, db,
					columns, where, selectionArgs, orderBy, limit);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Object> queryDatabaseForClass(Class<?> classz, String[] columns,
			String where, String[] selectionArgs) {

		try {
			return DatabaseUtil.queryDatabaseForClass(classz, db, columns,
					where, selectionArgs);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Object> queryDatabaseForClass(Class<?> classz, String where,
			String[] selectionArgs) {

		try {
			return DatabaseUtil.queryDatabaseForClass(classz, db, where,
					selectionArgs);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(String tabName, Object obj, String whereClause,
			String[] whereArgs) {
		try {
			DatabaseUtil.update(tabName, obj, db, whereClause, whereArgs);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void saveMyInfo(DomainObject myInfoObj , Context context) {
		try {
			DatabaseUtil.saveMyInfo(myInfoObj, db,context);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ZcmUser getMyInfo() {
		try {
			return DatabaseUtil.getMyInfo(db);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
