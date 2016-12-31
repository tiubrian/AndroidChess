package com.example.brian.androidchess.controllers;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.brian.androidchess.controllers.states.NothingPressedState;
import com.example.brian.androidchess.controllers.states.PieceAlreadySelectedState;
import com.example.brian.androidchess.controllers.states.PressState;
import com.example.brian.androidchess.model.GameModel;
import com.example.brian.androidchess.views.SquareAdapter;

/**
 * Created by Brian on 16/12/27.
 */

public class SquareController implements View.OnTouchListener {
    private short[] converter = {
            0,1,2,3,4,5,6,7,
            16,17,18,19,20,21,22,23,
            32,33,34,35,36,37,38,39,
            48,49,50,51,52,53,54,55,
            64,65,66,67,68,69,70,71,
            80,81,82,83,84,85,86,87,
            96,97,98,99,100,101,102,103,
            112,113,114,115,116,117,118,119
    };
    private GameModel gameModel;
    private SquareAdapter squareAdapter;
    private int position;
    private int realPosition;
    private PressState pressState;

    public SquareController(GameModel gameModel, SquareAdapter squareAdapter, int position) {
        this.gameModel = gameModel;
        this.squareAdapter = squareAdapter;
        this.position = position;
        realPosition = converter[position];
        pressState = new NothingPressedState(position,gameModel,this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(gameModel.isComputerThinking()) {
            return false;
        }

        switch (gameModel.getBoardModel().getStateEnum()) {
            case NOTHINGPRESSEDSTATE: pressState = new NothingPressedState(position,gameModel,this); break;
            case PIECEALREADYSELECTEDSTATE: pressState = new PieceAlreadySelectedState(position,gameModel,this); break;
        }


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // PRESSED
                pressState.press();
                squareAdapter.notifyDataSetChanged();
                return true; // if you want to handle the touch event
/*
            case MotionEvent.ACTION_UP:
                // RELEASED
                //gameModel.getBoardModel().resetHighlightBoard();
                squareAdapter.notifyDataSetChanged();
                return true; // if you want to handle the touch event*/
        }
        return false;
    }

    public short[] getConverter() {
        return converter;
    }

    public void setConverter(short[] converter) {
        this.converter = converter;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public SquareAdapter getSquareAdapter() {
        return squareAdapter;
    }

    public void setSquareAdapter(SquareAdapter squareAdapter) {
        this.squareAdapter = squareAdapter;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRealPosition() {
        return realPosition;
    }

    public void setRealPosition(int realPosition) {
        this.realPosition = realPosition;
    }

    public PressState getPressState() {
        return pressState;
    }

    public void setPressState(PressState pressState) {
        this.pressState = pressState;
    }


}
