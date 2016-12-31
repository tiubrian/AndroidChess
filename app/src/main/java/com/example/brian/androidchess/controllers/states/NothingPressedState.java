package com.example.brian.androidchess.controllers.states;

import com.example.brian.androidchess.controllers.SquareController;
import com.example.brian.androidchess.model.BoardModel;
import com.example.brian.androidchess.model.GameModel;

/**
 * Created by Brian on 16/12/27.
 */

public class NothingPressedState extends PressState{

    public NothingPressedState(int position, GameModel gameModel, SquareController squareController) {
        super(position, gameModel, squareController);
    }

    @Override
    public void press() {
        if(gameModel.isComputerThinking()) {
            return;
        }
        gameModel.getBoardModel().resetHighlightBoard();
        char pressedPieceColor = '#';
        if(gameModel.getBoardModel().getBoard()[converter[position]] < 0) {
            pressedPieceColor = 'b';
        } else if(gameModel.getBoardModel().getBoard()[converter[position]] > 0) {
            pressedPieceColor = 'w';
        }
        if(gameModel.getBoardModel().getBoard()[converter[position]] != 0 &&
            pressedPieceColor== gameModel.getTurn()) {
            gameModel.getBoardModel().getHighlightBoard()[position] = 1;
            gameModel.getBoardModel().setStateEnum(StateEnum.PIECEALREADYSELECTEDSTATE);
            gameModel.getBoardModel().setCurrentSelectedPosition(converter[position]);
            highlightPossible();
        }
    }
}
