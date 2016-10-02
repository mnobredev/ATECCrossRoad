package com.example.mario.ateccrossroad;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
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
    float positionW0, positionH0;
    float lastW, lastH;
    float stepH, stepW;
    float screenHeight, screenWidth;
    int player;
    private Boolean exit = false;
    Chars myChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newGame();
    }

    @Override
    public void onBackPressed() {
            if (exit) {
                finish();
            } else {
                Toast.makeText(this, "Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);
            }
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

        player = 0;
        Intent intent = getIntent();
        player  = intent.getIntExtra("Escolha", player);
        if (player==1) {
            myChar = new Chars(R.raw.song, R.drawable.playeranime);
        }
        if (player==2) {
            myChar = new Chars(R.raw.nuno, R.drawable.nunoanime);
        }
        if (player==3) {
            myChar = new Chars(R.raw.dariosong, R.drawable.darioanime);
        }
        setContentView(R.layout.activity_mainscreen);
        ImageView img_animation = (ImageView) findViewById(R.id.player);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        positionW0 = screenWidth/2;
        positionH0 = 0f;
        stepW = screenWidth/6;
        stepH = screenHeight/4;
        lastW = screenWidth-stepW;
        lastH = screenHeight-stepH;
        points = 0;
        FrameLayout fm = (FrameLayout) findViewById(R.id.main);
        fm.setOnTouchListener(new OnSwipeTouchListener(Mainscreen.this){

            ObjectAnimator animation;
            ImageView img_animation = (ImageView) findViewById(R.id.player);

            public void onSwipeTop() {
                float fromY = img_animation.getTranslationY();
                float toY = img_animation.getTranslationY()-stepH;
                if (whereIs==1 && toY<=stepH){
                    points++;
                    whereIs=0;
                }
                if (toY<=0){
                    toY=0;
                }
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", fromY, toY);
                animation.start();
                animation.setDuration(500);
            }
            public void onSwipeRight() {
                float fromX = img_animation.getTranslationX();
                float toX = img_animation.getTranslationX()+stepW;
                if (toX>=lastW){
                    toX=lastW;
                }
                animation = ObjectAnimator.ofFloat(img_animation, "translationX", fromX, toX);
                animation.start();
                animation.setDuration(500);
            }
            public void onSwipeLeft() {
                float fromX = img_animation.getTranslationX();
                float toX = img_animation.getTranslationX()-stepW;
                if (toX<=0){
                    toX=0;
                }
                animation = ObjectAnimator.ofFloat(img_animation, "translationX", fromX, toX);
                animation.start();
                animation.setDuration(500);
            }
            public void onSwipeBottom() {
                float fromY = img_animation.getTranslationY();
                float toY = img_animation.getTranslationY()+stepH;
                if (whereIs==0 && toY>=lastH){
                    points++;
                    whereIs=1;
                }
                if (toY>=lastH){
                    toY=lastH;
                }
                animation = ObjectAnimator.ofFloat(img_animation, "translationY", fromY, toY);
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
        gameSong = MediaPlayer.create(this, myChar.getThemeSong());
        brsound = MediaPlayer.create( this, R.raw.breaksound);
        gameSong.start();
        img_animation.setBackgroundResource(myChar.getAnimation());
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
                ObjectAnimator positionchange = ObjectAnimator.ofFloat(iv, "translationY", stepH, stepH);
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
                ObjectAnimator positionchange = ObjectAnimator.ofFloat(iv, "translationY", stepH*2, stepH*2);
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


        Intent intent = new Intent(Mainscreen.this, EndActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("score", Integer.toString(points));
        intent.putExtra("Escolha",player);
        startActivity(intent);

    }

}
