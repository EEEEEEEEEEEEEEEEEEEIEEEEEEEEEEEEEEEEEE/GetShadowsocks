package com.GET.freeSS;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.ClipboardManager;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class IssActivity extends Activity {
	private Button service, port, password,encryption,lostupdate;
	public static final String URL_MAIN = "http://www.ishadowsocks.org/#free";
	ServiceInfo serviceInfo = null;// ��ʼ��
	private boolean get = false;
	private String region;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				//��������
				service.setText(serviceInfo.getService());//������
				port.setText(serviceInfo.getPort());//�˿�
				password.setText("���룺" + serviceInfo.getPassword().substring(4));//����,���˶�����ַ�
				encryption.setText(serviceInfo.getEncryption());//���ܷ�ʽ
				lostupdate.setText("������ʱ�䣺" + serviceInfo.getLostupdate());
				
				
				//��������
				SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();//����д���ļ�����
				editor.putString(region+"_service", serviceInfo.getService());//������
				editor.putString(region+"_port", serviceInfo.getPort());//�˿�
				editor.putString(region+"_password", serviceInfo.getPassword().substring(4));//����
				editor.putString(region+"_encryption", serviceInfo.getEncryption());//���ܷ�ʽ
				editor.putString(region+"_lostupdate", serviceInfo.getLostupdate());//������ʱ��
				editor.commit();//�ύ����
			} else {
				Toast.makeText(IssActivity.this, "��ȡʧ������(/T��T)/��ȡ���ػ���", 0).show();
				SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);//�����ȡ�ļ�����
				service.setText(pref.getString(region+"_service", "null"));//������
				port.setText(pref.getString(region+"_port", "null"));//�˿�
				password.setText(pref.getString(region+"_password", "null"));//����,���˶�����ַ�
				encryption.setText(pref.getString(region+"_encryption", "null"));//���ܷ�ʽ
				lostupdate.setText(pref.getString(region+"_lostupdate", "null"));//������ʱ��
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature ( Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_iss);
		service = (Button) findViewById(R.id.service_url);
		port = (Button) findViewById(R.id.port);
		password = (Button) findViewById(R.id.password);
		encryption = (Button) findViewById(R.id.encryption);
		lostupdate = (Button) findViewById(R.id.lostupdate);
	}
	
	public void Click(View view) {//��ȡ��ť���¼�
		switch (view.getId()) {
		case R.id.service_a:
			sendGainHttpData(0);
			region = "Iss_service_A";
			break;
		case R.id.service_b:
			sendGainHttpData(1);
			region = "Iss_service_B";
			break;
		case R.id.service_c:
			sendGainHttpData(2);
			region = "Iss_service_C";
			break;
		}
	}

	private void sendGainHttpData(final int i) {//��ȡ�ķ���
		new Thread(new Runnable() {
			@Override
			public void run() {
				Document doc = null;
				try {
					doc = Jsoup.connect(URL_MAIN).get();
					if (doc.select("div[class=col-lg-4 text-center]") != null) {
						Elements text = doc.select("div[class=col-lg-4 text-center]");
						Elements se = text.get(i).select("h4");
						serviceInfo = new ServiceInfo();// �½�����
						serviceInfo.setService(se.get(0).text());
						serviceInfo.setPort(se.get(1).text());
						serviceInfo.setPassword(se.get(2).text());
						serviceInfo.setEncryption(se.get(3).text());
						SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						String date = sDateFormat.format(new java.util.Date());
						serviceInfo.setLostupdate(date);
						get = true;
						mHandler.sendEmptyMessage(0);
					}
				} catch (IOException e) {
					mHandler.sendEmptyMessage(1);
				}
			}
		}).start();
	}

	public void onclick_copy(View v) {//������Ƶ����а�
		ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		cm.setText("");
		if (get) {
			switch (v.getId()) {
			case R.id.service_url:
				String se[] = serviceInfo.getService().split(":");
				cm.setText(se[1]);
				Toast.makeText(this, "���Ƴɹ���", 0).show();
				break;
			case R.id.port:
				String us[] = serviceInfo.getPort().split(":");
				cm.setText(us[1]);
				Toast.makeText(this, "���Ƴɹ���", 0).show();
				break;
			case R.id.password:
				String pa[] = serviceInfo.getPassword().split(":");
				cm.setText(pa[1]);
				Toast.makeText(this, "���Ƴɹ���", 0).show();
				break;
			}
		} else {
			Toast.makeText(IssActivity.this, "��û�л�ȡ������_(:�٩f��)_", 0).show();
		}
	}


}
