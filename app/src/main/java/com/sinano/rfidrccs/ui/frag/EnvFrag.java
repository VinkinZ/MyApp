package com.sinano.rfidrccs.ui.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/19
 * Description: 环境信息fragment
 */
public class EnvFrag extends Fragment {
    private static int cabNum = 4;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        switch (cabNum) {
            case 1:
                view = inflater.inflate(R.layout.frag_env1, container, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.frag_env2, container, false);
                break;
            case 3:
                view = inflater.inflate(R.layout.frag_env3, container, false);
                break;
            case 4:
                view = inflater.inflate(R.layout.frag_env4, container, false);
                break;
            default:
                break;
        }
        return view;
    }

}
