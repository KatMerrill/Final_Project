package com.example.final_project;

import android.view.View;
import android.widget.TextView;

public class ButtonClickListener implements View.OnClickListener {
    static int current_turn = 1; // 1 = black, 2 = white
    static View turnIndicator;
    static TextView winner_panel;
    // colors[] contains the ids for black and white, in the positions corresponding to whose turn it is
    static int[] colors;
    static String[] players;
    MainActivity containing_activity;

    public ButtonClickListener(MainActivity containing_activity) {
        super();
        this.containing_activity = containing_activity;
        colors = new int[]{0, R.color.black, R.color.white};
        players = new String[]{"", "Black", "White"};
    }
    // indicator refers to the square displaying which player's turn it is
    public void addIndicator(View turnIndicator, TextView winner_panel) {
        this.turnIndicator = turnIndicator;
        this.winner_panel = winner_panel;
    }

    @Override
    public void onClick(View v) {
        TileButton button = (TileButton) v;
        if (button.isPlayable()) {
            button.playTile(current_turn);
            current_turn = ((current_turn) % 2) + 1;
            int results = button.evaluateWin();
            // if this player has won
            if(results > 0) {
                // progresses to the next turn because the most recent player is the winner
                current_turn = ((current_turn) % 2) + 1;
                winner_panel.setText(containing_activity.getString(R.string.winner, players[current_turn]));
                winner_panel.setVisibility(View.VISIBLE);
                button.confetti(current_turn);
            }
            turnIndicator.setBackgroundColor(v.getContext().getColor(colors[current_turn]));
        }
    }
}
