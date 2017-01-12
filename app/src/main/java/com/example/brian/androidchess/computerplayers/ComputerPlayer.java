package com.example.brian.androidchess.computerplayers;

import com.example.brian.androidchess.model.BoardModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Brian on 16/12/28.
 */

public abstract class ComputerPlayer {
    protected BoardModel boardModel;
    protected char color;
    protected short[] board;

    protected int whiteKingPosition;
    protected int blackKingPosition;

    protected boolean CANWHITEKINGSIDECASTLE;
    protected boolean CANWHITEQUEENSIDECASTLE;

    protected boolean CANBLACKKINGSIDECASTLE;
    protected boolean CANBLACKQUEENSIDECASTLE;

    protected HashMap<String,Integer> boardPositions;

    private short[] knightDeltaValues = {31,-31,33,-33,18,-18,14,-14};
    private short[] kingDeltaValues = {-1,1,-16,16,17,15,-17,-15};
    private short[] rookDeltaValues = {-1,1,16,-16};
    private short[] queenDeltaValues = {-1,1,16,-16,15,-15,17,-17};
    private short[] bishopDeltaValues = {15,-15,17,-17};

    boolean pos0 = false;
    boolean pos1 = false;
    boolean pos2 = false;
    boolean pos3 = false;
    boolean pos4 = false;
    boolean pos5 = false;
    boolean pos6 = false;
    boolean pos7 = false;

    boolean pos112 = false;
    boolean pos113 = false;
    boolean pos114 = false;
    boolean pos115 = false;
    boolean pos116 = false;
    boolean pos117 = false;
    boolean pos118 = false;
    boolean pos119 = false;

