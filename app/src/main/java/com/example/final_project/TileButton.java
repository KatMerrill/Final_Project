package com.example.final_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class TileButton extends androidx.appcompat.widget.AppCompatImageView {
    // fields shared by all tiles
    static Bitmap[] bitmaps;
    static int board_size = 5;
    static int[][] states = new int[board_size][board_size];
    static int prevRow = -1, prevCol = -1;
    static View parentView;

    // fields belonging to one tile
    int row, col;

    public TileButton(Context context) {
        super(context);
        setLayoutParams(new LinearLayout.LayoutParams(170, 170));
        bitmaps = new Bitmap[]{BitmapFactory.decodeResource(getResources(), R.drawable.empty),
                BitmapFactory.decodeResource(getResources(), R.drawable.b),
                BitmapFactory.decodeResource(getResources(), R.drawable.w)};
    }
    public TileButton(Context context, int row, int col) {
        // creates tile, which knows its own position
        this(context);
        this.row = row;
        this.col = col;
    }
    public void addView(View v) {
        parentView = v;
    }
    public void setState(int state) {
        // when a tile is selected, updates the data fields related to it
        states[row][col] = state;
        setImageBitmap(bitmaps[state]);
    }
    public void playTile(int state) {
        setState(state);
        // "globally" marks that this was the most recent turn
        prevRow = row;
        prevCol = col;
    }
    public boolean isPlayable(int row, int col) {
        // if this space is already taken, cannot be played
        if(states[row][col] != 0) {
            return false;
        }
        // if this is the first turn, can play anywhere
        if(prevRow == -1) {
            return true;
        }
        // all remaining turns must be played at tiles adjacent to the most recent tile
        if(Math.abs(prevRow - row) == 1 && (prevCol - col) == 0) {
            return true;
        }
        if(Math.abs(prevCol - col) == 1 && (prevRow - row) == 0) {
            return true;
        }
        return false;
    }
    public boolean isPlayable() {
        return isPlayable(row, col);
    }
    // resets the "global" variables
    public void reset() {
        prevRow = -1;
        prevCol = -1;
    }
    // returns 0 for game not over, 1 for black wins, 2 for white wins
    public int evaluateWin() {
        // if the game is just starting, it isn't over
        if(prevRow == -1) {
            return 0;
        }
        // if any of the nearby positions are playable, the game hasn't ended
        if(row + 1 < states.length) {
            if(isPlayable(row + 1, col)) {
                return 0;
            }
        }
        if(row - 1 > -1) {
            if(isPlayable(row - 1, col)) {
                return 0;
            }
        }
        if(col + 1 < states[0].length) {
            if(isPlayable(row, col + 1)) {
                return 0;
            }
        }
        if(col - 1 > -1) {
            if(isPlayable(row, col - 1)) {
                return 0;
            }
        }
        return states[row][col];
    }
    // the confetti animation that plays when someone wins
    public void confetti(int winner) {
        // sets colors based on who won
        List<Integer> col;
        if(winner == 1) { // black
            col = Arrays.asList(0xFF000000, 0xD84450, 0xE1ACC8, 0x9F244E);
        }
        else { // white
            col = Arrays.asList(0x8BC34A, 0x73A738, 0xE3ECD9, 0xB5F884);
        }

        KonfettiView konfettiView = parentView.findViewById(R.id.konfetti_view);
        // emitter is responsible for how fast the confetti generates, and for how long
        EmitterConfig emitterConfig = new Emitter(100L, TimeUnit.MILLISECONDS).max(100);
        // .start() is what actually runs the confetti animation
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        // spread accounts for how many directions the confetti goes in (in our case, all directions)
                        .spread(360)
                        // uses square and circular confetti
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE))
                        // uses the colors currently used in the application. here, listed as 0x + hex number
                        .colors(col)
                        // the speed of each piece of confetti varies between these values
                        .setSpeedBetween(0f, 30f)
                        // the starting position
                        .position(new Position.Relative(0.5, 0.3))
                        // creates the emitter configuration
                        .build()
        );
    }
}
