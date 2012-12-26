package br.com.globalcode.android;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import br.com.globalcode.android.model.TaskModel;
import br.com.globalcode.android.vo.Task;

public class MainActivity extends ListActivity {

	private List<Task> tasks;
	private TaskAdapter taskAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		tasks = TaskModel.getInstance( MainActivity.this ).getAllTasks();
		taskAdapter = TaskAdapter.getInstance( MainActivity.this, tasks );
		setListAdapter( taskAdapter );
		
		findViewById(R.id.buttonAddTask).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, NewTaskActivity.class);
				startActivity(i);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		tasks = TaskModel.getInstance(this).getAllTasks();
		taskAdapter.setTasks(tasks);
		taskAdapter.notifyDataSetChanged();
	}
}
