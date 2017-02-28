package de.pascaldierich.watchdog.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.lang.ref.WeakReference;

public class Converter {

    /*
        Instantiation
     */
    private Converter() {
    }

    public static WeakReference<Converter> getInstance() {
        return new WeakReference<Converter>(new Converter());
    }


    /*
        static methods
     */

    public static Bitmap getBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
