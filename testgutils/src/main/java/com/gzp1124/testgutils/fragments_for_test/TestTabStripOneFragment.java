package com.gzp1124.testgutils.fragments_for_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzp1124.testgutils.R;
import com.gzp1124.gutils.BaseFragment;

/**
 * author：高志鹏 on 16/6/24 18:43
 * email:imbagaozp@163.com
 */
public class TestTabStripOneFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_tabstrip_fragment,null);
    }
}
