package com.healthmudi.dia.rulerviewdemo;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.healthmudi.dia.rulerviewdemo.flowlayout.FlowLayoutActivity;
import com.healthmudi.dia.rulerviewdemo.scrolltoorbydemo.ScrollToOrByDemoActivity;
import com.healthmudi.dia.rulerviewdemo.tablayoutdemo.TabLayoutTestActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TabLayoutTestActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScrollToOrByDemoActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlowLayoutActivity.class);
                startActivity(intent);
            }
        });
    }
}
