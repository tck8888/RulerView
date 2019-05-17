package com.healthmudi.dia.rulerviewdemo.tablayoutdemo;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
public class TestFragment2 extends Fragment {

    public static TestFragment2 newInstance() {
        
        Bundle args = new Bundle();
        
        TestFragment2 fragment = new TestFragment2();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment2,container,false);
    }
}
