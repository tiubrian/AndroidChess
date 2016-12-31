package com.example.brian.androidchess.views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.brian.androidchess.R;
import com.example.brian.androidchess.controllers.SquareController;
import com.example.brian.androidchess.model.GameModel;


/**
 * Created by Brian on 16/12/26.
 */

public class SquareAdapter extends BaseAdapter{

    private Context context;
    private short[] board;
    private GridView gridView;
    private LayoutInflater inflater;
    private int[] highlightBoard;
    private GameModel gameModel;
    private boolean foward;
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

    public SquareAdapter(Context context, GameModel gameModel, GridView gridView, boolean foward) {
        this.context = context;
        this.board = gameModel.getBoardModel().getBoard();
        this.gridView = gridView;
        this.highlightBoard = gameModel.getBoardModel().getHighlightBoard();
        this.gameModel = gameModel;
        this.foward = foward;
        this.inflater = LayoutInflater.from(context.getApplicationContext());
    }

    @Override
    public int getCount() {
        return 64;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.square, null);
        }
        if(!foward) {
            position = 63-position;
        }

        final ImageView oImageView = (ImageView)convertView.findViewById(R.id.innersquare);
        int realIndex = converter[position];

        int row = position/8;
        int col = position%8;

        /*
        0 - no highlight
        1 - selected square highlight
        2 - possible move highlight
         */
        switch (highlightBoard[position]) {
            case 1: convertView.setBackgroundColor(Color.argb(255, 72, 3, 132));break;
            case 2:

                if (row % 2 == 0 && col % 2 == 0 || row % 2 == 1 && col % 2 == 1) {
                    convertView.setBackgroundColor(Color.argb(255, 200, 150, 50));
                } else {
                    convertView.setBackgroundColor(Color.argb(255, 180, 133, 34));
                }

                break;
            case 0:
                if (row % 2 == 0 && col % 2 == 0 || row % 2 == 1 && col % 2 == 1) {
                    convertView.setBackgroundColor(Color.argb(255, 128, 156, 225));
                } else {
                    convertView.setBackgroundColor(Color.argb(255, 8, 77, 147));
                }
                break;
        }


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
        switch (board[realIndex]) {
            case -1: oImageView.setImageResource(R.drawable.bp); break;
            case -2: oImageView.setImageResource(R.drawable.br); break;
            case -3: oImageView.setImageResource(R.drawable.bn); break;
            case -4: oImageView.setImageResource(R.drawable.bb); break;
            case -5: oImageView.setImageResource(R.drawable.bq); break;
            case -6: oImageView.setImageResource(R.drawable.bk); break;

            case 1: oImageView.setImageResource(R.drawable.wp); break;
            case 2: oImageView.setImageResource(R.drawable.wr); break;
            case 3: oImageView.setImageResource(R.drawable.wn); break;
            case 4: oImageView.setImageResource(R.drawable.wb); break;
            case 5: oImageView.setImageResource(R.drawable.wq); break;
            case 6: oImageView.setImageResource(R.drawable.wk); break;

            case 0: oImageView.setImageResource(R.color.colorClear); break;
        }

        convertView.setTag(convertView);
        convertView.setOnTouchListener(new SquareController(gameModel,this,position));

        return convertView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public short[] getBoard() {
        return board;
    }

    public void setBoard(short[] board) {
        this.board = board;
    }

    public GridView getGridView() {
        return gridView;
    }

    public void setGridView(GridView gridView) {
        this.gridView = gridView;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public int[] getHighlightBoard() {
        return highlightBoard;
    }

    public void setHighlightBoard(int[] highlightBoard) {
        this.highlightBoard = highlightBoard;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public boolean isFoward() {
        return foward;
    }

    public void setFoward(boolean foward) {
        this.foward = foward;
    }

    public short[] getConverter() {
        return converter;
    }

    public void setConverter(short[] converter) {
        this.converter = converter;
    }
}
