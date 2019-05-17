package com.healthmudi.dia.rulerviewdemo.tablayoutdemo;

import androidx.fragment.app.Fragment;

/**
 * <p>description:</p>
 * <p>created on: 2019/4/16 13:29</p>
 *
 * @author tck
 * @version 3.3
 */
public class ViewPageEntity {

    public String name;
    public Fragment fragment;

    public ViewPageEntity() {
    }

    public ViewPageEntity(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }
}
