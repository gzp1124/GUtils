package com.gzp1124.gutils.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 缓存fragment显示的view，下次createview的时候不在创建
 *
 *      在每次创建的时候不会重新创建
 *
 */
public abstract class CacheViewFragment extends Fragment {

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

    /**设置布局id  优先使用layoutView,然后使用layoutId */
    public abstract int getLayoutId();
    /**设置布局View 优先使用该View,然后使用layoutId */
    public abstract View getLayoutView();
}
