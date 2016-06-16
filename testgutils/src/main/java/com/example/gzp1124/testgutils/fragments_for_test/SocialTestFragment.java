package com.example.gzp1124.testgutils.fragments_for_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gzp1124.gutils.BaseFragment;
import com.example.gzp1124.gutils.R;
import com.example.gzp1124.gutils.three_platform.social.GShareInterface;
import com.example.gzp1124.gutils.three_platform.social.GSocialUtil;
import com.example.gzp1124.gutils.utils.GToastUtil;

/**
 * 社会化功能测试
 * author：高志鹏 on 16/5/18 13:35
 * email:imbagaozp@163.com
 */
public class SocialTestFragment extends BaseFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_social_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.share).setOnClickListener(this);
        view.findViewById(R.id.share_qq).setOnClickListener(this);
    }

    GShareInterface.GShareCallBack callBack = new GShareInterface.GShareCallBack() {
        @Override
        public void shareSuccess() {
            GToastUtil.getInstance(getActivity()).setText("hahaha").show();
        }

        @Override
        public void shareError() {
            GToastUtil.getInstance(getActivity()).setText("eeeeee").show();
        }
    };

    @Override
    public void onClick(View view) {
        GShareInterface.GShareInfo shareInfo = new GShareInterface.GShareInfo("内容","标题","图片","链接");
        int i = view.getId();
        if (i == R.id.share) {
            GSocialUtil.getShareInstance().weixinShare(shareInfo, callBack);

        } else if (i == R.id.share_qq) {
            GSocialUtil.getShareInstance().qqShare(shareInfo, callBack);

        }
    }
}
