/**
 * Created by Vlad on 10/27/13.
 */
package com.example.morse;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

//TODO hide SurfaceView
public class MainActivity extends Activity implements SurfaceHolder.Callback{
    public EditText msg;
    public Button button;
    public Button receive;
    public Button receiveCam;
    private TextView valueCam;
    private boolean bool = false;
    private static Camera cam = null;
    public static SurfaceView preview;
    public static SurfaceHolder mHolder;

    public static void led_on(){
        if(cam == null) cam = Camera.open();
        Parameters params = cam.getParameters();
        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
        cam.setParameters(params);
        cam.startPreview();
    }

    public static void led_off(){
        Parameters params = cam.getParameters();
        params.setFlashMode(Parameters.FLASH_MODE_OFF);
        cam.setParameters(params);
        cam.stopPreview();
    }

    private void setup_elements(){
        msg = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        receive = (Button) findViewById(R.id.button2);
        receiveCam = (Button) findViewById(R.id.button3);
        valueCam = (TextView) findViewById(R.id.textView);
    }

    private void setup_buttons(){
        receiveCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this,Test.class);
                //startActivity(intent);

                try {
                    cam.setPreviewDisplay(mHolder);

                    Parameters params = cam.getParameters();
                    //params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    //TODO lower picture resolution
                    params.setPreviewFrameRate(25); // !De testat
                    cam.setParameters(params);
                    cam.startPreview();
                    cam.setPreviewCallback(new Camera.PreviewCallback() {
                        @Override
                        public void onPreviewFrame(byte[] bytes, Camera camera) {
                            Log.d("Bytes size", String.valueOf(bytes.length));
                            long val = 0;
                            for(int i = 0;i<bytes.length;i=i+3){
                                val = bytes[i] + val;
                            }
                            Log.d("VAL",String.valueOf(val));
                            val = val / (bytes.length / 3);
                            Log.d("VAL",String.valueOf(val));
                            valueCam.setText(String.valueOf(val));
                    /*
                                val - average value from current frame
                                default_val - default luminence value

								if( val > default_val) {
									i++; // no of frames of light detected
									if( j != 0) {
										if( j == ...) put letter_space;
										if( j == ...) put word_space;
										j = 0;
									}
								else {
									j++; // no of frames of "nothing" detected
									if( i !=0 ) {
										if( i == ...) put point;
										if( i == ...) put line;
										i = 0;
									}
								}
                    * */
                            Log.d("tag", "TEST");
                        }
                    });

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FromMorseSensor.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cam.stopPreview();
                String message = String.valueOf(msg.getText());
                System.out.println(message);
                ToMorse tm = new ToMorse();
                try {
                    tm.transform(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("error in ToMorse");
                }
                led_off();
                /*bool = !bool;
                if(bool == true){
                    if(cam == null) cam = Camera.open();
                    Parameters params = cam.getParameters();
                    params.setFlashMode(Parameters.FLASH_MODE_TORCH);
                    cam.setParameters(params);
                    cam.startPreview();
                }
                else{
                    Parameters params = cam.getParameters();
                    params.setFlashMode(Parameters.FLASH_MODE_OFF);
                    cam.setParameters(params);
                    cam.stopPreview();
                    //cam.release();
                }*/
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup_elements();

        preview = (SurfaceView) findViewById(R.id.surfaceView);
        mHolder = preview.getHolder();
        mHolder.addCallback(this);

        cam = Camera.open();
        try {
            cam.setPreviewDisplay(mHolder);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setup_buttons();

    }

    protected void onStop(){
        super.onStop();
        //if(cam != null) cam.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add("Delays").setIntent(new Intent(MainActivity.this,Settings.class));
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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mHolder = holder;
        try {
            cam.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //TODO resolve bug landscape mode
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cam.stopPreview();
        mHolder = null;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
