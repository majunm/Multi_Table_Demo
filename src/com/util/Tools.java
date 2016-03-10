package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

/**
 * 功能概述:工具类封�?br/>
 * 创建日期�?015�?�?日上�?1:47:46<br/>
 * 创建作�?：majun<br/>
 * 联系作�?:<a href="mailto:747673016@qq.com">@author majun</a><br/>
 * ======================================================<br/>
 * ======================================================<br/>
 */
@SuppressLint("SimpleDateFormat")
public class Tools {
	/** 获取控件 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T getWidget(View view, int id) {
		SparseArray<View> hashMap = (SparseArray<View>) view.getTag();
		if (hashMap == null) {
			hashMap = new SparseArray<View>();
			view.setTag(hashMap);
		}
		View childView = hashMap.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			hashMap.put(id, childView);
		}
		return (T) childView;
	}
	/**
	 * @description :获取屏幕宽度
	 */
	public static int getScreenWidth(Activity context) {
		return getMetrics(context).widthPixels;
	}

	public static DisplayMetrics getMetrics(Activity context) {
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}

	/**
	 * @description :获取屏幕高度
	 */
	public static int getScreenHeight(Activity context) {
		return getMetrics(context).heightPixels;
	}
	/** 获取指定dip值 */
	public static int obtainDipValue(Context context, int des) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				des, metrics);
	}

	/**
	 */
	public static String getVersionInfo(Context context) {
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 */
	public static String getApplicationName(Activity context) {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = context.getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(
					context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			applicationInfo = null;
		}
		return (String) packageManager.getApplicationLabel(applicationInfo);
	}

	/** 非空判断 */
	public static boolean isEmpty(String str) {
		return TextUtils.isEmpty(str);
	}

	/** �?��浏览�? */
	public static void openBrowser(Context context, Uri uri) {
		Uri mUri = Uri.parse("http://www.mumayi.com/android-680519.html");
		Intent in = new Intent(Intent.ACTION_VIEW, mUri);
		((Activity) context).startActivity(in);
	}

	public static void activityJumpForResultWithBundle(Activity context,
			Class<?> clazz, int requestCode, Bundle bundle) {
		Intent intent = new Intent(context, clazz);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivityForResult(intent, requestCode);
		/** 取消系统默认的动画效�?*/
		((Activity) context).overridePendingTransition(Animation.INFINITE,
				Animation.INFINITE);
	}

	public static void jumpForResultWithAnim(Activity context, Class<?> clazz,
			int requestCode) {
		// Intent intent = new Intent(context, clazz);
		// context.startActivityForResult(intent, requestCode);
		// ((Activity) context).overridePendingTransition(R.anim.slide_right_in,
		// R.anim.mini_hold);
		jumpForResulting(context, clazz, requestCode, null);
	}

	public static void jumpForResulting(Activity context, Class<?> clazz,
			int requestCode, Bundle bundle) {
		Intent intent = new Intent(context, clazz);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivityForResult(intent, requestCode);
		// ((Activity) context).overridePendingTransition(R.anim.slide_right_in,
		// R.anim.mini_hold);
	}

	/**
	 * 
	 * @date :2015-3-23 上午11:49:49
	 * @param:@param context
	 * @param:@param clazz
	 * @param:@param requestCode
	 * @return :@param context
	 * @return :@param clazz
	 * @return :@param requestCode
	 * @description :up 方式 弹出
	 */
	public static void jumpForResulWithAnim(Activity context, Class<?> clazz,
			int requestCode) {
		Intent intent = new Intent(context, clazz);
		context.startActivityForResult(intent, requestCode);
		// ((Activity) context).overridePendingTransition(R.anim.up, 0);
	}

	/**
	 * 
	 * @date :2015-3-23 下午4:55:15
	 * @param:@param context
	 * @param:@param clazz
	 * @param:@param requestCode
	 * @param:@param bundle
	 * @return :@param context
	 * @return :@param clazz
	 * @return :@param requestCode
	 * @return :@param bundle
	 * @description :up 方式 弹出
	 */
	public static void jumpActivityWithAnim(Activity context, Class<?> clazz,
			Bundle bundle) {
		Intent intent = new Intent(context, clazz);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		// context.startActivityForResult(intent, requestCode);
		context.startActivity(intent);
		// ((Activity) context).overridePendingTransition(R.anim.up, 0);
	}

	private static final float TARGET_WIDTH = 400;
	private static final float TARGET_HEIGHT = 200;

	/** 按比例缩放图�?*/
	public static Bitmap zoomBitmap(Bitmap target) {
		int width = target.getWidth();
		int height = target.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) TARGET_WIDTH) / width;
		float scaleHeight = ((float) TARGET_HEIGHT) / height;
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap result = Bitmap.createBitmap(target, 0, 0, width, height,
				matrix, true);
		return result;
	}

	/**
	 * 获取手机是否链接网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnection(Context context) {
		ConnectivityManager manager = getConnectivityManager(context);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		boolean isAvailable;
		if (networkInfo != null) {
			isAvailable = networkInfo.isAvailable();
		} else {
			isAvailable = false;
		}
		return isAvailable;
	}

	/**
	 * 获取手机联网的类�?
	 * 
	 * @param context
	 */
	public static String getConnectionType(Context context) {
		boolean connection = isConnection(context);
		if (connection) {
			ConnectivityManager manager = getConnectivityManager(context);
			NetworkInfo networkInfo = manager.getActiveNetworkInfo();
			String typeName = networkInfo.getTypeName();
			Log.i("ConnectionVerdict", typeName);
			return typeName;
		} else {
			return null;
		}
	}

	/**
	 * 判断WiFi�?��是否打开
	 * 
	 * @return
	 */
	public boolean isWifiEnabled(Context context) {
		ConnectivityManager mgrConn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager mgrTel = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		NetworkInfo info = mgrConn.getActiveNetworkInfo();
		return ((info != null && info.getState() == NetworkInfo.State.CONNECTED) || mgrTel
				.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
	}

	/**
	 * 判断当前使用的网络是否WiFi
	 * 
	 * @return
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager manager = getConnectivityManager(context);
		NetworkInfo networkINfo = manager.getActiveNetworkInfo();
		if (networkINfo != null
				&& networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前链接的网络是否是手机流量网络
	 * 
	 * @return
	 */
	public static boolean isMobile(Context context) {
		ConnectivityManager manager = getConnectivityManager(context);
		NetworkInfo networkINfo = manager.getActiveNetworkInfo();
		if (networkINfo != null
				&& networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	/**
	 * 获取联网的Manager
	 * 
	 * @param context
	 * @return
	 */
	private static ConnectivityManager getConnectivityManager(Context context) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (mConnectivityManager == null) {
			return null;
		}
		return mConnectivityManager;
	}

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnect(Context context) {
		boolean isConnected = false;
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
			isConnected = true;
		} else if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
			isConnected = true;
		}
		return isConnected;
	}

	/**
	 * @return yyyyMMddHHmmss 20100225102000
	 */
	public static String getCurrentTime() {
		long time = System.currentTimeMillis();
		String formatTime = "00";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.getDefault());
		formatTime = format.format(time);
		return formatTime;
	}
	@SuppressLint("SimpleDateFormat")
	public static long[] getDeltaTime(String nowTime, String passedTime, long tm) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long[] result = new long[5];
		try {
			java.util.Date now = df.parse(nowTime);
			java.util.Date date = df.parse(passedTime);
			Log.e("JULY", "expire:" + (date.getTime() + tm));
			Log.e("JULY", "   now:" + now.getTime());
			long l = (date.getTime() + tm) - now.getTime();
			Log.e("JULY", "     l:" + l);
			if (l <= 0) {
				result[4] = -1;
				return result;
			}
			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			result[0] = day;
			result[1] = hour;
			result[2] = min;
			result[3] = s;
		} catch (Exception e) {
			result[4] = -1;
		}
		return result;
	}


	public static void showToast(Context context, String string) {
		Toast.makeText(context, string, 0).show();
	}
	private static ProgressDialog mProgressDialog;

	/**
	 * ��ʾProgressDialog
	 * 
	 * @param context
	 * @param message
	 */
	public static void show(Context context, CharSequence message) {
		if (mProgressDialog == null) {
			mProgressDialog = ProgressDialog.show(context, "", message);
		} else {
			mProgressDialog.show();
		}
	}

	/**
	 */
	public static void dismiss() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
	/** 
	 * 1: 2014-10-10  周日
	 * 2: 2014-10-10 20:08:08 周日
	 *  */
	public static String getCurrentTime(int type) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd E");
		switch (type) {
		case 1:
			format = new SimpleDateFormat("yyyy-MM-dd E");
			return format.format(new Date());
		case 2:
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(new Date());
		}
		return format.format(new Date());
	}

}
