package br.com.redeindustrial;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;


public class RIAndroidApplication extends Application {
    // This flag should be set to true to enable VectorDrawable support for API < 21
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }
}