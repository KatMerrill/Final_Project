package com.example.final_project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ButtonClickListener implements View.OnClickListener {
    static int current_turn = 1; // 1 = black, 2 = white
    MainActivity containing_activity;

    public ButtonClickListener(MainActivity containing_activity) {
        super();
        this.containing_activity = containing_activity;
    }

    @Override
    public void onClick(View view) {
        TileButton button = (TileButton) view;
        button.setState(current_turn);
        current_turn = ((current_turn) % 2) + 1;
        containing_activity.generate_game_string();
    }
}
