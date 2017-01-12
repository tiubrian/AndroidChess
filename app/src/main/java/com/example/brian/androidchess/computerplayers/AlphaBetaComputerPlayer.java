package com.example.brian.androidchess.computerplayers;

import com.example.brian.androidchess.model.BoardModel;

import java.util.Vector;

/**
 * Created by Brian on 16/12/28.
 */

public class AlphaBetaComputerPlayer extends ComputerPlayer {

    int depth = 3;
    public AlphaBetaComputerPlayer(BoardModel boardModel, char color) {
        super(boardModel, color);
    }

    @Override
    public Move getMove() {
        whiteKingPosition = boardModel.getWhiteKingPosition();
        blackKingPosition = boardModel.getBlackKingPosition();
/*
        canWhiteKingSideCastle = boardModel.isCanWhiteKingSideCastle();
        canWhiteQueenSideCastle = boardModel.isCanWhiteQueenSideCastle();

        canBlackKingSideCastle = boardModel.isCanBlackKingSideCastle();
        canBlackQueenSideCastle = boardModel.isCanBlackQueenSideCastle();
        */

        return alphaBetaRoot();
    }

    private Move alphaBetaRoot() {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        Move bestMove = null;

        boolean canWhiteKingSideCastle = boardModel.isCanWhiteKingSideCastle();
        boolean canWhiteQueenSideCastle = boardModel.isCanWhiteQueenSideCastle();

        boolean canBlackKingSideCastle = boardModel.isCanBlackKingSideCastle();
        boolean canBlackQueenSideCastle = boardModel.isCanBlackQueenSideCastle();



        if(color == 'w') {
            int score = Integer.MIN_VALUE;
            Vector<Move> possibleMoves = prioritize(getAllPossibleWhiteMoves(canWhiteKingSideCastle,canWhiteQueenSideCastle,canBlackKingSideCastle,canBlackQueenSideCastle));
            for(int i = 0; i < possibleMoves.size(); i++) {
                CANWHITEKINGSIDECASTLE = canWhiteKingSideCastle;
                CANWHITEQUEENSIDECASTLE = canWhiteQueenSideCastle;
                CANBLACKKINGSIDECASTLE = canBlackKingSideCastle;
                CANBLACKQUEENSIDECASTLE = canBlackQueenSideCastle;
                Move move = possibleMoves.get(i);

                short taken = simulateMove(move);
                int value = alphaBeta('b', alpha,beta,depth,CANWHITEKINGSIDECASTLE,CANWHITEQUEENSIDECASTLE,CANBLACKKINGSIDECASTLE,CANWHITEQUEENSIDECASTLE);
                if(value > score) {
                    score = value;
                    bestMove = move;
                }
                undoMove(taken,move);
            }

        } else {
            double score = Integer.MAX_VALUE;
            Vector<Move> possibleMoves = prioritize(getAllPossibleBlackMoves(canWhiteKingSideCastle,canWhiteQueenSideCastle,canBlackKingSideCastle,canBlackQueenSideCastle));
            for(int i = 0; i < possibleMoves.size(); i++) {
                CANWHITEKINGSIDECASTLE = canWhiteKingSideCastle;
                CANWHITEQUEENSIDECASTLE = canWhiteQueenSideCastle;
                CANBLACKKINGSIDECASTLE = canBlackKingSideCastle;
                CANBLACKQUEENSIDECASTLE = canBlackQueenSideCastle;
                Move move = possibleMoves.get(i);

                short taken = simulateMove(move);
                int value = alphaBeta('w', alpha,beta,depth,CANWHITEKINGSIDECASTLE,CANWHITEQUEENSIDECASTLE,CANBLACKKINGSIDECASTLE,CANWHITEQUEENSIDECASTLE);
                if(value < score) {
                    score = value;
                    bestMove = move;
                }
                undoMove(taken,move);
            }

        }
        return bestMove;
    }

