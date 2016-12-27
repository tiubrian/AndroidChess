package com.example.brian.androidchess.model;

/**
 * Created by Brian on 16/12/27.
 */

public class GameModel {
    private BoardModel boardModel = new BoardModel();
    public GameModel() {
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public void setBoardModel(BoardModel boardModel) {
        this.boardModel = boardModel;
    }
}
