package br.com.globalcode.android;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import br.com.globalcode.android.vo.Task;

public class TaskAdapter extends BaseAdapter {
	
	private List<Task> tasks;
	private Context context;
	private static TaskAdapter instance;
	
	private TaskAdapter() {
		
	}
	
	public static final TaskAdapter getInstance(Context _context, List<Task> _tasks) {
		
		if(instance == null) {
			
			instance = new TaskAdapter();
			instance.context = _context;
			instance.tasks = _tasks;
		}
		
		return instance;
	}
	
	@Override
	public int getCount() {
		
		return tasks.size();
	}

	@Override
	public Object getItem(int position) {

		return tasks.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder vH = null;

		if( convertView == null ) {
			
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.task_cell_layout, null);
			
			vH = new ViewHolder();
			vH.taskCheckBox = (CheckBox) convertView.findViewById(R.id.checkBoxTask);
			vH.taskLinearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayoutTaskCell);
			convertView.setTag(vH);
		} else {
			
			vH = (ViewHolder) convertView.getTag();
		}
		
		final Task task = tasks.get(position);
		vH.taskCheckBox.setText(task.getName());
		vH.taskCheckBox.setChecked(task.getFinished());
		vH.taskLinearLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent i = new Intent(context, DetailActivity.class);
				i.putExtra("task", task);
				context.startActivity(i);
			}
		});
		
		return convertView;
	}
	
	private class ViewHolder {
		
		CheckBox taskCheckBox;
		LinearLayout taskLinearLayout;
	}
	
	public final List<Task> getTasks() {
		return tasks;
	}

	public final void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
