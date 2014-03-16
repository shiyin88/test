package com.example.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RegistrationFinished extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_finished);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String strUsername = bundle.getString("username");
		String strMail = bundle.getString("email");

		TextView username = (TextView) findViewById(R.id.username);
		username.setText(strUsername);
		TextView mail = (TextView) findViewById(R.id.email);
		mail.setText(strMail);

	}

}