    private int alphaBeta(char turn, int alpha, int beta, int depth,boolean canWhiteKingSideCastle,boolean canWhiteQueenSideCastle,boolean canBlackKingSideCastle,boolean canBlackQueenSideCastle) {
        depth--;
        if(depth == 0) {
            //return evaluateBoard();/*
            if(turn == 'w') {
                return quiesent('w',alpha, beta,canWhiteQueenSideCastle,canBlackKingSideCastle,canBlackQueenSideCastle,canBlackKingSideCastle);
            } else {
                return quiesent('b',alpha, beta,canWhiteQueenSideCastle,canBlackKingSideCastle,canBlackQueenSideCastle,canBlackKingSideCastle);

            }
        }
        if(turn == 'w') {
            Vector<Move> possibleMoves = prioritize(getAllPossibleWhiteMoves(canWhiteKingSideCastle,canWhiteQueenSideCastle,canBlackKingSideCastle,canBlackQueenSideCastle));
            if(possibleMoves.size() == 0) {
                if(!isWhiteKingInCheck()) {
                    return 0;
                }
                return -100000;
            }
            for(int i = 0; i < possibleMoves.size(); i++) {
                CANWHITEKINGSIDECASTLE = canWhiteKingSideCastle;
                CANWHITEQUEENSIDECASTLE = canWhiteQueenSideCastle;
                CANBLACKKINGSIDECASTLE = canBlackKingSideCastle;
                CANBLACKQUEENSIDECASTLE = canBlackQueenSideCastle;

                Move move = possibleMoves.get(i);
                short taken = simulateMove(move);
                int score = alphaBeta('b', alpha,beta,depth,CANWHITEKINGSIDECASTLE,CANWHITEQUEENSIDECASTLE,CANBLACKKINGSIDECASTLE,CANWHITEQUEENSIDECASTLE);
                undoMove(taken,move);

                if(score > alpha) {
                    alpha = score;
                }
                if(alpha >= beta) {
                    return alpha;
                }
            }
            return alpha;
        } else {
            Vector<Move> possibleMoves = prioritize(getAllPossibleBlackMoves(canWhiteKingSideCastle,canWhiteQueenSideCastle,canBlackKingSideCastle,canBlackQueenSideCastle));
            if(possibleMoves.size() == 0) {
                if(!isBlackKingInCheck()) {
                    return 0;
                }
                return 100000;
            }
            for(int i = 0; i < possibleMoves.size(); i++) {
                CANWHITEKINGSIDECASTLE = canWhiteKingSideCastle;
                CANWHITEQUEENSIDECASTLE = canWhiteQueenSideCastle;
                CANBLACKKINGSIDECASTLE = canBlackKingSideCastle;
                CANBLACKQUEENSIDECASTLE = canBlackQueenSideCastle;

                Move move = possibleMoves.get(i);
                short taken = simulateMove(move);

                int score = alphaBeta('w', alpha,beta,depth,CANWHITEKINGSIDECASTLE,CANWHITEQUEENSIDECASTLE,CANBLACKKINGSIDECASTLE,CANWHITEQUEENSIDECASTLE);
                undoMove(taken,move);

                if(score < beta) {
                    beta = score;
                }
                if(alpha >= beta) {
                    return beta;
                }
            }
            return beta;
        }
    }



