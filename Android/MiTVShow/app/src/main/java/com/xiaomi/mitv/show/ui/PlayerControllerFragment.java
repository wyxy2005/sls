package com.xiaomi.mitv.show.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaomi.mitv.show.MainActivity;
import com.xiaomi.mitv.show.R;

import mitv.tv.SourceManager;

public class PlayerControllerFragment extends Fragment {
    private static final int SOURCE_NULL = -1;
    private static final int SOURCE_URL = -2;
    private static final int SOURCE_USB = -3;

    public static final int[] sInputSourceIdArray = new int[]{
            SOURCE_USB,
            SOURCE_URL,
            SourceManager.TYPE_INPUT_SOURCE_CVBS,
            SourceManager.TYPE_INPUT_SOURCE_HDMI,
            SourceManager.TYPE_INPUT_SOURCE_HDMI2,
            SourceManager.TYPE_INPUT_SOURCE_HDMI3,
            SourceManager.TYPE_INPUT_SOURCE_VGA,
            SOURCE_NULL};

    public static final String[] sInputSourceNameArray = new String[]{"USB", "URL", "AV", "HDMI1",
            "HDMI2", "HDMI3", "VGA", "STOP"};

    private View mRootView;


    void switchSource(int source) {
        if (source >= 0) {
            ((MainActivity) getActivity()).playInputSource(source);
        } else {
            ((MainActivity) getActivity()).stopPlaying();
        }
        mRootView.findViewById(R.id.url_controll_view).setVisibility(source == SOURCE_URL ? View.VISIBLE : View.GONE);
        mRootView.findViewById(R.id.usb_controll_view).setVisibility(source == SOURCE_USB ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_player_controller, null);
        SourceView sourceView = (SourceView) mRootView.findViewById(R.id.source_view);
        sourceView.setController(this);
        mRootView.findViewById(R.id.screen_scale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).scaleSurfaceView(960,540,100,100);
            }
        });
        mRootView.findViewById(R.id.full_screen).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).resetScaleSurfaceView();
            }
        });
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
