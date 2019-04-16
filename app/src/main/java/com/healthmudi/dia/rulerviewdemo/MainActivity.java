package com.healthmudi.dia.rulerviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
    }
}
