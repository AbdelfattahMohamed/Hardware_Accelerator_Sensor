package com.example.sensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    // there was a mistake in name of gryscope -> Gyroscope
    //  variables
    private Accelerometer accelerometer;
    private Gryscope gryscope;
    private static final String FILE_NAME = "Results.txt";
    private static final int PERMISSION_REQUEST_CODE = 100;
    // TXT GUI
    TextView translation_x;
    TextView translation_y;
    TextView translation_z;
    // TXT GUI
    TextView rotation_x;
    TextView rotation_y;
    TextView rotation_z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // GUI
        translation_x = (TextView) findViewById(R.id.translation_x);
        translation_y = (TextView) findViewById(R.id.translation_y);
        translation_z = (TextView) findViewById(R.id.translation_z);
        // GUI
        rotation_x = (TextView) findViewById(R.id.rotation_x);
        rotation_y = (TextView) findViewById(R.id.rotation_y);
        rotation_z = (TextView) findViewById(R.id.rotation_z);


        // Here Take Objects
        accelerometer = new Accelerometer(this);
        gryscope = new Gryscope(this);

        // Listener for accelerometer
        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float last_x, float last_y, float last_z) throws IOException {
                /*
                FILE STREAM ( GET DATA & PASS IT IN FILE )
                 */
                // Variables
                translation_x.setText(last_x + "");
                translation_y.setText(last_x + "");
                translation_z.setText(last_x + "");
                // COMMA SEPARATED DATA FILES
                String text = last_x + "," + last_y + "," + last_z + ",";
                byte[] text_byte = text.getBytes();
                // WRITE HERE
                writeToFile(text_byte);
            }
        });

        // Listener for gyroscope
        gryscope.setListener(new Gryscope.Listener() {
            @Override
            public void onRotation(float rotate_x, float rotate_y, float rotate_z) {
                /*
                FILE STREAM ( GET DATA & PASS IT IN FILE )
                 */
                // Variables
                rotation_x.setText(rotate_x + "");
                rotation_y.setText(rotate_y + "");
                rotation_z.setText(rotate_z + "");
                // Array of objects
                String text = rotate_x + "," + rotate_y + "," + rotate_z + "\n";
                byte[] text_byte = text.getBytes();
                // Write DATA
                writeToFile(text_byte);

            }
        });
    }

    // Methods start , capture and end
    @Override
    protected void onResume() {
        super.onResume();

        accelerometer.register();
        gryscope.register();
    }

    @Override
    protected void onPause() {
        super.onPause();

        accelerometer.unregister();
        gryscope.unregister();
    }

    /*
    FILE STREAM APPEND DATA
     */
    public void writeToFile(byte[] text){
        try {
            FileOutputStream fileOutputStream = openFileOutput("result.txt", MODE_APPEND);
            fileOutputStream.write(text);
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    END
     */

}

