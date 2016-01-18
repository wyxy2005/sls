package com.xiaomi.mitv.show.ui;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaomi.mitv.show.MainActivity;
import com.xiaomi.mitv.show.R;

import java.io.File;

public class UsbPlayerControllView extends RelativeLayout {
    //    private static final String TV_MNT_POINT = "/storage";
//    private static final String M6_MNT_POINT = "/storage/external_storage";
//    private static final String M8_MNT_POINT = "/storage/external_storage";
//    private static final String M3_MNT_POINT = "/mnt/usb";
    private static final String TV2_MNT_POINT = "/mnt/usb";
    private final static String ParentPath = "../";

    private final static String[] VideoPostfix = new String[]{"asf", "avi",
            "wm", "wmp", "wmv", /* Windows Media */
            "dat", "m1v", "m2p", "m2t", "m2ts", "m2v", "mp2v", "mpeg", "mpg",
            "mpv2", "pss", "pva", "tp", "tpr", "ts", /* MPEG 2 */
            "m4b", "m4p", "m4v", "mp4", "mpeg4", /* MPEG 4 */
            "3g2", "3gp", "3gp2", "3gpp", /* 3GPP */
            "mov", "qt", /* Apple */
            "f4v", "flv", "hlv", "swf", /* Flash */
            "ifo", "vob", /* DVD */
            "mkv", /* Other*/
            "rm", "ram", "rmvb", /* Real Media */
    };
    Button mBnPause;
    Button mBnResume;
    Button mBnSeek;
    EditText mEtTime;

    VerticalGridView mListView;
    TextView mUsbPath;

    private File mCurrentPath;
    private String[] fileNameList;

    Toast toast = null;

    public UsbPlayerControllView(Context context) {
        super(context);
    }

    public UsbPlayerControllView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UsbPlayerControllView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBnPause = (Button) findViewById(R.id.usb_video_pause);
        mBnResume = (Button) findViewById(R.id.usb_video_resume);
        mBnSeek = (Button) findViewById(R.id.usb_video_seek_button);
        mEtTime = (EditText) findViewById(R.id.usb_video_seek_position);
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
        mUsbPath = (TextView) findViewById(R.id.usb_path);
        mListView = (VerticalGridView) findViewById(R.id.file_list);
        mListView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView view = new TextView(getContext());
                RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(view) {
                };
                return holder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                TextView view = ((TextView) holder.itemView);
                view.setText(fileNameList[position]);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView view = (TextView) v;
                        String filename = view.getText().toString();
                        if (isVideoFile(filename)) {
                            try {
                                Uri uri = Uri.fromFile(new File(mCurrentPath.getAbsolutePath() + File.separator + filename));
                                ((MainActivity) getContext()).playMedia(uri);
                            } catch (Exception e) {
                                if (toast != null) {
                                    toast.cancel();
                                }
                                toast = Toast.makeText(getContext(), "打开视频文件失败", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        } else if (ParentPath.equals(filename)) {
                            mCurrentPath = mCurrentPath.getParentFile();
                            mListView.getAdapter().notifyDataSetChanged();
                        } else {
                            File folder = buildFolder(mCurrentPath.getAbsolutePath() + File.separator + filename);
                            if (folder != null) {
                                mCurrentPath = folder;
                                mListView.getAdapter().notifyDataSetChanged();
                            } else {
                                if (toast != null) {
                                    toast.cancel();
                                }
                                toast = Toast.makeText(getContext(), "不是文件夹或视频", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }
                });
                view.setFocusable(true);
                view.setOnFocusChangeListener(new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        v.setBackgroundColor(hasFocus ? Color.BLUE : Color.TRANSPARENT);
                    }
                });
            }

            @Override
            public int getItemCount() {
                String path = mCurrentPath.getAbsolutePath().toLowerCase();
                mUsbPath.setText(path);
                if (path.equals(TV2_MNT_POINT)) {
                    fileNameList = mCurrentPath.list();
                    if(fileNameList == null){
                        fileNameList = new String[]{};
                    }
                } else {
                    String[] list = mCurrentPath.list();
                    if(list == null){
                        list = new String[]{};
                    }
                    fileNameList = new String[list.length + 1];
                    fileNameList[0] = ParentPath;
                    System.arraycopy(list, 0, fileNameList, 1, list.length);
                }
                return fileNameList.length;
            }
        });
        buildUsbRootFolder();
    }

    private void buildUsbRootFolder() {
        mCurrentPath = buildFolder(TV2_MNT_POINT);
        if (mCurrentPath == null) {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(getContext(), "U盘挂载路径错误", Toast.LENGTH_SHORT);
            toast.show();

        } else {
            mListView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if(visibility ==View.VISIBLE){
            buildUsbRootFolder();
        }
    }

    private File buildFolder(String path) {
        path = path.toLowerCase();
        if (!path.startsWith(TV2_MNT_POINT)) {
            return null;
        }
        File f = new File(path);
        if (f != null && f.exists() && f.isDirectory()) {
            return f;
        }
        return null;
    }

    private boolean isVideoFile(String filename) {
        for (int i = 0; i < VideoPostfix.length; i++) {
            if (filename.endsWith(VideoPostfix[i])) {
                return true;
            }
        }
        return false;
    }

    void toast(String str){
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
        toast.show();
    }

}
