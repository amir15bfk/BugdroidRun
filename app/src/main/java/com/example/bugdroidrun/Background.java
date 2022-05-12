package com.example.bugdroidrun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {
    public float x=0,y=0;
    public Bitmap background;
    public Background(int screenX, int screenY, Resources res,int background_number){

        if (background_number==1)
        background = BitmapFactory.decodeResource(res,R.drawable.background1);
        else if (background_number==2)  background = BitmapFactory.decodeResource(res,R.drawable.background);
                else if (background_number==3) background = BitmapFactory.decodeResource(res,R.drawable.grasshigh);
                else if(background_number==400) background = BitmapFactory.decodeResource(res,R.drawable.gameover);
                    else
                    background = BitmapFactory.decodeResource(res,R.drawable.page2) ;
        background = Bitmap.createScaledBitmap(background,screenX,screenY,false);
    }


    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }
}
