package com.example.serj.eattheburger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Bandeja {

    private Bitmap bmp;
    private int frameActual=0;
    private static final int COLUMNAS = 8;
    private static final int FILAS = 2;
    private int ancho, alto;
    private int ejeY = Integer.valueOf((int)(VistaJuego.screenHeight*(0.85)));
    private int direccionY;
    private int ejeX = 0;
    private int direccionX;
    private static int anchoMax=0, altoMax=0;
    public static int velocidadBandeja = 15;

    public Bandeja(Context contexto) {
        this.bmp = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.bandeja);

        this.ancho = bmp.getWidth();
        this.alto = bmp.getHeight();


    }

    public void dibujar(Canvas canvas){
        movimiento();
        /*int origenx = frameActual * ancho;
        int origeny = 0;
        if(direccionX<0)
            origeny=alto;
        else
            origeny=0;
        Rect origen = new Rect(origenx, origeny, origenx + ancho, origeny +
                alto);
        Rect destino = new Rect(ejeX, ejeY, ejeX + ancho, ejeY + alto);*/
        canvas.drawBitmap(bmp, ejeX, ejeY, null);
        //canvas.drawBitmap(bmp, origen, destino, null);
    }

    public static void setDimension(int ancho, int alto){
        // Dimensiones de la bandeja
        anchoMax = ancho;
        altoMax = alto;
    }

    public void setPosicion(float x){
        // poner posicion inicial
        ejeX =(int) x;
    }



    public boolean recogido(Comida[] comidas) {
        // Para saber si he recogido una comida(dependiendo del ti
        return true;
    }

    public void setDireccion(float aceleracion) {

        // Hay q tneer en cuenta
        if(aceleracion > 2){
            // IZQUIERDA
            direccionX = -velocidadBandeja;
        }else if(aceleracion < - 2){
            // DERECHA
            direccionX = velocidadBandeja;
        }else if(aceleracion < 2 && aceleracion > -2){
            direccionX = 0;
        }
        //Log.e("DIRECCION", String.valueOf(direccionX));
    }
    private void movimiento(){
            ejeX = ejeX + direccionX;
            if(ejeX >= (VistaJuego.screenWidth-bmp.getWidth())){
                ejeX = VistaJuego.screenWidth-bmp.getWidth();
            }
            if(ejeX <= 0){
                ejeX = 1;
            }
    }
    public static void setVelocidadBandeja(int velocidadNueva){
        velocidadBandeja = velocidadNueva;
    }

    public int getEjeY() {
        return ejeY;
    }

    public int getEjeX() {
        return ejeX;
    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }
}
