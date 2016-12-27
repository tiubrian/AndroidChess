package com.example.brian.androidchess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.brian.androidchess.model.GameModel;
import com.example.brian.androidchess.views.SquareAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boardview);

        GameModel gameModel = new GameModel();
        short[] board = gameModel.getBoardModel().getBoard();

        GridView gridview = (GridView) findViewById(R.id.boardview);
        SquareAdapter squareAdapter = new SquareAdapter(this,gameModel,gridview);
        gridview.setAdapter(squareAdapter);

    }
}
