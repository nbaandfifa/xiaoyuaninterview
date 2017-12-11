package demo.mianshiti.com.mianshiti.bean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper {
	private static DatabaseHelper dbHelper;
	private static SQLiteDatabase db;
	private static final String DATABASE_NAME = "hyphenate.db";
	private static final int DATABASE_VERSION = 1;
	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			String sql = DBrecordBean.sqlcreateTable();

			db.execSQL(sql);
           }

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			System.out.println("upgrade a database");

		}

	}

	public DBHelper(Context ctx) {
		this.mCtx = ctx;
	}

	public DBHelper open() {
		if (dbHelper == null) {
			dbHelper = new DatabaseHelper(mCtx);
		}
		
		if (db == null) {
			db = dbHelper.getWritableDatabase();
		}

		if (!db.isOpen()) {
			db = dbHelper.getWritableDatabase();
		}
		return this;
	}

	public void closeclose() {
		db.close();
		dbHelper.close();
	}

	public long insert(String tableName, ContentValues initialValues) {
		return db.insert(tableName, null, initialValues);
	}

	public boolean delete(String tableName, String deleteCondition,
						  String[] deleteArgs) {
		return db.delete(tableName, deleteCondition, deleteArgs) > 0;
	}

	public boolean update(String tableName, ContentValues initialValues,
						  String selecion, String[] selectArgs) {
		int returnValue = db.update(tableName, initialValues, selecion,
				selectArgs);
		return returnValue > 0;
	}

	public Cursor findList(String tableName, String[] columns, String selction,
						   String[] selectionArgs, String groupBy, String having,
						   String orderBy) {
		return db.query(tableName, columns, selction, selectionArgs, groupBy,
				having, orderBy);
	}

	public Cursor findInfo(String tableName, String[] columns,
						   String selection, String[] selectionArgs, String groupBy,
						   String having, String orderBy, String limit, boolean distinct) {
		Cursor mCursor = db.query(distinct, tableName, columns, selection,
				selectionArgs, groupBy, having, orderBy, limit);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public void execSQL(String sql) {
		db.execSQL(sql);
	}

	public boolean isTableExist(String tableName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}
		try {
			Cursor cursor = null;
			String sql = "select count(1) as c from sqlite_master where"
					+ "type = 'table' and name='" + tableName.trim() + "'";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}
			cursor.close();
		} catch (Exception e) {

		}
		return result;
	}

	public long getCount(String tableName) {
		long result = 0;
		if (tableName == null) {
			return 0;
		}
		try {
			open();
			Cursor cursor = null;
			String sql = "select count(*)   '" + tableName.trim() + "' ";
			cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				Log.i("mylog", "count=" + count + "");
				if (count > 0) {
					result = count;
				}
			}
			cursor.close();
		} catch (Exception d) {

		} finally {
			closeclose();
		}
		return result;
	}

	public boolean isColumnExist(String tableName, String columnName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}
		try {
			Cursor cursor = null;
			String sql = "select count(1) as c from sqlite_master where"
					+ "type='table'and name='" + tableName.trim()
					+ "' and sql like '%" + columnName.trim() + "%'";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}

			}
			cursor.close();
		} catch (Exception e) {

		}
		return result;
	}
}
