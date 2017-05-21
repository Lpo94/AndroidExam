package com.example.lp.androidexam;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import java.util.UUID;


public class MainActivity extends AppCompatActivity{



    @Override
    protected void onPause() {
        super.onPause();
        StaticValues.Instance().baggroundMusic.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StaticValues.Instance().baggroundMusic.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        StaticValues.Instance().staticContext = this;

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        StaticValues.Instance().SCREEN_WIDTH = dm.widthPixels;
        StaticValues.Instance().SCREEN_HEIGHT = dm.heightPixels;







        if(StaticValues.Instance().baggroundMusic == null) {
            StaticValues.Instance().baggroundMusic = MediaPlayer.create(this, R.raw.menu);
            StaticValues.Instance().baggroundMusic.setLooping(true);
            StaticValues.Instance().baggroundMusic.setVolume(0.1f, 0.1f);
            StaticValues.Instance().baggroundMusic.start();
        }

         StaticValues.Instance().BA = BluetoothAdapter.getDefaultAdapter();



//        btnStartConnection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDevice = BA.getRemoteDevice(mDeviceAdress);
//                startBTConenction(mDevice,MY_UUID_INSECURE);
//            }
//        });

//        btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                byte[] bytes = editText.getText().toString().getBytes(Charset.defaultCharset());
//                mBTService.write(bytes);
//                messageBoard +=editText.getText() + "\n";
//                incomingMessages.setText(messageBoard);
//                editText.setText("");
//            }
//        });





        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(mReceiver, filter);



        if(StaticValues.Instance().BA == null)
        {
            Toast.makeText(this, "No Bluetooth Detected", Toast.LENGTH_SHORT).show();
            // Implenter så man ryger tilbage til menu
        }
        else if(!StaticValues.Instance().BA.isEnabled())
        {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 1);
            Toast.makeText(getApplicationContext(), "Turned on",Toast.LENGTH_LONG).show();
        }
        else{
            StaticValues.Instance().mBTService = new BTService(this);

        }
    }



    public void startBTConenction(BluetoothDevice device, UUID uuid)
    {
        StaticValues.Instance().mBTService.startClient(device,uuid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED)
        {
            Toast.makeText(this, "Bluetooth needs to be enabled", Toast.LENGTH_LONG).show();

        }
    }




    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

                if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
            {
                //Fix liste
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {


            }
            else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))
            {
                if(StaticValues.Instance().BA.getState() == StaticValues.Instance().BA.STATE_OFF)
                {
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, 1);
                }
            }
            else if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action))
            {


                StaticValues.Instance().connectedDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                StaticValues.Instance().connectedDeviceAdress = StaticValues.Instance().connectedDevice.getAddress();

                Toast.makeText(context, "Connection Succes with " +StaticValues.Instance().connectedDevice.getName(), Toast.LENGTH_SHORT).show();

                startGame(GameState.BluetoothMultiplayer);
            }

            else if(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action))
                {
                    Toast.makeText(context, "Disconnected Request with " +StaticValues.Instance().connectedDevice.getName(), Toast.LENGTH_SHORT).show();
                }




        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void startGame(GameState _gameState){
        StaticValues.Instance().gameState = _gameState;
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();

    }

}