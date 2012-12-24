package br.com.globalcode.android;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import br.com.globalcode.android.helper.DatabaseHelper;
import br.com.globalcode.android.model.Task;

public class MainActivity extends ListActivity {
	
	private DatabaseHelper dbHelper;
	private ArrayAdapter<Task> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		dbHelper = DatabaseHelper.getInstance( this );
		
		adapter = new ArrayAdapter<Task>(MainActivity.this,
				android.R.layout.simple_list_item_1, android.R.id.text1, dbHelper.getAllTasks());
		
		setListAdapter(adapter);
		
		((Button)findViewById(R.id.buttonNewTask)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				dbHelper.addTask(Task.getInstance(null, "test", "teste", false));
				adapter.notifyDataSetChanged();
			}
		});
	}
}
