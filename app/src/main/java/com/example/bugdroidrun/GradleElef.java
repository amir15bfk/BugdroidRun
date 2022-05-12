package com.example.bugdroidrun;

import static com.example.bugdroidrun.GameView.screenRatioX;
import static com.example.bugdroidrun.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class GradleElef {

    int width, height, count = 0,screenX,screenY,selected;
    float x,y;
    Bitmap gradle_c1, gradle_c2, gradle_c3,current;
    GradleElef the_other_one;


    GradleElef (int screenX, int screenY, Resources res) {

        this.screenX=screenX;
        this.screenY=screenY;
        gradle_c1 = BitmapFactory.decodeResource(res, R.drawable.gradle_c1);
        gradle_c2 = BitmapFactory.decodeResource(res, R.drawable.gradle_c2);
        gradle_c3 = BitmapFactory.decodeResource(res, R.drawable.gradle_c3);

        width = gradle_c1.getWidth();
        height = gradle_c1.getHeight();

        width /= 20;
        height /= 20;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        gradle_c1 = Bitmap.createScaledBitmap(gradle_c1, width, height, false);
        gradle_c2 = Bitmap.createScaledBitmap(gradle_c2, width, height, false);
        gradle_c3 = Bitmap.createScaledBitmap(gradle_c3, width, height, false);
        selected = 1;
        current = gradle_c1;
        y = (float)screenY*4 / 6;
        x = (64 * screenRatioX);

    }

    public void nextOne(){
        count++;
        if (count%4==0){
            switch(selected) {
                case 1:
                    selected=2;
                    current=gradle_c2;
                    break;
                case 2:
                    selected=3;
                    current=gradle_c3;
                    break;
                case 3:
                    selected=1;
                    current=gradle_c1;
                    break;
                default:
                    // code block
            }}

    }
    void update(One one){
        nextOne();
    if (x+width<0) {
        after();
        one.display = false;
    }
    }
    void setThe_other_one(GradleElef other_one){
        this.the_other_one=other_one;
    }
    public void after() {
        count=0;
        x=the_other_one.x+(new Random()).nextInt(((int)(2000/screenRatioX )- (int)(1000/screenRatioX )) + 1) + (int)(1000/screenRatioX );
    }
    Rect getCollisionShape () {
        return new Rect((int)(x+width*0.2),(int)( y+height*0.2), (int)(x + width*0.55), (int)y + height);
    }

}
