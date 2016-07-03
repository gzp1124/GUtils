package com.gzp1124.gutils.base;

import android.support.v4.app.Fragment;

/**
 * 懒加载fragment, fragment可见才加载数据
 *
 * 用于懒加载的Fragment，只有当前的Fragment可见时才开始执行requestData操作
 *
 *  用处，防止在viewpager预加载时，出现的当前fragment还不可见就被预加载，然后执行相应的初始化操作
 *
 *  注意：懒加载的只是数据，如果子类fragment放在viewpager中，还是会随着viewpager的预加载而执行oncreateview等方法，但是requestdata等请求数据的方法会在fragment可见的时候才执行
 *  也就是说懒加载的其实是requestData等请求数据。oncreateview不会懒加载
 *
 * author：高志鹏 on 16/7/3 10:12
 * email:imbagaozp@163.com
 */
public abstract class LazyLoadFragment extends Fragment {
    protected boolean isVisible;
    protected boolean isPrepared;
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onViewVisible();
        } else {
            isVisible = false;
            onViewInvisible();
        }
    }

    /**
     * 当前fragment界面可见
     */
    protected void onViewVisible(){
        requestData();
    }

    /**
     * 界面可见，执行请求数据操作
     */
    protected abstract void requestData();

    /**
     * 当前fragment界面不可见
     */
    protected void onViewInvisible(){
        destoryResource();
    }

    /**
     * 界面不可见时回收资源
     */
    protected abstract void destoryResource();
}
