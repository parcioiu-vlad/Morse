package com.example.morse;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends Activity {

    EditText letter,pnt,ln,word;
    Button back,save;
    private void setupElements(){
        letter = (EditText) findViewById(R.id.editText);
        pnt = (EditText) findViewById(R.id.editText2);
        ln = (EditText) findViewById(R.id.editText3);
        word = (EditText) findViewById(R.id.editText4);
        back = (Button) findViewById(R.id.button);
        save = (Button) findViewById(R.id.button2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        setupElements();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delays.letterSpace = Short.valueOf(String.valueOf(letter.getText()));
                Delays.point = Short.valueOf(String.valueOf(pnt.getText()));
                Delays.line = Short.valueOf(String.valueOf(ln.getText()));
                Delays.wordSpace = Short.valueOf(String.valueOf(word.getText()));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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


}
