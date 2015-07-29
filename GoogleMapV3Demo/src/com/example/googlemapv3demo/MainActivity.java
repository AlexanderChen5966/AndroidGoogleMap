/* �m�ߥ|
 * 
 * Google Map �i�H���O�\�happ ���|�ϥΨ쪺�\��
 * 
 * �o�ӽm�ߥD�n���Ψ� Gps Google MapV3 �H��Web View���ϥ�  
 * 
 * Google Map V3 �O�Q��html �N�a�ϸ�T�s�X�ӡA���O��V2�ݭn�}�Ҩϥ��v���H�ΤU���ʥ]�U�ӨϥΪ��覡�|��n 
 * 
 * �n�}�� �����M�a�z��T���v��
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
		setupWebView();// ���JWebview
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
		webView.getSettings().setJavaScriptEnabled(true);// �ҥ�Webview��JavaScript�\��
		// Wait for the page to load then send the location information
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {

			}

		});
		webView.loadUrl(MAP_URL); // ���JURL
		
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
			this.setTitle("�п�J...�ҡG�x��������");
			   
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
