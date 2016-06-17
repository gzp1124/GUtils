社会化功能
    登录，分享，支付等

原生
===================================================================
//微信
    AppID：wx54708fe8e4a5cc5d
    AppSecret：9a97182be7a0117deb02a4d7d99987ac

    微信分享、登录、收藏、支付等功能需要的库以及文件libammsdk.jar

    官方jar:libammsdk 当前使用的版本是3.1.1

    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

    混淆：
        -keep class com.tencent.mm.sdk.** {
           *;
        }












===================================================================
友盟
