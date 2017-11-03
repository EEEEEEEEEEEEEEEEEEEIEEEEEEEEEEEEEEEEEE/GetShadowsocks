package com.GET.freeSS;

import java.net.URL;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Main extends Activity {
	Intent intent;
	Uri  uri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	public void on_click (View v){
		switch (v.getId()){
		case R.id.button1:
			intent = new Intent(this,FreeActivity.class);
			startActivity(intent);
			break;
		case R.id.button2:
			intent = new Intent(this,IssActivity.class);
			startActivity(intent);
			break;
		case R.id.button3:
			uri = Uri.parse("https://www.namaho.com/prpr.html");
			intent = new Intent(Intent.ACTION_VIEW,uri);
			startActivity(intent);
			break;
		case R.id.button4:
			uri = Uri.parse("https://www.mianvpn.com/#free");
			intent = new Intent(Intent.ACTION_VIEW,uri);
			startActivity(intent);
			break;
		case R.id.esc:
			System.exit(0);
			break;
		}
	}
}
