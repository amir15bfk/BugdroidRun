package com.example.bugdroidrun;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;
    public static MediaPlayer gameMusic, waterfall,jump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Point point  = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        gameView = new GameView(this,point.x,point.y);
        gameMusic = MediaPlayer.create(GameActivity.this,R.raw.error);
        gameMusic.setVolume(0.7f,0.7f);

        jump = MediaPlayer.create(GameActivity.this,R.raw.jump);
        waterfall = MediaPlayer.create(GameActivity.this,R.raw.waterfall);
        waterfall.setVolume(0.17f,0.17f);

        if(!MainActivity.isMute)
        {
            gameMusic.setLooping(true);
            waterfall.setLooping(true);
        }

        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        gameMusic.pause();
        waterfall.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!MainActivity.isMute){
            gameMusic.start();
            waterfall.start();
        }
        gameView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameMusic.release();
        waterfall.release();
    }
}