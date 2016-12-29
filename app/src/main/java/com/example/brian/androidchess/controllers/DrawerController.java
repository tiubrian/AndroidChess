package com.example.brian.androidchess.controllers;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
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

    public DrawerController(DrawerLayout drawerLayout, String action, GameModel model, SquareAdapter squareAdapter, Context mContext, GridView gridview, Switch redcompswitch, Switch blackcompswitch) {
        this.drawerLayout = drawerLayout;
        this.action = action;
        this.model = model;
        this.squareAdapter = squareAdapter;
        this.mContext = mContext;
        this.gridview = gridview;
        this.redcompswitch = redcompswitch;
        this.blackcompswitch = blackcompswitch;
    }

    @Override
    public void onClick(View v) {
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

                GameModel gameModel = new GameModel(mContext, redcompon, blackcompon);
                SquareAdapter squareAdapter = new SquareAdapter(mContext,gameModel,gridview);
                gridview.setAdapter(squareAdapter);
                gameModel.setSquareAdapter(squareAdapter);

                gameModel.play();
                drawerLayout.closeDrawers();

                break;
            case "cancel": drawerLayout.closeDrawers(); break;
        }

    }
}
