package com.example.lp.androidexam;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HowTo extends Fragment {

    public Button Back, Next, Prev;
    public TextView HowTo1, HowTo2;
    public View view;
    Menu menu;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.how_to, container, false);
        Back = (Button) view.findViewById(R.id.Back_Button);
        Next = (Button) view.findViewById(R.id.Next_button);
        Prev = (Button) view.findViewById(R.id.Previous_button);

        Back.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        buttonClicked(v, "Back");
                    }
                }
        );

        Next.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        buttonClicked(v, "Next");
                    }
                }
        );
        //view.setVisibility(View.INVISIBLE);
        return view;
    }

    public void buttonClicked(View v, String button)
    {
        switch (button) {
            case "Back":
                StaticValues.Instance().fragment = new Menu();
                StaticValues.Instance().fm = getActivity().getSupportFragmentManager();
                StaticValues.Instance().ft = StaticValues.Instance().fm.beginTransaction();
                StaticValues.Instance().ft.replace(R.id.fragment7, StaticValues.Instance().fragment);
                StaticValues.Instance().ft.commit();
                Back.setVisibility(view.INVISIBLE);
                Next.setVisibility(view.INVISIBLE);
                Prev.setVisibility(view.INVISIBLE);
                break;

            case "Next":
                view.setVisibility(View.INVISIBLE);
                break;

            default:
                break;
        }
    }
}
