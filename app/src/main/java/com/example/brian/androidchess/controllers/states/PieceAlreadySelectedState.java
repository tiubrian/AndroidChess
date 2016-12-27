package com.example.brian.androidchess.controllers.states;

import com.example.brian.androidchess.controllers.SquareController;
import com.example.brian.androidchess.model.GameModel;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Brian on 16/12/27.
 */

public class PieceAlreadySelectedState extends PressState {

    public PieceAlreadySelectedState(int position, GameModel gameModel, SquareController squareController) {
        super(position, gameModel, squareController);
    }

    @Override
    public void press() {
        // Setup
        char pressedPieceColor = '#';
        if(gameModel.getBoardModel().getBoard()[converter[position]] < 0) {
            pressedPieceColor = 'b';
        } else if(gameModel.getBoardModel().getBoard()[converter[position]] > 0) {
            pressedPieceColor = 'w';
        }

        char selectedPieceColor = '-';
        if(gameModel.getBoardModel().getBoard()[gameModel.getBoardModel().getCurrentSelectedPosition()] < 0) {
            selectedPieceColor = 'b';
        } else if(gameModel.getBoardModel().getBoard()[gameModel.getBoardModel().getCurrentSelectedPosition()] > 0) {
            selectedPieceColor = 'w';
        }

        char turn = gameModel.getTurn();

        // Change board
        if(pressedPieceColor == '#') {
            gameModel.getBoardModel().resetHighlightBoard();
            gameModel.getBoardModel().setStateEnum(StateEnum.NOTHINGPRESSEDSTATE);
            gameModel.getBoardModel().movePiece(gameModel.getBoardModel().getCurrentSelectedPosition(),converter[position]);

        } else if(selectedPieceColor == pressedPieceColor) {
            gameModel.getBoardModel().resetHighlightBoard();
            gameModel.getBoardModel().getHighlightBoard()[position] = 1;
            highlightPossible();

        } else {
            gameModel.getBoardModel().resetHighlightBoard();
            gameModel.getBoardModel().setCurrentSelectedPosition(converter[position]);
            gameModel.getBoardModel().setStateEnum(StateEnum.NOTHINGPRESSEDSTATE);

        }
    }



}
