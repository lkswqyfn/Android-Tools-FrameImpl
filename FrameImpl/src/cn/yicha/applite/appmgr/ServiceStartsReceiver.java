package cn.yicha.applite.appmgr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * 全局广播接收
 * @author wqYuan
 *
 */
public class ServiceStartsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, UninstallCollectionInfoService.class);
		context.startService(service);
	}

}
