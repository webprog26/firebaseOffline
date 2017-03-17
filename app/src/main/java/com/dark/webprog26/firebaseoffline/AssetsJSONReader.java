package com.dark.webprog26.firebaseoffline;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by webpr on 16.03.2017.
 */

public class AssetsJSONReader {

    public static String loadJSONFromAsset(AssetManager assetManager, String jsonFilename) {
        String json = null;
        try {
            InputStream is = assetManager.open(jsonFilename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
