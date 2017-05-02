package com.example.lp.androidexam;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class BlueToothMenu extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayAdapter<String> listAdapter;
    ListView listView;
    Set<BluetoothDevice> devicesArray;
    BluetoothAdapter btAdapter;
    ArrayList<String> pairedDevices;
    IntentFilter filter;
    BroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_blue_tooth_menu);
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
//        listView =(ListView)findViewById(R.id.listView);
//        listView.setOnClickListener();
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,0);
        listView.setAdapter(listAdapter);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = new ArrayList<>();
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


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
                pairedDevices.add(device.getName());
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
//            ConnectThread connect = new ConnectThrea
        }
        else
        {
            Toast.makeText(this, "Device is not Paired", Toast.LENGTH_SHORT).show();
        }

    }
}
