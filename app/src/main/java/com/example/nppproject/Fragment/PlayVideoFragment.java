package com.example.nppproject.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.nppproject.Adapter.RLvideoAdapter;
import com.example.nppproject.Adapter.VideoAdapter;
import com.example.nppproject.Entity.VideoEntity;
import com.example.nppproject.MainActivity;
import com.example.nppproject.R;
import com.example.nppproject.VideoActivity;

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
    ImageButton btnPre, btnNext, btnPlay, btnPause, btnRew, btnFfwd;
    Button btnFullScreen;
    LinearLayout mediaControl;
    ConstraintLayout videoContainer;
    int currentPosition, positionVideo;
    boolean isVideoPlay;

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

        final Bundle bundle = getArguments();
        url = bundle.getString("url");

        Log.d(TAG, "onCreateView: position : " + bundle.getInt("position"));
        Log.d(TAG, "onCreateView: url : " + bundle.getString("url"));
        mListVideo = (ArrayList<VideoEntity>) bundle.getSerializable("listVideo");
        mListSend = (ArrayList<VideoEntity>) bundle.getSerializable("listSend");
        position = bundle.getInt("position");
        oldVideoEntity = (VideoEntity) bundle.getSerializable("videoEntity");
        vvVideo = view.findViewById(R.id.vvVideo);
        vvVideo.setVideoPath(url);
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
            mediaControl = view.findViewById(R.id.mediaControl);
            btnPause = view.findViewById(R.id.pause);
            btnFfwd = view.findViewById(R.id.ffwd);
            btnPre = view.findViewById(R.id.prev);
            btnRew = view.findViewById(R.id.rew);
            btnNext = view.findViewById(R.id.next);
            videoContainer = view.findViewById(R.id.videoContainer);
            btnFullScreen = view.findViewById(R.id.fullScreen1);
            final RLvideoAdapter videoAdapter = new RLvideoAdapter(mListSend);
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
                    getFragmentManager().beginTransaction().replace(R.id.container, playVideoFragment, playVideoFragment.getTag()).addToBackStack("stack").commit();

                }
            });
        }

        final VideoEntity videoEntity = (VideoEntity) bundle.getSerializable("videoEntity");
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
        videoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaControl.setVisibility(View.VISIBLE);
                hideMediaControl();
            }

        });
        hideMediaControl();
        btnPause.setImageResource(R.drawable.ic_pause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVideoPlay == true) {
                    isVideoPlay = false;
                    btnPause.setImageResource(R.drawable.ic_play);
                    currentPosition = vvVideo.getCurrentPosition();
                    vvVideo.pause();

                } else {
                    isVideoPlay = true;
                    btnPause.setImageResource(R.drawable.ic_pause);
                    vvVideo.seekTo(currentPosition);
                    vvVideo.start();
                }
            }
        });
        btnRew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vvVideo != null) {
                    int rew = currentPosition - 10000;
                    currentPosition = rew;
                    vvVideo.seekTo(currentPosition);
                }
            }
        });
        btnFfwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vvVideo != null) {
                    try {
                        int forward = currentPosition + 10000;
                        currentPosition = forward;
                        vvVideo.seekTo(currentPosition);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionVideo > 0) {
                    btnPre.setEnabled(true);
                    int newposition = positionVideo - 1;
                    VideoEntity videoEntity = mListVideo.get(newposition);
                    PlayVideoFragment playVideoFragment = newInstance(videoEntity.getYoutube_url(), mListVideo, mListSend, videoEntity, newposition);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, playVideoFragment, playVideoFragment.getTag()).commit();

                } else {
                    btnPre.setEnabled(false);
                }
            }
        });
        positionVideo = bundle.getInt("position");
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionVideo < mListVideo.size() - 1) {
                    btnNext.setEnabled(true);
                    int newposition = positionVideo + 1;
                    VideoEntity videoEntity = mListVideo.get(newposition);
                    Log.d(TAG, "onClick1: " + mListVideo.get(newposition).getTitle());
                    Log.d(TAG, "onClick2: " + mListVideo.get(3).getTitle());
                    Log.d(TAG, "onClick: ");
                    PlayVideoFragment playVideoFragment = newInstance(videoEntity.getFile_mp4(), mListVideo, mListSend, videoEntity, newposition);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, playVideoFragment, playVideoFragment.getTag()).addToBackStack("stack").commit();
                } else {
                    btnNext.setEnabled(false);
                }
            }
        });
        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlVideo = url;
                int currentPosition = vvVideo.getCurrentPosition();
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra("url", urlVideo);
                intent.putExtra("videoEntity", videoEntity);
                intent.putExtra("position", currentPosition);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12321) {
//            vvVideo.setVideoPath(data.getExtras("url"));
//            VideoEntity videoEntity = (VideoEntity) data.getSerializableExtra("videoEntity");
//            PlayVideoFragment playVideoFragment = newInstance(url, mListVideo, mListSend, videoEntity, position);
            vvVideo.seekTo(data.getIntExtra("position", 0));
            Log.d(TAG, "onActivityResult: position : " + data.getIntExtra("position", 0));
            vvVideo.start();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
