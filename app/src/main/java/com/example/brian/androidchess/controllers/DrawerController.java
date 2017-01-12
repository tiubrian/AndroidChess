package com.example.brian.androidchess.controllers;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.brian.androidchess.model.GameModel;
import com.example.brian.androidchess.views.SquareAdapter;

/**
 * Created by Brian on 16/12/27.
 */

public class DrawerController implements View.OnClickListener{

    private DrawerLayout drawerLayout;
    private String action;
    private GameModel model;
    private SquareAdapter squareAdapter;
    private Context mContext;
    private GridView gridview;
    private Switch redcompswitch;
    private Switch blackcompswitch;
    private Button flipButton;
    public DrawerController(DrawerLayout drawerLayout, String action, GameModel model, SquareAdapter squareAdapter, Context mContext, GridView gridview, Switch redcompswitch, Switch blackcompswitch,Button flipButton) {
        this.drawerLayout = drawerLayout;
        this.action = action;
        this.model = model;
        this.squareAdapter = squareAdapter;
        this.mContext = mContext;
        this.gridview = gridview;
        this.redcompswitch = redcompswitch;
        this.blackcompswitch = blackcompswitch;
        this.flipButton = flipButton;
    }

    @Override
    public void onClick(View v) {
        if(model.isComputerThinking()) {
            return;
        }
        switch (action) {
            case "newgame":
                boolean redcompon = redcompswitch.isChecked();
                boolean blackcompon = blackcompswitch.isChecked();

                if(redcompon && blackcompon) {
                    drawerLayout.closeDrawers();
                    CharSequence text = "You cannot have two computers play each other";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(mContext, text, duration);
                    toast.show();
                    return;
                }
                boolean fowward;
                if(!redcompon) {
                    fowward = true;
                } else {
                    fowward = false;
                }

                GameModel gameModel = new GameModel(mContext, redcompon, blackcompon);
                SquareAdapter squareAdapter = new SquareAdapter(mContext,gameModel,gridview,fowward);
                gridview.setAdapter(squareAdapter);
                gameModel.setSquareAdapter(squareAdapter);

                gameModel.play();
                drawerLayout.closeDrawers();

                ButtonController flipBoardListener = new ButtonController("flip",gameModel,squareAdapter,mContext,gridview);
                flipButton.setOnClickListener(flipBoardListener);

                break;
            case "cancel": drawerLayout.closeDrawers(); break;
        }

    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
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

    public Switch getRedcompswitch() {
        return redcompswitch;
    }

    public void setRedcompswitch(Switch redcompswitch) {
        this.redcompswitch = redcompswitch;
    }

    public Switch getBlackcompswitch() {
        return blackcompswitch;
    }

    public void setBlackcompswitch(Switch blackcompswitch) {
        this.blackcompswitch = blackcompswitch;
    }

    public Button getFlipButton() {
        return flipButton;
    }

    public void setFlipButton(Button flipButton) {
        this.flipButton = flipButton;
    }
}
