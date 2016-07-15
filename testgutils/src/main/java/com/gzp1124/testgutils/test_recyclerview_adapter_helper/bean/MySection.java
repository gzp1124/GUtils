package com.gzp1124.testgutils.test_recyclerview_adapter_helper.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MySection extends SectionEntity<Video> {
    private boolean isMroe;
    public MySection(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMroe = isMroe;
    }

    public MySection(Video t) {
        super(t);
    }

    public boolean isMroe() {
        return isMroe;
    }

    public void setMroe(boolean mroe) {
        isMroe = mroe;
    }
}