    public static final int[] ATTACK_ARRAY = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 5, 0, 0, 5, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 2, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 2, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 2, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 6, 2, 6, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 4, 1, 4, 6, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 1, 0, 1, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 6, 3, 1, 3, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 6, 2, 6, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 2, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 2, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 2, 0, 0, 0, 0, 5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 5, 0, 0, 5, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    public static final int[] DELTA_ARRAY =         {0, 0, 0, 0, 0, 0, 0, 0, 0, -17, 0, 0, 0, 0, 0, 0, -16, 0, 0, 0, 0, 0, 0, -15, 0, 0, -17, 0, 0, 0, 0, 0, -16, 0, 0, 0, 0, 0, -15, 0, 0, 0, 0, -17, 0, 0, 0, 0, -16, 0, 0, 0, 0, -15, 0, 0, 0, 0, 0, 0, -17, 0, 0, 0, -16, 0, 0, 0, -15, 0, 0, 0, 0, 0, 0, 0, 0, -17, 0, 0, -16, 0, 0, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -17, -33, -16, -31, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -18, -17, -16, -15, -14, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 14, 15, 16, 17, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 31, 16, 33, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 16, 0, 0, 17, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 16, 0, 0, 0, 17, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 16, 0, 0, 0, 0, 17, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 17, 0, 0, 15, 0, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 0, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static final int[] UPDATED_DELTA_ARRAY = {0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 0, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 0, 15, 0, 0, 17, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 17, 0, 0, 0, 0, 16, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0, 17, 0, 0, 0, 16, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 17, 0, 0, 16, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 33, 16, 31, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 17, 16, 15, 14, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, -14, -15, -16, -17, -18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -15, -31, -16, -33, -17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -15, 0, 0, -16, 0, 0, -17, 0, 0, 0, 0, 0, 0, 0, 0, -15, 0, 0, 0, -16, 0, 0, 0, -17, 0, 0, 0, 0, 0, 0, -15, 0, 0, 0, 0, -16, 0, 0, 0, 0, -17, 0, 0, 0, 0, -15, 0, 0, 0, 0, 0, -16, 0, 0, 0, 0, 0, -17, 0, 0, -15, 0, 0, 0, 0, 0, 0, -16, 0, 0, 0, 0, 0, 0, -17, 0, 0, 0, 0, 0, 0, 0, 0, 0};


    int[] pawnEvalTableWhite = {
            500,  500,  500,  500,  500,  500,  500,  500,  0,  0,  0,  0,  0,  0,  0,  0,
            50, 50, 50, 50, 50, 50, 50, 50, 0,  0,  0,  0,  0,  0,  0,  0,
            10, 10, 20, 30, 30, 20, 10, 10, 0,  0,  0,  0,  0,  0,  0,  0,
            5,  5, 10, 25, 25, 10,  5,  5,  0,  0,  0,  0,  0,  0,  0,  0,
            0,  0,  0, 20, 20,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
            5, -5,-10,  0,  0,-10, -5,  5,  0,  0,  0,  0,  0,  0,  0,  0,
            5, 10, 10,-20,-20, 10, 10,  5,  0,  0,  0,  0,  0,  0,  0,  0,
            0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0
    };

    int[] pawnEvalTableWhiteEnd = {
            900,900,900,900,900,900,900,900,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            50,50,50,50,50,50,50,50,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            40,40,40,40,40,40,40,40,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            30,30,30,30,30,30,30,30,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            20,20,20,20,20,20,20,20,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            10,10,10,10,10,10,10,10,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            0 ,0 ,0 ,0 ,0 ,0 ,0 , 0,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0
    };

    int[] pawnEvalTableBlackEnd = {
            0 ,0 ,0 ,0 ,0 ,0 ,0 ,0, 0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            0 ,0 ,0 ,0 ,0 ,0 ,0 , 0,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            10,10,10,10,10,10,10,10,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            20,20,20,20,20,20,20,20,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            30,30,30,30,30,30,30,30,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            40,40,40,40,40,40,40,40,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            50,50,50,50,50,50,50,50,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,
            900,900,900,900,900,900,900,900,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0
    };

    int[] knightEvalTableWhite = {
            -50,-40,-30,-30,-30,-30,-40,-50,    0,  0,  0,  0,  0,  0,  0,  0,
            -40,-20,  0,  0,  0,  0,-20,-40,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,  0, 10, 15, 15, 10,  0,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,  5, 15, 20, 20, 15,  5,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,  0, 15, 20, 20, 15,  0,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,  5, 10, 15, 15, 10,  5,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -40,-20,  0,  5,  5,  0,-20,-40,    0,  0,  0,  0,  0,  0,  0,  0,
            -50,-40,-30,-30,-30,-30,-40,-50,    0,  0,  0,  0,  0,  0,  0,  0
    };

    int[] bishopEvalTableWhite = {
            -20,-10,-10,-10,-10,-10,-10,-20,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  0,  0,  0,  0,  0,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  0,  5, 10, 10,  5,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  5,  5, 10, 10,  5,  5,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  0, 10, 10, 10, 10,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10, 10, 10, 10, 10, 10, 10,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  5,  0,  0,  0,  0,  5,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -20,-10,-10,-10,-10,-10,-10,-20,    0,  0,  0,  0,  0,  0,  0,  0
    };

    int[] rookEvalTableWhite = {
            0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
            5, 10, 10, 10, 10, 10, 10,  5,  0,  0,  0,  0,  0,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5, 0,  0,  0,  0,  0,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5, 0,  0,  0,  0,  0,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5, 0,  0,  0,  0,  0,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5, 0,  0,  0,  0,  0,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5, 0,  0,  0,  0,  0,  0,  0,  0,
            0,  0,  0,  5,  5,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0
    };

    int[] queenEvalTableWhite = {
            -20,-10,-10, -5, -5,-10,-10,-20,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  0,  0,  0,  0,  0,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  0,  5,  5,  5,  5,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -5,  0,  5,  5,  5,  5,  0, -5,     0,  0,  0,  0,  0,  0,  0,  0,
            0,  0,  5,  5,  5,  5,  0, -5,      0,  0,  0,  0,  0,  0,  0,  0,
            -10,  5,  5,  5,  5,  5,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  0,  5,  0,  0,  0,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -20,-10,-10, -5, -5,-10,-10,-20,    0,  0,  0,  0,  0,  0,  0,  0
    };

    int[] kingEvalTableBeginWhite = {
            -30,-40,-40,-50,-50,-40,-40,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-40,-40,-50,-50,-40,-40,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-40,-40,-50,-50,-40,-40,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-40,-40,-50,-50,-40,-40,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -20,-30,-30,-40,-40,-30,-30,-20,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,-20,-20,-20,-20,-20,-20,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            20, 20,  0,  0,  0,  0, 20, 20,     0,  0,  0,  0,  0,  0,  0,  0,
            20, 30, 10,  0,  0, 10, 30, 20,     0,  0,  0,  0,  0,  0,  0,  0
    };

    int[] kingEvalTableEndWhite = {
            -50,-40,-30,-20,-20,-30,-40,-50,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-20,-10,  0,  0,-10,-20,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-10, 20, 30, 30, 20,-10,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-10, 30, 40, 40, 30,-10,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-10, 30, 40, 40, 30,-10,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-10, 20, 30, 30, 20,-10,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-30,  0,  0,  0,  0,-30,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -50,-30,-30,-30,-30,-30,-30,-50,    0,  0,  0,  0,  0,  0,  0,  0
    };

    int[] pawnEvalTableBlack = {
            0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
            5, 10, 10,-20,-20, 10, 10,  5,  0,  0,  0,  0,  0,  0,  0,  0,
            5, -5,-10,  0,  0,-10, -5,  5,  0,  0,  0,  0,  0,  0,  0,  0,
            0,  0,  0, 20, 20,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
            5,  5, 10, 25, 25, 10,  5,  5,  0,  0,  0,  0,  0,  0,  0,  0,
            10, 10, 20, 30, 30, 20, 10, 10, 0,  0,  0,  0,  0,  0,  0,  0,
            50, 50, 50, 50, 50, 50, 50, 50, 0,  0,  0,  0,  0,  0,  0,  0,
            500,  500,  500,  500,  500,  500,  500,  500,  0,  0,  0,  0,  0,  0,  0,  0
    };

    int[] knightEvalTableBlack = {
            -50,-40,-30,-30,-30,-30,-40,-50,    0,  0,  0,  0,  0,  0,  0,  0,
            -40,-20,  0,  5,  5,  0,-20,-40,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,  5, 10, 15, 15, 10,  5,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,  0, 15, 20, 20, 15,  0,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,  5, 15, 20, 20, 15,  5,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,  0, 10, 15, 15, 10,  0,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -40,-20,  0,  0,  0,  0,-20,-40,    0,  0,  0,  0,  0,  0,  0,  0,
            -50,-40,-30,-30,-30,-30,-40,-50,    0,  0,  0,  0,  0,  0,  0,  0
    };
    int[] bishopEvalTableBlack = {
            -20,-10,-10,-10,-10,-10,-10,-20,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  5,  0,  0,  0,  0,  5,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10, 10, 10, 10, 10, 10, 10,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  0, 10, 10, 10, 10,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  5,  5, 10, 10,  5,  5,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  0,  5, 10, 10,  5,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  0,  0,  0,  0,  0,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -20,-10,-10,-10,-10,-10,-10,-20,    0,  0,  0,  0,  0,  0,  0,  0
    };
    int[] rookEvalTableBlack = {
            0,  0,  0,  5,  5,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5, 0,  0,  0,  0,  0,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5, 0,  0,  0,  0,  0,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5, 0,  0,  0,  0,  0,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5, 0,  0,  0,  0,  0,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5, 0,  0,  0,  0,  0,  0,  0,  0,
            5, 10, 10, 10, 10, 10, 10,  5,  0,  0,  0,  0,  0,  0,  0,  0,
            0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0
    };
    int[] queenEvalTableBlack = {
            -20,-10,-10, -5, -5,-10,-10,-20,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  0,  0,  0,  0,  5,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  5,  5,  5,  5,  5,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            0,  0,  5,  5,  5,  5,  0, -5,      0,  0,  0,  0,  0,  0,  0,  0,
            -5,  0,  5,  5,  5,  5,  0, -5,     0,  0,  0,  0,  0,  0,  0,  0,
            -10,  0,  5,  5,  5,  5,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -10,  0,  0,  0,  0,  0,  0,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -20,-10,-10, -5, -5,-10,-10,-20,    0,  0,  0,  0,  0,  0,  0,  0
    };
    int[] kingEvalTableBeginBlack = {
            20, 30, 10,  0,  0, 10, 30, 20,     0,  0,  0,  0,  0,  0,  0,  0,
            20, 20,  0,  0,  0,  0, 20, 20,     0,  0,  0,  0,  0,  0,  0,  0,
            -10,-20,-20,-20,-20,-20,-20,-10,    0,  0,  0,  0,  0,  0,  0,  0,
            -20,-30,-30,-40,-40,-30,-30,-20,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-40,-40,-50,-50,-40,-40,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-40,-40,-50,-50,-40,-40,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-40,-40,-50,-50,-40,-40,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-40,-40,-50,-50,-40,-40,-30,    0,  0,  0,  0,  0,  0,  0,  0
    };
    int[] kingEvalTableEndBlack = {
            -50,-30,-30,-30,-30,-30,-30,-50,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-30,  0,  0,  0,  0,-30,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-10, 20, 30, 30, 20,-10,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-10, 30, 40, 40, 30,-10,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-10, 30, 40, 40, 30,-10,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-10, 20, 30, 30, 20,-10,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -30,-20,-10,  0,  0,-10,-20,-30,    0,  0,  0,  0,  0,  0,  0,  0,
            -50,-40,-30,-20,-20,-30,-40,-50,    0,  0,  0,  0,  0,  0,  0,  0
    };


    public ComputerPlayer(BoardModel boardModel, char color) {
        this.boardModel = boardModel;
        this.color = color;
        board = boardModel.getBoard();

        whiteKingPosition = boardModel.getWhiteKingPosition();
        blackKingPosition = boardModel.getBlackKingPosition();

        boardPositions = (HashMap<String, Integer>) boardModel.getBoardPositions();
/*
        canWhiteKingSideCastle = boardModel.isCanWhiteKingSideCastle();
        canWhiteQueenSideCastle = boardModel.isCanWhiteQueenSideCastle();

        canBlackKingSideCastle = boardModel.isCanBlackKingSideCastle();
        canBlackQueenSideCastle = boardModel.isCanBlackQueenSideCastle();
        */
    }

    public abstract Move getMove();

    protected Vector<Move> getBlackCaptureMoves(boolean canWhiteKingSideCastle, boolean canWhiteQueenSideCastle,boolean canBlackKingSideCastle,boolean canBlackQueenSideCastle) {
        Vector<Move> possibleMoves = new Vector<>();
        for(int i = 0; i < 128; i++) {
            // skip fake board
            if ((i & 0x0f) == 8) {
                i += 7;
                continue;
            }
            if(board[i] < 0) {
                Vector<Integer> miniMoves = getPossibleMoves(i,canWhiteKingSideCastle,canWhiteQueenSideCastle,canBlackKingSideCastle,canBlackQueenSideCastle);
                for(int j = 0; j < miniMoves.size(); j++) {
                    Move move = new Move(i,miniMoves.get(j),board);
                    if(board[move.getAfter()] != 0) {
                        possibleMoves.add(move);
                    }
                }
            }
        }
        return possibleMoves;
    }

    protected Vector<Move> getAllPossibleBlackMoves(boolean canWhiteKingSideCastle, boolean canWhiteQueenSideCastle,boolean canBlackKingSideCastle,boolean canBlackQueenSideCastle) {
        Vector<Move> possibleMoves = new Vector<>();
        for(int i = 0; i < 128; i++) {
            // skip fake board
            if ((i & 0x0f) == 8) {
                i += 7;
                continue;
            }
            if(board[i] < 0) {
                Vector<Integer> miniMoves = getPossibleMoves(i,canWhiteKingSideCastle,canWhiteQueenSideCastle,canBlackKingSideCastle,canBlackQueenSideCastle);
                for(int j = 0; j < miniMoves.size(); j++) {
                    possibleMoves.add(new Move(i,miniMoves.get(j),board));
                }
            }
        }
        return possibleMoves;
    }

    protected Vector<Move> getWhiteCaptureMoves(boolean canWhiteKingSideCastle, boolean canWhiteQueenSideCastle,boolean canBlackKingSideCastle,boolean canBlackQueenSideCastle) {
        Vector<Move> possibleMoves = new Vector<>();
        for(int i = 0; i < 128; i++) {
            // skip fake board
            if ((i & 0x0f) == 8) {
                i += 7;
                continue;
            }
            if(board[i] > 0) {
                Vector<Integer> miniMoves = getPossibleMoves(i,canWhiteKingSideCastle,canWhiteQueenSideCastle,canBlackKingSideCastle,canBlackQueenSideCastle);
                for(int j = 0; j < miniMoves.size(); j++) {
                    Move move = new Move(i,miniMoves.get(j),board);
                    if(board[move.getAfter()] != 0) {
                        possibleMoves.add(move);
                    }
                }
            }
        }
        return possibleMoves;
    }

    protected Vector<Move> getAllPossibleWhiteMoves(boolean canWhiteKingSideCastle, boolean canWhiteQueenSideCastle,boolean canBlackKingSideCastle,boolean canBlackQueenSideCastle) {
        Vector<Move> possibleMoves = new Vector<>();
        for(int i = 0; i < 128; i++) {
            // skip fake board
            if ((i & 0x0f) == 8) {
                i += 7;
                continue;
            }
            if(board[i] > 0) {
                Vector<Integer> miniMoves = getPossibleMoves(i,canWhiteKingSideCastle,canWhiteQueenSideCastle,canBlackKingSideCastle,canBlackQueenSideCastle);
                for(int j = 0; j < miniMoves.size(); j++) {
                    possibleMoves.add(new Move(i,miniMoves.get(j),board));
                }
            }
        }
        return possibleMoves;
    }

    protected void undoMove(short taken, Move move) {
//        boardPositions.put(boardModel.toString(),boardPositions.get(boardModel.toString())-1);
        int source = move.getBefore();
        int target = move.getAfter();
        board[source] = board[target];
        board[target] = taken;
        switch (board[source]) {
            case 6:
                if(source == 116) {
                    if(target == 118) {
                        board[119] = 2;
                        board[117] = 0;
                        //canWhiteKingSideCastle = false;
                        //canWhiteQueenSideCastle = false;
                    } else if(target == 114) {
                        board[112] = 2;
                        board[115] = 0;
                        //canWhiteKingSideCastle = false;
                        //canWhiteQueenSideCastle = false;
                    }
                }
                whiteKingPosition = source;
                break;
            case -6:

                if(source == 4) {
                    if(target == 2) {
                        board[0] = -2;
                        board[3] = 0;
                        //canBlackKingSideCastle = true;
                        //canBlackQueenSideCastle = true;
                    } else if(target == 6) {
                        board[7] = -2;
                        board[5] = 0;
                        //canBlackKingSideCastle = true;
                        //canBlackQueenSideCastle = true;
                    }
                }
                blackKingPosition = source;
                break;

            case 2:
                if(source == 119) {
                    //canWhiteKingSideCastle = true;
                    //boardModel.setCanWhiteKingSideCastle(false);
                } else if(source == 12) {
                    //canWhiteQueenSideCastle = true;
                    //boardModel.setCanWhiteQueenSideCastle(false);
                }
                break;
            case -2:
                if(source == 0) {
                    //boardModel.setCanBlackQueenSideCastle(false);
                    //canBlackQueenSideCastle = true;
                } else if(source == 7) {
                    //boardModel.setCanBlackKingSideCastle(false);
                    //canBlackKingSideCastle = true;

                }
                break;
            case 5:
                /*
                switch (target) {
                    case 0:
                        if(pos0) {
                            pos0 = false;
                            board[source] = 1;
                        }
                        break;
                    case 1: if(pos1) {
                        pos1 = false;
                        board[source] = 1;
                    }
                        break;
                    case 2: if(pos2) {
                        pos2 = false;
                        board[source] = 1;
                    }
                        break;
                    case 3:
                        if(pos3) {
                            pos3 = false;
                            board[source] = 1;
                        }
                        break;
                    case 4:
                        if(pos4) {
                            pos4 = false;
                            board[source] = 1;
                        }
                        break;
                    case 5:
                        if(pos5) {
                            pos5 = false;
                            board[source] = 1;
                        }
                        break;
                    case 6:
                        if(pos6) {
                            pos6 = false;
                            board[source] = 1;
                        }
                        break;
                    case 7:
                        if(pos7) {
                            pos7 = false;
                            board[source] = 1;
                        }
                        break;
                }
                break;
                */
            case -5:
                /*
                switch (target) {
                    case 112:
                        if(pos112) {
                            pos112 = false;
                            board[source] = -1;
                        }
                        break;
                    case 113: if(pos113) {
                        pos113 = false;
                        board[source] = -1;
                    }
                        break;
                    case 114: if(pos114) {
                        pos114 = false;
                        board[source] = -1;
                    }
                        break;
                    case 115:
                        if(pos115) {
                            pos115 = false;
                            board[source] = -1;
                        }
                        break;
                    case 116:
                        if(pos116) {
                            pos116 = false;
                            board[source] = -1;
                        }
                        break;
                    case 117:
                        if(pos117) {
                            pos117 = false;
                            board[source] = -1;
                        }
                        break;
                    case 118:
                        if(pos118) {
                            pos118 = false;
                            board[source] = -1;
                        }
                        break;
                    case 119:
                        if(pos119) {
                            pos119 = false;
                            board[source] = -1;
                        }
                        break;
                }
                break;
*/
        }

    }


    protected short simulateMove(Move move) {
        int source = move.getBefore();
        int target = move.getAfter();
        short taken = board[target];
        board[target] = board[source];
        board[source] = 0;

        switch (board[target]) {

            case 6:
                //boardModel.setWhiteKingPosition(target);
                //boardModel.setCanWhiteKingSideCastle(false);
                //boardModel.setCanWhiteQueenSideCastle(false);
                whiteKingPosition = target;
                CANWHITEKINGSIDECASTLE = false;
                CANWHITEQUEENSIDECASTLE = false;
                if(source == 116) {
                    if(target == 118) {
                        board[119] = 0;
                        board[117] = 2;
                    } else if(target == 114) {
                        board[112] = 0;
                        board[115] = 2;
                    }
                }
                break;
            case -6:
                //boardModel.setBlackKingPosition(target);
                //boardModel.setCanBlackKingSideCastle(false);
                //boardModel.setCanBlackQueenSideCastle(false);
                blackKingPosition = target;
                CANBLACKKINGSIDECASTLE = false;
                CANBLACKQUEENSIDECASTLE = false;
                if(source == 4) {
                    if(target == 2) {
                        board[0] = 0;
                        board[3] = -2;
                    } else if(target == 6) {
                        board[7] = 0;
                        board[5] = -2;
                    }
                }
                break;

            case 2:
                if(source == 119) {
                    CANWHITEKINGSIDECASTLE = false;
                    //boardModel.setCanWhiteKingSideCastle(false);
                } else if(source == 112) {
                    CANWHITEQUEENSIDECASTLE = false;
                    //boardModel.setCanWhiteQueenSideCastle(false);
                }
                break;
            case -2:
                if(source == 0) {
                    //boardModel.setCanBlackQueenSideCastle(false);
                    CANBLACKQUEENSIDECASTLE = false;
                } else if(source == 7) {
                    //boardModel.setCanBlackKingSideCastle(false);
                    CANBLACKKINGSIDECASTLE = false;

                }
                break;

            case 1:
                /*
                if((target&0xf0)==0) {
                    board[target] = 5;
                    switch (target) {
                        case 0: pos0 = true; break;
                        case 1: pos1 = true; break;
                        case 2: pos2 = true; break;
                        case 3: pos3 = true; break;
                        case 4: pos4 = true; break;
                        case 5: pos5 = true; break;
                        case 6: pos6 = true; break;
                        case 7: pos7 = true; break;

                    }
                }*/
                break;
            case -1:
                /*
                if(((target+16)&0x88)!=0) {
                    board[target] = -5;
                    switch (target) {
                        case 112: pos112 = true; break;
                        case 113: pos113 = true; break;
                        case 114: pos114 = true; break;
                        case 115: pos115 = true; break;
                        case 116: pos116 = true; break;
                        case 117: pos117 = true; break;
                        case 118: pos118 = true; break;
                        case 119: pos119 = true; break;

                    }
                    break;
                }*/

        }
        //boardModel.addBoardPosition(boardModel.toString());
        return taken;
    }

    //////////////////////////////////////////////////////////////////
    /*
    public Vector<Integer> getAllPossibleWhiteMoves(boolean canWhiteKingSideCastle, boolean canWhiteQueenSideCastle,boolean canBlackKingSideCastle,boolean canBlackQueenSideCastle) {
        Vector<Integer> possible = new Vector<>();
        for(int i = 0; i < 128; i++) {
            // skip fake board
            if ((i & 0x0f) == 8) {
                i += 7;
                continue;
            }
            if(board[i] > 0) {
                Vector<Integer> singlePossible = getPossibleMoves(i, canWhiteKingSideCastle,  canWhiteQueenSideCastle, canBlackKingSideCastle, canBlackQueenSideCastle);
                for(int j = 0; j < singlePossible.size(); j++) {
                    possible.add(singlePossible.get((j)));
                }
            }
        }
        return possible;
    }


    public Vector<Integer> getAllPossibleBlackMoves(boolean canWhiteKingSideCastle, boolean canWhiteQueenSideCastle,boolean canBlackKingSideCastle,boolean canBlackQueenSideCastle) {
        Vector<Integer> possible = new Vector<>();
        for(int i = 0; i < 128; i++) {
            // skip fake board
            if ((i & 0x0f) == 8) {
                i += 7;
                continue;
            }
            if(board[i] < 0) {
                Vector<Integer> singlePossible = getPossibleMoves(i, canWhiteKingSideCastle,  canWhiteQueenSideCastle, canBlackKingSideCastle, canBlackQueenSideCastle);
                for(int j = 0; j < singlePossible.size(); j++) {
                    possible.add(singlePossible.get((j)));
                }
            }
        }
        return possible;
    }*/

    public Vector<Integer> getPossibleMoves(int position,boolean canWhiteKingSideCastle, boolean canWhiteQueenSideCastle,boolean canBlackKingSideCastle,boolean canBlackQueenSideCastle) {
        Vector<Integer> possibleMoves = new Vector<>();

        switch (board[position]) {
                /*  White Pieces  */
            // Pawn
            case 1:
                // Normal
                if(((position-16) & 0x88)==0) {
                    if(board[position-16]==0) {
                        short taken = simulateMove(position,position-16);
                        if(!isWhiteKingInCheck()) {
                            possibleMoves.add(position - 16);
                        }
                        undoMove(taken,position,position-16);

                        if ((position & 0x60) == 0x60 && board[position-32]==0) {
                            taken = simulateMove(position,position-32);
                            if(!isWhiteKingInCheck()) {
                                possibleMoves.add(position - 32);
                            }
                            undoMove(taken,position,position+-32);

                        }
                    }

                }
                // Eating
                if(((position-15) & 0x88)==0) {
                    if (board[position - 15] < 0) {
                        short taken = simulateMove(position,position-15);
                        if(!isWhiteKingInCheck()) {
                            possibleMoves.add(position - 15);
                        }
                        undoMove(taken,position,position-15);

                    }
                }
                if(((position-17) & 0x88)==0) {
                    if (board[position - 17] < 0) {
                        short taken = simulateMove(position,position-17);
                        if(!isWhiteKingInCheck()) {
                            possibleMoves.add(position - 17);
                        }
                        undoMove(taken,position,position-17);

                    }
                }

                break;

            // Rook
            case 2:
                for(int i = 0; i < 4; i++) {
                    int index = position+rookDeltaValues[i];
                    while((index&0x88) == 0) {
                        if(board[index] == 0) {
                            short taken = simulateMove(position,index);
                            if(!isWhiteKingInCheck()) {
                                possibleMoves.add(index);
                            }
                            undoMove(taken,position,index);

                            index += rookDeltaValues[i];
                        } else if(board[index] > 0){
                            break;
                        } else {
                            short taken = simulateMove(position,index);
                            if(!isWhiteKingInCheck()) {
                                possibleMoves.add(index);
                            }
                            undoMove(taken,position,index);

                            break;
                        }

                    }
                }
                break;

            // Knight
            case 3:
                for(int i = 0; i < 8; i++) {
                    if(((position+knightDeltaValues[i])&0x88) == 0 &&(board[position+knightDeltaValues[i]]) <= 0) {
                        short taken = simulateMove(position,position+knightDeltaValues[i]);
                        if(!isWhiteKingInCheck()) {
                            possibleMoves.add(position + knightDeltaValues[i]);
                        }
                        undoMove(taken,position,position+knightDeltaValues[i]);

                    }
                }
                break;

            // Bishop
            case 4:
                for(int i = 0; i < 4; i++) {
                    int index = position+bishopDeltaValues[i];
                    while((index&0x88) == 0) {
                        if(board[index] == 0) {
                            short taken = simulateMove(position,index);
                            if(!isWhiteKingInCheck()) {
                                possibleMoves.add(index);
                            }
                            undoMove(taken,position,index);

                            index += bishopDeltaValues[i];
                        } else if(board[index] > 0){
                            break;
                        } else {
                            short taken = simulateMove(position,index);
                            if(!isWhiteKingInCheck()) {
                                possibleMoves.add(index);
                            }
                            undoMove(taken,position,index);

                            break;
                        }

                    }
                }
                break;

            // Queen
            case 5:
                for(int i = 0; i < 8; i++) {
                    int index = position+queenDeltaValues[i];
                    while((index&0x88) == 0) {
                        if(board[index] == 0) {
                            short taken = simulateMove(position,index);
                            if(!isWhiteKingInCheck()) {
                                possibleMoves.add(index);
                            }
                            undoMove(taken,position,index);

                            index += queenDeltaValues[i];
                        } else if(board[index] > 0){
                            break;
                        } else {
                            short taken = simulateMove(position,index);
                            if(!isWhiteKingInCheck()) {
                                possibleMoves.add(index);
                            }
                            undoMove(taken,position,index);

                            break;
                        }

                    }
                }
                break;

            // King
            case 6:
                for(int i = 0; i < 8; i++) {
                    if(((position+kingDeltaValues[i])&0x88) == 0 &&(board[position+kingDeltaValues[i]]) <= 0) {
                        short taken = simulateMove(position,position+kingDeltaValues[i]);
                        if(!isWhiteKingInCheck()) {
                            possibleMoves.add(position + kingDeltaValues[i]);
                        }
                        undoMove(taken,position,position+kingDeltaValues[i]);
                    }
                }
                // King side castle

                if(canWhiteKingSideCastle &&
                        board[117] == 0 && board[118] == 0 && board[119] == 2 &&
                        !isAttackedByBlack(116) && !isAttackedByBlack(117) && !isAttackedByBlack(118)) {
                    possibleMoves.add(118);
                }

                // Queen side castle
                if(canWhiteQueenSideCastle &&
                        board[115] == 0 && board[114] == 0 && board[113] == 0 && board[112] == 112 &&
                        !isAttackedByBlack(116) && !isAttackedByBlack(115) && !isAttackedByBlack(114)) {
                    possibleMoves.add(114);
                }
                break;

                /*  Black Pieces  */
            // Pawn
            case -1:
                // Normal
                if(((position+16)&0x88)==0) {
                    if(board[position+16]==0) {
                        short taken = simulateMove(position,position+16);
                        if(!isBlackKingInCheck()) {
                            possibleMoves.add(position + 16);
                        }
                        undoMove(taken,position,position+16);
                        if ((position & 0xf0) == 0x10 && board[position+32]==0) {
                            taken = simulateMove(position,position+32);
                            if(!isBlackKingInCheck()) {
                                possibleMoves.add(position + 32);
                            }
                            undoMove(taken,position,position+32);
                        }
                    }

                }
                // Eating
                if(((position+15) & 0x88)==0) {
                    if (board[position + 15] > 0) {
                        short taken = simulateMove(position,position+15);
                        if(!isBlackKingInCheck()) {
                            possibleMoves.add(position + 15);
                        }
                        undoMove(taken,position,position+15);
                    }
                }
                if(((position+17) & 0x88)==0) {
                    if (board[position + 17] > 0) {
                        short taken = simulateMove(position,position+17);
                        if(!isBlackKingInCheck()) {
                            possibleMoves.add(position + 17);
                        }
                        undoMove(taken,position,position+17);
                    }
                }
                break;

            // Rook
            case -2:
                for(int i = 0; i < 4; i++) {
                    int index = position+rookDeltaValues[i];
                    while((index&0x88) == 0) {
                        if(board[index] == 0) {
                            short taken = simulateMove(position,index);
                            if(!isBlackKingInCheck()) {
                                possibleMoves.add(index);
                            }
                            undoMove(taken,position,index);

                            index += rookDeltaValues[i];
                        } else if(board[index] < 0){
                            break;
                        } else {
                            short taken = simulateMove(position,index);
                            if(!isBlackKingInCheck()) {
                                possibleMoves.add(index);
                            }
                            undoMove(taken,position,index);
                            break;
                        }

                    }
                }
                break;

            // Knight
            case -3:
                for(int i = 0; i < 8; i++) {
                    if(((position+knightDeltaValues[i])&0x88) == 0 && (board[position+knightDeltaValues[i]]) >= 0) {
                        short taken = simulateMove(position,position+knightDeltaValues[i]);
                        if(!isBlackKingInCheck()) {
                            possibleMoves.add(position + knightDeltaValues[i]);
                        }
                        undoMove(taken,position,position+knightDeltaValues[i]);
                    }
                }
                break;

            // Bishop
            case -4:
                for(int i = 0; i < 4; i++) {
                    int index = position+bishopDeltaValues[i];
                    while((index&0x88) == 0) {
                        if(board[index] == 0) {
                            short taken = simulateMove(position,index);
                            if(!isBlackKingInCheck()) {
                                possibleMoves.add(index);
                            }
                            undoMove(taken,position,index);
                            index += bishopDeltaValues[i];
                        } else if(board[index] < 0){
                            break;
                        } else {
                            short taken = simulateMove(position,index);
                            if(!isBlackKingInCheck()) {
                                possibleMoves.add(index);
                            }
                            undoMove(taken,position,index);
                            break;
                        }

                    }
                }
                break;

            // Queen
            case -5:
                for(int i = 0; i < 8; i++) {
                    int index = position+queenDeltaValues[i];
                    while((index&0x88) == 0) {
                        if(board[index] == 0) {
                            short taken = simulateMove(position,index);
                            if(!isBlackKingInCheck()) {
                                possibleMoves.add(index);
                            }
                            undoMove(taken,position,index);
                            index += queenDeltaValues[i];
                        } else if(board[index] < 0){
                            break;
                        } else {
                            short taken = simulateMove(position,index);
                            if(!isBlackKingInCheck()) {
                                possibleMoves.add(index);
                            }
                            undoMove(taken,position,index);
                            break;
                        }

                    }
                }
                break;

            // King
            case -6:
                for(int i = 0; i < 8; i++) {
                    if(((position+kingDeltaValues[i])&0x88) == 0 &&(board[position+kingDeltaValues[i]]) >= 0) {
                        short taken = simulateMove(position,position+kingDeltaValues[i]);
                        if(!isBlackKingInCheck()) {
                            possibleMoves.add(position + kingDeltaValues[i]);
                        }
                        undoMove(taken,position,position+kingDeltaValues[i]);
                    }
                }
                // King side castle

                if(canBlackKingSideCastle &&
                        board[5] == 0 && board[6] == 0 && board[7] == -2 &&
                        !isAttackedByWhite(4) && !isAttackedByWhite(5) && !isAttackedByWhite(6)) {
                    possibleMoves.add(6);
                }
                // Queen side castle
                if(canBlackQueenSideCastle &&
                        board[1] == 0 && board[2] == 0 && board[3] ==0 && board[0] == -2 &&
                        !isAttackedByWhite(2) && !isAttackedByWhite(3) && !isAttackedByWhite(4)){
                    possibleMoves.add(2);
                }
                break;
        }

        return possibleMoves;
    }

    public boolean isBlackKingInCheck() {
        for(int i = 0; i < 128; i++) {
            // If we found a white piece
            if(board[i] > 0) {
                if (ATTACK_ARRAY[(blackKingPosition - i + 128)] != 0) {
                    // Find the delta
                    int delta = DELTA_ARRAY[blackKingPosition-i+128];
                    int index = i;
                    int attackIndex = ATTACK_ARRAY[(blackKingPosition - i + 128)];
                    switch (board[i]) {
                        case 1:
                            if(attackIndex != 4) {
                                continue;
                            }
                            break;
                        case 2:
                            if(attackIndex != 1 && attackIndex != 2) {
                                continue;
                            }
                            break;
                        case 3:
                            if(attackIndex != 6) {
                                continue;
                            }
                            break;
                        case 4:
                            if(attackIndex != 4 &&
                                    attackIndex != 3 &&
                                    attackIndex != 5) {
                                continue;
                            }
                            break;
                        case 5:
                            if(attackIndex != 1 &&
                                    attackIndex != 2 &&
                                    attackIndex != 3 &&
                                    attackIndex != 4 &&
                                    attackIndex != 5) {
                                continue;
                            }
                            break;
                        case 6:
                            if(attackIndex != 1 &&
                                    attackIndex != 3 &&
                                    attackIndex != 4) {
                                continue;
                            }
                            break;
                    }
                    if(delta>0) {
                        while(true) {
                            index += delta;
                            if(index > blackKingPosition) {
                                break;
                            }
                            if(index == blackKingPosition) {
                                return true;
                            }
                            if(board[index] != 0) {
                                break;
                            }
                        }
                    } else {
                        while(true) {
                            index += delta;
                            if(index < blackKingPosition) {
                                break;
                            }
                            if(index == blackKingPosition) {
                                return true;
                            }
                            if(board[index] != 0) {
                                break;
                            }
                        }

                    }
                }
            }

        }

        return false;
    }

    public boolean isWhiteKingInCheck() {
        for(int i = 0; i < 128; i++) {
            // skip fake board
            if((i & 0x0f) == 8) {
                i += 7;
                continue;
            }

            // If we found a black piece
            if(board[i] < 0) {
                if (ATTACK_ARRAY[(whiteKingPosition - i + 128)] != 0) {
                    // Find the delta
                    int delta = DELTA_ARRAY[whiteKingPosition-i+128];
                    int index = i;
                    int attackIndex = ATTACK_ARRAY[(whiteKingPosition - i + 128)];
                    switch (board[i]) {
                        case -1:
                            if(attackIndex != 3) {
                                continue;
                            }
                            break;
                        case -2:
                            if(attackIndex != 1 && attackIndex != 2) {
                                continue;
                            }
                            break;
                        case -3:
                            if(attackIndex != 6) {
                                continue;
                            }
                            break;
                        case -4:
                            if(attackIndex != 4 &&
                                    attackIndex != 3 &&
                                    attackIndex != 5) {
                                continue;
                            }
                            break;
                        case -5:
                            if(attackIndex != 1 &&
                                    attackIndex != 2 &&
                                    attackIndex != 3 &&
                                    attackIndex != 4 &&
                                    attackIndex != 5) {
                                continue;
                            }
                            break;
                        case -6:
                            if(attackIndex != 1 &&
                                    attackIndex != 3 &&
                                    attackIndex != 4) {
                                continue;
                            }
                            break;
                    }
                    if(delta>0) {
                        while(true) {
                            index += delta;
                            if(index > whiteKingPosition) {
                                break;
                            }
                            if(index == whiteKingPosition) {
                                return true;
                            }
                            if(board[index] != 0) {
                                break;
                            }
                        }
                    } else {
                        while(true) {
                            index += delta;
                            if(index < whiteKingPosition) {
                                break;
                            }
                            if(index == whiteKingPosition) {
                                return true;
                            }
                            if(board[index] != 0) {
                                break;
                            }
                        }

                    }
                }
            }
        }

        return false;
    }

    public short simulateMove(int source, int target) {
        short taken = board[target];
        board[target] = board[source];
        board[source] = 0;
        if(board[target] == -6) {
            blackKingPosition = target;
        } else if(board[target] == 6) {
            whiteKingPosition = target;
        }
        return taken;
    }

    public void undoMove(short taken, int source, int target) {
        board[source] = board[target];
        board[target] = taken;
        if(board[source] == -6) {
            blackKingPosition = source;
        } else if(board[source] == 6) {
            whiteKingPosition = source;
        }
    }

    public boolean isAttackedByBlack(int position) {
        for(int i = 0; i < 128; i++) {
            // skip fake board
            if((i & 0x0f) == 8) {
                i += 7;
                continue;
            }

            // If we found a black piece
            if(board[i] < 0) {
                if (ATTACK_ARRAY[(position - i + 128)] != 0) {
                    // Find the delta
                    int delta = DELTA_ARRAY[position-i+128];
                    int index = i;
                    int attackIndex = ATTACK_ARRAY[(position - i + 128)];
                    switch (board[i]) {
                        case -1:
                            if(attackIndex != 3) {
                                continue;
                            }
                            break;
                        case -2:
                            if(attackIndex != 1 && attackIndex != 2) {
                                continue;
                            }
                            break;
                        case -3:
                            if(attackIndex != 6) {
                                continue;
                            }
                            break;
                        case -4:
                            if(attackIndex != 4 &&
                                    attackIndex != 3 &&
                                    attackIndex != 5) {
                                continue;
                            }
                            break;
                        case -5:
                            if(attackIndex != 1 &&
                                    attackIndex != 2 &&
                                    attackIndex != 3 &&
                                    attackIndex != 4 &&
                                    attackIndex != 5) {
                                continue;
                            }
                            break;
                        case -6:
                            if(attackIndex != 1 &&
                                    attackIndex != 3 &&
                                    attackIndex != 4) {
                                continue;
                            }
                            break;
                    }
                    if(delta>0) {
                        while(true) {
                            index += delta;
                            if(index > position) {
                                break;
                            }
                            if(index == position) {
                                return true;
                            }
                            if(board[index] != 0) {
                                break;
                            }
                        }
                    } else {
                        while(true) {
                            index += delta;
                            if(index < position) {
                                break;
                            }
                            if(index == position) {
                                return true;
                            }
                            if(board[index] != 0) {
                                break;
                            }
                        }

                    }
                }
            }

        }

        return false;
    }

    public boolean isAttackedByWhite(int position) {
        for(int i = 0; i < 128; i++) {
            // If we found a white piece
            if(board[i] > 0) {
                if (ATTACK_ARRAY[(position - i + 128)] != 0) {
                    // Find the delta
                    int delta = DELTA_ARRAY[position-i+128];
                    int index = i;
                    int attackIndex = ATTACK_ARRAY[(position - i + 128)];
                    switch (board[i]) {
                        case 1:
                            if(attackIndex != 4) {
                                continue;
                            }
                            break;
                        case 2:
                            if(attackIndex != 1 && attackIndex != 2) {
                                continue;
                            }
                            break;
                        case 3:
                            if(attackIndex != 6) {
                                continue;
                            }
                            break;
                        case 4:
                            if(attackIndex != 4 &&
                                    attackIndex != 3 &&
                                    attackIndex != 5) {
                                continue;
                            }
                            break;
                        case 5:
                            if(attackIndex != 1 &&
                                    attackIndex != 2 &&
                                    attackIndex != 3 &&
                                    attackIndex != 4 &&
                                    attackIndex != 5) {
                                continue;
                            }
                            break;
                        case 6:
                            if(attackIndex != 1 &&
                                    attackIndex != 3 &&
                                    attackIndex != 4) {
                                continue;
                            }
                            break;
                    }
                    if(delta>0) {
                        while(true) {
                            index += delta;
                            if(index > position) {
                                break;
                            }
                            if(index == position) {
                                return true;
                            }
                            if(board[index] != 0) {
                                break;
                            }
                        }
                    } else {
                        while(true) {
                            index += delta;
                            if(index < position) {
                                break;
                            }
                            if(index == position) {
                                return true;
                            }
                            if(board[index] != 0) {
                                break;
                            }
                        }

                    }
                }
            }

        }

        return false;
    }

    protected Vector<Move> prioritize(Vector<Move> moves) {
        Vector<Move> toReturn = moves;
        Collections.sort(toReturn, new Comparator<Move>() {
            @Override
            public int compare(Move lhs, Move rhs) {
                return lhs.getRanking()-rhs.getRanking();
            }
        });

        return toReturn;
    }

    protected boolean isThreeFoldDraw() {
        for(String key : boardPositions.keySet()) {
            if(boardPositions.get(key) >= 2) {
                return true;
            }
        }
        return false;
    }


}
