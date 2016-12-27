package com.example.brian.androidchess.controllers.states;

import com.example.brian.androidchess.controllers.SquareController;
import com.example.brian.androidchess.model.GameModel;

/**
 * Created by Brian on 16/12/27.
 */

public class PieceAlreadySelectedState extends PressState {

    public PieceAlreadySelectedState(int position, GameModel gameModel, SquareController squareController) {
        super(position, gameModel, squareController);
    }

    @Override
    public void press() {

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

        if(pressedPieceColor == '#') {
            gameModel.getBoardModel().resetHighlightBoard();
        } else if(selectedPieceColor == pressedPieceColor) {
            gameModel.getBoardModel().resetHighlightBoard();
            gameModel.getBoardModel().getHighlightBoard()[position] = 1;

        } else {
            gameModel.getBoardModel().resetHighlightBoard();
            gameModel.getBoardModel().setCurrentSelectedPosition(converter[position]);
            gameModel.getBoardModel().setStateEnum(StateEnum.NOTHINGPRESSEDSTATE);

        }
    }
}
