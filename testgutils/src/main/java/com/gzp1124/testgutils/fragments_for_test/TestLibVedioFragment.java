package com.gzp1124.testgutils.fragments_for_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.testgutils.R;

import tcking.github.com.giraffeplayer.GiraffePlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * 测试视频播放
 * author：高志鹏 on 16/7/30 22:32
 * email:imbagaozp@163.com
 */
public class TestLibVedioFragment extends BaseFragment{

    private GiraffePlayer player;

    @Override
    protected int getLayoutId() {
        return R.layout.test_lib_vedio_fragment;
    }

    @Override
    protected void initViews() {
        player = new GiraffePlayer(getActivity());
        player.onComplete(new Runnable() {
            @Override
            public void run() {
                //callback when video is finish
                Toast.makeText(getContext(), "video play completed",Toast.LENGTH_SHORT).show();
            }
        }).onInfo(new GiraffePlayer.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                switch (what) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        //do something when buffering start
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //do something when buffering end
                        break;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                        //download speed
//                        ((TextView) findViewById(R.id.tv_speed)).setText(Formatter.formatFileSize(getApplicationContext(),extra)+"/s");
                        break;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                        //do something when video rendering
//                        findViewById(R.id.tv_speed).setVisibility(View.GONE);
                        break;
                }
            }
        }).onError(new GiraffePlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
                Toast.makeText(getContext(), "video play error",Toast.LENGTH_SHORT).show();
            }
        });
        String url = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";
        player.play(url);
        player.setTitle(url);

    }

    @Override
    public void onPause() {
        super.onPause();
        player.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.onDestroy();
    }
}
