package com.gzp1124.gutils.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * fragment基类，简单流程规划
 *
 *  getLayoutId -> initViews -> requestData -> destoryResource
 *
 *
 * author：高志鹏 on 16/5/18 10:35
 * email:imbagaozp@163.com
 */
public abstract class BaseFragment extends LazyLoadFragment {
    protected Activity gActivity;
    protected View gView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gActivity = getActivity();
        if (getLayoutId() != 0) {
            gView = inflater.inflate(getLayoutId(), null);
        }
        return gView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initViews();
    }

    /**
     * 布局id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化布局
     */
    protected abstract void initViews();
    protected void initViews(View view){

    }

    @Override
    protected void requestData() {

    }
    @Override
    protected void destoryResource() {

    }


}
