package com.example.bugdroidrun;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;
    public static MediaPlayer gameMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Point point  = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        gameView = new GameView(this,point.x,point.y);
        gameMusic = MediaPlayer.create(GameActivity.this,R.raw.back);
        if(!MainActivity.isMute)
            gameMusic.setLooping(true);

        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        gameMusic.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!MainActivity.isMute)
            gameMusic.start();
        gameView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameMusic.release();
    }
}