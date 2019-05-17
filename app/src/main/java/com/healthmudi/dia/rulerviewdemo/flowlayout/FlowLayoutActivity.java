package com.healthmudi.dia.rulerviewdemo.flowlayout;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.healthmudi.dia.rulerviewdemo.R;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/17 8:14</p>
 *
 * @author tck
 * @version 3.5
 */
public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout flowLayout;

    String[] str={"java","android","vue","react-native","react","flutter","spring-boot","php"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.flow_layout_activity);

        flowLayout = (FlowLayout) findViewById(R.id.flow_layout);

        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        ViewGroup.MarginLayoutParams marginLayoutParams = new FlowLayout.MarginLayoutParams(-2, -2);
        marginLayoutParams.rightMargin=widthPixels*16/750;
        marginLayoutParams.bottomMargin=widthPixels*32/750;
        for (int i = 0; i < str.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(str[i]);
            textView.setTextSize(14);
            textView.setPadding(widthPixels*32/750,widthPixels*20/750,widthPixels*32/750,widthPixels*20/750);
            textView.setBackground(getResources().getDrawable(R.drawable.tag_bg));
            flowLayout.addView(textView,marginLayoutParams);
        }

    }
}
