package com.gzp1124.testgutils.fragments_for_test;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.gzp1124.testgutils.R;
import com.gzp1124.gutils.BaseFragment;
import com.gzp1124.lib_ui.bottomnavigation_2.BottomNavigationFragment2;
import com.gzp1124.testgutils.linshi.Frag1;
import com.gzp1124.testgutils.linshi.Frag2;
import com.gzp1124.testgutils.linshi.Frag3;

/**
 * author：高志鹏 on 16/7/2 09:49
 * email:imbagaozp@163.com
 */
public class TestBottomNavigationFrag2 extends BaseFragment {
    //图标集
    private int mImages[] = {
            R.drawable.tab_icon_explore,
            R.drawable.tab_icon_me,
            R.drawable.tab_icon_new
    };

    //标题
    String [] ll = {"首页","新闻","个人"};

    Class<Fragment> [] fs = new Class[]{Frag1.class,Frag2.class,Frag3.class};

    int [] colors = {0xff4a5965 ,0xff096c54 , 0xff8a6a64};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationFragment2.setAllDatas(mImages,ll,colors,fs,
                mMode,
                mBackgroundStyle);
    }

    static BottomNavigationFragment2.Mode mMode;
    static BottomNavigationFragment2.BackgroundStyle mBackgroundStyle;
    public static void setMB(BottomNavigationFragment2.Mode mode,BottomNavigationFragment2.BackgroundStyle backgroundStyle){
        mMode = mode;
        mBackgroundStyle = backgroundStyle;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_bottom_nagivation_2;
    }
}
