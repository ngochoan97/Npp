package com.example.nppproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.nppproject.Entity.VideoEntity;

public class VideoActivity extends AppCompatActivity {
    VideoView vvVideo;
    Button btnFullScreen;
    String url;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        final Intent intent = getIntent();
        url = intent.getStringExtra("url");
        currentPosition = (int) intent.getIntExtra("position", 0);
        final VideoEntity videoEntity = (VideoEntity) intent.getSerializableExtra("videoEntity");
        vvVideo = findViewById(R.id.vvVideo);
        btnFullScreen = findViewById(R.id.fullScreen1);
        btnFullScreen.setBackgroundResource(R.drawable.ic_fullscreenback);
        vvVideo.setVideoPath(url);
        vvVideo.seekTo(currentPosition);
        vvVideo.start();
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
    }
}
