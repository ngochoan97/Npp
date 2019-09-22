package com.example.nppproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toolbar;
import android.widget.VideoView;

import com.example.nppproject.Entity.VideoEntity;

public class VideoActivity extends AppCompatActivity {
    VideoView vvVideo;
    ImageButton btnPre, btnNext, btnPlay, btnPause, btnRew, btnFfwd;
    LinearLayout mediaControl;
    Button btnFullScreen;
    String url;
    ConstraintLayout videoContainer;
    Toolbar toolbar;
    int currentPosition;
    boolean isVideoPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        final Intent intent = getIntent();
        url = intent.getStringExtra("url");
        currentPosition = (int) intent.getIntExtra("position", 0);
        final VideoEntity videoEntity = (VideoEntity) intent.getSerializableExtra("videoEntity");
        vvVideo = findViewById(R.id.vvVideo);
        btnFfwd = findViewById(R.id.ffwd);
        btnNext = findViewById(R.id.next);
        btnPause = findViewById(R.id.pause);
        btnPre = findViewById(R.id.prev);
        btnRew = findViewById(R.id.rew);
        mediaControl = findViewById(R.id.mediaControl);
        btnFullScreen = findViewById(R.id.fullScreen1);
        videoContainer = findViewById(R.id.videoContainer);


        videoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaControl.setVisibility(View.VISIBLE);
                hideMediaControl();
            }
        });
        btnPause.setImageResource(R.drawable.ic_pause);
        btnFullScreen.setBackgroundResource(R.drawable.ic_fullscreenback);
        vvVideo.setVideoPath(url);
        vvVideo.seekTo(currentPosition);
        vvVideo.start();
        isVideoPlay = true;
        hideMediaControl();
        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                currentPosition = vvVideo.getCurrentPosition();
                intent1.putExtra("url", url);
                intent1.putExtra("position", currentPosition);
                intent.putExtra("videoEntity", videoEntity);
                setResult(RESULT_OK, intent1);
                finish();
            }
        });

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
}
