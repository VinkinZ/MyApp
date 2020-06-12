package com.sinano.rfidrccs.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseActivity;
import com.sinano.rfidrccs.ui.frag.CabFrag1;
import com.sinano.rfidrccs.ui.frag.CabFrag2;
import com.sinano.rfidrccs.ui.frag.CabFrag3;
import com.sinano.rfidrccs.ui.frag.CabFrag4;
import com.sinano.rfidrccs.ui.frag.NullFrag;
import com.sinano.rfidrccs.base.BaseCabFragment;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/19 10:29
 * Description: 试剂柜查询
 */
public class CabinetActivity extends BaseActivity {
    private ImageButton btnC2m;

    @Override
    protected int initLayout() {
        return R.layout.activity_cab;
    }

    @Override
    protected void initView() {
        btnC2m = findView(R.id.btnC2M);
        btnC2m.setBackgroundResource(R.drawable.btn2main_bg);
        setCabFrag();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void doBusiness() {
        pageJump(btnC2m, MainActivity.class);
    }

    private int getCabOrderNum() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        return bundle.getInt("cabOrderNum");
    }

    private void setCabFrag() {
        switch (getCabOrderNum()) {
            case 1:
                BaseCabFragment cabFrag1 = new CabFrag1();
                getSupportFragmentManager().beginTransaction().add(R.id.fragCab, cabFrag1).commit();
                break;
            case 2:
                BaseCabFragment cabFrag2 = new CabFrag2();
                getSupportFragmentManager().beginTransaction().add(R.id.fragCab, cabFrag2).commit();
                break;
            case 3:
                BaseCabFragment cabFrag3 = new CabFrag3();
                getSupportFragmentManager().beginTransaction().add(R.id.fragCab, cabFrag3).commit();
                break;
            case 4:
                BaseCabFragment cabFrag4 = new CabFrag4();
                getSupportFragmentManager().beginTransaction().add(R.id.fragCab, cabFrag4).commit();
                break;
            default:
                Fragment nullfrag = new NullFrag();
                getSupportFragmentManager().beginTransaction().add(R.id.fragCab, nullfrag).commit();
                break;
        }
    }
}
