package com.example.masproposal;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class ChangeFragment {

    Context context;

    public ChangeFragment(Context context) {
        this.context = context;
    }

    public void changeFragment(Fragment fragment){

        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_summary_frame,fragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }
}
