package com.example.nppproject.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.nppproject.Adapter.VideoAdapter;
import com.example.nppproject.Entity.VideoEntity;
import com.example.nppproject.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayVideoFragment extends Fragment {
    VideoView vvVideo;
    TextView tvTitleVD;
    String url;
    int position;
    ArrayList<VideoEntity> mListVideo;
    ArrayList<VideoEntity> mListSend;
    RecyclerView rvRelateVideo;
    VideoEntity oldVideoEntity;

    public PlayVideoFragment() {
        // Required empty public constructor
    }

    public static PlayVideoFragment newInstance(String url, ArrayList<VideoEntity> listVideo, ArrayList<VideoEntity> listSend, VideoEntity videoEntity, int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("url", url);
        //  Log.d(TAG, "newInstance: "+url);

        args.putSerializable("videoEntity", videoEntity);
        args.putSerializable("listVideo", listVideo);
        args.putSerializable("listSend", listSend);
        PlayVideoFragment fragment = new PlayVideoFragment();

        fragment.setArguments(args);
        return fragment;
    }

    private static final String TAG = "PlayVideoFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_video, container, false);

        Bundle bundle = getArguments();
        url = bundle.getString("url");

        mListVideo = (ArrayList<VideoEntity>) bundle.getSerializable("listVideo");
        mListSend = (ArrayList<VideoEntity>) bundle.getSerializable("listSend");
        position = bundle.getInt("position");
        oldVideoEntity = (VideoEntity) bundle.getSerializable("videoEntity");
        vvVideo = view.findViewById(R.id.vvVideo);
        vvVideo.start();
//        MediaController mediaController = new MediaController(getActivity());
//        mediaController.setMediaPlayer(vvVideo);
//        mediaController.setAnchorView(vvVideo);
//        vvVideo.setMediaController(mediaController);
//        vvVideo.requestFocus();
        position = bundle.getInt("position");
        if (mListVideo.size() > 0) {
            rvRelateVideo = view.findViewById(R.id.rvRelateVD);
            tvTitleVD = view.findViewById(R.id.tvTitleVD);
            final VideoAdapter videoAdapter = new VideoAdapter(mListSend);
            rvRelateVideo.setAdapter(videoAdapter);
            videoAdapter.notifyDataSetChanged();

            videoAdapter.setOnClickItemListener(new VideoAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v, boolean check) {
                    String url = "";

                    VideoEntity videoEntity = mListVideo.get(position);
                    url = videoEntity.getFile_mp4();
                    mListSend.remove(position);
                    mListSend.add(oldVideoEntity);
//                    ArrayList<VideoEntity> mListSend = mListVideo;
//                    mListSend.remove(position);
                    PlayVideoFragment playVideoFragment = PlayVideoFragment.newInstance(url, mListSend, mListVideo, videoEntity, position);
                    getFragmentManager().beginTransaction().replace(R.id.container, playVideoFragment,playVideoFragment.getTag()).addToBackStack("stack").commit();

                }
            });
        }
        vvVideo.setVideoPath(url);
        VideoEntity videoEntity = (VideoEntity) bundle.getSerializable("videoEntity");
        tvTitleVD = view.findViewById(R.id.tvTitleVD);
        String title = "";
        title = videoEntity.getTitle();
        Log.d(TAG, "onCreateView: " + title);
        tvTitleVD.setText(title);
        //      Log.d(TAG, "onCreateView: ");


        vvVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                ControlFragment controlFragment = ControlFragment.newInstance(url, mListVideo, position);
                fragmentManager.beginTransaction().replace(R.id.container, controlFragment).addToBackStack("stack").commit();

            }
        });
        // Inflate the layout for this fragment
        return view;
    }


}
