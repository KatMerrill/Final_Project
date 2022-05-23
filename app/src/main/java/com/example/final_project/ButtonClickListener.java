package com.example.final_project;

import android.view.View;

public class ButtonClickListener implements View.OnClickListener {
    static int current_turn = 1; // 1 = black, 2 = white
    MainActivity containing_activity;

    public ButtonClickListener(MainActivity containing_activity) {
        super();
        this.containing_activity = containing_activity;
    }

    @Override
    public void onClick(View v) {
        TileButton button = (TileButton) v;
        if (button.isPlayable()) {
            button.playTile(current_turn);
            current_turn = ((current_turn) % 2) + 1;
            int results = button.evaluateWin();
            if(results > 0) {
                // TODO Confetti here. https://github.com/DanielMartinus/Konfetti
            }
        }
    }
}
