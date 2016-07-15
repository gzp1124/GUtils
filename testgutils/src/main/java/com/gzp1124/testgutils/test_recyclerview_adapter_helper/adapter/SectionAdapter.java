package com.gzp1124.testgutils.test_recyclerview_adapter_helper.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gzp1124.testgutils.R;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.bean.MySection;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.bean.Video;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SectionAdapter extends BaseSectionQuickAdapter<MySection> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super( layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final MySection item) {
        helper.setText(R.id.header, item.header);
        helper.setVisible(R.id.more,item.isMroe());
        helper.setOnClickListener(R.id.more,new OnItemChildClickListener());
    }


    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        Video video = (Video) item.t;
        //helper.setImageUrl(R.id.iv, video.getImg());
        helper.setText(R.id.tv, video.getName());
    }
}
