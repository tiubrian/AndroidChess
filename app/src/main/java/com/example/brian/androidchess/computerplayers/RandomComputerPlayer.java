package com.example.brian.androidchess.computerplayers;

import com.example.brian.androidchess.model.BoardModel;

import java.util.Collections;
import java.util.Vector;


/**
 * Created by Brian on 16/12/28.
 */

public class RandomComputerPlayer extends ComputerPlayer {

    public RandomComputerPlayer(BoardModel boardModel, char color) {
        super(boardModel, color);
    }

    @Override
    public Move getMove() {
        Vector<Move> possibleMoves;
        if(color == 'w') {
            possibleMoves = getAllPossibleWhiteMoves();
        } else {
            possibleMoves = getAllPossibleBlackMoves();
        }
        Collections.shuffle(possibleMoves);
        return possibleMoves.get(0);
    }
}
