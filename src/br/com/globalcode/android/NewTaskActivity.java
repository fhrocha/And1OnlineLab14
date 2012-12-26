package br.com.globalcode.android;

import br.com.globalcode.android.model.TaskModel;
import br.com.globalcode.android.vo.Task;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class NewTaskActivity extends Activity {
	
	private static final int DIALOG_INVALID_VALUES = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_task_layout);
		
		findViewById(R.id.buttonSave).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(isUserInputsValid()) {
					
					createNewTask(); 
				} else {
					
					showInvalidValuesDialog();
				}
			}
		});
	}
	
	private void showInvalidValuesDialog() {
		
		removeDialog( DIALOG_INVALID_VALUES );
		showDialog( DIALOG_INVALID_VALUES );
	}

	protected void createNewTask() {
		
		String taskName = ((TextView)findViewById(R.id.editTextName)).getText().toString();
		String taskDescription = ((TextView)findViewById(R.id.editTextDescription)).getText().toString();
		
		Task newTask = Task.getInstance(null, taskName, taskDescription, Boolean.FALSE);
		
		TaskModel taskModel = TaskModel.getInstance(NewTaskActivity.this);
		taskModel.addTask(newTask);
		finish();
	}
	
	private boolean isUserInputsValid() {
		
		return isTaskNameValid() 
					&& isTaskDescriptionValid();
	}
	
	private boolean isTaskNameValid() {
		
		String taskName = ((TextView)findViewById(R.id.editTextName)).getText().toString();
		return !(taskName.isEmpty() || taskName.trim().length() == 0);
	}
	
	private boolean isTaskDescriptionValid() {
		
		String taskDescription = ((TextView)findViewById(R.id.editTextDescription)).getText().toString();
		return !(taskDescription.isEmpty() || taskDescription.trim().length() == 0);
	}
	
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int idDialogType) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (idDialogType) {
		
			case DIALOG_INVALID_VALUES: 
				
				String inputField = "";
				String message = "Para continuar, por favor, informe um valor para ";
				
				if( ! isTaskNameValid() ) {
					
					inputField = "Tarefa";
				} else if( ! isTaskDescriptionValid() ) {
					
					inputField = "Descrição";
				}
				
				builder.setTitle("Aviso Importante");
				builder.setMessage(message + inputField);
				builder.setPositiveButton("Ok", null);
				return builder.create();
				
			default:
				
				return super.onCreateDialog(idDialogType);
		}
	}
}
