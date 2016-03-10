package com.adapter;

import java.util.ArrayList;
import java.util.List;

import com.domain.TestBean;
import com.multi_table_demo.R;
import com.util.Tools;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayAdapter extends BaseAdapter {
	// ===================================
	private static final int TOTAL_COUNT = 2;
	private static final int TITLE = 0;
	private static final int CONTENT = 1;
	public List<Object> mDataSource = new ArrayList<Object>();
	public Context mContext;

	// ===================================

	@Override
	public int getItemViewType(int position) {
		Object key = mDataSource.get(position);
		if (key instanceof String) {
			return TITLE; // 标题......
		}
		return CONTENT;
	}

	public DisplayAdapter(List<Object> mDataSource, Context mContext) {
		super();
		this.mDataSource = mDataSource;
		this.mContext = mContext;
	}

	@Override
	public int getViewTypeCount() {
		return TOTAL_COUNT;
	}

	@Override
	public int getCount() {
		return mDataSource.size() == 0 ? 0 : mDataSource.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int iViewType = getItemViewType(position);
		Object key = mDataSource.get(position);
		switch (iViewType) {
		case TITLE:
			if (convertView == null) {
				convertView = View.inflate(mContext, R.layout.item, null);
			}
			TextView mTitleTv = Tools.getWidget(convertView, R.id.title_tv);
			//mTitleTv.setBackgroundResource(R.drawable.hft_rect_transparent);
			mTitleTv.setBackgroundColor(Color.parseColor("#ffff6600"));
			mTitleTv.setGravity(Gravity.CENTER);
			mTitleTv.setText("表:"+(String) key);
			break;
		default:
			if (convertView == null) {
				convertView = View.inflate(mContext, R.layout.content_item,
						null);
			}
			ListView contentLstView = Tools.getWidget(convertView,
					R.id.common_lstv);
			if (key instanceof List) {
				List<TestBean> keys = (List<TestBean>) key;
//				Log.e("JULY", "++++++++++++++++++++++++++++++++++++++");
//				Log.e("JULY", "____-----"+keys.size());
//				Log.e("JULY", "++++++++++++++++++++++++++++++++++++++");
				contentLstView.setAdapter(new ContentAdapter(keys, mContext));
			}
			break;
		}

		return convertView;
	}

}
