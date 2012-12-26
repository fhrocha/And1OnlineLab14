package br.com.globalcode.android;

import br.com.globalcode.android.model.TaskModel;
import br.com.globalcode.android.vo.Task;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;

public class DetailActivity extends Activity {
	
	private Task task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_layout);
		
		task = (Task) getIntent().getSerializableExtra("task");
		
		((TextView)findViewById(R.id.textViewName)).setText(task.getName());
		((TextView)findViewById(R.id.textViewDescription)).setText(task.getDescription());
		((CheckBox)findViewById(R.id.checkBoxFinished)).setChecked(task.getFinished());
		
		findViewById(R.id.buttonConclude).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				concludeTask();
			}
		});
		
		findViewById(R.id.buttonDelete).setOnClickListener(new OnClickListener() { 
			
			@Override
			public void onClick(View v) {
				deleteTask();
			}
		});
	}

	protected void concludeTask() {
		
		Task finshedTask = Task.getInstance(task.getId(), task.getName(), task.getDescription(), Boolean.TRUE);
		TaskModel.getInstance(this).updateTask(finshedTask);
		finish();
	}

	protected void deleteTask() {
		
		TaskModel.getInstance(this).deleteTask(task);
		finish();
	}

}
