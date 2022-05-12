package com.example.bugdroidrun;

import static com.example.bugdroidrun.GameView.screenRatioX;
import static com.example.bugdroidrun.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class One {
    public float x=0,y=0;
    int width, height;
    public boolean display=false;
    public Bitmap background;
    public One(int screenX, int screenY, Resources res){

        background = BitmapFactory.decodeResource(res,R.drawable.plusone) ;
        width = background.getWidth();
        height = background.getHeight();

        width /= 5;
        height /= 5;

        width = (int) (width / screenRatioX);
        height = (int) (height / screenRatioY);

        background = Bitmap.createScaledBitmap(background, width, height, false);
        y = (float)screenY / 2-(float)height/2;
        x =(float)screenX/2 -(float)width/2;
    }

}
