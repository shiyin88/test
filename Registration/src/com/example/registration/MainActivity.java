package com.example.registration;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
//import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
//import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	// start of code by Maulik
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	// url to get all products list
	private static String url_confirm_login = "http://128.238.241.14/peekbite/confirm_login.php";

	// end of code by Maulik
	EditText usernameEditText = null;
	EditText pwdEditText = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Log in");

		usernameEditText = (EditText) findViewById(R.id.et_password);
		pwdEditText = (EditText) findViewById(R.id.et_username);
	}

	public void login(View view) {
		String username = usernameEditText.getText().toString().trim();
		String password = pwdEditText.getText().toString().trim();

		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(this, "Username or password can't be null.",
					Toast.LENGTH_LONG).show();
		} else {
			/**
			 * RETRIVE DATA FROM DATABASE
			 */
			// Start of code by Maulik

			// Check login details in Background Thread
			new ConfirmLogin().execute(username, password);

			// End of code by Maulik
		}
	}

	public void signup(View view) {

		Intent intent = new Intent();
		intent.setClass(this, RegistrationActivity.class);
		startActivity(intent);
	}

	// Start Code change by Maulik
	/**
	 * Background Async Task to check login details by making HTTP Request
	 * */
	class ConfirmLogin extends AsyncTask<String, String, String> {

		// Progress Dialog
		private ProgressDialog pDialog;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("verifying user...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("uname", args[0]));
			params.add(new BasicNameValuePair("password", args[1]));

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_confirm_login,
					"POST", params);

			// Check your log cat for JSON response
			Log.d("JSON", "JSON Response in MainActivity: " + json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					Intent frontPage = new Intent(getApplicationContext(),
							FrontPageActivity.class);
					// Closing all previous activities
					// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(frontPage);

				} else {
					MainActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							// your alert dialog builder here
							new AlertDialog.Builder(MainActivity.this)
									.setIcon(android.R.drawable.ic_dialog_alert)
									.setTitle("Error")
									.setMessage(
											"Incorrect Username or Password.")
									.setPositiveButton("OK", null).show();
						}
					});

				}
			} catch (JSONException je) {
				Log.e("ERROR",
						"Error in MainActivity.doInBackground()"
								+ je.toString());
			}

			return null;
		}
		
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
		}

	}
	// End code change by Maulik
}
