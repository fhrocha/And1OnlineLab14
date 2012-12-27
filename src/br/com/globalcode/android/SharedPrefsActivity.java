package br.com.globalcode.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SharedPrefsActivity extends Activity implements OnClickListener {

	private EditText editTextEmail;
	private EditText editTextName;
	public final static String FILE_NAME = "MyPreferences";
	private SharedPreferences myPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shared_prefs_layout);
		
		bindValues();
		
		myPreferences = getSharedPreferences(FILE_NAME, 0);
		
		loadSavedPrefs();
	}

	private void bindValues() {
		
		Button buttonSave = (Button) findViewById(R.id.buttonSave);
		editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		editTextName = (EditText) findViewById(R.id.editTextName);
		
		buttonSave.setOnClickListener(this);
	}
	
	private void loadSavedPrefs() {
		
		if(myPreferences != null) {
			
			editTextEmail.setText(myPreferences.getString("email", ""));
			editTextName.setText(myPreferences.getString("name", ""));
		}
	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		
		case R.id.buttonSave:
			
			String eMail = editTextEmail.getText().toString();
			String name = editTextName.getText().toString();
			
			if(isEmailValid(eMail)) {
				
				savePrefs(eMail, name);
				showAlertSavedOk();
			} else {
				
				showAlertInvalidEmail();
			}			

			break;
		}
	}

	private void savePrefs(String eMail, String name) {
		
		SharedPreferences.Editor editor = myPreferences.edit();
		editor.putString("email", eMail);
		editor.putString("name", name);
		editor.commit();
	}
	
	private void showAlertSavedOk() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Aviso");
		builder.setMessage("Preferências salvas.");
		builder.setPositiveButton("Ok", null);
		builder.create();
		builder.show();
	}
	
	private void showAlertInvalidEmail() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Aviso");
		builder.setMessage("Email Inválido.");
		builder.setPositiveButton("Ok", null);
		builder.create();
		builder.show();
	}
	
	private boolean isEmailValid(String eMail) {
		
		return eMail.trim().length() > 0 
					&& android.util.Patterns.EMAIL_ADDRESS.matcher(eMail).matches();
	}

}