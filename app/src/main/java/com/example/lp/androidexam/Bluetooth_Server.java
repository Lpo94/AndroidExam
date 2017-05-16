package com.example.lp.androidexam;

import android.app.Activity;
import android.content.Intent;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Shark on 15-05-2017.
 */

public class Bluetooth_Server extends Fragment {
    public Button visListe, listView, update, discover, scan, back;
    public View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bluetooth_server, container, false);

        visListe.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.INVISIBLE);
        update.setVisibility(View.INVISIBLE);
        scan .setVisibility(View.INVISIBLE);
        discover.setVisibility(View.INVISIBLE);

        visListe.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        view = v;
                        buttonClicked(v, "visListe");
                    }
                }
        );

        listView.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View _view)
                    {
                        view = _view;
                        buttonClicked(view, "listView");
                    }
                }
        );
        update.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View _view)
                    {
                        view = _view;
                        buttonClicked(view, "update");
                    }
                }
        );
        scan.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View _view)
                    {
                        view = _view;
                        buttonClicked(view, "scan");
                    }
                }
        );
        discover.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View _view)
                    {
                        view = _view;
                        buttonClicked(view, "discover");
                    }
                }
        );
        back.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View _view)
                    {
                        view = _view;
                        buttonClicked(view, "back");
                    }
                }
        );
        return view;
    }


    public void buttonClicked(View v, String button)
    {
        switch (button) {
            case "visListe":

                break;

            case "listView":

                break;
            case "update":

                break;
            case "scan":

                break;
            case "discover":

                break;
            case "back":
                StaticValues.fragment = new Bluetooth_menu();
                StaticValues.fm = getActivity().getSupportFragmentManager();
                StaticValues.ft = StaticValues.fm.beginTransaction();
                StaticValues.ft.replace(R.id.fragment7, StaticValues.fragment);
                StaticValues.ft.commit();
                back.setVisibility(view.INVISIBLE);

            default:
                break;
        }
    }
}
