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
    public Button Server, Connect, Pair, Back, visListe, listView, update, discover, scan, _back;
    public View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bluetooth_menu, container, false);
        Server = (Button) view.findViewById(R.id.Server_button);
        Connect = (Button) view.findViewById(R.id.Connect_button);
        Pair = (Button) view.findViewById(R.id.Pair_button);
        Back = (Button) view.findViewById(R.id.BackM_button);
        visListe = (Button) view.findViewById(R.id.btnVisListe);
        listView = (Button) view.findViewById(R.id.btnListview);
        update = (Button) view.findViewById(R.id.btnUpdate);
        scan = (Button) view.findViewById(R.id.btnScan);
        discover = (Button) view.findViewById(R.id.btnDiscover);
        _back = (Button) view.findViewById(R.id.btnBack);

        visListe.setVisibility(view.INVISIBLE);
        listView.setVisibility(view.INVISIBLE);
        update.setVisibility(view.INVISIBLE);
        discover.setVisibility(view.INVISIBLE);
        scan.setVisibility(view.INVISIBLE);
        _back.setVisibility(view.INVISIBLE);

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
                    public void onClick(View v)
                    {
                        view = v;
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

        _back.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        view = v;
                        buttonClicked(v, "_back");
                    }
                }
        );
        return view;
    }

    public void buttonClicked(View v, String button)
    {
        switch (button) {
            case "Server":
                Server.setVisibility(view.INVISIBLE);
                Connect.setVisibility(view.INVISIBLE);
                Pair.setVisibility(view.INVISIBLE);
                Back.setVisibility(view.INVISIBLE);
                _back.setVisibility(view.VISIBLE);
                break;

            case "Connect":
                Server.setVisibility(view.INVISIBLE);
                Connect.setVisibility(view.INVISIBLE);
                Pair.setVisibility(view.INVISIBLE);
                Back.setVisibility(view.INVISIBLE);
                visListe.setVisibility(view.VISIBLE);
                listView.setVisibility(view.VISIBLE);
                update.setVisibility(view.VISIBLE);
                _back.setVisibility(view.VISIBLE);
                break;

            case "Pair":
                Server.setVisibility(view.INVISIBLE);
                Connect.setVisibility(view.INVISIBLE);
                Pair.setVisibility(view.INVISIBLE);
                Back.setVisibility(view.INVISIBLE);
                discover.setVisibility(view.VISIBLE);
                scan.setVisibility(view.VISIBLE);
                _back.setVisibility(view.VISIBLE);
                break;

            case "Back":
                StaticValues.fragment = new ModeMenu();
                StaticValues.fm = getActivity().getSupportFragmentManager();
                StaticValues.ft = StaticValues.fm.beginTransaction();
                StaticValues.ft.replace(R.id.fragment7, StaticValues.fragment);
                StaticValues.ft.commit();
                Server.setVisibility(view.INVISIBLE);
                Connect.setVisibility(view.INVISIBLE);
                Pair.setVisibility(view.INVISIBLE);
                Back.setVisibility(view.INVISIBLE);
                break;

            case "_back":
                Server.setVisibility(view.VISIBLE);
                Connect.setVisibility(view.VISIBLE);
                Pair.setVisibility(view.VISIBLE);
                Back.setVisibility(view.VISIBLE);
                visListe.setVisibility(view.INVISIBLE);
                listView.setVisibility(view.INVISIBLE);
                update.setVisibility(view.INVISIBLE);
                discover.setVisibility(view.INVISIBLE);
                scan.setVisibility(view.INVISIBLE);
                _back.setVisibility(view.INVISIBLE);
                break;

            default:
                break;
        }
    }
}
