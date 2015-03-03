package com.example.serj.eattheburger;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;


public class Juego extends Activity {
    private VistaJuego vistaJuego;
    PowerManager.WakeLock mWakeLock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        vistaJuego = new VistaJuego(this);
        setContentView(vistaJuego);
        /* This code together with the one in onDestroy()
         * will make the screen be always on until this Activity gets destroyed. */
        final PowerManager pm = (PowerManager) getSystemService(this.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
    }
    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
    }



}
