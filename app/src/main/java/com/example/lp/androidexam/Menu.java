package com.example.lp.androidexam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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

    public Button StartButton, HowTo, Exit,list, connection, visible, send, discover;
    public View view;
    private Context context;
    static MediaPlayer music;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_menu, container, false);
        StartButton = (Button) view.findViewById(R.id.Start_button);
        HowTo = (Button) view.findViewById(R.id.HTP_button);
        Exit = (Button) view.findViewById(R.id.Exit_button);
/*        list = (Button) view.findViewById(R.id.btnlist);
        connection = (Button) view.findViewById(R.id.btnStartConnection);
        visible = (Button) view.findViewById(R.id.btnvisible);
        send = (Button) view.findViewById(R.id.btnSend);
        discover = (Button) view.findViewById(R.id.Discover);

        list.setVisibility(view.INVISIBLE);
        connection.setVisibility(view.INVISIBLE);
        visible.setVisibility(view.INVISIBLE);
        send.setVisibility(view.INVISIBLE);
        discover.setVisibility(view.INVISIBLE);*/


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
        return view;
    }


    public void buttonClicked(View v, String button)
    {
        switch (button) {
            case "Start":
                StaticValues.baggroundMusic.stop();
                StaticValues.baggroundMusic = MediaPlayer.create(getContext(), R.raw.ingame);
                StaticValues.baggroundMusic.setLooping(true);
                StaticValues.baggroundMusic.setVolume(0.1f, 0.1f);
                StaticValues.baggroundMusic.start();
                StaticValues.fragment = new ModeMenu();
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
