package sls.android.tool.windowmanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class MyWindowManager {
    private WindowManager mWindowManager = null;
    private Context mContext = null;
    private View mView;

    public MyWindowManager(Context context) {
        mWindowManager = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        mContext = context.getApplicationContext();
    }

    public void show() {
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        mParams.height = 300;
        mParams.width = 300;
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        relativeLayout.setBackgroundColor(Color.RED);
        relativeLayout.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        mView = relativeLayout;
        //uses-permission android.permission.SYSTEM_ALERT_WINDOW
        //小米手机须在权限中心把弹窗权限打开
        mWindowManager.addView(relativeLayout, mParams);
    }

    public void hide() {
        mWindowManager.removeView(mView);
        mView = null;
    }

}
