package com.example.morse;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.hardware.Camera.Parameters;
import java.io.IOException;

public class Test extends Activity implements SurfaceHolder.Callback{
    private static Camera cam = null;
    public static SurfaceView preview;
    public static SurfaceHolder mHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        preview = (SurfaceView) findViewById(R.id.surfaceView);
        mHolder = preview.getHolder();
        mHolder.addCallback(this);

        cam = Camera.open();

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
                    /*
                        Aici o sa calculam media luminozitatii a imaginii. Cum media trebuie calculata in 40ms, media ar
                        trebui calculata intr-un thread separat.
                    * */
                    Log.d("tag", "TEST");
                }
            });

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


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

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mHolder = surfaceHolder;
        try {
            cam.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        cam.stopPreview();
        mHolder = null;
    }
}
