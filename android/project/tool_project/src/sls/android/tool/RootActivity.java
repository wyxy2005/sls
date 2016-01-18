package sls.android.tool;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RootActivity extends Activity {
    private static final String TAG = "sls.RootActivity";
    private ArrayList<ActivityInfo> mActivityInfoList = new ArrayList<ActivityInfo>();
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setContentView(R.layout.activity_root);
        RelativeLayout root = (RelativeLayout) findViewById(R.id.root);
        PackageManager packageManager = getPackageManager();
        PackageInfo info = null;
        try {
            info = packageManager.getPackageInfo(this.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
        } catch (NameNotFoundException e) {
        }
        if (info != null) {
            ActivityInfo[] activities = info.activities;
            for(ActivityInfo ai :activities){
                if(ai.name.endsWith("FEntry")){
                    mActivityInfoList.add(ai);
                }
            }
        }
        mListView = new ListView(this);
        root.addView(mListView);
        mListView.setAdapter(new BaseAdapter() {
            
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textview = null; 
                if(convertView != null){
                    textview = (TextView) ((RelativeLayout)convertView).getChildAt(0);
                }else{
                    convertView = new RelativeLayout(RootActivity.this);
                    textview = new TextView(RootActivity.this);
                    ((RelativeLayout)convertView).addView(textview);
                }
                final String name = mActivityInfoList.get(position).name;
                textview.setText(name);
                textview.setBackgroundColor(Color.GREEN);
                ((RelativeLayout)convertView).setGravity(Gravity.CENTER_VERTICAL);
                convertView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT,200));
                convertView.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        Class displayActivity = null;
                        try {
                            displayActivity = Class.forName(name);
                        } catch (ClassNotFoundException e) {
                        }
                        startActivity(new Intent(RootActivity.this,displayActivity));//FIXME
                    }
                });
                return convertView;
            }
            
            @Override
            public long getItemId(int position) {
                return 0;
            }
            
            @Override
            public Object getItem(int position) {
                return null;
            }
            
            @Override
            public int getCount() {
                return mActivityInfoList.size();
            }
        });
    }
    
    

}
