package com.example.brian.androidchess.model;

import com.example.brian.androidchess.controllers.states.StateEnum;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Brian on 16/12/26.
 */

public class BoardModel {
    /*
        0 - empty space
        negative - black
        positive - white
        1 - pawn
        2 - rook
        3 - knight
        4 - bishop
        5 - queen
        6 - king
     */
    private short[] board = {
            -2,-3,-4,-5,-6,-4,-3,-2,    0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,
            -1,-1,-1,-1,-1,-1,-1,-1,    0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,
            0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,    0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,
            0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,    0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,
            0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,    0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,
            0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,    0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,
            1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,    0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,
            2 ,3 ,4 ,5 ,6 ,4 ,3 ,2 ,    0 ,0 ,0 ,0 ,0 ,0 ,0 ,0
    };
    /*
    0 - no highlight
    1 - selected square highlight
    2 - possible move highlight
     */
    private int[] highlightBoard = new int[128];
    private int currentSelectedPosition = -1;
    private StateEnum stateEnum = StateEnum.NOTHINGPRESSEDSTATE;

    public BoardModel() {
    }

    public Vector<Integer> getPossibleMoves(int position) {
        Vector<Integer> possibleMoves = new Vector<>();

        switch (board[position]) {
                /*  White Pieces  */
            // Pawn
            case 1:
                possibleMoves.add(position-16);
                break;

            // Rook
            case 2: break;

            // Knight
            case 3: break;

            // Bishop
            case 4: break;

            // Queen
            case 5: break;

            // King
            case 6: break;

                /*  Black Pieces  */
            // Pawn
            case -1:
                possibleMoves.add(position+16);
                break;

            // Rook
            case -2: break;

            // Knight
            case -3: break;

            // Bishop
            case -4: break;

            // Queen
            case -5: break;

            // King
            case -6: break;
        }

        return possibleMoves;
    }




    public void resetHighlightBoard() {
        for(int i = 0; i < 128; i++) {
            highlightBoard[i] = 0;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    // Getters and Setters
    public short[] getBoard() {
        return board;
    }

    public void setBoard(short[] board) {
        this.board = board;
    }

    public int[] getHighlightBoard() {
        return highlightBoard;
    }

    public void setHighlightBoard(int[] highlightBoard) {
        this.highlightBoard = highlightBoard;
    }

    public int getCurrentSelectedPosition() {
        return currentSelectedPosition;
    }

    public void setCurrentSelectedPosition(int currentSelectedPosition) {
        this.currentSelectedPosition = currentSelectedPosition;
    }

    public StateEnum getStateEnum() {
        return stateEnum;
    }

    public void setStateEnum(StateEnum stateEnum) {
        this.stateEnum = stateEnum;
    }
}
