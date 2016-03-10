package com.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.domain.TestBean;
import com.multi_table_demo.R;
import com.util.Tools;

public class ContentAdapter extends BaseAdapter {
	// ===================================
	public List<TestBean> mDataSource;
	public Context mContext;

	// ===================================

	public ContentAdapter(List<TestBean> mDataSource, Context mContext) {
		super();
		this.mDataSource = mDataSource;
		this.mContext = mContext;
		if (this.mDataSource != null) {
			if(this.mDataSource.get(0).getId()==-1){
				return;
			}
			this.mDataSource.add(0, new TestBean(-1, "姓名", "性别", -2, "爱好"));
			Log.e("JULY", "mDataSource=" + mDataSource.size());
		}
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TestBean key = mDataSource.get(position);
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.content_layout_item,
					null);
		}
		TextView mIdTv = Tools.getWidget(convertView, R.id.id_tv);
		TextView mNameTv = Tools.getWidget(convertView, R.id.name_tv);
		TextView mSexTv = Tools.getWidget(convertView, R.id.sex_tv);
		TextView mAgeTv = Tools.getWidget(convertView, R.id.age_tv);
		TextView mHobbyTv = Tools.getWidget(convertView, R.id.hobby_tv);
		int id = key.getId();
		int age = key.getAge();
		String name = key.getName();
		String sex = key.getSex();
		String hobby = key.getHobby();
		if (id == -1) {
			mIdTv.setText("id");
		} else {
			mIdTv.setText(id + "");
		}
		if (age == -2) {
			mAgeTv.setText("年龄");
		} else {
			mAgeTv.setText(age + "");
		}
		mNameTv.setText(name);
		mSexTv.setText(sex);
		mHobbyTv.setText(hobby);
		return convertView;
	}

}
