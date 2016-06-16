package com.example.gzp1124.gutils.three_ui_widget.have_header_viewpager;

public interface HVScrollableFragmentListener {

    public void onFragmentAttached(HVScrollableListener fragment, int position);

    public void onFragmentDetached(HVScrollableListener fragment, int position);
}
