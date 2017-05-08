package com.example.lp.androidexam;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LP on 07-05-2017.
 */

public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice>{
    private LayoutInflater mLayoutInflater;
    private ArrayList<BluetoothDevice> mDevices;
    private int mViewReesourceId;


    public DeviceListAdapter(Context _context, int _tvResourceId, ArrayList<BluetoothDevice> _devices)
    {
        super(_context,_tvResourceId,_devices);
        mDevices = _devices;
        mLayoutInflater =(LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewReesourceId = _tvResourceId;
    }

    public View getView(int _pos, View _convectView, ViewGroup Parent)
    {
        _convectView = mLayoutInflater.inflate(mViewReesourceId,null);
        BluetoothDevice device = mDevices.get(_pos);
        if(device != null)
        {
            TextView deviceName = (TextView)_convectView.findViewById(R.id.tvDeviceName);
            TextView deviceAdress = (TextView)_convectView.findViewById(R.id.tvDeviceAddress);

            if(deviceName != null)
            {
                deviceName.setText(device.getName());
            }
            if(deviceAdress != null)
            {
                deviceAdress.setText(device.getAddress());
            }
        }
        return _convectView;
    }
}
