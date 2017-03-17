package com.dark.webprog26.firebaseoffline;

import android.content.res.AssetManager;

/**
 * Created by webpr on 16.03.2017.
 */

public class JSONLoaderThread extends Thread {

    private AssetManager mAssetManager;
    private OnJSONReadFromAssetsListener mReadFromAssetsListener;
    private String mJsonFilename;

    public JSONLoaderThread(AssetManager assetManager, OnJSONReadFromAssetsListener listener, String jsonFilename) {
        this.mAssetManager = assetManager;
        this.mReadFromAssetsListener = listener;
        this.mJsonFilename = jsonFilename;
    }

    @Override
    public void run() {
        super.run();
        mReadFromAssetsListener.jsonReadFromAssets(AssetsJSONReader.loadJSONFromAsset(mAssetManager, mJsonFilename));
    }
}
