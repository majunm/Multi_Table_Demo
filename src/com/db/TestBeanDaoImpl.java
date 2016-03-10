package com.db;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.domain.TestBean;

public class TestBeanDaoImpl implements Dao<TestBean>, TableParams {
	private TestSql iSql;
	private String mDbPath;
	private static String DATABASE_NAME = "JULY_JULY.db";

	/** #######################单例模式######################### */
	private TestBeanDaoImpl() {
	}

	public static TestBeanDaoImpl getInstance() {
		return TestBeanDaoImplHolder.INSTANCE;
	}

	public static final class TestBeanDaoImplHolder {
		private static final TestBeanDaoImpl INSTANCE = new TestBeanDaoImpl();
	}

	public TestBeanDaoImpl init(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			mDbPath = Environment.getExternalStorageDirectory()
					+ File.separator + DATABASE_NAME;
		} else {
			mDbPath = context.getFilesDir().getPath() + File.separator
					+ DATABASE_NAME;
		}
		iSql = new TestSql(context, mDbPath);
		return this;
	}

	/** #######################单例模式######################### */

	@Override
	public long insert(TestBean m) {
		SQLiteDatabase db = getDatabase();
		ContentValues values = new ContentValues();
		long isOk = -1;
		for (int i = 0; i < TAB_NAMES.length; i++) {
			values.clear();
			db.beginTransaction();
			values.put("id", m.getId());
			values.put("sex", m.getSex());
			values.put("age", m.getAge());
			values.put("name", m.getName());
			values.put("hobby", m.getHobby());
			// values.put("pending", m.getPending());
			isOk = db.insert(TAB_NAMES[i], "id", values);
			db.setTransactionSuccessful();
			db.endTransaction();
		}
		/** 返回0 代表插入数据库失败.... */
		return isOk;
	}

	public long insert(TestBean m, int tableNum) {
		SQLiteDatabase db = getDatabase();
		ContentValues values = new ContentValues();
		long isOk = -1;
		values.clear();
		db.beginTransaction();
		values.put("id", m.getId());
		values.put("sex", m.getSex());
		values.put("age", m.getAge());
		values.put("name", m.getName());
		values.put("hobby", m.getHobby());
		// values.put("pending", m.getPending());
		isOk = db.insert(TAB_NAMES[tableNum], "id", values);
		db.setTransactionSuccessful();
		db.endTransaction();
		/** 返回0 代表插入数据库失败.... */
		return isOk;
	}

	public SQLiteDatabase getDatabase() {
		return iSql.getWritableDatabase();
	}

	@Override
	public int delete(Serializable id) {
		return 0;
	}

	@Override
	public int update(TestBean m) {
		SQLiteDatabase db = getDatabase();
		int update = -1;
		ContentValues values = new ContentValues();
		for (int i = 0; i < TAB_NAMES.length; i++) {
			values.clear();
			values.put("id", m.getId());
			values.put("amount", m.getSex());
			values.put("total", m.getAge());
			values.put("time", m.getName());
			values.put("content", m.getHobby());
			values.put("pending", m.getPending());
			update = db.update(TAB_NAMES[i], values, "id = ?",
					new String[] { (m.getId() + "") });
		}
		return update;
	}

	public List<TestBean> findAllByTableNum(int nameNum) {
		SQLiteDatabase db = getDatabase();
		List<TestBean> list = new ArrayList<TestBean>();
		String sql = "SELECT * FROM " + TAB_NAMES[nameNum % 7]
				+ " ORDER BY _id DESC;";
		Cursor cursor = db.rawQuery(sql, null);
		Log.e("JULY", "SQL=" + sql);
		TestBean bean = null;
		if (cursor != null) {
			while (cursor.moveToNext()) {
				bean = new TestBean();
				bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
				bean.setName(cursor.getString(cursor.getColumnIndex("name")));
				bean.setSex(cursor.getString(cursor.getColumnIndex("sex")));
				bean.setAge(cursor.getInt(cursor.getColumnIndex("age")));
				bean.setHobby(cursor.getString(cursor.getColumnIndex("hobby")));
				// bean.setPending(cursor.getString(cursor
				// .getColumnIndex("pending")));
				list.add(bean);
			}
		}
		Log.e("JULY", "list=" + list.size());
		return list;
	}

	@Override
	public List<TestBean> findAll() {
		SQLiteDatabase db = getDatabase();
		List<TestBean> list = new ArrayList<TestBean>();
		String sql = "SELECT * FROM consume ORDER BY _id DESC;";
		Cursor cursor = db.rawQuery(sql, null);
		TestBean bean = null;
		if (cursor != null) {
			while (cursor.moveToNext()) {
				bean = new TestBean();
				bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
				bean.setName(cursor.getString(cursor.getColumnIndex("time")));
				bean.setSex(cursor.getString(cursor.getColumnIndex("amount")));
				bean.setAge(cursor.getInt(cursor.getColumnIndex("total")));
				bean.setHobby(cursor.getString(cursor.getColumnIndex("content")));
				bean.setPending(cursor.getString(cursor
						.getColumnIndex("pending")));
				list.add(bean);
			}
		}
		return list;
	}

	public TestBean findBeanById(int id) {
		SQLiteDatabase db = getDatabase();
		// String sql = "SELECT * FROM consume where id = " + id + ";";
		String sql = "SELECT * FROM Monday WHERE _id = " + id + ";";
		Cursor cursor = db.rawQuery(sql, null);
		TestBean bean = null;
		if (cursor != null) {
			if (cursor.moveToNext()) {
				bean = new TestBean();
				bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
				bean.setName(cursor.getString(cursor.getColumnIndex("name")));
				bean.setSex(cursor.getString(cursor.getColumnIndex("sex")));
				bean.setAge(cursor.getInt(cursor.getColumnIndex("age")));
				bean.setHobby(cursor.getString(cursor.getColumnIndex("hobby")));
				// bean.setPending(cursor.getString(cursor
				// .getColumnIndex("pending")));
			}
		}
		return bean;
	}

	public int insert(TestBean m, int... id) {
		SQLiteDatabase db = getDatabase();
		ContentValues values = new ContentValues();
		values.put("total", m.getAge());
		int insert = db.update("consume", values, "id = ?",
				new String[] { (id + "") });
		Log.e("JULY", "insert-->" + insert);
		return insert;
	}

	public int insert(double m, int id) {
		SQLiteDatabase db = getDatabase();
		ContentValues values = new ContentValues();
		values.put("total", m);
		int insert = db.update("consume", values, "id = ?",
				new String[] { (id + "") });
		Log.e("JULY", "insert-->" + insert);
		return insert;
	}

	/**
	 * 右外链接
	 * 
	 * @return
	 */
	public List<TestBean> rightJoinQuery() {
		return iQuery(1);
	}

	/**
	 * 右外链接 type=0 // 左外链接 type=1 // 右外链接 type=2 // 全外链接 type=3 // 内连接 type=4 //
	 * 交叉链接
	 * 
	 * @return
	 */
	public List<TestBean> iQuery(int type) {
		SQLiteDatabase db = getDatabase();
		List<TestBean> list = new ArrayList<TestBean>();
		// String sql = "SELECT * FROM consume ORDER BY _id DESC;";
		String sql = "select * from Monday left join Tuesday on Monday.name=Tuesday.name";
		switch (type) {
		case 0:
			sql = "SELECT * FROM Monday LEFT JOIN Tuesday ON Monday.name=Tuesday.name";
			break;
		case 1:
			sql = "SELECT * FROM Monday RIGHT OUTER JOIN Tuesday ON Monday.name=Tuesday.name";
			break;
		case 2:
			sql = "SELECT * FROM Monday FULL OUTER JOIN Tuesday ON Monday.name=Tuesday.name";
			break;
		case 3:
			sql = "SELECT * FROM Monday JOIN Tuesday ON Monday.name=Tuesday.name";
			break;
		case 4:
			sql = "SELECT * FROM Monday CROSS JOIN Tuesday";
			break;
		}
		Cursor cursor = db.rawQuery(sql, null);
		TestBean bean = null;
		if (cursor != null) {
			while (cursor.moveToNext()) {
				bean = new TestBean();
				bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
				bean.setName(cursor.getString(cursor.getColumnIndex("name")));
				bean.setSex(cursor.getString(cursor.getColumnIndex("sex")));
				bean.setAge(cursor.getInt(cursor.getColumnIndex("age")));
				bean.setHobby(cursor.getString(cursor.getColumnIndex("hobby")));
				// bean.setPending(cursor.getString(cursor
				// .getColumnIndex("pending")));
				list.add(bean);
			}
		}
		return list;
	}

	public List<TestBean> exeSql(String sql) {
		SQLiteDatabase db = getDatabase();
		List<TestBean> list = new ArrayList<TestBean>();
		Log.e("JULY", "exeSql="+sql);
		try {
			Cursor cursor = db.rawQuery(sql, null);
			TestBean bean = null;
			if (cursor != null) {
				while (cursor.moveToNext()) {
					bean = new TestBean();
					bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
					bean.setName(cursor.getString(cursor.getColumnIndex("name")));
					bean.setSex(cursor.getString(cursor.getColumnIndex("sex")));
					bean.setAge(cursor.getInt(cursor.getColumnIndex("age")));
					bean.setHobby(cursor.getString(cursor
							.getColumnIndex("hobby")));
					list.add(bean);
				}
			}
		} catch (Exception e) {
		}
		Log.e("JULY", "list="+list.size());
		return list;
	}
}