    private int evaluateBoard() {
       /* if(isThreeFoldDraw()) {
            return 0;
        }*/




        int score = 0;
        for (int i = 0; i < 128; i++) {
            // skip fake board
            if ((i & 0x0f) == 8) {
                i += 7;
                continue;
            }
            switch (board[i]) {
                case 1:
                    score += 100;
                    if(boardModel.getNumBlackQueen() == 0 &&
                            (boardModel.getNumBlackBishop() + boardModel.getNumBlackKnight()) <= 2 &&
                            boardModel.getNumBlackRook() < 2) {
                        score += pawnEvalTableWhiteEnd[i];
                    } else {
                        score += pawnEvalTableWhite[i];
                    }
                    break;
                case 2:
                    score += 525;
                    score += rookEvalTableWhite[i];
                    break;
                case 3:
                    score += 350;
                    score += knightEvalTableWhite[i];
                    break;
                case 4:
                    score += 350;
                    score += bishopEvalTableWhite[i];
                    break;
                case 5:
                    score += 1000;
                    score += queenEvalTableWhite[i];
                    break;
                case 6:
                    score += 100000;
                    if(boardModel.getNumBlackQueen() == 0 &&
                            (boardModel.getNumBlackBishop() + boardModel.getNumBlackKnight()) <= 2 &&
                            boardModel.getNumBlackRook() < 2) {
                        score += kingEvalTableEndWhite[i];
                    } else {
                        score += kingEvalTableBeginWhite[i];
                    }
                    break;

                case -1:
                    score -= 100;

                    if(boardModel.getNumWhiteQueen() == 0 &&
                            (boardModel.getNumWhiteBishop() + boardModel.getNumWhiteKnight()) <= 2 &&
                            boardModel.getNumWhiteRook() < 2) {
                        score -= pawnEvalTableBlackEnd[i];
                    } else {
                        score -= pawnEvalTableBlack[i];
                    }
                    break;
                case -2:
                    score -= 525;
                    score -= rookEvalTableBlack[i];
                    break;
                case -3:
                    score -= 350;
                    score -= knightEvalTableBlack[i];
                    break;
                case -4:
                    score -= 350;
                    score -= bishopEvalTableBlack[i];

                    break;
                case -5:
                    score -= 1000;
                    score -= queenEvalTableBlack[i];
                    break;
                case -6:
                    score -= 100000;
                    if(boardModel.getNumWhiteQueen() == 0 &&
                            (boardModel.getNumWhiteBishop() + boardModel.getNumWhiteKnight()) <= 2 &&
                            boardModel.getNumWhiteRook() < 2) {
                        depth = 4;

                        score -= kingEvalTableEndBlack[i];
                    } else {
                        score -= kingEvalTableBeginBlack[i];
                    }
                    break;
            }
        }
        return score;
    }




    private int quiesent(char turn, int alpha, int beta,boolean canWhiteKingSideCastle,boolean canWhiteQueenSideCastle,boolean canBlackKingSideCastle,boolean canBlackQueenSideCastle) {



        if(turn == 'w') {

            int stand_pat = evaluateBoard();
            if(stand_pat > alpha) {
                alpha = stand_pat;
            }
            if(alpha >= beta) {
                return alpha;
            }



            Vector<Move> possibleMoves = prioritize(getWhiteCaptureMoves(canWhiteKingSideCastle,canWhiteQueenSideCastle,canBlackKingSideCastle,canBlackQueenSideCastle));
            /*if(possibleMoves.size() == 0) {
                if(getAllPossibleWhiteMoves(false,false,false,false).size() == 0) {
                    if(isWhiteKingInCheck()) {
                        return -10000;
                    }
                    return 0;
                }
            }*/
            for(int i = 0; i < possibleMoves.size(); i++) {
                Move move = possibleMoves.get(i);
                short taken = simulateMove(move);
                int score = quiesent('b', alpha,beta,CANWHITEKINGSIDECASTLE,CANWHITEQUEENSIDECASTLE,CANBLACKKINGSIDECASTLE,CANWHITEQUEENSIDECASTLE);
                undoMove(taken,move);

                if(score > alpha) {
                    alpha = score;
                }
                if(alpha >= beta) {
                    return alpha;
                }
            }
            return alpha;
        } else {

            int stand_pat = evaluateBoard();
            if(stand_pat < beta) {
                beta = stand_pat;
            }
            if(alpha >= beta) {
                return beta;
            }

            Vector<Move> possibleMoves = prioritize(getBlackCaptureMoves(canWhiteKingSideCastle,canWhiteQueenSideCastle,canBlackKingSideCastle,canBlackQueenSideCastle));
            /*if(possibleMoves.size()== 0) {
                if(getAllPossibleBlackMoves(false,false,false,false).size() == 0) {
                    if(isBlackKingInCheck()) {
                        return 10000;
                    }
                    return 0;
                }
            }*/
            for(int i = 0; i < possibleMoves.size(); i++) {

                Move move = possibleMoves.get(i);
                short taken = simulateMove(move);

                int score = quiesent('w', alpha,beta,CANWHITEKINGSIDECASTLE,CANWHITEQUEENSIDECASTLE,CANBLACKKINGSIDECASTLE,CANWHITEQUEENSIDECASTLE);
                undoMove(taken,move);

                if(score < beta) {
                    beta = score;
                }
                if(alpha >= beta) {
                    return beta;
                }
            }
            return beta;
        }
    }

}
