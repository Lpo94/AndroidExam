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

public class Menu extends Fragment {

    public Button StartButton, HowTo, Exit;
    public View view;
    HowTo howTo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_menu, container, false);
        StartButton = (Button) view.findViewById(R.id.Start_button);
        HowTo = (Button) view.findViewById(R.id.HTP_button);
        Exit = (Button) view.findViewById(R.id.Exit_button);

        StartButton.setOnClickListener(
            new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    view = v;
                buttonClicked(v, "Start");
                }
            }
        );

        HowTo.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View _view)
                    {
                        view = _view;
                        buttonClicked(view, "HowTo");
                    }
                }
        );
        //view.setVisibility(View.INVISIBLE);
        return view;
    }

    public void Show()
    {
        view.setVisibility(View.VISIBLE);
    }

    public void buttonClicked(View v, String button)
    {
        switch (button) {
            case "Start":
                StaticValues.fragment = new Bluetooth_menu();
                StaticValues.fm = getActivity().getSupportFragmentManager();
                StaticValues.ft = StaticValues.fm.beginTransaction();
                StaticValues.ft.replace(R.id.fragment7, StaticValues.fragment);
                StaticValues.ft.commit();
                StartButton.setVisibility(view.INVISIBLE);
                HowTo.setVisibility(view.INVISIBLE);
                Exit.setVisibility(view.INVISIBLE);
                break;

            case "HowTo":
                StaticValues.fragment = new HowTo();
                StaticValues.fm = getActivity().getSupportFragmentManager();
                StaticValues.ft = StaticValues.fm.beginTransaction();
                StaticValues.ft.replace(R.id.fragment7, StaticValues.fragment);
                StaticValues.ft.commit();
                StartButton.setVisibility(view.INVISIBLE);
                HowTo.setVisibility(view.INVISIBLE);
                Exit.setVisibility(view.INVISIBLE);
                break;

            default:
                break;
        }
    }
}
