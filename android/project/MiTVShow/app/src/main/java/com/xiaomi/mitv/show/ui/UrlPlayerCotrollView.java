package com.xiaomi.mitv.show.ui;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiaomi.mitv.show.MainActivity;
import com.xiaomi.mitv.show.R;

public class UrlPlayerCotrollView extends RelativeLayout {
    Button mBnPause;
    Button mBnResume;
    Button mBnSeek;
    EditText mEtTime;
    Button mBnPlay;
    EditText mEtUrl;

    public UrlPlayerCotrollView(Context context) {
        super(context);
    }

    public UrlPlayerCotrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UrlPlayerCotrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBnPause = (Button) findViewById(R.id.online_video_pause);
        mBnResume = (Button) findViewById(R.id.online_video_resume);
        mBnSeek = (Button) findViewById(R.id.online_video_seek_button);
        mEtTime = (EditText) findViewById(R.id.online_video_seek_position);
        mEtUrl = (EditText) findViewById(R.id.online_video_url);
        mBnPlay = (Button) findViewById(R.id.online_video_play);
        mBnPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).getMediaPlayer().pause();
            }
        });
        mBnResume.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).getMediaPlayer().start();
            }
        });
        mBnSeek.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = -1;
                try {
                    time = Integer.valueOf(mEtTime.getText().toString());
                    time = time * 1000;
                } catch (Exception e) {
                    return;
                }
                ((MainActivity) getContext()).getMediaPlayer().seekTo(time);
            }
        });
        mBnPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mEtUrl.getText().toString();
                Uri uri = null;
                try {
                    uri = Uri.parse(url);
                } catch (Exception e) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(getContext(), "错误的URL", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                ((MainActivity) getContext()).playMedia(uri);
            }
        });
    }
    Toast toast;
}
