package com.example.lp.androidexam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;

/**
 * Created by Shark on 14-05-2017.
 */

public class Bluetooth_menu extends Fragment {
    public Button Server, Connect, Pair, Back;
    public View view;
    HowTo howTo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bluetooth_menu, container, false);
        Server = (Button) view.findViewById(R.id.Server_button);
        Connect = (Button) view.findViewById(R.id.Connect_button);
        Pair = (Button) view.findViewById(R.id.Pair_button);
        Back = (Button) view.findViewById(R.id.BackM_button);

        Server.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        view = v;
                        buttonClicked(v, "Server");
                    }
                }
        );

        Connect.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View _view)
                    {
                        view = _view;
                        buttonClicked(view, "Connect");
                    }
                }
        );

        Pair.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        view = v;
                        buttonClicked(v, "Pair");
                    }
                }
        );

        Back.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        view = v;
                        buttonClicked(v, "Back");
                    }
                }
        );
        return view;
    }

    public void Show()
    {
        view.setVisibility(View.VISIBLE);
    }

    public void buttonClicked(View v, String button)
    {
        switch (button) {
            case "Server":
                StaticValues.fragment = new HowTo();
                StaticValues.fm = getActivity().getSupportFragmentManager();
                StaticValues.ft = StaticValues.fm.beginTransaction();
                StaticValues.ft.replace(R.id.fragment7, StaticValues.fragment);
                StaticValues.ft.commit();
                Server.setVisibility(view.INVISIBLE);
                Connect.setVisibility(view.INVISIBLE);
                Pair.setVisibility(view.INVISIBLE);
                Back.setVisibility(view.INVISIBLE);
                break;

            case "Connect":
                StaticValues.fragment = new HowTo();
                StaticValues.fm = getActivity().getSupportFragmentManager();
                StaticValues.ft = StaticValues.fm.beginTransaction();
                StaticValues.ft.replace(R.id.fragment7, StaticValues.fragment);
                StaticValues.ft.commit();
                Server.setVisibility(view.INVISIBLE);
                Connect.setVisibility(view.INVISIBLE);
                Pair.setVisibility(view.INVISIBLE);
                Back.setVisibility(view.INVISIBLE);
                break;

            case "Pair":
                StaticValues.fragment = new HowTo();
                StaticValues.fm = getActivity().getSupportFragmentManager();
                StaticValues.ft = StaticValues.fm.beginTransaction();
                StaticValues.ft.replace(R.id.fragment7, StaticValues.fragment);
                StaticValues.ft.commit();
                Server.setVisibility(view.INVISIBLE);
                Connect.setVisibility(view.INVISIBLE);
                Pair.setVisibility(view.INVISIBLE);
                Back.setVisibility(view.INVISIBLE);
                break;

            case "Back":
                StaticValues.fragment = new Menu();
                StaticValues.fm = getActivity().getSupportFragmentManager();
                StaticValues.ft = StaticValues.fm.beginTransaction();
                StaticValues.ft.replace(R.id.fragment7, StaticValues.fragment);
                StaticValues.ft.commit();
                Server.setVisibility(view.INVISIBLE);
                Connect.setVisibility(view.INVISIBLE);
                Pair.setVisibility(view.INVISIBLE);
                Back.setVisibility(view.INVISIBLE);
                break;

            default:
                break;
        }
    }
}
