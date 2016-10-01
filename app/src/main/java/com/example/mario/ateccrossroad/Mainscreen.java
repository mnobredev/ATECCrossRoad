package com.example.mario.ateccrossroad;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.util.Timer;
import java.util.TimerTask;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class Mainscreen extends AppCompatActivity {

    public int whereIs, points;
    public boolean gameOn;
    static Rect r1 = new Rect();
    static Rect r2 = new Rect();
    static ImageView img_animation;
    AnimationDrawable rocketAnimation;
    MediaPlayer gameSong, brsound;

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
        img_animation = (ImageView) findViewById(R.id.player);
        FrameLayout fm = (FrameLayout) findViewById(R.id.main);
        fm.setOnTouchListener(new OnSwipeTouchListener(Mainscreen.this){
            public void onSwipeTop() {
                Toast.makeText(Mainscreen.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(Mainscreen.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(Mainscreen.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(Mainscreen.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
        whereIs=0;
        gameOn=true;
        ObjectAnimator animation = ObjectAnimator.ofFloat(img_animation, "translationX", 550f, 550f);
        animation.start();
        ObjectAnimator anim = ObjectAnimator.ofFloat(img_animation, "translationY", 0f, 0f);
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
                        newCarup();
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 2000);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newCardown();
                        if (gameOn==false){
                            timer.cancel();
                            timer.purge();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1500);

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

        switch (whereIs){

            case 0:
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", 0f, 100f);
                animation.start();
                animation.setDuration(500);
                whereIs++;
                break;

            case 1:
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", 100f, 250f);
                animation.start();
                animation.setDuration(500);
                whereIs++;
                break;

            case 2:
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", 250f, 400f);
                animation.start();
                animation.setDuration(500);
                whereIs++;
                break;

            case 3:
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", 400f, 250f);
                animation.start();
                animation.setDuration(500);
                whereIs++;
                break;

            case 4:
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", 250f, 100f);
                animation.start();
                animation.setDuration(500);
                whereIs++;
                break;

            case 5:
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", 100f, 0f);
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

    public void newCarup(){
        final ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.car1);
        FrameLayout fl = (FrameLayout) findViewById(R.id.main);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        fl.addView(iv,lp);
        ObjectAnimator positionchange = ObjectAnimator.ofFloat(iv, "translationY", 200f, 200f);
        positionchange.start();
        final ObjectAnimator animation = ObjectAnimator.ofFloat(iv, "translationX", -400f, 2000f);
        animation.start();
        animation.setDuration(7000);
        final Context context = this;

        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                r1.set((int)iv.getTranslationX(), (int)iv.getTranslationY(), ((int)iv.getTranslationX()+100), ((int)iv.getTranslationY()+50));
                r2.set((int)img_animation.getTranslationX(), (int)img_animation.getTranslationY(), ((int)img_animation.getTranslationX()+100), ((int)img_animation.getTranslationY()+200));
                if(Rect.intersects(r1, r2) && gameOn==true)
                {
                    gameOn=false;
                    FrameLayout getFrame = (FrameLayout) findViewById(R.id.main);
                    gameSong.stop();
                    brsound.start();

                    new AlertDialog.Builder(context)
                            .setTitle("Sanguinetti was Wrecked!")
                            .setMessage("He was able to develop Bubbly Invasion but he couldn't cross the road!\nYou scored "+points+" points!\nWould you like to play again?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    newGame();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

    }

    public void newCardown(){
        final ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.car2);
        final ObjectAnimator animation;
        FrameLayout fl = (FrameLayout) findViewById(R.id.main);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        fl.addView(iv,lp);
        ObjectAnimator positionchange = ObjectAnimator.ofFloat(iv, "translationY", 300f, 300f);
        positionchange.start();
        animation = ObjectAnimator.ofFloat(iv, "translationX", 2000f, -400f);
        animation.start();
        animation.setDuration(7000);
        final Context context = this;

        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {


                r1.set((int)iv.getTranslationX(), (int)iv.getTranslationY(), ((int)iv.getTranslationX()+100), ((int)iv.getTranslationY()+50));
                r2.set((int)img_animation.getTranslationX(), (int)img_animation.getTranslationY(), ((int)img_animation.getTranslationX()+100), ((int)img_animation.getTranslationY()+200));
                if(Rect.intersects(r1, r2) && gameOn==true)
                {
                    gameOn=false;
                    gameSong.stop();
                    brsound.start();

                    new AlertDialog.Builder(context)
                            .setTitle("Sanguinetti was Wrecked!")
                            .setMessage("He was able to develop Bubbly Invasion but he couldn't cross the road!\nYou scored "+points+" points!\nWould you like to play again?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    newGame();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
    }



}
