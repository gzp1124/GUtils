package com.gzp1124.lib_ui.have_header_viewpager;

public interface HVScrollableFragmentListener {

    public void onFragmentAttached(HVScrollableListener fragment, int position);

    public void onFragmentDetached(HVScrollableListener fragment, int position);
}
