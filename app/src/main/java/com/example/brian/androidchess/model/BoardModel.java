package com.example.brian.androidchess.model;

import com.example.brian.androidchess.controllers.states.StateEnum;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Brian on 16/12/26.
 */

public class BoardModel {
    private short[] knightDeltaValues = {31,-31,33,-33,18,-18,14,-14};
    private short[] kingDeltaValues = {-1,1,-16,16,17,15,-17,-15};
    private short[] rookDeltaValues = {-1,1,16,-16};
    private short[] queenDeltaValues = {-1,1,16,-16,15,-15,17,-17};
    private short[] bishopDeltaValues = {15,-15,17,-17};

    private boolean canEnpassant = false;
    private int enpassant1 = -1;
    private int enpassant2 = -1;
    private int enpassantPosition = -1;

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


    private int[] highlightBoard = new int[128];
    private int currentSelectedPosition = -1;
    private StateEnum stateEnum = StateEnum.NOTHINGPRESSEDSTATE;
    private Vector<Integer> currentPossibleMoves = new Vector<>();

    private int whiteKingPosition = 116;
    private int blackKingPosition = 4;


    public BoardModel() {
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
                }break;
        }

        return possibleMoves;
    }


    public short movePiece(int source, int target) {
        short taken = board[target];
        board[target] = board[source];
        board[source] = 0;

        if(board[target] == 6) {
            whiteKingPosition = target;
        } else if(board[target] == -6) {
            blackKingPosition = target;
        }

        return taken;
    }

    public short pawnMove(int source, int target) {


        short taken = board[target];
        board[target] = board[source];
        board[source] = 0;

        // Promotion stuff
        if((target&0xf0)==0 && board[target] == 1) {
            board[target] = 5;
            return taken;
        } else if(((target+16)&0x88)!=0 && board[target] == -1) {
            board[target] = -5;
            return taken;
        }

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
        }

        return taken;
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
}
