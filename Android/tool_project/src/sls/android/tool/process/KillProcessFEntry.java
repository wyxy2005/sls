package sls.android.tool.process;

import sls.android.tool.R;
import sls.android.tool.R.id;
import sls.android.tool.R.layout;
import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class KillProcessFEntry extends Activity {
	private Button mBnKillSelfProcess;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kill_process);
		mBnKillSelfProcess = (Button) findViewById(R.id.bn_kill_self_process);
		mBnKillSelfProcess.setText("只能杀死自己的进程");
		mBnKillSelfProcess.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				/*
				 * 只能杀自己
				 */
				android.os.Process.killProcess(Process.myPid());
			}
		});
	}

}
