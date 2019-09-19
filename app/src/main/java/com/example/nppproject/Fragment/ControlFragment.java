package com.example.nppproject.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.example.nppproject.Entity.VideoEntity;
import com.example.nppproject.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends Fragment implements SurfaceHolder.Callback {

    VideoView mediaPlayer;
    String url;
    ImageButton btnPause, btnFfwd, btnRew, btnPrev, btnNext;
    boolean isVideoPlay;
    int currentPosition, positionVideo;
    LinearLayout mediaControl;
    FrameLayout videoSurface;
    ArrayList<VideoEntity> mListVideo;

    public ControlFragment() {
        // Required empty public constructor
    }

    public static ControlFragment newInstance(String urlVideo, ArrayList<VideoEntity> videoEntities, int position) {

        Bundle args = new Bundle();
        args.putString("urlVideo", urlVideo);
        args.putSerializable("listVideo", videoEntities);
        args.putInt("position", position);
        ControlFragment fragment = new ControlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        // Inflate the layout for this fragment
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init(view);
        videoSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaControl.setVisibility(View.VISIBLE);
                hideMediaControl();
            }
        });
        hideMediaControl();
        Bundle bundle = getArguments();
        url = bundle.getString("urlVideo");
        mListVideo = (ArrayList<VideoEntity>) bundle.getSerializable("listVideo");
        positionVideo = bundle.getInt("position");
        try {
            mediaPlayer.setVideoPath(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        return view;
    }

    private void init(View view) {
        btnNext = view.findViewById(R.id.next);
        btnFfwd = view.findViewById(R.id.ffwd);
        btnPause = view.findViewById(R.id.pause);
        btnRew = view.findViewById(R.id.rew);
        btnPrev = view.findViewById(R.id.prev);
        mediaControl = view.findViewById(R.id.mediaControl);
        videoSurface = view.findViewById(R.id.videoSurface);
        mediaPlayer = view.findViewById(R.id.vvVideo);
    }

    private void hideMediaControl() {
        final View view = mediaControl;
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mediaControl.isPressed()) {
                    view.setVisibility(View.INVISIBLE);
                } else {
                    hideMediaControl();

                }
            }
        }, 5000);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
