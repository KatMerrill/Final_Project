package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String game_string = "";
    TileButton[][] tiles = new TileButton[8][8];
    LinearLayout[] horiz_layouts = new LinearLayout[8];
    ArrayList<View> selections = new ArrayList<View>();
    LinearLayout vert_layout; // contained by the overall Constraint layout, contains the horiz_layouts
    ButtonClickListener buttonClickListener;
    // othello.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vert_layout = findViewById(R.id.vert_layout);
        vert_layout.setGravity(Gravity.CENTER);

        // initializes the horizontal layouts and adds them to the vertical layout
        for(int x = 0; x < horiz_layouts.length; x++) {
            horiz_layouts[x] = new LinearLayout(this);
            horiz_layouts[x].setOrientation(LinearLayout.HORIZONTAL);

            horiz_layouts[x].setPadding(10,0,10,0);
            horiz_layouts[x].setGravity(Gravity.CENTER);

            vert_layout.addView(horiz_layouts[x]);
        }
        buttonClickListener = new ButtonClickListener(this);

        // creates the buttons and adds them to the horizontal layouts
        for(int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = new TileButton(this);
                tiles[x][y].setState(0);
                tiles[x][y].setOnClickListener(buttonClickListener);
                horiz_layouts[x].addView(tiles[x][y]);
            }
        }

        generate_game_string(tiles);
    }
    public void generate_game_string(TileButton[][] tiles) {
        game_string = "";
        for(int x = 0; x < tiles.length; x++) {
            for(int y = 0; y < tiles.length; y++) {
                game_string += tiles[x][y].getState();
            }
        }
        System.out.println("gamestring" + game_string);
    }
    public void generate_game_string() {
        generate_game_string(tiles);
    }

    public ArrayList<Integer> determine_available() {
        return new ArrayList<Integer>();
    }
}