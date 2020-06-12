package com.sinano.rfidrccs.ui.frag;

import com.sinano.rfidrccs.base.BaseCabFragment;

/**
 * Created by Vinkin on 2020/5/20
 */
public class CabFrag2 extends BaseCabFragment {

    public CabFrag2() {
        super.cabOrderNum = 2;
        System.out.println("2号试剂柜类型：" + cabType);
    }

    @Override
    protected void initData() {

    }

}
