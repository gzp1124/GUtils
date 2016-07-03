package com.gzp1124.testgutils.fragments_for_test;

import android.content.Intent;
import android.view.View;

import com.gzp1124.testgutils.R;
import com.gzp1124.gutils.base.BaseApplication;
import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.testgutils.ShowActivity;

/**
 * author：高志鹏 on 16/6/24 18:43
 * email:imbagaozp@163.com
 */
public class TestTabStripFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected void initViews() {
        gView.findViewById(R.id.one).setOnClickListener(this);
        gView.findViewById(R.id.more).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_tabstrip_fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.one:
                BaseApplication.showFragment = new TestTabStripOneFragment();
                getActivity().startActivity(new Intent(getActivity(), ShowActivity.class));
                break;
            case R.id.more:
                BaseApplication.showFragment = new TestTabStripMoreFragment();
                getActivity().startActivity(new Intent(getActivity(), ShowActivity.class));
                break;

        }
    }
}
