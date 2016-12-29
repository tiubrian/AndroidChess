package com.example.brian.androidchess.model;

import android.content.Context;
import android.widget.Toast;

import com.example.brian.androidchess.computerplayers.ComputerPlayer;
import com.example.brian.androidchess.computerplayers.Move;
import com.example.brian.androidchess.computerplayers.RandomComputerPlayer;
import com.example.brian.androidchess.views.SquareAdapter;

import java.util.ArrayList;

/**
 * Created by Brian on 16/12/27.
 */

public class GameModel {
    private BoardModel boardModel = new BoardModel();
    private Context context;
    private char turn = 'w';
    private boolean gameover = false;
    private ArrayList<Move> gameHistory = new ArrayList<>();

    private ComputerPlayer whiteComputerPlayer = null;
    private ComputerPlayer blackComputerPlayer = null;

    protected SquareAdapter squareAdapter;


    public GameModel(Context context, boolean whiteComputer, boolean blackComputer) {
        this.context = context;
        if(whiteComputer) {
            whiteComputerPlayer = new RandomComputerPlayer(boardModel, 'w');
        }
        if(blackComputer) {
            blackComputerPlayer = new RandomComputerPlayer(boardModel, 'b');
        }
    }

    public void play() {
        if(whiteComputerPlayer != null) {
            Move move = whiteComputerPlayer.getMove();
            gameHistory.add(move);
            if(!(this.getBoardModel().getBoard()[move.getBefore()] == 1 ||
                    this.getBoardModel().getBoard()[move.getBefore()] == -1)) {
                this.getBoardModel().movePiece(move.getBefore(),move.getAfter());
            } else {
                this.getBoardModel().pawnMove(move.getBefore(),move.getAfter());
            }
            boardModel.getHighlightBoard()[boardModel.unconvert(move.getBefore())] = 2;
            boardModel.getHighlightBoard()[boardModel.unconvert(move.getAfter())] = 2;
            squareAdapter.notifyDataSetChanged();
            switchTurn();
        }
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public void setBoardModel(BoardModel boardModel) {
        this.boardModel = boardModel;
    }

    public void switchTurn() {
        if(gameover) {
            return;
        }
        if(turn == 'w') {
            turn = 'b';
            if(boardModel.getAllPossibleBlackMoves().size() == 0) {
                gameover = true;
                CharSequence text = "White wins!";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }
            if(blackComputerPlayer != null) {
                Move move = blackComputerPlayer.getMove();
                gameHistory.add(move);
                if(!(this.getBoardModel().getBoard()[move.getBefore()] == 1 ||
                        this.getBoardModel().getBoard()[move.getBefore()] == -1)) {
                    this.getBoardModel().movePiece(move.getBefore(),move.getAfter());
                } else {
                    this.getBoardModel().pawnMove(move.getBefore(),move.getAfter());
                }
                boardModel.getHighlightBoard()[boardModel.unconvert(move.getBefore())] = 2;
                boardModel.getHighlightBoard()[boardModel.unconvert(move.getAfter())] = 2;
                squareAdapter.notifyDataSetChanged();
                switchTurn();
            }
        } else {
            turn = 'w';
            if(boardModel.getAllPossibleWhiteMoves().size() == 0) {
                gameover = true;
                CharSequence text = "Black wins!";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }
            if(whiteComputerPlayer != null) {
                Move move = whiteComputerPlayer.getMove();
                gameHistory.add(move);
                if(!(this.getBoardModel().getBoard()[move.getBefore()] == 1 ||
                        this.getBoardModel().getBoard()[move.getBefore()] == -1)) {
                    this.getBoardModel().movePiece(move.getBefore(),move.getAfter());
                } else {
                    this.getBoardModel().pawnMove(move.getBefore(),move.getAfter());
                }
                boardModel.getHighlightBoard()[boardModel.unconvert(move.getBefore())] = 2;
                boardModel.getHighlightBoard()[boardModel.unconvert(move.getAfter())] = 2;
                squareAdapter.notifyDataSetChanged();
                switchTurn();
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
}
