package com.example.brian.androidchess.controllers;

import android.content.Context;
import android.view.View;
import android.widget.GridView;

import com.example.brian.androidchess.model.GameModel;
import com.example.brian.androidchess.views.SquareAdapter;

/**
 * Created by Brian on 16/12/30.
 */

public class ButtonController implements View.OnClickListener {


    String action;
    GameModel model;
    SquareAdapter squareAdapter;
    Context mContext;
    GridView gridview;

    public ButtonController(String action, GameModel model, SquareAdapter squareAdapter, Context mContext, GridView gridview) {
        this.action = action;
        this.model = model;
        this.squareAdapter = squareAdapter;
        this.mContext = mContext;
        this.gridview = gridview;
    }

    @Override
    public void onClick(View v) {
        switch (action) {
            case "flip":
                boolean currentFoward = squareAdapter.isFoward();
                boolean newFoward;
                if(currentFoward) {
                    newFoward = false;
                } else {
                    newFoward = true;
                }

                SquareAdapter squareAdapter = new SquareAdapter(mContext,model,gridview,newFoward);
                gridview.setAdapter(squareAdapter);
                model.setSquareAdapter(squareAdapter);
                this.squareAdapter = squareAdapter;

                break;
            case "undo":
                break;
        }
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public GameModel getModel() {
        return model;
    }

    public void setModel(GameModel model) {
        this.model = model;
    }

    public SquareAdapter getSquareAdapter() {
        return squareAdapter;
    }

    public void setSquareAdapter(SquareAdapter squareAdapter) {
        this.squareAdapter = squareAdapter;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public GridView getGridview() {
        return gridview;
    }

    public void setGridview(GridView gridview) {
        this.gridview = gridview;
    }
}
