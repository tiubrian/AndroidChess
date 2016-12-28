package com.example.brian.androidchess.model;

import android.content.Context;

/**
 * Created by Brian on 16/12/27.
 */

public class GameModel {
    private BoardModel boardModel = new BoardModel();
    private Context context;
    private char turn = 'w';
    public GameModel(Context context) {
        this.context = context;
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public void setBoardModel(BoardModel boardModel) {
        this.boardModel = boardModel;
    }

    public void switchTurn() {
        if(turn == 'w') {
            turn = 'b';
        } else {
            turn = 'w';
        }
    }

    // Getters and Setters
    public char getTurn() {
        return turn;
    }

    public void setTurn(char turn) {
        this.turn = turn;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
