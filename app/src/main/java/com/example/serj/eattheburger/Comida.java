package com.example.serj.eattheburger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

/**
 * Created by Marco on 26/2/15.
 */
public class Comida {
    private Bitmap bmp;
    private int tipo;
    private boolean isBueno;
    private Context contexto;
    private int ejeX;
    private int ejeY = 0;
    private int alto, ancho;
    private int direccionX = 5, direccionY = 5;
    public Comida(int tipo,Context contexto) {
        // CASE 0 = ANDROID
        // CASE 1 = VIRUS
        this.tipo = tipo;
        this.contexto = contexto;
        setTipo(tipo);


    }
    public void dibujar (Canvas canvas){
        caer();
        canvas.drawBitmap(bmp, ejeX, ejeY, null);

    }

    private void caer() {
        ejeY = ejeY + direccionY;
    }

    public void setTipo(int tipo){
        switch (tipo){
            case 0:
                this.bmp = BitmapFactory.decodeResource
                        (contexto.getResources(), R.drawable.android);
                this.isBueno = true;
                setDimensiones(bmp);
                break;
            case 1:
                this.bmp = BitmapFactory.decodeResource
                        (contexto.getResources(), R.drawable.virus);
                this.isBueno = false;
                setDimensiones(bmp);
                break;
        }
    }
    private void setDimensiones(Bitmap bmp){
        this.ancho = bmp.getWidth();

        this.alto = bmp.getHeight();
    }

    public void setPosicionX(int posXColumna) {

        this.ejeX = posXColumna;
    }

    public void setVelocidad(int i) {
        Random velocidad = new Random();
        // ESTO PARA PROBAR SI FUERA MAS NIVEL
        //i = 10;
        this.direccionY = velocidad.nextInt(5+(i*2))+2*i;
        if(direccionY>25){
            direccionY = 25; // CON ESTO LA VEL MAXIMA SERA 17
        }
        // if i == nivel es X pues Bandeja.setVelocidadBandeja(....)

            Bandeja.setVelocidadBandeja(15+(i*2));

    }

    public void setPosicionY() {
        Random y = new Random();
        this.ejeY = -(y.nextInt(60)+(y.nextInt(13)*100));
    }



    public int getEjeY() {
        return ejeY;
    }

    public int getEjeX() {
        return ejeX;
    }

    public boolean recogido(Bandeja bandeja){
        if(this.ejeX + this.ancho < bandeja.getEjeX()){
            return false;
        }
        if(this.ejeY + this.alto  < bandeja.getEjeY()){
            return false;
        }
        if(this.ejeX > bandeja.getEjeX()+  bandeja.getAncho()){
            return false;
        }
        if(this.ejeY > bandeja.getEjeY() + bandeja.getAlto()){
            return false;
        }

        return true;
    }

    public boolean isBueno() {
        return isBueno;
    }
}
