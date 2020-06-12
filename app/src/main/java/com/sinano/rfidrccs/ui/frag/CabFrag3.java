package com.sinano.rfidrccs.ui.frag;

import com.sinano.rfidrccs.base.BaseCabFragment;

/**
 * Created by Vinkin on 2020/5/21
 */
public class CabFrag3 extends BaseCabFragment {

    public CabFrag3() {
        super.cabOrderNum = 3;
        System.out.println("3号试剂柜类型：" + cabType);
    }

    @Override
    protected void initData() {

    }
}
