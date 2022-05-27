package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // items related to the board itself
    TileButton[][] tiles = new TileButton[5][5];
    LinearLayout[] horiz_layouts = new LinearLayout[8];
    ArrayList<View> selections = new ArrayList<View>();
    LinearLayout vert_layout; // contained by the overall Constraint layout, contains the horiz_layouts
    ButtonClickListener buttonClickListener;

    // items related to options
    Button optionButton;
    TextView instructions;

    //items related to current player's turn and results
    View indicator;
    TextView winner_panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // sets up for the buttons
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

        instructions = findViewById(R.id.instructions);
        instructions.setVisibility(View.INVISIBLE);

        winner_panel = findViewById(R.id.display_winner);
        winner_panel.setVisibility(View.INVISIBLE);

        // indicator refers to whose turn it is -- initialized before buttonClickListener
        indicator = findViewById(R.id.player_indicator);
        buttonClickListener = new ButtonClickListener(this);
        buttonClickListener.addIndicator(indicator, winner_panel);

        // sets up the actual game: buttons and such
        setup_game();

        // options button
        optionButton = findViewById(R.id.option_button);
        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creates the pop-up menu, attached to the button
                PopupMenu popUp = new PopupMenu(MainActivity.this, optionButton);
                popUp.getMenuInflater().inflate(R.menu.dropdown, popUp.getMenu());

                // sets up the listener for when items are clicked on
                popUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("Reset")) {
                            reset_game();
                        }
                        if(menuItem.getTitle().equals("Instructions")) {
                            instructions.setVisibility(View.VISIBLE);
                            // lambda operator
                            instructions.setOnClickListener(view1 -> view1.setVisibility(View.INVISIBLE));
                        }
                        return true;
                    }
                });
                // displays the pop-up menu when button clicked
                popUp.show();
            }
        });
    }
    public void setup_game() {
        // creates the buttons and adds them to the horizontal layouts
        for(int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = new TileButton(this, x, y);
                tiles[x][y].setState(0);
                tiles[x][y].setOnClickListener(buttonClickListener);
                horiz_layouts[x].addView(tiles[x][y]);
            }
        }
        tiles[0][0].addView(findViewById(R.id.activity_main));
    }

    public void reset_game() {
        for(int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y].setState(0);
            }
        }
        // resets the global variables
        tiles[0][0].reset();
        winner_panel.setVisibility(View.INVISIBLE);
    }

}