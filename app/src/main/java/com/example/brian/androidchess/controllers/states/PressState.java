package com.example.brian.androidchess.controllers.states;

import com.example.brian.androidchess.controllers.SquareController;
import com.example.brian.androidchess.model.BoardModel;
import com.example.brian.androidchess.model.GameModel;

/**
 * Created by Brian on 16/12/27.
 */

public abstract class PressState {
    protected SquareController squareController;
    protected GameModel gameModel;
    protected int position;
    protected short[] converter = {
            0,1,2,3,4,5,6,7,
            16,17,18,19,20,21,22,23,
            32,33,34,35,36,37,38,39,
            48,49,50,51,52,53,54,55,
            64,65,66,67,68,69,70,71,
            80,81,82,83,84,85,86,87,
            96,97,98,99,100,101,102,103,
            112,113,114,115,116,117,118,119
    };

    public PressState(int position, GameModel gameModel, SquareController squareController) {
        this.position = position;
        this.gameModel = gameModel;
        this.squareController = squareController;
    }

    public abstract void press();
}
