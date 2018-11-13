package com.example.nguyenhuutu.convenientmenu.helper;

import android.graphics.Point;
import android.view.Display;

public class Helper {
    public static float getOneDP() {
        float oneDP = 1;

        return oneDP;
    }

    public static Point getDisplaySize(Display display) {
        // display size in pixels
        Point size = new Point();
        display.getSize(size);

        return size;
    }
}
