/* 練習四
 * 
 * Google Map 可以說是許多app 都會使用到的功能
 * 
 * 這個練習主要有用到 Gps Google MapV3 以及Web View的使用  
 * 
 * Google Map V3 是利用html 將地圖資訊叫出來，有別於V2需要開啟使用權限以及下載封包下來使用的方式會更好 
 * 
 * 要開啟 網路和地理資訊的權限
 * <uses-permission android:name="android.permission.INTERNET"/>
	<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
	<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 * 
 * */

package com.example.googlemapv3demo;



import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements LocationListener{

	private static final String MAP_URL = "file:///android_asset/Map.html";
	private WebView webView;
	private LocationManager locationManager;
	
	private Button searchBtn;
	private Button enterBtn;
	private EditText input;

	private SearchDialog sd;

	@Override
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupWebView();// 載入Webview
		initView();
		initHandler();
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		searchBtn = (Button)findViewById(R.id.bt);

		enterBtn = (Button)findViewById(R.id.d_bt);
		input = (EditText)findViewById(R.id.et1);
		
		sd = new SearchDialog(this);
	}

	private void initHandler() {
		// TODO Auto-generated method stub
		searchBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sd.show();
			}
		});
	}

	/** Sets up the WebView object and loads the URL of the page **/
	private void setupWebView() {
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);// 啟用Webview的JavaScript功能
		// Wait for the page to load then send the location information
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {

			}

		});
		webView.loadUrl(MAP_URL); // 載入URL
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, MainActivity.this);
	}

	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Double longitude = location.getLongitude() ;
		Double latitude = location.getLatitude() ;
		locationManager.removeUpdates(MainActivity.this);
		webView.loadUrl("javascript:initialize('"+ latitude + "','"+longitude+"')");
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	private class SearchDialog extends Dialog{

		public SearchDialog(Context context) {
			super(context);
			setContentView(R.layout.search_dialog);
			// TODO Auto-generated constructor stub
			this.setTitle("請輸入...例：台中火車站");
			   
			   input = (EditText)findViewById(R.id.et1);
			   input.clearFocus();
			   
			   enterBtn = (Button)findViewById(R.id.d_bt);
			   enterBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					webView.loadUrl("javascript:goto('"+ input.getText() + "')");
					input.setText("");
				    sd.dismiss();
				}
			});
		}
	}
}
