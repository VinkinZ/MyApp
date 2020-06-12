package com.sinano.rfidrccs.ui.frag;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;


public class PowerFrag extends Fragment {
    private ImageView imgDy, imgTx;
    private TextView tvDy, tvTx;
    private static boolean power = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_power, container, false);

        imgDy = view.findViewById(R.id.imgDY);
        tvDy = view.findViewById(R.id.tvDY);
        tvDy.setText(R.string.dy);
        if (power) {
            imgDy.setBackgroundResource(R.drawable.ledgreen);
        } else {
            imgDy.setBackgroundResource(R.drawable.ledred);
        }

        imgTx = view.findViewById(R.id.imgTX);
        imgTx.setBackgroundResource(R.drawable.ledgreen);
        tvTx = view.findViewById(R.id.tvTX);
        tvTx.setText(R.string.tx);

        return view;
    }
}

