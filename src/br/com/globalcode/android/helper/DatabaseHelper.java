package br.com.globalcode.android.helper;

import java.util.ArrayList;
import java.util.List;

import br.com.globalcode.android.model.Task;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static DatabaseHelper instance;

	private static final String DATABASE_NAME = "DBName";
	
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_TASK = "task";
	
	private static final String TASK_ID = "id";
	private static final String TASK_NAME = "name";
	private static final String TASK_DESCRIPTION = "description";
	private static final String TASK_FINISHED = "finished";
	
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_TASK
			+ " (" + TASK_ID + " integer primary key autoincrement, " + TASK_NAME + " text, "
			+ TASK_DESCRIPTION + " text, " + TASK_FINISHED + " boolean)";
	
	private static final String SELECT_ALL_TASKS_QUERY = "SELECT " + TASK_ID + ", " + TASK_NAME + ", "
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
	public final void onCreate(SQLiteDatabase db) {

		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public final void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
		
		_db.execSQL( "DROP TABLE IF EXISTS " + TABLE_TASK );
		onCreate( _db );
	}
	
	public final Long addTask(Task _newTask) {
		
		Long result = Long.valueOf(0);
		
		ContentValues values = new ContentValues();
		values.put( TASK_NAME, _newTask.getName() );
		values.put( TASK_DESCRIPTION, _newTask.getDescription() );
		values.put( TASK_FINISHED, _newTask.getFinished().booleanValue() );

		SQLiteDatabase db = this.getWritableDatabase();
		result = db.insert( TABLE_TASK, null, values );
		
		db.close();
		
		return result;
	}
	
	public final Task findTaskBy(Integer _id) {
		
		Task result = null;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_TASK, new String[] { TASK_ID,
                TASK_NAME, TASK_DESCRIPTION, TASK_FINISHED }, TASK_ID + "=?",
                new String[] { String.valueOf(_id) }, null, null, null, null);
		
        if ( cursor != null ) {
        	
            cursor.moveToFirst();
            
			Integer id = Integer.parseInt( cursor.getString(0) );
			String name = cursor.getString(1);
			String description = cursor.getString(2);
			Boolean finished = Boolean.getBoolean(cursor.getString(3));
			
			result = Task.getInstance( id, name , description, finished);
        }
 
        db.close();
        
        return result;
	}
	
	public final List<Task> getAllTasks() {
		
		List<Task> result = new ArrayList<Task>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery( SELECT_ALL_TASKS_QUERY, null );
		
		if( cursor.moveToFirst() ) {
			
			result = new ArrayList<Task>(cursor.getCount());
			
			do {
				
				Integer id = Integer.parseInt( cursor.getString(0) );
				String name = cursor.getString(1);
				String description = cursor.getString(2);
				Boolean finished = Boolean.getBoolean(cursor.getString(3));
				
				Task task = Task.getInstance( id, name , description, finished);
				result.add(task);
				
			} while( cursor.moveToNext() );
		}
		
		db.close();
		
		return result;
	}
	
	public final Integer updateTask(Task _task) {
		
		Integer result = Integer.valueOf(0);
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put( TASK_ID, _task.getId() );
		values.put( TASK_NAME, _task.getName() );
		values.put( TASK_DESCRIPTION, _task.getDescription() ); 
		values.put( TASK_FINISHED, _task.getFinished() );
		
		result = db.update(TABLE_TASK, values, TASK_ID + " = ? ", new String[] { String.valueOf( _task.getId() ) } );
		
		db.close();
		
		return result;
	}
	
	public final Integer deleteTask(Task _task) {
		
		Integer result = Integer.valueOf(0);
		
		SQLiteDatabase db = this.getWritableDatabase();
		result = db.delete(TABLE_TASK, TASK_ID + " = ? ", new String[] { String.valueOf( _task.getId() ) } );
		
		db.close();
		
		return result;
	}
}
