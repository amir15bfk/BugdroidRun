package com.example.bugdroidrun;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bugdroidrun.Background;
import com.example.bugdroidrun.GameActivity;
import com.example.bugdroidrun.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable  {


    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private float gamespeed=2;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private int sound1,sound2;
    private GameActivity activity;
    private Background background1, background2,grass1,grass2,page2,gameOver;
    private Bugdroid bugdriod;
    private Waterfall waterfall;
    private GradleElef gradle1,gradle2;
    public One one;
    private MediaPlayer backgroundMusic;


    public GameView(GameActivity activity, int screenX, int screenY) {

        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        sound1 = soundPool.load(activity, R.raw.point, 1);
        sound2 = soundPool.load(activity, R.raw.gameover, 1);


        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 3040f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new Background(screenX, screenY, getResources(),1);
        background2 = new Background(screenX, screenY, getResources(),2);
        grass1 = new Background(screenX, screenY, getResources(),3);
        grass2 = new Background(screenX, screenY, getResources(),3);
        page2 = new Background(screenX, screenY, getResources(),4);
        bugdriod = new Bugdroid(this,screenY,getResources()) ;
        gradle1 = new GradleElef(screenX, screenY, getResources());
        gradle2 = new GradleElef(screenX, screenY, getResources());

        waterfall = new Waterfall(screenX, screenY, getResources());
        one = new One(screenX, screenY, getResources());
        background2.x = screenX;
        grass2.x = screenX;

        gradle1.setThe_other_one(gradle2);
        gradle2.setThe_other_one(gradle1);
        gradle1.x = screenX;
        gradle2.after();

        page2.x =screenX;
        paint = new Paint();
        paint.setTextSize(128/screenRatioX);
        paint.setColor(Color.WHITE);

        random = new Random();

    }

    @Override
    public void run() {

        while (isPlaying) {

            update ();
            draw ();
            sleep ();
            gamespeed+=0.003;

        }

    }

    private void update ()  {

        background1.x -= 2 * screenRatioX*gamespeed;
        background2.x -= 2 * screenRatioX*gamespeed;
        grass1.x -= 6 * screenRatioX*gamespeed;
        grass2.x -= 6 * screenRatioX*gamespeed;
        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }
        if (grass1.x + grass1.background.getWidth() < 0) {
            grass1.x = screenX;
            grass2.x = 0;
        }

        if (grass2.x + grass2.background.getWidth() < 0) {
            grass1.x = 0;
            grass2.x = screenX;
        }
        waterfall.x -= 3 * screenRatioX*gamespeed;
        waterfall.nextOne();
        page2.x -= 3 * screenRatioX*gamespeed;

        if (waterfall.x + waterfall.background.getWidth() < 0) {
            waterfall.x = screenX;
            page2.x = 0;
        }
        if (page2.x + page2.background.getWidth()< 0) {
            page2.x = screenX;
            waterfall.x = 0;
        }
        bugdriod.update();
        gradle1.x -= 6 * screenRatioX*gamespeed;
        gradle2.x -= 6 * screenRatioX*gamespeed;
        gradle1.update(one);
        gradle2.update(one);
            if (((gradle1.x<0)||(gradle2.x<0))&& !one.display){
                score+=1;
                one.display=true;
                if (!prefs.getBoolean("isMute", false))
                    soundPool.play(sound1, 1, 1, 0, 0, 1);
            }
        if (Rect.intersects(gradle1.getCollisionShape(),bugdriod.getCollisionShape()) || Rect.intersects(gradle2.getCollisionShape(),bugdriod.getCollisionShape())){
            isGameOver= true;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            canvas.drawBitmap(page2.background, page2.x, page2.y, paint);
            canvas.drawBitmap(waterfall.background, waterfall.x, waterfall.y, paint);
            canvas.drawBitmap(grass1.background, grass1.x, grass1.y, paint);
            canvas.drawBitmap(grass2.background, grass2.x, grass2.y, paint);
            canvas.drawBitmap(bugdriod.getBugdriod(), bugdriod.x, bugdriod.y, paint);
            canvas.drawBitmap(gradle1.current, gradle1.x, gradle1.y, paint);
            canvas.drawBitmap(gradle2.current, gradle2.x, gradle2.y, paint);
            canvas.drawText(score+"",(screenX-200/screenRatioX),(164/screenRatioY),paint);
            if (one.display)
            canvas.drawBitmap(one.background, one.x, one.y, paint);



            if (isGameOver){
                GameActivity.gameMusic.stop();
                GameActivity.waterfall.stop();

                isPlaying=false;
                gameOver = new Background(screenX,screenY,getResources(),400);
                canvas.drawBitmap(gameOver.background,0,0,paint);
                if (!prefs.getBoolean("isMute", false))
                    soundPool.play(sound2, 20, 20, 0, 0, 1);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();

                return;
            }



            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void waitBeforeExiting() {

            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();

    }

    private void saveIfHighScore() {

        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }

    }

    private void sleep () {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause () {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (isGameOver)waitBeforeExiting ();
                if (bugdriod.y==(float)screenY*4 / 6)bugdriod.speed=45;
                else bugdriod.speed=-45*gamespeed;

                    break;
            case MotionEvent.ACTION_UP:
                if (!prefs.getBoolean("isMute", false))
                    GameActivity.jump.start();
                    break;
        }

        return true;
    }
}
