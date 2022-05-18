package com.example.final_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.LinearLayout;

public class TileButton extends androidx.appcompat.widget.AppCompatImageView {
    static Bitmap[] bitmaps;
    int state = 0; // starts empty

    public TileButton(Context context) {
        super(context);
        setLayoutParams(new LinearLayout.LayoutParams(120, 120));
        bitmaps = new Bitmap[]{BitmapFactory.decodeResource(getResources(), R.drawable.empty),
                BitmapFactory.decodeResource(getResources(), R.drawable.b),
                BitmapFactory.decodeResource(getResources(), R.drawable.w)};
    }
    public void setState(int state) {
        this.state = state;
        setImageBitmap(bitmaps[state]);
    }
    public int getState() {
        return state;
    }
}
