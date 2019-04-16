package com.healthmudi.dia.rulerviewdemo.tablayoutdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthmudi.dia.rulerviewdemo.R;

/**
 * <p>description:</p>
 * <p>created on: 2019/4/16 13:27</p>
 *
 * @author tck
 * @version 3.3
 */
public class TestFragment3 extends Fragment {

    public static TestFragment3 newInstance() {
        
        Bundle args = new Bundle();
        
        TestFragment3 fragment = new TestFragment3();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment3,container,false);
    }
}
