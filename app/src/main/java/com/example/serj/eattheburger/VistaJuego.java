package com.example.serj.eattheburger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Marco on 26/2/15.
 */
public class VistaJuego extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener{
    private Bitmap bmp;
    private SensorManager sensorManager = null;
    private HebraJuego hebraJuego;
    private Bandeja bandeja;
    private final int COLUMNAS = 6;
    private int vidas = 3;
    private int puntuacion = 0;
    public static int nivel = 1;
    private int ejeXColumna;
    private int posXColumna;
    private Comida[] comidas;
    public static int screenWidth, screenHeight;
    private Random tipo, numColumna;
    private Canvas canvas;
    float ax,ay,az; // these are the acceleration in x,y and z axis
    public VistaJuego(Context context) {
        super(context);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bandeja);
        getHolder().addCallback(this);
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
       // OBTENER DIMENSIONES PANTALLA
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        screenWidth = p.x;
        screenHeight = p.y;

        hebraJuego = new HebraJuego(this);
        //fondo = fondo...
        bandeja = new Bandeja(getContext());
        bandeja.setPosicion((screenWidth/2)-(bmp.getWidth()/2));// MENOS LA MITAD DEL TAMAÑO DE LA BANDEJA
        ejeXColumna = screenWidth/COLUMNAS;
        //generar comidas
        tipo = new Random();
        numColumna = new Random();

        nuevoNivel();
    }

    // SurfaceView

    @Override
    public void draw(Canvas canvas) {
        this.canvas = canvas;
        super.draw(canvas);
        canvas.drawColor(Color.GRAY);
        bandeja.dibujar(canvas);
        // Dibujar comidas for
        if(vidas > 0) {
            for (Comida c : comidas) {
                c.dibujar(canvas);


            }
        }
        for (int i = 0; i<comidas.length; i++){
            if(comidas[i].recogido(bandeja)){
                // Y QUE PASA SEGUN EL TIPO
                if(!comidas[i].isBueno()){
                    //sumas puntuacion
                    puntuacion = puntuacion + 10;
                    Log.e(Integer.toString(puntuacion)," - Puntuacion");

                }else{
                    vidas = vidas - 1;
                    if(vidas <=0){
                        vidas = 0;
                    }
                    Log.e(Integer.toString(vidas)," - VIDAS");
                }
                comidas = ArrayUtils.removeElement(comidas, comidas[i]);

                //Log.e("LOOL","ASUMBAWE"+comidas.length);
            }

        }
        for(int i = 0; i<comidas.length; i++){
            if(comidas[i].getEjeY() >= screenHeight){
                comidas = ArrayUtils.removeElement(comidas, comidas[i]);
            }
        }
        if(comidas.length==0){
            //Log.e("FIN nivel","HOLA");
            if(vidas > 0) {
                nivel++;
                nuevoNivel();
            }else if (vidas <=0){
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setTextSize(60);

                canvas.drawText("¡HAS PERDIDO!",(int) (screenWidth*0.015),(int)(screenHeight*0.45), paint);
            }

        }
       // Log.e("TIENE"," "+comidas.length);
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setTextSize(40);
        canvas.drawText("Puntuacion: " + Integer.toString(puntuacion),(int) (screenWidth*0.015),(int)(screenHeight*0.05), paint);
        canvas.drawText("NIVEL: " + Integer.toString(nivel), (int)(screenWidth*0.78),(int)(screenHeight*0.05), paint);
        canvas.drawText("Vidas: "+Integer.toString(vidas), (int) (screenWidth*0.015),(int)(screenHeight*0.1), paint);
        if (vidas <=0){
            Paint paint2 = new Paint();
            paint2.setColor(Color.RED);
            paint2.setTextSize(110);
            nuevoNivel();
            canvas.drawText("¡HAS PERDIDO!",(int) (screenWidth*0.015),(int)(screenHeight*0.45), paint2);
        }
    }

    // Callback

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Register this class as a listener for the accelerometer sensor
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
        hebraJuego.setFuncionando(true); //Si el juego esta funcionando, ejecutamos la hebra
        hebraJuego.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Unregister the listener
        sensorManager.unregisterListener(this);
    }

    // SensorEventListener

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];
            double escala = screenWidth / 10.36; // CAMBIAR A OTRO VALOR (19,36)
            /***************************************/
            // -10.36......10.36
            //         0 -> BANDEJA X = mitad pantalla
            // 30px ancho -> posicion de la bandeja va a ser un numero concreto. Cuando compares
            //Log.e("Ancho",String.valueOf(width));
            //int xReal= ((int)escala * (int) ax)+(screenWidth/2); // HAY QUE TENER EN CUENTA EL ANCHO DEL BMP
            bandeja.setDireccion(ax);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void nuevoNivel(){
        //switch (nivel) {

            //case 1:

                comidas = new Comida[10];
                for (int i = 0; i < comidas.length; i++) {

                    comidas[i] = new Comida(tipo.nextInt(2), getContext()); // TIPO DE COMIDA
                    posXColumna = ejeXColumna * numColumna.nextInt(COLUMNAS) + 0;
                    comidas[i].setPosicionX(posXColumna); // EN QUE COLUMNA
                    comidas[i].setVelocidad(nivel);
                    comidas[i].setPosicionY();
                }




               // break;
        //}

    }


}
