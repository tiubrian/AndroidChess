package com.example.brian.androidchess.model;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

import com.example.brian.androidchess.R;
import com.example.brian.androidchess.controllers.states.StateEnum;

import java.security.Policy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Brian on 16/12/26.
 */

public class BoardModel {
    Map<String,Integer> boardPositions = new HashMap<>();
    int numWhiteQueen = 1;
    int numWhiteRook = 2;
    int numWhiteKnight = 2;
    int numWhiteBishop = 2;

    int numBlackQueen = 1;
    int numBlackRook = 2;
    int numBlackKnight = 2;
    int numBlackBishop = 2;

    // Delta values
    private short[] knightDeltaValues = {31,-31,33,-33,18,-18,14,-14};
    private short[] kingDeltaValues = {-1,1,-16,16,17,15,-17,-15};
    private short[] rookDeltaValues = {-1,1,16,-16};
    private short[] queenDeltaValues = {-1,1,16,-16,15,-15,17,-17};
    private short[] bishopDeltaValues = {15,-15,17,-17};

    // en passant stuff
    private boolean canEnpassant = false;
    private int enpassant1 = -1;
    private int enpassant2 = -1;
    private int enpassantPosition = -1;

    // castling stuff
    boolean canWhiteKingSideCastle = true;
    boolean canWhiteQueenSideCastle = true;

    boolean canBlackKingSideCastle = true;
    boolean canBlackQueenSideCastle = true;

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

    public static final int ATTACK_NONE = 0;
    public static final int ATTACK_KQR = 1;
    public static final int ATTACK_QR = 2;
    public static final int ATTACK_KQBwP = 4; // i switcted them
    public static final int ATTACK_KQBbP = 3;
    public static final int ATTACK_QB = 5;
    public static final int ATTACK_N = 6;

    public static final int[] ATTACK_ARRAY = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 5, 0, 0, 5, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 2, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 2, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 2, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 6, 2, 6, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 4, 1, 4, 6, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 1, 0, 1, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 6, 3, 1, 3, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 6, 2, 6, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 2, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 2, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 2, 0, 0, 0, 0, 5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 5, 0, 0, 5, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    public static final int[] DELTA_ARRAY =         {0, 0, 0, 0, 0, 0, 0, 0, 0, -17, 0, 0, 0, 0, 0, 0, -16, 0, 0, 0, 0, 0, 0, -15, 0, 0, -17, 0, 0, 0, 0, 0, -16, 0, 0, 0, 0, 0, -15, 0, 0, 0, 0, -17, 0, 0, 0, 0, -16, 0, 0, 0, 0, -15, 0, 0, 0, 0, 0, 0, -17, 0, 0, 0, -16, 0, 0, 0, -15, 0, 0, 0, 0, 0, 0, 0, 0, -17, 0, 0, -16, 0, 0, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -17, -33, -16, -31, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -18, -17, -16, -15, -14, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 14, 15, 16, 17, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 31, 16, 33, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 16, 0, 0, 17, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 16, 0, 0, 0, 17, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 16, 0, 0, 0, 0, 17, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 17, 0, 0, 15, 0, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 0, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static final int[] UPDATED_DELTA_ARRAY = {0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 0, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 0, 15, 0, 0, 17, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 17, 0, 0, 0, 0, 16, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0, 17, 0, 0, 0, 16, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 17, 0, 0, 16, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 33, 16, 31, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 17, 16, 15, 14, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, -14, -15, -16, -17, -18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -15, -31, -16, -33, -17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -15, 0, 0, -16, 0, 0, -17, 0, 0, 0, 0, 0, 0, 0, 0, -15, 0, 0, 0, -16, 0, 0, 0, -17, 0, 0, 0, 0, 0, 0, -15, 0, 0, 0, 0, -16, 0, 0, 0, 0, -17, 0, 0, 0, 0, -15, 0, 0, 0, 0, 0, -16, 0, 0, 0, 0, 0, -17, 0, 0, -15, 0, 0, 0, 0, 0, 0, -16, 0, 0, 0, 0, 0, 0, -17, 0, 0, 0, 0, 0, 0, 0, 0, 0};

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

    private int[] highlightBoard = new int[128];
    private int currentSelectedPosition = -1;
    private StateEnum stateEnum = StateEnum.NOTHINGPRESSEDSTATE;
    private Vector<Integer> currentPossibleMoves = new Vector<>();

    private int whiteKingPosition = 116;
    private int blackKingPosition = 4;

    private Context context;

    BoardModel(Context context) {
        this.context = context;
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

    public Vector<Integer> getAllPossibleWhiteMoves() {
        Vector<Integer> possible = new Vector<>();
        for(int i = 0; i < 128; i++) {
            // skip fake board
            if ((i & 0x0f) == 8) {
                i += 7;
                continue;
            }
            if(board[i] > 0) {
                Vector<Integer> singlePossible = getPossibleMoves(i);
                for(int j = 0; j < singlePossible.size(); j++) {
                    possible.add(singlePossible.get((j)));
                }
            }
        }
        return possible;
    }


    public Vector<Integer> getAllPossibleBlackMoves() {
        Vector<Integer> possible = new Vector<>();
        for(int i = 0; i < 128; i++) {
            // skip fake board
            if ((i & 0x0f) == 8) {
                i += 7;
                continue;
            }
            if(board[i] < 0) {
                Vector<Integer> singlePossible = getPossibleMoves(i);
                for(int j = 0; j < singlePossible.size(); j++) {
                    possible.add(singlePossible.get((j)));
                }
            }
        }
        return possible;
    }

    public Vector<Integer> getPossibleMoves(int position) {
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
                        board[117] == 0 && board[118] == 0 &&
                        !isAttackedByBlack(116) && !isAttackedByBlack(117) && !isAttackedByBlack(118)) {
                   possibleMoves.add(118);
                }

                // Queen side castle
                if(canWhiteQueenSideCastle &&
                        board[115] == 0 && board[114] == 0 && board[113] == 0 &&
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
                        board[5] == 0 && board[6] == 0 &&
                        !isAttackedByWhite(4) && !isAttackedByWhite(5) && !isAttackedByWhite(6)) {
                    possibleMoves.add(6);
                }
                // Queen side castle
                if(canBlackQueenSideCastle &&
                        board[1] == 0 && board[2] == 0 && board[3] ==0 &&
                        !isAttackedByWhite(2) && !isAttackedByWhite(3) && !isAttackedByWhite(4)){
                    possibleMoves.add(2);
                }
                break;
        }

        return possibleMoves;
    }

