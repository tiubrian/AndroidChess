package com.example.brian.androidchess.computerplayers;

/**
 * Created by Brian on 16/12/28.
 */

public class Move {

    private int before;
    private int after;
    private short[] board;

    public Move(int before, int after, short[] board) {
        this.before = before;
        this.after = after;
        this.board = board;
    }

    public int getRanking() {
        switch (Math.abs(board[after])) {
            case 1:
                switch (Math.abs(board[before])) {
                    case 6: return 36;
                    case 5: return 35;
                    case 4: return 34;
                    case 3: return 33;
                    case 2: return 32;
                    case 1: return 31;
                }
                break;
            case 2:
                switch (Math.abs(board[before])) {
                    case 6: return 20;
                    case 5: return 29;
                    case 4: return 28;
                    case 3: return 27;
                    case 2: return 26;
                    case 1: return 25;
                }
                break;
            case 3:
                switch (Math.abs(board[before])) {
                    case 6: return 24;
                    case 5: return 23;
                    case 4: return 22;
                    case 3: return 21;
                    case 2: return 20;
                    case 1: return 19;
                }
                break;
            case 4:
                switch (Math.abs(board[before])) {
                    case 6: return 18;
                    case 5: return 17;
                    case 4: return 16;
                    case 3: return 15;
                    case 2: return 14;
                    case 1: return 13;
                }
                break;
            case 5:
                switch (Math.abs(board[before])) {
                    case 6: return 12;
                    case 5: return 11;
                    case 4: return 10;
                    case 3: return 9;
                    case 2: return 8;
                    case 1: return 7;
                }
                break;
            case 6:
                switch (Math.abs(board[before])) {
                    case 6: return 6;
                    case 5: return 5;
                    case 4: return 4;
                    case 3: return 3;
                    case 2: return 2;
                    case 1: return 1;
                }
                break;
            default: return 100;
        }
        return -1;
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
