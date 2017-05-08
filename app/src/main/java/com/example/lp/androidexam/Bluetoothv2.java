package com.example.lp.androidexam;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Bluetoothv2 extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = "MainAcvitity";

    Button btnBTDiscov;
    BluetoothAdapter btAdapter;
    public ArrayList<BluetoothDevice> btDevices = new ArrayList<>();
    public DeviceListAdapter deviceListAdapter;
    ListView listViewDevices;

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
    };

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver2 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED))
            {
                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,BluetoothAdapter.ERROR);
                switch (mode)
                {
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        break;
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        break;

                    case BluetoothAdapter.SCAN_MODE_NONE:
                        break;

                    case BluetoothAdapter.STATE_CONNECTING:
                        break;

                    case BluetoothAdapter.STATE_CONNECTED:
                        break;
                }
            }
        }
    };

    private final BroadcastReceiver mReceiver3 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                btDevices.add(device);
                deviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, btDevices);
                listViewDevices.setAdapter(deviceListAdapter);
            }
        }
    };

    private final BroadcastReceiver mReceiver4 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver1);
        unregisterReceiver(mReceiver2);
        unregisterReceiver(mReceiver3);
        unregisterReceiver(mReceiver4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetoothv2);
        btnBTDiscov = (Button)findViewById(R.id.EnableDiscovery);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        listViewDevices = (ListView)findViewById(R.id.listView);
        btDevices = new ArrayList<>();
        listViewDevices.setOnItemClickListener(this);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mReceiver4,filter);

        if(btAdapter == null)
        {
            Toast.makeText(this, "No Bluetooth detected", Toast.LENGTH_SHORT).show();
        }
        else{
            if(!btAdapter.isEnabled())
            {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent,1);

                IntentFilter BTItent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                registerReceiver(mReceiver1,BTItent);
            }
        }

    }

    public void btnDicover(View View)
    {
        if(btAdapter.isDiscovering())
        {
            btAdapter.cancelDiscovery();
            btAdapter.startDiscovery();

            checkBTPermissions();

            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver3,discoverDevicesIntent);
        }
        if(!btAdapter.isDiscovering())
        {
            checkBTPermissions();
            btAdapter.startDiscovery();

            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver3,discoverDevicesIntent);
        }

    }

    public void btnDiscovery(View view)
    {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(btAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mReceiver2, intentFilter);
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0){
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
            }
            else{
                Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK Version < LOLLIPOP");
            }
        }
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(btAdapter.isDiscovering()) {
            btAdapter.cancelDiscovery();
        }
        String deviceName = btDevices.get(position).getName();
        String deviceAddress = btDevices.get(position).getAddress();

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            btDevices.get(position).createBond();
        }

    }
}
