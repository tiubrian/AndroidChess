package com.example.brian.androidchess.computerplayers;

/**
 * Created by Brian on 16/12/28.
 */

public class Move {

    private int before;
    private int after;

    public Move(int before, int after) {
        this.before = before;
        this.after = after;
    }

    public int getBefore() {
        return before;
    }

    public void setBefore(int before) {
        this.before = before;
    }

    public int getAfter() {
        return after;
    }

    public void setAfter(int after) {
        this.after = after;
    }
}
