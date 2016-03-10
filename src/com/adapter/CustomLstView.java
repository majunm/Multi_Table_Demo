package com.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class CustomLstView extends ListView {
	public CustomLstView(Context context) {
		super(context);
	}

	public CustomLstView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomLstView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
