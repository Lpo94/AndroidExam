package com.example.lp.androidexam;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.logging.LogRecord;

import static android.content.ContentValues.TAG;

public class BlueToothMenu extends AppCompatActivity implements AdapterView.OnItemClickListener {
    static Context _context;
    ArrayAdapter<String> listAdapter;
    ListView listView;
    Set<BluetoothDevice> devicesArray;
    public static BluetoothAdapter btAdapter;
    ArrayList<String> pairedDevices;
    ArrayList<BluetoothDevice> devices;
    public static final int SUCCES_CONNECT = 3;
    public static final int MESSAGE_READ = 0;
    public static final int MESSAGE_WRITE = 1;
    public static final int MESSAGE_TOAST = 2;
    public static final String TAG = "MY_APP_DEBUG_TAG";
    IntentFilter filter;
    BroadcastReceiver receiver;
    public static Handler mHandler = new Handler(){


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case SUCCES_CONNECT:
                    ConnectedThread connectThread = new ConnectedThread((BluetoothSocket)msg.obj);
                    connectThread.start();
                    String s = "Succefully connected";
                    Toast.makeText(_context, s, Toast.LENGTH_SHORT).show();

                    connectThread.write(s.getBytes());
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[])msg.obj;
                    String st = new String(readBuf);
                    Toast.makeText(_context, st, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_blue_tooth_menu);
        init();
        if(btAdapter == null)
        {
            Toast.makeText(this, "No Bluetooth decected", Toast.LENGTH_SHORT).show();
        }
        else{
            if(!btAdapter.isEnabled())
            {
                turnOnBT();
            }

            getPairedDevices();
            startDiscovery();
        }
    }

    private void init(){
        _context = this;
        listView =(ListView)findViewById(R.id.listView);
//        listView.setOnClickListener();
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,0);
        listView.setAdapter(listAdapter);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = new ArrayList<>();
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        devices = new ArrayList<>();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    devices.add(device);

                    String s = "";

                    for(int x = 0; x < pairedDevices.size(); x++)
                        {
                            if(device.getName().equals(pairedDevices.get(x)))
                            {
                                s = "(Paired)";
                                break;
                            }
                        }

                    listAdapter.add(device.getName()+" "+s+"\n"+device.getAddress());
                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
                {

                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
                {

                }
                else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))
                {
                    if(btAdapter.getState() == btAdapter.STATE_OFF)
                    {
                        turnOnBT();
                    }

                }

            }
        };
        registerReceiver(receiver,filter);
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(receiver,filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver,filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver,filter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Bluetooth needs to be enabled", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void getPairedDevices()
    {
        devicesArray = btAdapter.getBondedDevices();
        if(devicesArray.size()>0)
        {
            for(BluetoothDevice device:devicesArray)
            {
//                pairedDevices.add(device.getName());
            }
        }
    }
    private void turnOnBT()
    {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent,1);
    }

    private void startDiscovery()
    {
        btAdapter.cancelDiscovery();
        btAdapter.startDiscovery();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(btAdapter.isDiscovering())
        {
            btAdapter.cancelDiscovery();
        }
        if(listAdapter.getItem(position).contains("(Paired)"))
        {
            BluetoothDevice selectedDevice =devices.get(position);
            ConnectThread connect = new ConnectThread(selectedDevice);
            connect.start();
        }
        else
        {
            Toast.makeText(this, "Device is not Paired", Toast.LENGTH_SHORT).show();
        }

    }

}



