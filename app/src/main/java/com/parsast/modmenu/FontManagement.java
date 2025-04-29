package com.parsast.modmenu;

import static com.parsast.modmenu.Menu.Font;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FontManagement {
    private static final String FONT = Font();
    private static final String TAG = FontManagement.class.getSimpleName();

    // Cache the loaded Typeface
    private static Typeface cachedTypeface = null;

    public static Typeface getTypeface(Context context) {
        if (cachedTypeface != null) {
            return cachedTypeface;
        }

        try {
            byte[] fontDecode = Base64.decode(FONT, Base64.DEFAULT);
            if (fontDecode.length == 0) {
                Log.e(TAG, "Font bytes are empty. Check the FONT string.");
                return Typeface.DEFAULT;
            }

            // Create a temporary file inside cache
            File tmpFontFile = File.createTempFile("tempfont", ".ttf", context.getCacheDir());

            try (FileOutputStream fos = new FileOutputStream(tmpFontFile)) {
                fos.write(fontDecode);
            }

            // Load Typeface
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cachedTypeface = new Typeface.Builder(tmpFontFile).build();
            } else {
                cachedTypeface = Typeface.createFromFile(tmpFontFile);
            }

            // Immediately delete temp file after loading
            boolean deleted = tmpFontFile.delete();
            if (!deleted) {
                Log.w(TAG, "Temporary font file could not be deleted immediately.");
            }

            return cachedTypeface;

        } catch (IOException e) {
            Log.e(TAG, "IO Error while loading font", e);
            return Typeface.DEFAULT;
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error while loading font", e);
            return Typeface.DEFAULT;
        }
    }
}
