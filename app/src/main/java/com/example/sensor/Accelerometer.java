package com.example.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.io.FileNotFoundException;
import java.io.IOException;

// Class Accelerometer
public class Accelerometer {

    public interface Listener{
        // Method to get x , y & z
        void onTranslation(float last_x, float last_y, float last_z) throws IOException;

    }
    // Listener variable
    private Listener listener;

    public void setListener(Listener l){
        listener = l;
    }
    // Other Variables from hardware packages

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    // Create a constructor to take arguments
    Accelerometer(Context context){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(listener != null){
                    try {
                        listener.onTranslation(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

    }
    // register Sensor data = start
    public void register(){
        sensorManager.registerListener(sensorEventListener, sensor, sensorManager.SENSOR_DELAY_NORMAL);

    }
    // Unregister = stop
    public void unregister(){
        sensorManager.unregisterListener(sensorEventListener);
    }
}