    public void addBoardPosition(String boardPosition) {
        if(!boardPositions.containsKey(boardPosition)) {
            boardPositions.put(boardPosition,1);
        } else {
            boardPositions.put(boardPosition,boardPositions.get(boardPosition)+1);
        }
    }

    private void playSound(int source, int target,short taken) {
        if(isWhiteKingInCheck() || isBlackKingInCheck()) {
            if(getAllPossibleWhiteMoves().size() == 0 ||
                    getAllPossibleBlackMoves().size() == 0) {
                ((Activity)(context)).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MediaPlayer mp = MediaPlayer.create(context, R.raw.check2);
                        mp.start();
                    }
                });
            } else {
                ((Activity)(context)).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MediaPlayer mp = MediaPlayer.create(context, R.raw.check);
                        mp.start();
                    }
                });
            }
        } else if(taken != 0) {
            ((Activity)(context)).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MediaPlayer mp = MediaPlayer.create(context, R.raw.capture);
                    mp.start();
                }
            });
        } else {
            ((Activity)(context)).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MediaPlayer mp = MediaPlayer.create(context, R.raw.move);
                    mp.start();
                }
            });
        }
    }

    public short movePiece(int source, int target) {

        switch (board[target]) {
            case 2: numWhiteRook--; break;
            case 3: numWhiteKnight--; break;
            case 4: numWhiteBishop--; break;
            case 5: numWhiteQueen--; break;

            case -2: numBlackRook--; break;
            case -3: numBlackKnight--; break;
            case -4: numBlackBishop--; break;
            case -5: numBlackQueen--; break;
        }

        short taken = board[target];
        board[target] = board[source];
        board[source] = 0;

        switch (board[target]) {
            case 6:
                whiteKingPosition = target;
                canWhiteKingSideCastle = false;
                canWhiteQueenSideCastle = false;
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
                blackKingPosition = target;
                canBlackKingSideCastle = false;
                canBlackQueenSideCastle = false;
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
                    canWhiteKingSideCastle = false;
                } else if(source == 112) {
                    canWhiteQueenSideCastle = false;
                }
                break;
            case -2:
                if(source == 0) {
                    this.setCanBlackQueenSideCastle(false);
                } else if(source == 7) {
                    this.setCanBlackKingSideCastle(false);

                }
                break;
        }
        //addBoardPosition(this.toString());
        playSound(source,target,taken);

        return taken;
    }

    public short pawnMove(int source, int target) {

        switch (board[target]) {
            case 2: numWhiteRook--; break;
            case 3: numWhiteKnight--; break;
            case 4: numWhiteBishop--; break;
            case 5: numWhiteQueen--; break;

            case -2: numBlackRook--; break;
            case -3: numBlackKnight--; break;
            case -4: numBlackBishop--; break;
            case -5: numBlackQueen--; break;
        }

        short taken = board[target];
        board[target] = board[source];
        board[source] = 0;
        playSound(source,target,taken);

        // Promotion stuff
        if((target&0xf0)==0 && board[target] == 1) {
            board[target] = 5;
            return taken;
        } else if(((target+16)&0x88)!=0 && board[target] == -1) {
            board[target] = -5;
            return taken;
        }
        /*

        // En passant stuff
        if((target-source==32) && target == 1) {
            if(board[target-1] == -1) {
                canEnpassant = true;
                enpassant1 = target-1;
                enpassantPosition = target+16;
            }
            if(board[target+1] == -1) {
                canEnpassant = true;
                enpassant2 = target+1;
                enpassantPosition = target+16;
            }
        }*/
        //addBoardPosition(this.toString());

        return taken;
    }
    public int unconvert(int bigPosition) {
        for(int i = 0; i < 64; i++) {
            if(bigPosition == converter[i]) {
                return i;
            }
        }
        return -1;
    }

    public void resetHighlightBoard() {
        for(int i = 0; i < 128; i++) {
            highlightBoard[i] = 0;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 128; i++) {
            // skip fake board
            if ((i & 0x0f) == 8) {
                i += 7;
                continue;
            }
            sb.append(board[i]);
        }
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

    public Vector<Integer> getCurrentPossibleMoves() {
        return currentPossibleMoves;
    }

    public void setCurrentPossibleMoves(Vector<Integer> currentPossibleMoves) {
        this.currentPossibleMoves = currentPossibleMoves;
    }

    public short[] getKnightDeltaValues() {
        return knightDeltaValues;
    }

    public void setKnightDeltaValues(short[] knightDeltaValues) {
        this.knightDeltaValues = knightDeltaValues;
    }

    public short[] getKingDeltaValues() {
        return kingDeltaValues;
    }

    public void setKingDeltaValues(short[] kingDeltaValues) {
        this.kingDeltaValues = kingDeltaValues;
    }

    public short[] getRookDeltaValues() {
        return rookDeltaValues;
    }

    public void setRookDeltaValues(short[] rookDeltaValues) {
        this.rookDeltaValues = rookDeltaValues;
    }

    public short[] getQueenDeltaValues() {
        return queenDeltaValues;
    }

    public void setQueenDeltaValues(short[] queenDeltaValues) {
        this.queenDeltaValues = queenDeltaValues;
    }

    public short[] getBishopDeltaValues() {
        return bishopDeltaValues;
    }

    public void setBishopDeltaValues(short[] bishopDeltaValues) {
        this.bishopDeltaValues = bishopDeltaValues;
    }

    public boolean isCanEnpassant() {
        return canEnpassant;
    }

    public void setCanEnpassant(boolean canEnpassant) {
        this.canEnpassant = canEnpassant;
    }

    public int getEnpassant1() {
        return enpassant1;
    }

    public void setEnpassant1(int enpassant1) {
        this.enpassant1 = enpassant1;
    }

    public int getEnpassant2() {
        return enpassant2;
    }

    public void setEnpassant2(int enpassant2) {
        this.enpassant2 = enpassant2;
    }

    public int getEnpassantPosition() {
        return enpassantPosition;
    }

    public void setEnpassantPosition(int enpassantPosition) {
        this.enpassantPosition = enpassantPosition;
    }

    public static int getAttackNone() {
        return ATTACK_NONE;
    }

    public static int getAttackKqr() {
        return ATTACK_KQR;
    }

    public static int getAttackQr() {
        return ATTACK_QR;
    }

    public static int getATTACK_KQBwP() {
        return ATTACK_KQBwP;
    }

    public static int getATTACK_KQBbP() {
        return ATTACK_KQBbP;
    }

    public static int getAttackQb() {
        return ATTACK_QB;
    }

    public static int getAttackN() {
        return ATTACK_N;
    }

    public static int[] getAttackArray() {
        return ATTACK_ARRAY;
    }

    public static int[] getDeltaArray() {
        return DELTA_ARRAY;
    }

    public static int[] getUpdatedDeltaArray() {
        return UPDATED_DELTA_ARRAY;
    }

    public int getWhiteKingPosition() {
        return whiteKingPosition;
    }

    public void setWhiteKingPosition(int whiteKingPosition) {
        this.whiteKingPosition = whiteKingPosition;
    }

    public int getBlackKingPosition() {
        return blackKingPosition;
    }

    public void setBlackKingPosition(int blackKingPosition) {
        this.blackKingPosition = blackKingPosition;
    }

    public boolean isCanWhiteKingSideCastle() {
        return canWhiteKingSideCastle;
    }

    public void setCanWhiteKingSideCastle(boolean canWhiteKingSideCastle) {
        this.canWhiteKingSideCastle = canWhiteKingSideCastle;
    }

    public boolean isCanWhiteQueenSideCastle() {
        return canWhiteQueenSideCastle;
    }

    public void setCanWhiteQueenSideCastle(boolean canWhiteQueenSideCastle) {
        this.canWhiteQueenSideCastle = canWhiteQueenSideCastle;
    }

    public boolean isCanBlackKingSideCastle() {
        return canBlackKingSideCastle;
    }

    public void setCanBlackKingSideCastle(boolean canBlackKingSideCastle) {
        this.canBlackKingSideCastle = canBlackKingSideCastle;
    }

    public boolean isCanBlackQueenSideCastle() {
        return canBlackQueenSideCastle;
    }

    public void setCanBlackQueenSideCastle(boolean canBlackQueenSideCastle) {
        this.canBlackQueenSideCastle = canBlackQueenSideCastle;
    }

    public short[] getConverter() {
        return converter;
    }

    public void setConverter(short[] converter) {
        this.converter = converter;
    }

    public Map<String, Integer> getBoardPositions() {
        return boardPositions;
    }

    public void setBoardPositions(Map<String, Integer> boardPositions) {
        this.boardPositions = boardPositions;
    }

    public int getNumWhiteQueen() {
        return numWhiteQueen;
    }

    public void setNumWhiteQueen(int numWhiteQueen) {
        this.numWhiteQueen = numWhiteQueen;
    }

    public int getNumWhiteRook() {
        return numWhiteRook;
    }

    public void setNumWhiteRook(int numWhiteRook) {
        this.numWhiteRook = numWhiteRook;
    }

    public int getNumWhiteKnight() {
        return numWhiteKnight;
    }

    public void setNumWhiteKnight(int numWhiteKnight) {
        this.numWhiteKnight = numWhiteKnight;
    }

    public int getNumWhiteBishop() {
        return numWhiteBishop;
    }

    public void setNumWhiteBishop(int numWhiteBishop) {
        this.numWhiteBishop = numWhiteBishop;
    }

    public int getNumBlackQueen() {
        return numBlackQueen;
    }

    public void setNumBlackQueen(int numBlackQueen) {
        this.numBlackQueen = numBlackQueen;
    }

    public int getNumBlackRook() {
        return numBlackRook;
    }

    public void setNumBlackRook(int numBlackRook) {
        this.numBlackRook = numBlackRook;
    }

    public int getNumBlackKnight() {
        return numBlackKnight;
    }

    public void setNumBlackKnight(int numBlackKnight) {
        this.numBlackKnight = numBlackKnight;
    }

    public int getNumBlackBishop() {
        return numBlackBishop;
    }

    public void setNumBlackBishop(int numBlackBishop) {
        this.numBlackBishop = numBlackBishop;
    }
}
