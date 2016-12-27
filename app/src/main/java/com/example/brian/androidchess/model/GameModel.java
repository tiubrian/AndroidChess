package com.example.brian.androidchess.model;

/**
 * Created by Brian on 16/12/27.
 */

public class GameModel {
    private BoardModel boardModel = new BoardModel();
    private char turn = 'w';
    public GameModel() {
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
}
