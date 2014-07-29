package cn.yicha.applite.appmgr;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.frameimpl.util.DeviceUtil;
import com.example.frameimpl.util.NetworkUtil;

public class UninstallCollectionInfoService extends Service {
	static {
		System.loadLibrary("Uninstall");
	}
	public native int init(String mac,String imei,String site,String ip);
	
	@Override
	public void onCreate() {
		super.onCreate();
//		System.out.println(init("38:bc:1a:04:ef:30", "868033019634676", "20141314", "60.28.215.74"));
		new Thread(){
			public void run() {
				if (NetworkUtil.isOnline(UninstallCollectionInfoService.this)){
					init(DeviceUtil.getWifiMac(UninstallCollectionInfoService.this), DeviceUtil.getImei(UninstallCollectionInfoService.this), DeviceUtil.getSite(UninstallCollectionInfoService.this), NetworkUtil.getHostAddress("adsmart.yicha.cn"));
				}else {
					String address = "60.28.215.74";
					init(DeviceUtil.getWifiMac(UninstallCollectionInfoService.this), DeviceUtil.getImei(UninstallCollectionInfoService.this), DeviceUtil.getSite(UninstallCollectionInfoService.this), address);
				}
			};
		}.start();
	}
	
	@Override
	public void onDestroy() {
		Intent service = new Intent(this, getClass());
		startService(service);
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("xcmsjcs");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
