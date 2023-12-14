package com.example.sequencegame;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnTouchListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final float ACCELEROMETER_THRESHOLD = 10.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        CircleView circleView = new CircleView(this);

        linearLayout.addView(circleView);
        linearLayout.setOnTouchListener(this); //Set onTouchListener for the layout

        setContentView(linearLayout);

        //Initialize accelerometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            //Check if the phone is tilted
            if (Math.sqrt(x * x + y * y + z * z) > ACCELEROMETER_THRESHOLD) {
                //Start the NextActivity
                Intent intent = new Intent(MainActivity.this, ActivityPlay.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Do nothing for this example
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister the sensor listener when the activity is destroyed
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //Start the NextActivity when the screen is touched
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(MainActivity.this, ActivityPlay.class);
            startActivity(intent);
        }
        return true;
    }
}
