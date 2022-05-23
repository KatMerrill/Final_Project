package com.example.final_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.LinearLayout;

public class TileButton extends androidx.appcompat.widget.AppCompatImageView {
    // fields shared by all tiles
    static Bitmap[] bitmaps;
    static int board_size = 5;
    static int[][] states = new int[board_size][board_size];
    static int prevRow = -1, prevCol = -1;

    // fields belonging to one tile
    int state = 0; // starts empty
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
    public void setState(int state) {
        // when a tile is selected, updates the data fields related to it
        this.state = state;
        states[row][col] = state;
        setImageBitmap(bitmaps[state]);
    }
    public void playTile(int state) {
        setState(state);
        // "globally" marks that this was the most recent turn
        prevRow = row;
        prevCol = col;
    }
    public int getState() {
        return state;
    }
    public boolean isPlayable(int row, int col) {
        // if this space is already taken, cannot be played
        if(state != 0) {
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
        return state;
    }
}
