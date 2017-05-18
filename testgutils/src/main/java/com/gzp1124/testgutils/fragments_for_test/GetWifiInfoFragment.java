package com.gzp1124.testgutils.fragments_for_test;


import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.gutils.utils.GWifiManage;
import com.gzp1124.testgutils.R;

import java.util.List;

/**
 * author：高志鹏 on 16/9/2 13:07
 * email:imbagaozp@163.com
 */
public class GetWifiInfoFragment extends BaseFragment{
    @Override
    protected int getLayoutId() {
        return R.layout.get_wifi_info_fragment;
    }

    @Override
    protected void initViews() {
        try {
            String wifiInfos = new GWifiManage().getInfo(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
