package br.com.globalcode.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.globalcode.android.helper.DatabaseHelper;
import br.com.globalcode.android.vo.Task;

public class TaskModel implements Serializable {

	private static final long serialVersionUID = 6734638702431329595L;

	private static TaskModel instance;
	private DatabaseHelper databaseHelper;
	
	private TaskModel() {
 
	}
	
	public static final TaskModel getInstance(Context context) {
		
		if(instance == null) {
			instance = new TaskModel();
			instance.databaseHelper = DatabaseHelper.getInstance(context);
		}
		
		return instance;
	}

	public final Long addTask(Task _newTask) {
		
		Long result = Long.valueOf(0);
		
		ContentValues values = new ContentValues();
		values.put( DatabaseHelper.TASK_NAME, _newTask.getName() );
		values.put( DatabaseHelper.TASK_DESCRIPTION, _newTask.getDescription() );
		values.put( DatabaseHelper.TASK_FINISHED, _newTask.getFinished().booleanValue() );

		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		result = db.insert( DatabaseHelper.TABLE_TASK, null, values );
		
		db.close();
		
		return result;
	}
	
	public final Task findTaskBy(Integer _id) {
		
		Task result = null;
		
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor cursor = db.query( DatabaseHelper.TABLE_TASK, new String[] { DatabaseHelper.TASK_ID,
				DatabaseHelper.TASK_NAME, DatabaseHelper.TASK_DESCRIPTION, DatabaseHelper.TASK_FINISHED }, DatabaseHelper.TASK_ID + "=?",
                new String[] { String.valueOf(_id) }, null, null, null, null );
		
        if ( cursor != null ) {
        	
            cursor.moveToFirst();
            
			Integer id = Integer.parseInt( cursor.getString(0) );
			String name = cursor.getString(1);
			String description = cursor.getString(2);
			Boolean finished = Boolean.getBoolean( cursor.getString(3) );
			
			result = Task.getInstance( id, name , description, finished );
			cursor.close();
        }
 
        db.close();
        
        return result;
	}
	
	public final List<Task> getAllTasks() {
		
		List<Task> result = new ArrayList<Task>();
		
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery( DatabaseHelper.SELECT_ALL_TASKS_QUERY, null );
		
		if( cursor.moveToFirst() ) {
			
			result = new ArrayList<Task>( cursor.getCount() );
			
			do {
				
				Integer id = Integer.parseInt( cursor.getString(0) );
				String name = cursor.getString(1);
				String description = cursor.getString(2);
				Boolean finished = cursor.getInt(3) == 1 ? Boolean.TRUE : Boolean.FALSE;
				
				Task task = Task.getInstance( id, name, description, finished );
				result.add(task);
				
			} while( cursor.moveToNext() );
		}
		
		cursor.close();
		db.close();
		
		return result;
	}
	
	public final Integer updateTask(Task _task) {
		
		Integer result = Integer.valueOf(0);
		
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put( DatabaseHelper.TASK_ID, _task.getId() );
		values.put( DatabaseHelper.TASK_NAME, _task.getName());
		values.put( DatabaseHelper.TASK_DESCRIPTION, _task.getDescription() ); 
		values.put( DatabaseHelper.TASK_FINISHED, _task.getFinished() );
		
		result = db.update( DatabaseHelper.TABLE_TASK, values, DatabaseHelper.TASK_ID + " = ? ", new String[] { String.valueOf( _task.getId() ) } );
		
		db.close();
		
		return result;
	}
	
	public final Integer deleteTask(Task _task) {
		
		Integer result = Integer.valueOf(0);
		
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		result = db.delete( DatabaseHelper.TABLE_TASK, DatabaseHelper.TASK_ID + " = ? ", new String[] { String.valueOf( _task.getId() ) } );

		db.close();
		
		return result;
	}
	
}
