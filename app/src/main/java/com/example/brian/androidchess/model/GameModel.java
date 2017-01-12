package com.example.brian.androidchess.model;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.brian.androidchess.R;
import com.example.brian.androidchess.computerplayers.AlphaBetaComputerPlayer;
import com.example.brian.androidchess.computerplayers.ComputerPlayer;
import com.example.brian.androidchess.computerplayers.Move;
import com.example.brian.androidchess.computerplayers.RandomComputerPlayer;
import com.example.brian.androidchess.views.SquareAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brian on 16/12/27.
 */

public class GameModel {

    private BoardModel boardModel;
    private Context context;
    private char turn = 'w';
    private boolean gameover = false;
    private ArrayList<Move> gameHistory = new ArrayList<>();

    private ComputerPlayer whiteComputerPlayer = null;
    private ComputerPlayer blackComputerPlayer = null;
    private boolean computerThinking = false;

    protected SquareAdapter squareAdapter;


    public GameModel(final Context context, boolean whiteComputer, boolean blackComputer) {
        boardModel = new BoardModel(context);
        ((Activity)(context)).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MediaPlayer mp = MediaPlayer.create(context, R.raw.newgame);
                mp.start();
            }
        });
        this.context = context;
        if(whiteComputer) {
            whiteComputerPlayer = new AlphaBetaComputerPlayer(boardModel, 'w');
        }
        if(blackComputer) {
            blackComputerPlayer = new AlphaBetaComputerPlayer(boardModel, 'b');
        }
    }

    public void play() {
        if(whiteComputerPlayer != null) {
            //turn = 'b';
            CharSequence text = "Computer is thinking...";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            new Thread(new WhiteAITurn()).start();
            squareAdapter.notifyDataSetChanged();
        }
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public void setBoardModel(BoardModel boardModel) {
        this.boardModel = boardModel;
    }

    public synchronized void switchTurn() {
        if(gameover) {
            return;
        }


        if(turn == 'w') {
            turn = 'b';
            if(boardModel.getAllPossibleBlackMoves().size() == 0) {
                gameover = true;
                ((Activity)(context)).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        squareAdapter.notifyDataSetChanged();
                        CharSequence text = "White wins!";
                        if(!boardModel.isBlackKingInCheck()) {
                            text = "Stalemate!";
                        }
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });

                return;
            }
            if(blackComputerPlayer != null) {
                CharSequence text = "Computer is thinking...";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                new Thread(new BlackAITurn()).start();

            }
        } else {
            turn = 'w';
            if(boardModel.getAllPossibleWhiteMoves().size() == 0) {
                gameover = true;


                ((Activity)(context)).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CharSequence text = "Black wins!";
                        if(!boardModel.isWhiteKingInCheck()) {
                            text = "Stalemate!";
                        }
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });

                return;
            }
            if(whiteComputerPlayer != null) {


                ((Activity)(context)).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CharSequence text = "Computer is thinking...";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        new Thread(new WhiteAITurn()).start();
                    }
                });

            }
        }
    }

    public void addMove(Move move) {
        gameHistory.add(move);
    }

    // Getters and Setters
    public char getTurn() {
        return turn;
    }

    public void setTurn(char turn) {
        this.turn = turn;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isGameover() {
        return gameover;
    }

    public void setGameover(boolean gameover) {
        this.gameover = gameover;
    }

    public ComputerPlayer getWhiteComputerPlayer() {
        return whiteComputerPlayer;
    }

    public void setWhiteComputerPlayer(ComputerPlayer whiteComputerPlayer) {
        this.whiteComputerPlayer = whiteComputerPlayer;
    }

    public ComputerPlayer getBlackComputerPlayer() {
        return blackComputerPlayer;
    }

    public void setBlackComputerPlayer(ComputerPlayer blackComputerPlayer) {
        this.blackComputerPlayer = blackComputerPlayer;
    }

    public SquareAdapter getSquareAdapter() {
        return squareAdapter;
    }

    public void setSquareAdapter(SquareAdapter squareAdapter) {
        this.squareAdapter = squareAdapter;
    }

    class BlackAITurn implements Runnable{

        @Override
        public void run() {
            computerThinking = true;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                Move move = blackComputerPlayer.getMove();
                if(move == null) {
                    gameover = true;
                    return;
                }
                gameHistory.add(move);
                if(!(boardModel.getBoard()[move.getBefore()] == 1 ||
                        boardModel.getBoard()[move.getBefore()] == -1)) {
                    boardModel.movePiece(move.getBefore(),move.getAfter());
                } else {
                    boardModel.pawnMove(move.getBefore(),move.getAfter());
                }
                boardModel.getHighlightBoard()[boardModel.unconvert(move.getBefore())] = 2;
                boardModel.getHighlightBoard()[boardModel.unconvert(move.getAfter())] = 2;
                ((Activity)(context)).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        squareAdapter.notifyDataSetChanged();
                    }
                });
                computerThinking = false;
                switchTurn();
            }

        }
    }
    class WhiteAITurn implements Runnable{

        @Override
        public void run() {
            computerThinking = true;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                Move move = whiteComputerPlayer.getMove();
                if(move == null) {
                    gameover = true;
                    return;
                }
                gameHistory.add(move);
                if(!(boardModel.getBoard()[move.getBefore()] == 1 ||
                        boardModel.getBoard()[move.getBefore()] == -1)) {
                    boardModel.movePiece(move.getBefore(),move.getAfter());
                } else {
                    boardModel.pawnMove(move.getBefore(),move.getAfter());
                }
                boardModel.getHighlightBoard()[boardModel.unconvert(move.getBefore())] = 2;
                boardModel.getHighlightBoard()[boardModel.unconvert(move.getAfter())] = 2;
                ((Activity)(context)).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        squareAdapter.notifyDataSetChanged();
                    }
                });
                computerThinking = false;
                switchTurn();
            }

        }
    }

    public ArrayList<Move> getGameHistory() {
        return gameHistory;
    }

    public void setGameHistory(ArrayList<Move> gameHistory) {
        this.gameHistory = gameHistory;
    }

    public boolean isComputerThinking() {
        return computerThinking;
    }

    public void setComputerThinking(boolean computerThinking) {
        this.computerThinking = computerThinking;
    }
}
