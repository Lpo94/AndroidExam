package com.example.lp.androidexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;


/**
 * Created by Shark on 16-05-2017.
 */

public class ModeMenu extends Fragment {

    Button singleplayer, bluetooth;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mode_menu, container, false);

        singleplayer = (Button) view.findViewById(R.id.btnSingleP);
        bluetooth = (Button) view.findViewById(R.id.btnBluetooth);

        singleplayer.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        ((MainActivity)getActivity()).startGame(GameState.SinglePlayer);
                        view = v;
                        buttonClicked(v, "singleplayer");

                    }
                }
        );

        bluetooth.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        view = v;
                        buttonClicked(view, "bluetooth");

                    }
                }
        );

        return view;
    }

    public void buttonClicked(View v, String button) {
        switch (button) {
            case "singlePlayer":
                singleplayer.setVisibility(view.INVISIBLE);
                bluetooth.setVisibility(view.INVISIBLE);

                break;

            case "bluetooth":
                StaticValues.fragment = new Bluetooth_menu();
                StaticValues.fm = getActivity().getSupportFragmentManager();
                StaticValues.ft = StaticValues.fm.beginTransaction();
                StaticValues.ft.replace(R.id.fragment7, StaticValues.fragment);
                StaticValues.ft.commit();
                singleplayer.setVisibility(view.INVISIBLE);
                bluetooth.setVisibility(view.INVISIBLE);
                break;
            default:
                break;
        }
    }
}