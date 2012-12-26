package br.com.globalcode.android.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static DatabaseHelper instance;

	private static final String DATABASE_NAME = "DBTask";
	
	private static final int DATABASE_VERSION = 2;
	
	public static final String TABLE_TASK = "task";
	
	public static final String TASK_ID = "id";
	public static final String TASK_NAME = "name";
	public static final String TASK_DESCRIPTION = "description";
	public static final String TASK_FINISHED = "finished";
	
	public static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_TASK
			+ " (" + TASK_ID + " integer primary key autoincrement, " + TASK_NAME + " text, "
			+ TASK_DESCRIPTION + " text, " + TASK_FINISHED + " integer)";
	
	public static final String SELECT_ALL_TASKS_QUERY = "SELECT " + TASK_ID + ", " + TASK_NAME + ", "
			+ TASK_DESCRIPTION + ", " + TASK_FINISHED + " FROM "
			+ TABLE_TASK;
	
	private DatabaseHelper(Context context) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public static final DatabaseHelper getInstance(Context context) {

		if (instance == null) {
			instance = new DatabaseHelper(context);
		}

		return instance;
	}
	
	@Override
	public final void onCreate(SQLiteDatabase _db) {

		_db.execSQL(DATABASE_CREATE);
	}

	@Override
	public final void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {

		_db.execSQL( "DROP TABLE IF EXISTS " + TABLE_TASK );
		onCreate( _db );
	}

}
