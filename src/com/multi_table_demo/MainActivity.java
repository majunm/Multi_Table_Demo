package com.multi_table_demo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.adapter.ChoiceLevel;
import com.adapter.DisplayAdapter;
import com.app.ActvSearchAdapter;
import com.db.TableParams;
import com.db.TestBeanDaoImpl;
import com.domain.TestBean;
import com.util.Tools;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity implements TableParams {
	private Receiver mBroadCastreceiv;
	final List<Object> mDataSource = new ArrayList<Object>();
	private SharedPreferences shpf;
	private AutoCompleteTextView mAutoTv;
	private ListView mListView;
	private TestBeanDaoImpl dao;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Tools.dismiss();
				mListView.setAdapter(new DisplayAdapter(mDataSource,
						getApplicationContext()));
				break;

			default:
				break;
			}
		}
	};

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout);
		dao = TestBeanDaoImpl.getInstance().init(getApplicationContext());
		shpf = PreferenceManager.getDefaultSharedPreferences(this);
		mAutoTv = (AutoCompleteTextView) findViewById(R.id.et_alipay_account);
		mAutoTv.setHint("输入SQL命令进行操作");
		mListView = (ListView) findViewById(R.id.common_lstv);
		List<String> list = new ArrayList<String>();
		list.add("左外连接|SELECT * FROM Monday LEFT JOIN Tuesday on Monday.name=Tuesday.name;");
		list.add("左外连接|SELECT * FROM Monday LEFT JOIN Tuesday on Monday.age=Tuesday.age;");
		list.add("左外连接|SELECT * FROM Monday LEFT JOIN Tuesday on Monday.sex=Tuesday.sex;");
		list.add("左外连接|SELECT * FROM Monday LEFT JOIN Tuesday on Monday.hobby=Tuesday.hobby;");
		list.add("左外连接|SELECT * FROM Monday LEFT JOIN Tuesday on Monday.id=Tuesday.id;");
		list.add("左外连接|SELECT * FROM Monday LEFT JOIN Tuesday on Monday._id=Tuesday._id;");
		list.add("右外连接|不支持--------------------------------------------------------------");
		list.add("全外连接|不支持--------------------------------------------------------------");
		list.add("内连接|SELECT * FROM Monday JOIN Tuesday on Monday.name=Tuesday.name;");
		list.add("内连接|SELECT * FROM Monday JOIN Tuesday on Monday.age=Tuesday.age;");
		list.add("内连接|SELECT * FROM Monday JOIN Tuesday on Monday.sex=Tuesday.sex;");
		list.add("内连接|SELECT * FROM Monday JOIN Tuesday on Monday.hobby=Tuesday.hobby;");
		list.add("内连接|SELECT * FROM Monday JOIN Tuesday on Monday.id=Tuesday.id;");
		list.add("内连接|SELECT * FROM Monday JOIN Tuesday on Monday._id=Tuesday._id;");
		list.add("交叉链接|SELECT * FROM Monday CROSS JOIN Tuesday;");
		if (list != null && !list.isEmpty()) {
			mAutoTv.setThreshold(1); // 输入1个就开始提示
			mAutoTv.setDropDownVerticalOffset(Tools.obtainDipValue(this, 1));
			// 设置下拉菜单的高度
			mAutoTv.setDropDownHeight(Tools.getScreenHeight(this) / 3);
			mAutoTv.setDropDownBackgroundDrawable(this.getResources()
					.getDrawable(R.drawable.border_light));
			mAutoTv.setAdapter(new ActvSearchAdapter<String>(this,
					R.layout.item, list, list.get(0)));
		}
		if (shpf.getBoolean("HAS_DATA", false)) {
			iniDataSource();
		}
		mBroadCastreceiv = new Receiver();
		IntentFilter filter = new IntentFilter("TASK");
		registerReceiver(mBroadCastreceiv, filter);

		findViewById(R.id.query_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String value = mAutoTv.getText().toString();
				Tools.showToast(getApplicationContext(), "exe...");
				Tools.show(MainActivity.this, "加载中...........");
				if (!Tools.isEmpty(value)) {
					if (!value.contains("不支持")) {
						String keys[] = value.split("\\|");
						Log.e("JULY", "keys.length=" + keys.length);
						if (keys.length == 2) {
							List<TestBean> exeSql = dao.exeSql(keys[1]);
							showLogical(exeSql);
						} else {
							List<TestBean> exeSql = dao.exeSql(value);
							showLogical(exeSql);
						}
						Tools.dismiss();
					}
				}
			}

		});
	}

	private void showLogical(List<TestBean> exeSql) {
		if (exeSql.size() == 0) {
			Tools.showToast(getApplicationContext(), "没有结果,或者SQL命令错误!");
		} else {
			Tools.showToast(getApplicationContext(), "has data");
			final ChoiceLevel popp = new ChoiceLevel(MainActivity.this);
			popp.show();
			popp.setCancelable(false);
			// LinearLayout.MarginLayoutParams params = new
			// LinearLayout.MarginLayoutParams(
			// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			params.setMargins(30, 0, 30, 0);
			popp.mListView.setLayoutParams(params);
			popp.addItems(exeSql);
			popp.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					popp.dismiss();
				}
			});
		}
	}

	private void iniDataSource() {
		Tools.show(MainActivity.this, "加载数据......");
		new Thread() {

			public void run() {
				for (int i = 0; i < TAB_NAMES.length; i++) {
					List<TestBean> beans = dao.findAllByTableNum(i);
					mDataSource.add(TAB_NAMES[i]);
					mDataSource.add(beans);
				}
				mHandler.sendEmptyMessage(0);
			}
		}.start();
	}

	public final class Receiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				if (intent.getAction().equals("TASK")) {
					iniDataSource();
				}
			} catch (Exception e) {
			}
		}

	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mBroadCastreceiv);
		super.onDestroy();
	}
}
