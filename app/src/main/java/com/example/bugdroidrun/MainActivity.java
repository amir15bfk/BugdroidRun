package com.example.bugdroidrun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer menuMusic;

    public static boolean isMute = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                (findViewById(R.id.loading)).setVisibility(View.VISIBLE);
                (findViewById(R.id.loading_img3)).setVisibility(View.VISIBLE);


                menuMusic.pause();
                startActivity(new Intent(MainActivity.this, GameActivity.class));

            }
        });
        //turning up the music on when class create
        menuMusic = MediaPlayer.create(MainActivity.this, R.raw.back);
        menuMusic.setLooping(true);
        menuMusic.start();


        TextView highScoreTxt = findViewById(R.id.hightscore);
        TextView lastScoreTxt = findViewById(R.id.lastscore);
        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        lastScoreTxt.setText(": " + prefs.getInt("lastscore", 0));
        highScoreTxt.setText(": " + prefs.getInt("highscore", 0));

        isMute = prefs.getBoolean("isMute", false);

        final ImageView volumeCtrl = findViewById(R.id.volumeCtrl);

        if (isMute) {
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
            menuMusic.pause();
        } else {
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);
            menuMusic.start();
        }

        volumeCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isMute = !isMute;
                if (isMute) {
                    volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
                    menuMusic.pause();
                } else {
                    volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);
                    menuMusic.start();
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMute", isMute);
                editor.apply();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //restart the music after pause
        if (!isMute)
            menuMusic.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //pause  the music if app has paused
        menuMusic.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuMusic.stop();
        menuMusic.release();
    }
}