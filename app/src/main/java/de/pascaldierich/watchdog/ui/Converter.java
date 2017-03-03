package de.pascaldierich.watchdog.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Converter for presentation-layer.
 */
public abstract class Converter {
    
    /*
        static methods
     */
    
    /**
     * returns byte[] as Bitmap
     * <p/>
     * @param data, byte[]
     * @return Bitmap
     */
    public static Bitmap getBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
