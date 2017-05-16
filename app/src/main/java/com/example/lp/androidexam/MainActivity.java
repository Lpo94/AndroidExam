package com.example.lp.androidexam;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

//implements AdapterView.OnItemClickListener

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private BluetoothAdapter BA;
    private Button btnSend, btnStartConnection;
    private TextView incomingMessages;
    private StringBuilder messages;
    private EditText editText;
    private BTService mBTService;
    private Set<BluetoothDevice> devicesArray;
    private ArrayList deviceList;
    private ArrayList pairedDevices;
    private ArrayAdapter<String> newDeviceAdapter;
    private String messageBoard = "";
    private Context context;

    private BluetoothDevice mDevice;
    private String mDeviceAdress;
    private static final UUID MY_UUID_INSECURE =UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    ListView lv;

    @Override
    protected void onPause() {
        super.onPause();
        StaticValues.baggroundMusic.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StaticValues.baggroundMusic.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
/*        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

        setContentView(new GameView(this));*/



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(StaticValues.baggroundMusic == null) {
            StaticValues.baggroundMusic = MediaPlayer.create(this, R.raw.menu);
            StaticValues.baggroundMusic.setLooping(true);
            StaticValues.baggroundMusic.setVolume(0.1f, 0.1f);
            StaticValues.baggroundMusic.start();
        }

        BA = BluetoothAdapter.getDefaultAdapter();
        deviceList = new ArrayList();
        pairedDevices = new ArrayList();
        incomingMessages = (TextView)findViewById(R.id.incChat);
        messages = new StringBuilder();

        LocalBroadcastManager.getInstance(this).registerReceiver(msgReciever,new IntentFilter("IncomingMessage"));

        btnStartConnection =(Button)findViewById(R.id.btnStartConnection);
        btnSend = (Button)findViewById(R.id.btnSend);
        editText = (EditText)findViewById(R.id.editText);

        btnStartConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDevice = BA.getRemoteDevice(mDeviceAdress);
                startBTConenction(mDevice,MY_UUID_INSECURE);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] bytes = editText.getText().toString().getBytes(Charset.defaultCharset());
                mBTService.write(bytes);
                messageBoard +=editText.getText() + "\n";
                incomingMessages.setText(messageBoard);
                editText.setText("");
            }
        });


        lv = (ListView)findViewById(R.id.listView);
        lv.setOnItemClickListener(this);
        newDeviceAdapter = new  ArrayAdapter(this,android.R.layout.simple_list_item_1, deviceList);
        lv.setAdapter(newDeviceAdapter);



        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(mReceiver, filter);



        if(BA == null)
        {
            Toast.makeText(this, "No Bluetooth Detected", Toast.LENGTH_SHORT).show();
            // Implenter så man ryger tilbage til menu
        }
        else if(!BA.isEnabled())
        {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 1);
            Toast.makeText(getApplicationContext(), "Turned on",Toast.LENGTH_LONG).show();
        }
    }

    BroadcastReceiver msgReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("theMessage");
            messages.append(text + "\n");

            messageBoard += messages;
            incomingMessages.setText(messageBoard);
        }
    };

    public void startBTConenction(BluetoothDevice device, UUID uuid)
    {
        mBTService.startClient(device,uuid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED)
        {
            Toast.makeText(this, "Bluetooth needs to be enabled", Toast.LENGTH_LONG).show();
            //Skift tilbage til menu
        }
    }


    public  void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }


    public void list(View v){
        devicesArray = BA.getBondedDevices();
        if (devicesArray.size() > 0) {
            for (BluetoothDevice bt : devicesArray)
            {
                deviceList.add(bt.getName() + "\n" + bt.getAddress());
            }
            Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();
        }

        final ArrayAdapter adapter = new  ArrayAdapter(this,android.R.layout.simple_list_item_1, deviceList);

        lv.setAdapter(adapter);
    }


    public  void startDiscovery(View v){
        if(BA.isDiscovering())
        {
            BA.cancelDiscovery();
        }

        int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

        BA.startDiscovery();

//        if(BA.isDiscovering())
//        {
//            Toast.makeText(this, "IS IT WORKING ???", Toast.LENGTH_SHORT).show();
//        }

    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address

                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    newDeviceAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
            {
                //Fix liste
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {


            }
            else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))
            {
                if(BA.getState() == BA.STATE_OFF)
                {
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, 1);
                }
            }
            else if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action))
            {
                Toast.makeText(context, "Connection Succes", Toast.LENGTH_SHORT).show();



            }




        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(BA.isDiscovering())
        {
            BA.cancelDiscovery();
        }

        String info = ((TextView) view).getText().toString();
        mDeviceAdress = info.substring(info.length() - 17);

        mBTService = new BTService(MainActivity.this);


    }
}