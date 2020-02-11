package com.example.masproposal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MovieSummary extends Fragment {
    TextView summary_textView;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_movie_summary, container, false);
        summary_textView=view.findViewById(R.id.summary);
        String summary=getArguments().getString("summary").toString();
        summary_textView.setText(summary);
        return view;
    }


}
