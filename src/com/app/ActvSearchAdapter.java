package com.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

/**
 * 
 * 文件名称: ActvSearchAdapter.java <br/>
 * 创建日期：2015年10月29日下午7:20:07<br/>
 * 创建作者：过滤支付宝帐号<br/>
 * 联系作者:<a href="mailto:747673016@qq.com">@author majun</a><br/>
 * 
 * @Description:@param <T><br/>
 *                     ======================================================<br/>
 *                     版权声明：Copyright 2011 绿石开门 保留所有权利。<br/>
 *                     ======================================================<br/>
 */
@SuppressLint({ "DefaultLocale", "UseSparseArrays" })
public class ActvSearchAdapter<T> extends BaseAdapter implements Filterable {

	private List<T> mObjects;
	private final Object mLock = new Object();
	private int mResource;
	private int mFieldId = 0;
	Context mContext;
	private ArrayList<T> mOriginalValues;
	private ArrayFilter mFilter;
	private List<T> originData; // 显示一个
	private LayoutInflater mInflater;

	public ActvSearchAdapter(Context context, int resource, List<T> objects,
			T dataSource) {
		init(context, resource, 0, objects, dataSource);
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	private void init(Context context, int resource, int textViewResourceId,
			List<T> objects, T dataSource) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mResource = resource;
		mObjects = new ArrayList<T>();
		mObjects.add(dataSource);
		originData = objects;
		mFieldId = textViewResourceId;
	}

	public int getCount() {
		return mObjects.size();
	}

	public T getItem(int position) {
		return mObjects.get(position);
	}

	public int getPosition(T item) {
		return mObjects.indexOf(item);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, mResource);
	}

	private View createViewFromResource(int position, View convertView,
			ViewGroup parent, int resource) {
		View view;
		TextView text;

		if (convertView == null) {
			view = mInflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}

		try {
			if (mFieldId == 0) {
				// If no custom field is assigned, assume the whole resource is
				// a TextView
				text = (TextView) view;
			} else {
				// Otherwise, find the TextView field within the layout
				text = (TextView) view.findViewById(mFieldId);
			}
		} catch (ClassCastException e) {
			throw new IllegalStateException(
					"ArrayAdapter requires the resource ID to be a TextView", e);
		}
		text.setTextSize(15);
		String source = getItem(position).toString();
		// "可用余额: "
		// + "<font color=\"#FF8700\">" + iMoney + "</font>" + "元")
		try {
			if (searchKeyLen == -1) {
				text.setText(source);
			} else {
				StringBuilder sb = new StringBuilder();
				SparseArray<Boolean> arry = searchRecord.get(position);
				for (int i = 0; i < source.length(); i++) {
					if (arry != null && arry.get(i)) {
						sb.append("<font color=\"#FF8700\">" + source.charAt(i)
								+ "</font>");
					} else {
						sb.append("<font color=\"#00A600\">" + source.charAt(i)
								+ "</font>");
					}
				}
				Log.e("JULY", "sb=" + sb.toString());
				// text.setText(Html.fromHtml("<font color=\"#FF8700\">"
				// + source.substring(0, searchKeyLen) + "</font>"
				// + "<font color=\"#00A600\">"
				// + source.substring(searchKeyLen) + "</font>"));
				text.setText(Html.fromHtml(sb.toString()));
			}
		} catch (Exception e) {
			Log.e("JULY", "Exception=");
			text.setText(source);
		}
		return view;
	}

	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new ArrayFilter();
		}
		return mFilter;
	}

	// 搜素的关键字的长度
	int searchKeyLen = -1;
	Map<Integer, Boolean> searchIndex;
	List<SparseArray<Boolean>> searchRecord = new ArrayList<SparseArray<Boolean>>();

	@SuppressLint("DefaultLocale")
	private class ArrayFilter extends Filter {
		@SuppressLint("DefaultLocale")
		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();

			if (mOriginalValues == null) {
				synchronized (mLock) {
					mOriginalValues = new ArrayList<T>(originData);
				}
			}

			if (prefix == null || prefix.length() == 0) {
				synchronized (mLock) {
					ArrayList<T> list = new ArrayList<T>(mOriginalValues);
					results.values = list;
					results.count = list.size();
				}
			} else {
				String prefixString = prefix.toString().toLowerCase();
				searchKeyLen = prefixString.length();
				Log.e("JULY", "searchKeyLen=" + searchKeyLen);
				searchRecord.clear();
				final ArrayList<T> values = mOriginalValues;
				final int count = values.size();

				final ArrayList<T> newValues = new ArrayList<T>(count);
				// if (searchIndex == null) {
				// searchIndex = new HashMap<Integer, Boolean>();
				// }

				// for (int i = 0; i < count; i++) {
				// arry = new SparseArray<Boolean>();
				// arry.put(i, false);
				// searchRecord.add(arry);
				// }
				// for (int i = 0; i < count; i++) {
				// final T value = values.get(i);
				// final String valueText = value.toString().toLowerCase();
				// SparseArray<Boolean> sparse = searchRecord.get(i);
				// for (int y = 0; y < valueText.length(); y++) {
				// sparse.put(y, false);
				// }
				// }
				SparseArray<Boolean> arry = null;
				for (int i = 0; i < count; i++) {
					final T value = values.get(i);
					final String valueText = value.toString().toLowerCase();
					int index = valueText.indexOf(prefixString);
					if (index > -1) {
						arry = new SparseArray<Boolean>();
						for (int y = 0; y < valueText.length(); y++) {
							arry.put(y, false);
						}
						for (int x = 0; x < searchKeyLen; x++) {
							arry.put(index+x, true);
						}
						searchRecord.add(arry);
						newValues.add(value);
					}
				}
				results.values = newValues;
				results.count = newValues.size();
			}

			return results;
		}

		// 需要最高显示3个吗??true,需要
		boolean needThanThr = true;

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			List<T> result = (List<T>) results.values;
			if (result != null && !result.isEmpty()) {
				if (!mObjects.isEmpty()) {
					mObjects.clear();
				}
				if (needThanThr) { // 需要,就有几个显示几个
					mObjects.addAll(result);
				} else {
					// 最高显示3个,大于3 变成1个了......
					if (result.size() > 3) {
						mObjects.add(0, result.get(0));
						mObjects.add(0, result.get(1));
						mObjects.add(0, result.get(2));
					} else {
						mObjects.addAll(result);
					}
				}
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}
	}
}
