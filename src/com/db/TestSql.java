package com.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TestSql extends SQLiteOpenHelper implements TableParams{
	public static final int CURRENT_VERSION = 1;

	/**
	 * ++++++++++++++++++++++++++++++参考代码++++++++++++++++++++++++++++++++++++++++++<br/>
	 * 1.修改表名为临时表<br/>
	 *  ALTER TABLE Subscription RENAME TO __temp__Subscription;<br/>
	 * 2.创建新表<br/>
	 *  CREATE TABLE Subscription (OrderId VARCHAR(32) PRIMARY KEY ,UserName VARCHAR(32) NOT NULL ,ProductId VARCHAR(16) NOT NULL);<br/>
	 * 3.导入数据<br/>
	 *	INSERT INTO Subscription SELECT OrderId, “”, ProductId FROM __temp__Subscription;<br/>
	 *	或者<br/>
	 *	INSERT INTO Subscription() SELECT OrderId, “”, ProductId FROM __temp__Subscription;<br/>
	 *  注意 双引号”” 是用来补充原来不存在的数据的<br/>
	 * 4.删除临时表<br/>
	 *	DROP TABLE __temp__Subscription;<br/>
	 * ++++++++++++++++++++++++++++++参考代码++++++++++++++++++++++++++++++++++++++++++<br/>
	 */
	public TestSql(Context context) {
		super(context, "consume.db", null, CURRENT_VERSION);
	}

	/** 
	 *  数据库写入sd卡内
	 *  context
	 *			.openOrCreateDatabase(mDbPath, Context.MODE_PRIVATE, null);
	 *  */
	public TestSql(Context context, String path) {
		super(context, path, null, CURRENT_VERSION);
		Log.e("JULY", "path=" + path);
	}

	/** db.execSQL("CREATE TABLE IF NOT EXISTS user_info(_id INTEGER PRIMARY KEY AUTOINCREMENT,id TEXT,username TEXT,password TEXT,phone TEXT,taobaoname TEXT,name TEXT,unlockpassword TEXT,pending TEXT);"); */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// db.execSQL("CREATE TABLE IF NOT EXISTS consume(_id INTEGER PRIMARY KEY AUTOINCREMENT,id INTEGER,time TEXT,amount TEXT,total TEXT,content TEXT,pending TEXT);");
		for (int i = 0; i < TAB_NAMES.length; i++) {
			db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_NAMES[i]+"(_id INTEGER PRIMARY KEY AUTOINCREMENT,id INTEGER,name VARCHAR(60),sex VARCHAR(8),age INTEGER,hobby VARCHAR(100),pending TEXT,weekconsume TEXT,yearconsume TEXT,monthconsume TEXT);");
		}
	}

	/**
	 *  下面的例子会制作 "Persons" 表的备份复件：
	 *	SELECT * INTO Persons_backup FROM Persons
	 * 	IN 子句可用于向另一个数据库中拷贝表：
	 *	SELECT * INTO Persons IN 'Backup.mdb' FROM Persons
	 *  Insert into Table2(field1,field2,...) select value1,value2,... from Table1
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int newValue, int oldValue) {
		// ========================参考代码========================
		if (newValue != oldValue) {
			db.beginTransaction();
			// db.execSQL("DROP TABLE IF EXISTS consume;");
			db.execSQL("ALTER TABLE consume RENAME TO oldconsume;");
			db.execSQL("CREATE TABLE IF NOT EXISTS consume(_id INTEGER PRIMARY KEY AUTOINCREMENT,id INTEGER,time TEXT,amount TEXT,total TEXT,content TEXT NOT NULL,pending TEXT,weekconsume TEXT,yearconsume TEXT,monthconsume TEXT);");
			db.execSQL("INSERT INTO consume SELECT _id,id,time,amount,total,content,pending,'','','' FROM oldconsume;");
			db.execSQL("DROP TABLE oldconsume;");

			db.execSQL("ALTER TABLE revenue RENAME TO oldrevenue;");
			db.execSQL("CREATE TABLE IF NOT EXISTS revenue(_id INTEGER PRIMARY KEY AUTOINCREMENT,id INTEGER,time TEXT,amount TEXT,total TEXT,content TEXT NOT NULL,pending TEXT,weekconsume TEXT,yearconsume TEXT,monthconsume TEXT);");
			db.execSQL("INSERT INTO revenue SELECT _id,id,time,amount,total,content,pending,'','','' FROM oldrevenue;");
			db.execSQL("DROP TABLE oldrevenue;");
			// onCreate(db);
			db.setTransactionSuccessful();
			db.endTransaction();
		}
		// ========================参考代码========================
	}

}
