package com.example.mario.ateccrossroad;

public class Chars {
    int themeSong;
    int animation;

    public int getThemeSong() {
        return themeSong;
    }

    public void setThemeSong(int themeSong) {
        this.themeSong = themeSong;
    }

    public int getAnimation() {
        return animation;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public Chars(int themeSong, int animation) {

        this.themeSong = themeSong;
        this.animation = animation;
    }
}
