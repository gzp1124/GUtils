package com.gzp1124.gutils.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gzp1124.gutils.R;
import com.gzp1124.gutils.utils.GToastUtil;

/**
 * 浏览器界面
 * 
 * @author kymjs(kymjs123@gmail.com)
 */
@SuppressLint("NewApi")
public class BrowserFragment extends Fragment implements View.OnClickListener {

    public final static String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";

    public static final String BROWSER_KEY = "browser_url";
    public static String DEFAULT = "http://www.baidu.com";

    private int TAG = 1; // 双击事件需要
    private Activity aty;
    private String mCurrentUrl = DEFAULT;

    private Animation animBottomIn, animBottomOut;
    private GestureDetector mGestureDetector;
    private CookieManager cookie;

    String cookieData = "";
    private WebView mWebView;
    private ImageView mImgBack;
    private ImageView mImgForward;
    private ImageView mImgRefresh;
    private ImageView mImgSystemBrowser;
    private LinearLayout mLayoutBottom;
    private ProgressBar mProgress;

    @Override
    public void onClick(View v) {
        int i1 = v.getId();
        if (i1 == R.id.browser_back) {
            mWebView.goBack();

        } else if (i1 == R.id.browser_forward) {
            mWebView.goForward();

        } else if (i1 == R.id.browser_refresh) {
            mWebView.loadUrl(mWebView.getUrl());

        } else if (i1 == R.id.browser_system_browser) {
            try {
                // 启用外部浏览器
                Uri uri = Uri.parse(mCurrentUrl);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                aty.startActivity(it);
            } catch (Exception e) {
                GToastUtil.showToast("网页地址错误");
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWebView();
        initBarAnim();
        mImgBack.setOnClickListener(this);
        mImgForward.setOnClickListener(this);
        mImgRefresh.setOnClickListener(this);
        mImgSystemBrowser.setOnClickListener(this);

        mGestureDetector = new GestureDetector(aty, new MyGestureListener());
        mWebView.loadUrl(mCurrentUrl);

        mWebView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });

//        if (!isShowBottom){
        mLayoutBottom.startAnimation(animBottomOut);
//        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        isShowBottom = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_browser, container,
                false);
        initView(rootView);
        aty = getActivity();

        //真正的cookie
//        cookieData = AppContext.getInstance().getProperty(AppConfig.CONF_COOKIE);

        if (!TextUtils.isEmpty(cookieData)) {
            //取到其中的要用到的cookie，解决重复init_token引起的问题
            String[] datas = cookieData.split(";");
            String res = "";
            if (null != datas && datas.length > 0) {
                for (String s : datas) {
                    if (s.contains("aixin_user_cookie")) {
                        res = s;
                        break;
                    }
                }
                cookieData = res;
            }
        }
        return rootView;
    }

    /**初始化布局*/
    private void initView(View view) {
        mWebView = (WebView) view.findViewById(R.id.webview);
        mImgBack = (ImageView) view.findViewById(R.id.browser_back);
        mImgForward = (ImageView) view.findViewById(R.id.browser_forward);
        mImgRefresh = (ImageView) view.findViewById(R.id.browser_refresh);
        mImgSystemBrowser = (ImageView) view.findViewById(R.id.browser_system_browser);
        mLayoutBottom = (LinearLayout) view.findViewById(R.id.browser_bottom);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.browser_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i1 = item.getItemId();
        if (i1 == R.id.public_menu_shared) {
            showSharedDialog();

        }
        return true;
    }

    /**
     * 初始化上下栏的动画并设置结束监听事件
     */
    private void initBarAnim() {
        animBottomIn = AnimationUtils.loadAnimation(aty, R.anim.anim_bottom_in);
        animBottomOut = AnimationUtils.loadAnimation(aty,
                R.anim.anim_bottom_out);
        animBottomIn.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLayoutBottom.setVisibility(View.VISIBLE);
            }
        });
        animBottomOut.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLayoutBottom.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 打开分享dialog
     */
    private void showSharedDialog() {
        GToastUtil.showToast("打开分享界面");
    }

    private void removeCookie(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    /**
     * 载入链接之前会被调用
     * 
     * @param view
     *            WebView
     * @param url
     *            链接地址
     */
    protected void onUrlLoading(WebView view, String url) {
        mProgress.setVisibility(View.VISIBLE);
        cookie.setCookie(url,cookieData);
    }

    /**
     * 链接载入成功后会被调用
     * 
     * @param view
     *            WebView
     * @param url
     *            链接地址
     */
    protected void onUrlFinished(WebView view, String url) {
        mCurrentUrl = url;
        mProgress.setVisibility(View.GONE);
    }

    /**
     * 当前WebView显示页面的标题
     * 
     * @param view
     *            WebView
     * @param title
     *            web页面标题
     */
    protected void onWebTitle(WebView view, String title) {
//        if (aty != null && mWebView != null) { // 必须做判断，由于webview加载属于耗时操作，可能会本Activity已经关闭了才被调用
//            ((BaseActivity) aty).setActionBarTitle(mWebView.getTitle());
//        }
    }

    /**
     * 当前WebView显示页面的图标
     * 
     * @param view
     *            WebView
     * @param icon
     *            web页面图标
     */
    protected void onWebIcon(WebView view, Bitmap icon) {}

    /**
     * 初始化浏览器设置信息
     */
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 启用支持javascript
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 优先使用缓存
        webSettings.setAllowFileAccess(true);// 可以访问文件
        webSettings.setBuiltInZoomControls(true);// 支持缩放

        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        cookie = CookieManager.getInstance();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            cookie.setAcceptThirdPartyCookies(mWebView,true);
        }
//        cookie.setCookie(url,
//                AppContext.getInstance().getProperty(AppConfig.CONF_COOKIE));
//        onUrlLoading();
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            webSettings.setPluginState(PluginState.ON);
            webSettings.setDisplayZoomControls(false);// 支持缩放
        }

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
    }

    private static boolean isShowBottom = true;
    public static void setIsShowBottom(boolean showBottom){
        isShowBottom = showBottom;
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            onWebTitle(view, title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
            onWebIcon(view, icon);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) { // 进度
            super.onProgressChanged(view, newProgress);
            if (newProgress > 90) {
                mProgress.setVisibility(View.GONE);
            }
        }
    }

    int i = 0;
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            onUrlLoading(view, url);
//            boolean flag = super.shouldOverrideUrlLoading(view, url);
//            mCurrentUrl = url;
//            return flag;
            mWebView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
            onUrlLoading(view, url);
            mProgress.setVisibility(View.VISIBLE);
            mCurrentUrl = url;
            String str = cookie.getCookie(url);
            super.onPageStarted(view, url,favicon);

            if (i++ == 0 && url.contains("store/index")) {
                mWebView.reload();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            onUrlFinished(view, url);
        }
    }

    private class MyGestureListener extends SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {// webview的双击事件
            mLayoutBottom.setVisibility(View.VISIBLE);
            if (TAG % 2 == 0) {
                TAG++;
                mLayoutBottom.startAnimation(animBottomIn);
            } else {
                TAG++;
                mLayoutBottom.startAnimation(animBottomOut);
            }
            return super.onDoubleTap(e);
        }
    }

    private String getShareTitle() {
        return mWebView.getTitle();
    }

    private String getShareContent() {
        return mWebView.getTitle();
    }
}
