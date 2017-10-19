package com.example.riris.accelerometerexample;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Date;


public class MainActivity extends Activity implements SensorEventListener {

    private int flagDuduk = 0;
    private int flagNaikMontor = 0;
    private int flagNaikMobil = 0;
    private int tesflagDuduk = 0;
    private int tesflagNaikMontor = 0;
    private int tesflagNaikMobil = 0;
    private int counttesduduk = 0;
    private int counttesnaikmontor = 0;
    private int counttesnaikmobil = 0;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;

    private Date dateAwal = new Date();
    private Date dateAkhir = new Date();

    private String temp = "";
    private String discover = "";
    private String host = "http://192.168.8.101:8000/PKJS1/welcome/";

    private float vibrateThreshold = 0;

    private TextView currentX, currentY, currentZ;

    public Vibrator v;

    BluetoothAdapter bluetoothAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            vibrateThreshold = accelerometer.getMaximumRange() / 2;
        } else {
            // fai! we dont have an accelerometer!
        }
        //initialize vibration
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        registerReceiver(ActionFoundReceiver,new IntentFilter(BluetoothDevice.ACTION_FOUND));
    }

    public void initializeViews() {
        currentX = (TextView) findViewById(R.id.currentX);
        currentY = (TextView) findViewById(R.id.currentY);
        currentZ = (TextView) findViewById(R.id.currentZ);

        Button buttonduduk = (Button) findViewById(R.id.dudukaja);
        buttonduduk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flagDuduk = 1;
                flagNaikMontor = 0;
                flagNaikMobil = 0;
                tesflagDuduk = 0;
                tesflagNaikMontor = 0;
                tesflagNaikMobil = 0;
            }
        });

        Button buttonNaikMontor = (Button) findViewById(R.id.naikmontor);
        buttonNaikMontor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flagDuduk = 0;
                flagNaikMontor = 1;
                flagNaikMobil = 0;
                tesflagDuduk = 0;
                tesflagNaikMontor = 0;
                tesflagNaikMobil = 0;
            }
        });
        Button buttonNaikMobil = (Button) findViewById(R.id.naikmobil);
        buttonNaikMobil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flagDuduk = 0;
                flagNaikMontor = 0;
                flagNaikMobil = 1;
                tesflagDuduk = 0;
                tesflagNaikMontor = 0;
                tesflagNaikMobil = 0;
            }
        });

        Button tesbuttonduduk = (Button) findViewById(R.id.tesdudukaja);
        tesbuttonduduk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flagDuduk = 0;
                flagNaikMontor = 0;
                flagNaikMobil = 0;
                tesflagDuduk = 1;
                tesflagNaikMontor = 0;
                tesflagNaikMobil = 0;
                bluetoothAdapter.startDiscovery();
            }
        });

        Button tesbuttonNaikMontor = (Button) findViewById(R.id.tesnaikmontor);
        tesbuttonNaikMontor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flagDuduk = 0;
                flagNaikMontor = 0;
                flagNaikMobil = 0;
                tesflagDuduk = 0;
                tesflagNaikMontor = 1;
                tesflagNaikMobil = 0;
            }
        });
        Button tesbuttonNaikMobil = (Button) findViewById(R.id.tesnaikmobil);
        tesbuttonNaikMobil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flagDuduk = 0;
                flagNaikMontor = 0;
                flagNaikMobil = 0;
                tesflagDuduk = 0;
                tesflagNaikMontor = 0;
                tesflagNaikMobil = 1;
            }
        });

    }

    //onResume() register the accelerometer for listening the events
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //onPause() unregister the accelerometer for stop listening the events
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // clean current values
        //displayCleanValues();

        // display the current x,y,z accelerometer values

        try {
            displayCurrentValues();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // mendapat nilai xyz aja
        deltaX = Math.abs(event.values[0]);
        deltaY = Math.abs(event.values[1]);
        deltaZ = Math.abs(event.values[2]);
       }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void displayValue() {
        currentX.setText(Float.toString(deltaX));
        currentY.setText(Float.toString(deltaY));
        currentZ.setText(Float.toString(deltaZ));
    }

    private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            Log.i("Debug :", "cek discover");
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                discover += device.getName() + "_";
                //btArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                //btArrayAdapter.notifyDataSetChanged();
                Log.i("Device :", discover);
            }
        }};

    // display the current x,y,z accelerometer values
    public void displayCurrentValues() throws IOException {
        if (flagDuduk==1){
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(host + "tambah_data/" + Float.toString(deltaX) + "/" + Float.toString(deltaY) + "/" + Float.toString(deltaZ)+ "/1" );
            HttpResponse response = null;
            response = httpClient.execute(httpget);
            if(response.getStatusLine().getStatusCode()==200){
                String server_response = EntityUtils.toString(response.getEntity());
                Log.i("Server response", server_response );
            } else {
                Log.i("Server response", "Failed to get server response" );
            }
            displayValue();
        }
        if (flagNaikMontor ==1){
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(host + "tambah_data/" + Float.toString(deltaX) + "/" + Float.toString(deltaY) + "/" + Float.toString(deltaZ)+ "/2" );
            HttpResponse response = null;
            response = httpClient.execute(httpget);
            if(response.getStatusLine().getStatusCode()==200){
                String server_response = EntityUtils.toString(response.getEntity());
                Log.i("Server response", server_response );
            } else {
                Log.i("Server response", "Failed to get server response" );
            }
            displayValue();
        }
        if (flagNaikMobil ==1){
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(host + "tambah_data/" + Float.toString(deltaX) + "/" + Float.toString(deltaY) + "/" + Float.toString(deltaZ)+ "/3" );
            HttpResponse response = null;
            response = httpClient.execute(httpget);
            if(response.getStatusLine().getStatusCode()==200){
                String server_response = EntityUtils.toString(response.getEntity());
                Log.i("Server response", server_response );
            } else {
                Log.i("Server response", "Failed to get server response" );
            }
            displayValue();
        }

        if (tesflagDuduk==1){

            if (counttesduduk < 10){
                counttesduduk = counttesduduk+1;
                // tambah ke temp
                temp += "temp[]=" + Float.toString(deltaX)+"_"+Float.toString(deltaY)+ "_"+Float.toString(deltaZ) + "_" + "1" +"&";
                //display value
                displayValue();
            }
            else {
                //kirim temp
                bluetoothAdapter.startDiscovery();
                HttpClient httpClient = new DefaultHttpClient();
                String url = host +  "cek_duduk/?" + temp + "discover=" + discover;
                //Log.i("" , url);
                HttpGet httpget = new HttpGet(url);
                HttpResponse response = null;
                response = httpClient.execute(httpget);
                if(response.getStatusLine().getStatusCode()==200){
                    String server_response = EntityUtils.toString(response.getEntity());
                    Log.i("Server response", server_response );
                } else {
                    Log.i("Server response", "Failed to get server response" );
                }
                //temp di kosong kan
                temp = "";
                // count test duduk di nolkan
                counttesduduk = 0;
                // dispaly
                displayValue();
                //bluetoothAdapter.startDiscovery();
            }
        }
        if (tesflagNaikMontor==1){
            if (counttesnaikmontor < 10){
                counttesnaikmontor = counttesnaikmontor+1;
                // tambah ke temp
                temp += "temp[]=" + Float.toString(deltaX)+"_"+Float.toString(deltaY)+ "_"+Float.toString(deltaZ) + "_" + "1" +"&";
                //display value
                displayValue();
            }
            else {
                //kirim temp
                bluetoothAdapter.startDiscovery();
                HttpClient httpClient = new DefaultHttpClient();
                String url = host + "cek_naikmontor/?" + temp + "discover=" + discover;
                HttpGet httpget = new HttpGet(url);
                HttpResponse response = null;
                response = httpClient.execute(httpget);
                if(response.getStatusLine().getStatusCode()==200){
                    String server_response = EntityUtils.toString(response.getEntity());
                    Log.i("Server response", server_response );
                } else {
                    Log.i("Server response", "Failed to get server response" );
                }
                //temp di kosong kan
                temp = "";
                // count test duduk di nolkan
                counttesnaikmontor = 0;
                // dispaly
                displayValue();
            }
        }
        if (tesflagNaikMobil==1){
            if (counttesnaikmobil < 10){
                counttesnaikmobil = counttesnaikmobil+1;
                // tambah ke temp
                temp += "temp[]=" + Float.toString(deltaX)+"_"+Float.toString(deltaY)+ "_"+Float.toString(deltaZ) + "_" + "1" +"&";
                //display value
                displayValue();
            }
            else {
                //kirim temp
                bluetoothAdapter.startDiscovery();
                HttpClient httpClient = new DefaultHttpClient();
                String url = host + "cek_naikmobil/?" + temp + "discover=" + discover;
                HttpGet httpget = new HttpGet(url);
                HttpResponse response = null;
                response = httpClient.execute(httpget);
                if(response.getStatusLine().getStatusCode()==200){
                    String server_response = EntityUtils.toString(response.getEntity());
                    Log.i("Server response", server_response );
                } else {
                    Log.i("Server response", "Failed to get server response" );
                }
                //temp di kosong kan
                temp = "";
                // count test duduk di nolkan
                counttesnaikmobil = 0;
                // dispaly
                displayValue();
            }
        }

        else {
            displayValue();
        }

    }
}
