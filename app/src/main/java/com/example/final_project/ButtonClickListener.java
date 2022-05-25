package com.example.final_project;

import android.view.View;

public class ButtonClickListener implements View.OnClickListener {
    static int current_turn = 1; // 1 = black, 2 = white
    static View turnIndicator;
    // colors[] contains the ids for black and white, in the positions corresponding to whose turn it is
    static int[] colors;
    MainActivity containing_activity;

    public ButtonClickListener(MainActivity containing_activity) {
        super();
        this.containing_activity = containing_activity;
        colors = new int[]{0, R.color.black, R.color.white};
    }
    public void addIndicator(View turnIndicator) {
        this.turnIndicator = turnIndicator;
    }



    // TODO: finish turnIndicator. right now, the colored square cannot be accessed from here, so
    // you probably want to call it from the containing_activity





    @Override
    public void onClick(View v) {
        TileButton button = (TileButton) v;
        if (button.isPlayable()) {
            button.playTile(current_turn);
            current_turn = ((current_turn) % 2) + 1;
            int results = button.evaluateWin();
            if(results > 0) {
                button.confetti();
            }
        }
    }
}
