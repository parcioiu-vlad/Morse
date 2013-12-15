package com.example.morse;

import android.app.Activity;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vlad on 11/9/13.
 */

//!!!!!!!!!!!!!!!!!!!!!!!! THIS APP WILL CRASH!!!!!!!!!!!!!!!!!!!!!!
public class FromMorseSensor extends Activity {
    TextView sensor;
    TextView value;
    Button button;
    TextView textView;
    float defaultValue =  -1;
    ArrayList<String> code = new ArrayList<String>(); // contains the morse code received
    long startTime = 0;
    long time = 0;
    boolean stop = true;

    private void setupElements(){
        code.add(0,"Testing");
        sensor = (TextView) findViewById(R.id.textView);
        value = (TextView) findViewById(R.id.textView2);
        button = (Button) findViewById(R.id.button);

        //ScrollView scroller = new ScrollView(this);
        textView = (TextView) findViewById(R.id.textView);
        //scroller.addView(textView);

        button.setText("Start");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_sensor);

        setupElements();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop = !stop;
                if(stop == true) {
                    code.clear();
                    textView.setText("");
                    button.setText("Start");
                }
                else{
                    button.setText("Stop");
                }
            }
        });

        SensorManager mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        Sensor LightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(LightSensor != null){
            sensor.setText("Sensor.TYPE_LIGHT Available");

            mySensorManager.registerListener(
                    LightSensorListener,
                    LightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }else{
            sensor.setText("Sensor.TYPE_LIGHT NOT Available");
        }
    }

    private final SensorEventListener LightSensorListener
            = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(stop == false && sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
                value.setText("LIGHT: " + sensorEvent.values[0]);

                time = System.currentTimeMillis() - startTime;

                if(defaultValue == -1) defaultValue = sensorEvent.values[0];

                if( time < 50){
                    code.add(" ");
                    return;
                }
                if( time < 90){
                    code.add(".");
                    return;
                }
                if(time < 130){
                    code.add("-");
                    return;
                }
                if(time < 150){
                    code.add("  ");
                    return;
                }

                textView.append(code.get(code.size()-1)+"/n");
                /*if(sensorEvent.values[0] > defaultValue){
                    //TODO start timer;
                    startTime = System.currentTimeMillis();
                } else{
                    startTime = System.currentTimeMillis();
                }*/
                startTime = System.currentTimeMillis();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
