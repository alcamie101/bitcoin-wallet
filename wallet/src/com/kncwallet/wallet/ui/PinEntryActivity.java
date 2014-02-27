/*
 * This is KnC Software
 * Please see EULA for more information
 */

package com.kncwallet.wallet.ui;

import com.actionbarsherlock.app.ActionBar;
import com.kncwallet.wallet.Constants;

import com.kncwallet.wallet_test.R;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PinEntryActivity extends Activity {

	EditText pinEditor;
	String appPinValue = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pin_entry);
		pinEditor = (EditText)findViewById(R.id.pin_entry_editor);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		
		String appPinEnabled = sharedPref.getString(Constants.PREFS_KEY_APP_PIN_VALUE, "");
		
		appPinValue = sharedPref.getString(Constants.PREFS_KEY_APP_PIN_VALUE, "");
		if(appPinValue.equals(""))
		{
			//app pin is not enabled
			//bail out
			finish();
		}
		
		final android.app.ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.knc_action_bar_background)));
		actionBar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.knc_background_lighter)));
		actionBar.setIcon(R.drawable.ic_knclogo);
	}
	
	public void submitPressed(View view)
	{	
		String enteredPin = pinEditor.getText().toString();
		if(enteredPin.equals(appPinValue))
		{
			finish();
			//kill the view
		} else {
			Toast toast = Toast.makeText(this, "Invalid pin, please try again!", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
			pinEditor.setText(null);
			toast.show();
		}
	}
	
	//If they are on the app pin screen and press back, we want to
	//close the app
	private int backButtonCount = 0;
	public void onBackPressed()
	{
		if(backButtonCount >= 1)
		{
			backButtonCount = 0;
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} else {
			Toast toast = Toast.makeText(this, "Press back again to leave the application.", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
			backButtonCount++;
		}
	}

}
