package com.example.bugdroidrun;

import static com.example.bugdroidrun.GameView.screenRatioX;
import static com.example.bugdroidrun.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


public class Bugdroid {

    int width, height, wingCounter = 0,screenY;
    float x,y,speed=0;
    Bitmap bugdriod1, bugdriod2, dead;
    private GameView gameView;

    Bugdroid (GameView gameView, int screenY, Resources res) {

        this.gameView = gameView;
        this.screenY=screenY;
        bugdriod1 = BitmapFactory.decodeResource(res, R.drawable.bugdroid1);
        bugdriod2 = BitmapFactory.decodeResource(res, R.drawable.bugdriod2);

        width = bugdriod1.getWidth();
        height = bugdriod1.getHeight();

        width /= 5;
        height /= 5;

        width = (int) (width / screenRatioX);
        height = (int) (height / screenRatioY);

        bugdriod1 = Bitmap.createScaledBitmap(bugdriod1, width, height, false);
        bugdriod2 = Bitmap.createScaledBitmap(bugdriod2, width, height, false);


        dead = BitmapFactory.decodeResource(res, R.drawable.bugdroid1);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (64 * screenRatioX);

    }

    Bitmap getBugdriod () {

        if (wingCounter>=4)wingCounter=0;

        if (wingCounter < 2) {
            wingCounter++;
            return bugdriod1;
        }
        wingCounter++;

        return bugdriod2;
    }
    void update(){
        y-=speed*screenRatioY;
        if (y<(float)screenY*4 / 6)
            speed-=0.9*screenRatioY;
            else
        if (y>=(float)screenY*4 / 6){
            y=(float)screenY*4 / 6;
            speed = 0;
        }
    }
    Rect getCollisionShape () {
        return new Rect((int)x,(int) y, (int)x + width, (int)y + height);
    }

    Bitmap getDead () {
        return dead;
    }

}