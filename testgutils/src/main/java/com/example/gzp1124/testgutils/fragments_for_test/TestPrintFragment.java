package com.example.gzp1124.testgutils.fragments_for_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gzp1124.gutils.BaseFragment;
import com.example.gzp1124.gutils.print.CaptureActivity;

/**
 * author：高志鹏 on 16/6/15 15:02
 * email:imbagaozp@163.com
 */
public class TestPrintFragment extends BaseFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CaptureActivity.setReceiveResultText(new CaptureActivity.ReceiveResultText() {
            @Override
            public void receiveText(String resultText) {
                Toast.makeText(getContext(),"结果为："+resultText,Toast.LENGTH_SHORT).show();
            }
        });
        getActivity().startActivity(new Intent(getActivity(), CaptureActivity.class));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setText("TestPrintFragment");
        return textView;
    }
}
