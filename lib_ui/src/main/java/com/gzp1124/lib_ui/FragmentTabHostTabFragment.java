package com.gzp1124.lib_ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Tab的Fragment
 *
 *      在每次创建的时候不会重新创建
 *
 */
public abstract class FragmentTabHostTabFragment extends Fragment {

    private View mViewContent; // 缓存视图内容

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutView() != null) {
            mViewContent = getLayoutView();
        } else if (mViewContent == null) {
            mViewContent = inflater.inflate(getLayoutId(), container, false);
        }

        // 缓存View判断是否含有parent, 如果有需要从parent删除, 否则发生已有parent的错误.
        ViewGroup parent = (ViewGroup) mViewContent.getParent();
        if (parent != null) {
            parent.removeView(mViewContent);
        }

        return mViewContent;
    }

    /**设置布局id*/
    public abstract int getLayoutId();
    /**设置布局View*/
    public abstract View getLayoutView();
}
