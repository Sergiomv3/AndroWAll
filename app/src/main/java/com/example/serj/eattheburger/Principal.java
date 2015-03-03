package com.example.serj.eattheburger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Principal extends Activity {

    private VistaJuego vistaJuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

    }
    public void nuevoJuego(View v){
        Intent intent = new Intent(this, Juego.class);
        startActivity(intent);
    }
}
