package com.xiaomi.mitv.show.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v17.leanback.widget.VerticalGridView;

import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SourceView extends VerticalGridView {
    private int mSelectedIndex = -1;
    private final static int sFocusedItemColor = Color.argb(128, 128, 128, 128);
    private final static int sSelectedItemColor = Color.argb(255, 128, 128, 128);
    private final static int sDefaultItemColor = Color.TRANSPARENT;

    public SourceView(Context context) {
        super(context);
    }

    public SourceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SourceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setAdapter(mAdapter);
    }

    private RecyclerView.Adapter mAdapter = new RecyclerView.Adapter() {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            TextView view = new TextView(getContext());
            view.setTextColor(Color.WHITE);
            return new ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int i) {
            final TextView view = (TextView) viewHolder.itemView;
            view.setBackgroundColor(i == mSelectedIndex ? sSelectedItemColor : sDefaultItemColor);
            view.setText(PlayerControllerFragment.sInputSourceNameArray[i]);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedIndex = i;
                    mAdapter.notifyDataSetChanged();
                    mFragment.switchSource(PlayerControllerFragment.sInputSourceIdArray[mSelectedIndex]);
                }
            });
            view.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        view.setBackgroundColor(i == mSelectedIndex ? sSelectedItemColor : sFocusedItemColor);
                    } else {
                        view.setBackgroundColor(i == mSelectedIndex ? sSelectedItemColor : sDefaultItemColor);
                    }
                }
            });
            view.setClickable(true);
            view.setFocusable(true);
        }

        @Override
        public int getItemCount() {
            return PlayerControllerFragment.sInputSourceNameArray.length;
        }
    };

    private PlayerControllerFragment mFragment = null;

    public void setController(PlayerControllerFragment fragment) {
        mFragment = fragment;
    }


}
