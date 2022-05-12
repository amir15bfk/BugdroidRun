package com.example.bugdroidrun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Waterfall {
    public float x=0,y=0;
    public int selected=1,count=0;
    public Bitmap wf1,wf2,wf3,wf4;
    public Bitmap background;
    public Resources res;
    public void nextOne(){
        count++;
        if (count%2==0){
        switch(selected) {
            case 1:
                selected=2;
                background=wf2;
                break;
            case 2:
                selected=3;
                background=wf3;
                break;
            case 3:
                selected=4;
                background=wf4;
                break;
            case 4:
                selected=1;
                background=wf4;
                break;
            default:
                // code block
        }}

    }
    public Waterfall(int screenX, int screenY, Resources res){
        this.res =res;
        wf1 = BitmapFactory.decodeResource(res,R.drawable.waterfall1);
        wf2 = BitmapFactory.decodeResource(res,R.drawable.waterfall2);
        wf3 = BitmapFactory.decodeResource(res,R.drawable.waterfall3);
        wf4 = BitmapFactory.decodeResource(res,R.drawable.waterfall4);
        wf1 = Bitmap.createScaledBitmap(wf1,screenX,screenY,false);
        wf2 = Bitmap.createScaledBitmap(wf2,screenX,screenY,false);
        wf3 = Bitmap.createScaledBitmap(wf3,screenX,screenY,false);
        wf4 = Bitmap.createScaledBitmap(wf4,screenX,screenY,false);
        background = Bitmap.createScaledBitmap(wf1,screenX,screenY,false);


    }
}
