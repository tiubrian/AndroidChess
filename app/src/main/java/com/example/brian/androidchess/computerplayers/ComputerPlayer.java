package com.example.brian.androidchess.computerplayers;

import com.example.brian.androidchess.model.BoardModel;

import java.net.Inet4Address;
import java.util.Vector;

/**
 * Created by Brian on 16/12/28.
 */

public abstract class ComputerPlayer {
    protected BoardModel boardModel;
    protected char color;
    protected short[] board;

    public ComputerPlayer(BoardModel boardModel, char color) {
        this.boardModel = boardModel;
        this.color = color;
        board = boardModel.getBoard();
    }

    public abstract Move getMove();

    protected Vector<Move> getAllPossibleBlackMoves() {
        Vector<Move> possibleMoves = new Vector<>();
        for(int i = 0; i < 128; i++) {
            // skip fake board
            if ((i & 0x0f) == 8) {
                i += 7;
                continue;
            }
            if(board[i] < 0) {
                Vector<Integer> miniMoves = boardModel.getPossibleMoves(i);
                for(int j = 0; j < miniMoves.size(); j++) {
                    possibleMoves.add(new Move(i,miniMoves.get(j)));
                }
            }
        }
        return possibleMoves;
    }

    protected Vector<Move> getAllPossibleWhiteMoves() {
        Vector<Move> possibleMoves = new Vector<>();
        for(int i = 0; i < 128; i++) {
            // skip fake board
            if ((i & 0x0f) == 8) {
                i += 7;
                continue;
            }
            if(board[i] > 0) {
                Vector<Integer> miniMoves = boardModel.getPossibleMoves(i);
                for(int j = 0; j < miniMoves.size(); j++) {
                    possibleMoves.add(new Move(i,miniMoves.get(j)));
                }
            }
        }
        return possibleMoves;
    }
}
