package sls.android.tool.windowmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class WindowManagerFEntry extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout root = new RelativeLayout(this);
        setContentView(root);
        Button button = new Button(this);
        button.setText("弹出window,按home键退至桌面,window不消失"
                + "\n要先打开浮窗权限");
        button.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                MyWindowManager manager = new MyWindowManager(WindowManagerFEntry.this.getApplicationContext());
                manager.show();
            }
        });
        root.addView(button);
    }
}
