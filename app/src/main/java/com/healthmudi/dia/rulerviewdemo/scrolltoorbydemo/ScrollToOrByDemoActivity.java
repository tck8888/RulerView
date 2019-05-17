package com.healthmudi.dia.rulerviewdemo.scrolltoorbydemo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.healthmudi.dia.rulerviewdemo.R;

/**
 * <p>description:</p>
 * <p>created on: 2019/4/16 19:37</p>
 *
 * @author tck
 * @version 3.3
 */
public class ScrollToOrByDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_to_or_by_demo_activity);

        findViewById(R.id.ib_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
