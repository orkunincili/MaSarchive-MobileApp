package com.example.masproposal;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MySummary extends Fragment {

    TextView mySummary_textView;
    View view;
    String notes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_movie_summary, container, false);
        mySummary_textView=view.findViewById(R.id.summary);
        if(getArguments().getString("notes")==null){
            notes="empty :( let's write something now";
        }
        else{
            notes=getArguments().getString("notes").toString();

        }
        mySummary_textView.setText(notes);
        return view;
    }

}
