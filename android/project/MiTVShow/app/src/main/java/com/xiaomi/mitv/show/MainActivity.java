package com.xiaomi.mitv.show;

import mitv.tv.Player;
import mitv.tv.PlayerManager;
import mitv.tv.SourceManager;
import mitv.tv.TvContext;
import mitv.tv.TvPlayer;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends Activity {
    private static final String TAG = "MiTVShow";

    private AudioManager mAudioMgr;
    /**
     * 输入源控制
     */
    SourceManager mTvSourceManager;
    /**
     * 输入源播放器
     */
    TvPlayer mTvPlayer;

    RelativeLayout mSurfaceViewContainer;
    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;

    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.xiaomi.mitv.show.R.layout.activity_main);
        initTvContext();
        mSurfaceViewContainer = (RelativeLayout) findViewById(com.xiaomi.mitv.show.R.id.surface_view_container);
        createSurfaceView();
    }

    /**
     * 播放外接输入源 hdmi/av/vga
     */
    public void playInputSource(int inputSource) {
        Log.v(TAG, "playInputSource:" + inputSource);
        releaseTvPlayer();
        releaseMediaPlayer();
        createTVPlayer();
        mTvSourceManager.setCurrentSource(inputSource);
        handleVolumeChange(0);
    }

    private Uri mCachedUri = null;

    public void playMedia(Uri uri) {
        releaseTvPlayer();
        releaseMediaPlayer();
        createMediaPlayer();
        mCachedUri = uri;
        try {
            mMediaPlayer.setDataSource(this, uri);

        } catch (Exception e) {
            Log.v(TAG, "playMedia setDataSource exception:" + e);
            return;
        }
        try {
            mMediaPlayer.prepare();
        } catch (Exception e) {
            Log.v(TAG, "playMedia prepare exception:" + e);
            return;
        }

    }

    public void stopPlaying() {
        releaseTvPlayer();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        mCachedUri = null;
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void releaseTvPlayer() {
        if (mTvPlayer != null) {
            mTvPlayer.stop();
            mTvPlayer.release();
            mTvPlayer = null;
        }
    }

    public void scaleSurfaceView(int width,int height,int x,int y) {
        if(mMediaPlayer!= null){
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) mSurfaceView.getLayoutParams();
            param.width = width;
            param.height = height;
            param.setMargins(x,y,0,0);
            mSurfaceView.setLayoutParams(param);
        }else if(mTvPlayer != null){
            mTvPlayer.setCommonCommand(Player.COMMAND_ATV_SHAPE_SCALE, String.valueOf(x), String.valueOf(y),
                    String.valueOf(width), String.valueOf(height));
        }
    }

    public void resetScaleSurfaceView(){
        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) mSurfaceView.getLayoutParams();
        param.width = 1920;
        param.height = 1080;
        param.setMargins(0,0,0,0);
        mSurfaceView.setLayoutParams(param);
        if(mTvPlayer != null){
            mTvPlayer.setCommonCommand(Player.COMMAND_ATV_SHAPE_SCALE, String.valueOf(0), String.valueOf(0),
                    String.valueOf(1920), String.valueOf(1080));
        }
    }

    PlayerManager mTvPlayerManager;

    private void initTvContext() {
        TvContext mTvContext = TvContext.getInstance();
        try {
            mTvSourceManager = mTvContext.getSourceManager();
            mTvPlayerManager = mTvContext.getPlayerManager();
            mAudioMgr = (AudioManager) getSystemService(AUDIO_SERVICE);
        } catch (Exception e) {
            return;
        }
        mTvSourceManager.addSourceStatusListener(mSourceListener);
    }

    private void createTVPlayer() {
        resetScaleSurfaceView();
        if (mTvPlayer == null) {
            mTvPlayer = mTvPlayerManager.createTvPlayer();
            mTvPlayer.setDisplay(mSurfaceHolder);
            // 全屏（如果不设置，画面大小会保持原桌面画中画的大小）
            mTvPlayer.setCommonCommand(Player.COMMAND_ATV_SHAPE_SCALE, "0", "0",
                    "1920", "1080");
        }
    }

    private void createSurfaceView() {
        if (mSurfaceView == null) {
            mSurfaceView = new SurfaceView(this);
            mSurfaceView.setLayoutParams(new RelativeLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            mSurfaceHolder = mSurfaceView.getHolder();
            mSurfaceHolder.addCallback(mSurfaceHolderCallback);
            mSurfaceViewContainer.addView(mSurfaceView);
        }
    }

    private void createMediaPlayer() {
        resetScaleSurfaceView();
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    Log.v(TAG, "MediaPlayer.OnSeekComplete");
                }
            });
            mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    Log.v(TAG, "MediaPlayer.OnBufferingUpdate:" + percent);
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.v(TAG, "MediaPlayer.OnError,what" + what + ",extra:" + extra);
                    return false;
                }
            });

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.v(TAG, "MediaPlayer.OnCompletion");
                    //循环播放
                    if (mCachedUri != null) {
                        playMedia(mCachedUri);
                    }
                }
            });
            mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    Log.v(TAG, "MediaPlayer.OnInfo,what:" + what + ",extra:" + extra);
                    return false;
                }
            });
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.v(TAG, "MediaPlayer.OnPrepared");
                    if (mIsSurfaceAvailable) {
                        mMediaPlayer.start();
                        mMediaPlayer.setDisplay(mSurfaceHolder);
                    } else {
                        if (mToast != null) {
                            mToast.cancel();
                        }
                        mToast = Toast.makeText(MainActivity.this, "SurfaceView is not avaiable", Toast.LENGTH_SHORT);
                        mToast.show();
                    }
                }
            });
        }
    }

    private Toast mToast = null;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
        releaseTvPlayer();
    }

    /**
     * 音量设置
     */
    private void handleVolumeChange(int delta) {
        int cur = mAudioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);// 系统声音当前音量
        int max = mAudioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        cur += delta;
        if (cur < 0) {
            cur = 0;
        } else if (cur > max) {
            cur = max;
        }
        if (mTvPlayer != null) {
            try {
                /*
                 * TVPlayer的声音可能与系统音量是不同的audio stream,以下是更改TVPlayer音量的方法
                 */
                mTvPlayer.setVolumeIndex(cur);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    SourceManager.CommonSourceChangeListener mSourceListener = new SourceManager.CommonSourceChangeListener() {

        public boolean onSourceConnected(int source) {
            return true;
        }

        public boolean onSourceRemoved(int source) {
            return true;
        }

        public boolean onCurrentSourceChanged(int source) {
            return true;
        }

        /**
         * 视频信号稳定（这个回调最常用，是判断切换输入源成功的依据）
         */
        public boolean onSourceSignalStable(int source, boolean stable) {
            Log.v(TAG, "onSourceSignalStable,source:" + source + ",stable:"
                    + stable);
            return true;
        }

        /**
         * 视频第一帧(一般不用)
         */
        public boolean onFirstFrameStable(int source, boolean stable) {
            Log.v(TAG, "onFirstFrameStable");
            return true;
        }

    };

    private boolean mIsSurfaceAvailable = false;

    SurfaceHolder.Callback mSurfaceHolderCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.w(TAG, "surfaceCreated");
            mIsSurfaceAvailable = true;
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.v(TAG, "surfaceDestroyed");
            mIsSurfaceAvailable = false;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            Log.v(TAG, "surfaceChanged,format:" + format + ",w:" + width + ",h:" + height);
        }
    };

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                handleVolumeChange(1);
            }
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                handleVolumeChange(-1);
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

}
