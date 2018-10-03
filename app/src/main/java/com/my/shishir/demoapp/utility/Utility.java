package com.my.shishir.demoapp.utility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import com.my.shishir.demoapp.AppContext;
import com.my.shishir.demoapp.NewsListAdapter;

/**
 * This is the class to hold static data used in app mostly for urls, String etc
 */
public class Utility {

    public final static int NETWORK_ERROR_CODE = 1;
    public final static int OTHER_ERROR_CODE = 0;
    public final static int NO_ERROR_CODE = -1;

    public final static String KEY_URL = "url";

    public static String BASE_URL = "https://api.nytimes.com/";

    public static boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) AppContext.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm != null ? cm.getActiveNetworkInfo() : null;
        return info != null &&
                (info.getState() == NetworkInfo.State.CONNECTED
                        || info.getState() == NetworkInfo.State.CONNECTING);

    }

    public static void imageRounder(final Bitmap bitmap,
                              final Resources resource,
                              @NonNull final NewsListAdapter.ProcessedBitmapListener processedBitmapListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(resource, bitmap);
                imageDrawable.setCircular(true);
                imageDrawable.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);
                processedBitmapListener.onProcessDone(imageDrawable);
            }
        }).start();
    }
}
