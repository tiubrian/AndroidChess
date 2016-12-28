package com.example.brian.androidchess;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Switch;

import com.example.brian.androidchess.controllers.DrawerController;
import com.example.brian.androidchess.model.GameModel;
import com.example.brian.androidchess.views.SquareAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boardview);

        GameModel gameModel = new GameModel(this);
        short[] board = gameModel.getBoardModel().getBoard();

        GridView gridview = (GridView) findViewById(R.id.boardview);
        SquareAdapter squareAdapter = new SquareAdapter(this,gameModel,gridview);
        gridview.setAdapter(squareAdapter);

        Switch redcompswitch = (Switch) findViewById(R.id.switch7);
        Switch blackcompswitch = (Switch) findViewById(R.id.switch8);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        Button cancelbutton = (Button) findViewById(R.id.cancelbutton);
        cancelbutton.setOnClickListener(new DrawerController(drawerLayout,"cancel",gameModel,squareAdapter,this,gridview,redcompswitch,blackcompswitch));

        Button newgamebutton = (Button) findViewById(R.id.newgamebutton);
        newgamebutton.setOnClickListener(new DrawerController(drawerLayout,"newgame",gameModel,squareAdapter,this,gridview,redcompswitch,blackcompswitch));
    }
}
