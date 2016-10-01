package com.example.mario.ateccrossroad;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class Mainscreen extends AppCompatActivity {

    public int whereIs, points;
    public boolean gameOn;
    AnimationDrawable rocketAnimation;
    MediaPlayer gameSong, brsound;
    float positionW0;
    float positionH0;
    float positionH1;
    float positionH2;
    float positionH3;
    final int RIGHT=6;
    final int LEFT=4;
    final int UP=8;
    final int DOWN=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newGame();
    }

    public class OnSwipeTouchListener implements OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener (Context ctx){
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                        result = true;
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                    result = true;

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }

    public void newGame(){
        setContentView(R.layout.activity_mainscreen);
        ImageView img_animation = (ImageView) findViewById(R.id.player);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float screenWidth = metrics.widthPixels;
        float screenHeight = metrics.heightPixels;
        positionW0 = screenWidth/2;
        final float position = screenWidth/6;
        positionH0 = 0f;
        positionH1 = screenHeight/4;
        positionH2 = (screenHeight/4)*2;
        positionH3 = (screenHeight/4)*3;
        FrameLayout fm = (FrameLayout) findViewById(R.id.main);
        fm.setOnTouchListener(new OnSwipeTouchListener(Mainscreen.this){
            ObjectAnimator animation;
            ImageView img_animation = (ImageView) findViewById(R.id.player);
            public void onSwipeTop() {
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", img_animation.getTranslationY(), img_animation.getTranslationY()-positionH1);
                animation.start();
                animation.setDuration(500);
            }
            public void onSwipeRight() {
                animation = ObjectAnimator.ofFloat(img_animation, "translationX", img_animation.getTranslationX(), img_animation.getTranslationX()+position);
                animation.start();
                animation.setDuration(500);
            }
            public void onSwipeLeft() {
                animation = ObjectAnimator.ofFloat(img_animation, "translationX", img_animation.getTranslationX(), img_animation.getTranslationX()-position);
                animation.start();
                animation.setDuration(500);
            }
            public void onSwipeBottom() {
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", img_animation.getTranslationY(), img_animation.getTranslationY()+positionH1);
                animation.start();
                animation.setDuration(500);
            }


        });
        whereIs=0;
        gameOn=true;
        ObjectAnimator animation = ObjectAnimator.ofFloat(img_animation, "translationX", positionW0, positionW0);
        animation.start();
        ObjectAnimator anim = ObjectAnimator.ofFloat(img_animation, "translationY", positionH0, positionH0);
        anim.start();
        cars();
        gameSong = MediaPlayer.create(this, R.raw.song);
        brsound = MediaPlayer.create( this, R.raw.breaksound);
        gameSong.start();
        img_animation.setBackgroundResource(R.drawable.playeranime);

        rocketAnimation = (AnimationDrawable) img_animation.getBackground();
        rocketAnimation.start();
    }

    public void cars(){

        final Timer timer = new Timer();
        TimerTask timerTask;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (gameOn==false){
                            timer.cancel();
                            timer.purge();
                        }
                        newCar();
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        animateStreet();
                        if (gameOn==false){
                            timer.cancel();
                            timer.purge();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 2500);
    }

    public void move(View view){

        ObjectAnimator animation;
        ImageView img_animation = (ImageView) findViewById(R.id.player);


        switch (whereIs){

            case 0:
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", positionH0, positionH1);
                animation.start();
                animation.setDuration(500);
                whereIs++;
                break;

            case 1:
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", positionH1, positionH2);
                animation.start();
                animation.setDuration(500);
                whereIs++;
                break;

            case 2:
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", positionH2, positionH3);
                animation.start();
                animation.setDuration(500);
                whereIs++;
                break;

            case 3:
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", positionH3, positionH2);
                animation.start();
                animation.setDuration(500);
                whereIs++;
                break;

            case 4:
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", positionH2, positionH1);
                animation.start();
                animation.setDuration(500);
                whereIs++;
                break;

            case 5:
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", positionH1, positionH0);
                animation.start();
                animation.setDuration(500);
                points++;
                whereIs=0;
                break;
        }

    }

    public void animateStreet(){

        ImageView rocketImage = (ImageView) findViewById(R.id.imv);
        rocketImage.setBackgroundResource(R.drawable.valuesnim);
        rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
        ObjectAnimator positionchange = ObjectAnimator.ofFloat(rocketImage, "translationY", 0f, 0f);
        positionchange.start();
        ObjectAnimator animation = ObjectAnimator.ofFloat(rocketImage, "translationX", 1200f, -40f);
        animation.start();
        animation.setDuration(2000);
        rocketAnimation.start();
    }



    public void newCar(){
        Random rnd = new Random();
        int r = rnd.nextInt(2);
        final Rect r1 = new Rect();
        final Rect r2 = new Rect();
        final ImageView img_animation = (ImageView) findViewById(R.id.player);
        switch (r)
        {
            case 0:
            {
                final ImageView iv = new ImageView(this);
                iv.setImageResource(R.drawable.car1);
                final FrameLayout fl = (FrameLayout) findViewById(R.id.main);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                fl.addView(iv,lp);
                ObjectAnimator positionchange = ObjectAnimator.ofFloat(iv, "translationY", positionH1, positionH1);
                positionchange.start();
                final ObjectAnimator animation = ObjectAnimator.ofFloat(iv, "translationX", -400f, positionW0*2);
                animation.start();
                animation.setDuration(2000);
                final Context context = this;
                animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        r1.set((int)iv.getTranslationX(), (int)iv.getTranslationY(), ((int)iv.getTranslationX()+iv.getMeasuredWidth()), ((int)iv.getTranslationY()+iv.getMeasuredHeight()));
                        r2.set((int)img_animation.getTranslationX(), (int)img_animation.getTranslationY(), ((int)img_animation.getTranslationX()+img_animation.getMeasuredWidth()), ((int)img_animation.getTranslationY()+img_animation.getMeasuredHeight()));
                        if(Rect.intersects(r1, r2) && gameOn==true) {
                            endGame(context);
                        }
                    }
                });
                break;
            }
            case 1:
            {
                final ImageView iv = new ImageView(this);
                iv.setImageResource(R.drawable.car2);
                FrameLayout fl = (FrameLayout) findViewById(R.id.main);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
                fl.addView(iv,lp);
                ObjectAnimator positionchange = ObjectAnimator.ofFloat(iv, "translationY", positionH2, positionH2);
                positionchange.start();
                final ObjectAnimator animation = ObjectAnimator.ofFloat(iv, "translationX", positionW0*2, -400f);
                animation.start();
                animation.setDuration(2000);
                final Context context = this;

                animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {


                        r1.set((int)iv.getTranslationX(), (int)iv.getTranslationY(), ((int)iv.getTranslationX()+iv.getMeasuredWidth()), ((int)iv.getTranslationY()+iv.getMeasuredHeight()));
                        r2.set((int)img_animation.getTranslationX(), (int)img_animation.getTranslationY(), ((int)img_animation.getTranslationX()+img_animation.getMeasuredWidth()), ((int)img_animation.getTranslationY()+img_animation.getMeasuredHeight()));
                        if(Rect.intersects(r1, r2) && gameOn==true)
                        {
                           endGame(context);
                        }
                    }
                });
            }
        }
    }

public void endGame(Context context)
{
    gameOn=false;
    gameSong.stop();
    brsound.start();

    new AlertDialog.Builder(context)
            .setTitle("Sanguinetti was Wrecked!")
            .setMessage("He was able to develop Bubbly Invasion but he couldn't cross the road!\nYou scored "+points+" points!\nWould you like to play again?")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Mainscreen.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Mainscreen.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
}

}
