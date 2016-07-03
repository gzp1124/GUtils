

原始代码：各种tablayout源代码（来源github）
    CommonTabLayout
    SegmentTabLayout
    SlidingTabLayout

中间层：抽取出的方便直接项目中使用的中间层fragment
    TabLayoutBottomFragmentWithFragment
    TabLayoutBottomFragmentWithViewPager
    TabLayoutUpFragmentVP1
    TabLayoutUpFragmentVP2
    TabLayoutUpFragmentF

使用中间层的示例代码：
    TestTLBottomFrameLayout   --使用-->    TabLayoutBottomFragmentWithFragment
    TestTLBottomViewpager     --使用-->    TabLayoutBottomFragmentWithViewPager
    TestTLUpFragmentVP1       --使用-->    TabLayoutUpFragmentVP1
    TestTLUpFragmentVP2       --使用-->    TabLayoutUpFragmentVP2
    TestTLUpFragmentF         --使用-->    TabLayoutUpFragmentF

        1. TabLayoutBottomFragmentWithFragment
            底部导航，结合FrameLayout
        2. TabLayoutBottomFragmentWithViewPager
            底部导航，结合ViewPager
        3. TabLayoutUpFragmentVP1
            顶部导航，结合ViewPager，tab横向铺满屏幕
        4. TabLayoutUpFragmentVP2
            顶部导航，结合ViewPager，tab不铺满屏幕
        5. TabLayoutUpFragmentF
            顶部导航，结合FrameLayout

不适用中间层，直接使用tablayout
    TestTabLayoutFragment

使用方式：
    1. 直接使用tablayout，不用上面的中间层fragment，见：TestTabLayoutFragment
    2. 使用抽取的中间层
        大概使用步骤：
            01.在xml中直接用<fragment class='上面的中间层' />
            02.在代码中，最初的生命周期方法中设置数据到中间层，设置方式为中间层类名.setAllDatas
            03.在代码中，当前界面显示的时候，通过中间层提供的getTabLayout方法可以获取到对应中间层的tablayout引用
                使用tablayout可以控制tab切换，以及tab上未读数的显示
            具体使用方式看对应的fragment代码


