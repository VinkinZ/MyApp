package com.sinano.rfidrccs.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.sinano.rfidrccs.business.DetailQuery;
import com.sinano.rfidrccs.business.RFIDScan;
import com.sinano.rfidrccs.ui.act.CabinetActivity;
import com.sinano.rfidrccs.db.CabinetTable;
import com.sinano.rfidrccs.utils.PageJump;

import org.simple.eventbus.EventBus;


/**
 * Created by Vinkin on 2020/5/20
 */
public abstract class BaseCabFragment extends Fragment {
    protected ImageButton btnOpenDoor, btnf2c;
    protected ImageView imgIcon;
    protected TextView tvId, tvAttr, tvTemp, tvHum, tvVoc;
    protected int cabOrderNum;
    protected String cabType;
    protected RFIDScan rfidScan;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rfidScan = new RFIDScan();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cab, container, false);
        initView(view);
        initData();
        doBusiness();
        return view;
    }

    /**
     * 封装findViewById
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findView(View view, int resid) {
        return (T) view.findViewById(resid);
    }

    /**
     * 初始化控件
     */
    protected void initView(View v) {
        btnf2c = findView(v, R.id.btnFragCab);

        ImageView imgtemp = findView(v, R.id.imgTemp);
        imgtemp.setBackgroundResource(R.drawable.iconenv_temp);

        ImageView imghum = findView(v, R.id.imgHum);
        imghum.setBackgroundResource(R.drawable.iconenv_hum);

        ImageView imgvoc = findView(v, R.id.imgVoc);
        imgvoc.setBackgroundResource(R.drawable.iconenv_voc);

        tvId = findView(v, R.id.tvIdCab);
        initTvCabId(cabOrderNum);

        tvAttr = findView(v, R.id.tvAttrCab);
        imgIcon = findView(v, R.id.imgIconFragCab);
        CabinetTable tCab = new CabinetTable();
        initCabType(tCab.queryCabinetType(Integer.toString(cabOrderNum)));

        ImageButton btnOpenDoor = findView(v, R.id.btnOpenDoor);
        btnOpenDoor.setBackgroundResource(R.drawable.btnopen_bg);
    }

    private void initTvCabId (int cabOrderNum){
        switch (cabOrderNum) {
            case 1:
                tvId.setText(R.string.cabinet1);
                break;
            case 2:
                tvId.setText(R.string.cabinet2);
                break;
            case 3:
                tvId.setText(R.string.cabinet3);
                break;
            case 4:
                tvId.setText(R.string.cabinet4);
                break;
            default:
                break;
        }
    }

    private void initCabType (String cabType) {
        switch (cabType) {
            case "普通型":
                tvAttr.setText(R.string.rc_ptx);
                imgIcon.setBackgroundResource(R.drawable.iconcab_grayo);
                break;
            case "阻燃型":
                tvAttr.setText(R.string.rc_zrx);
                imgIcon.setBackgroundResource(R.drawable.iconcab_yellowf);
                break;
            case "抗腐蚀型":
                tvAttr.setText(R.string.rc_kfsx);
                imgIcon.setBackgroundResource(R.drawable.iconcab_greenc);
                break;
            case "防爆型":
                tvAttr.setText(R.string.rc_fbx);
                imgIcon.setBackgroundResource(R.drawable.iconcab_rede);
                break;
            case "防爆无浓度型":
                tvAttr.setText(R.string.rc_fbx);
                imgIcon.setBackgroundResource(R.drawable.iconcab_rede);
                break;
            case "有毒称重型":
                tvAttr.setText(R.string.rc_ydx);
                imgIcon.setBackgroundResource(R.drawable.iconcab_bluet);
                break;
            case "有毒不称重型":
                tvAttr.setText(R.string.rc_ydx);
                imgIcon.setBackgroundResource(R.drawable.iconcab_bluet);
                break;
            default:
                break;
        }
    }

    /**
     * 数据
     * 温度、湿度、浓度
     */
    protected void initData() {

    }


    /**
     * 业务：试剂详情查询
     * 携带柜号的页面跳转
     */
    protected void doBusiness() {
        Bundle bundle = new Bundle();
        bundle.putInt("cabOrderNum", cabOrderNum);
        btnf2c.setOnClickListener(new DetailQuery(getActivity(), "试剂详情查询", CabinetActivity.class, bundle));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
