package com.healthmudi.dia.rulerviewdemo.tablayoutdemo;


/**
 * <p>description:</p>
 * <p>created on: 2019/4/16 20:55</p>
 *
 * @author tck
 * @version 3.3
 */
public class TabWidgetImpl  {

    public String title = "";
    public String extra = "";
    public boolean isSelected = false;

    public TabWidgetImpl() {
    }

    public TabWidgetImpl(String title, String extra) {
        this.title = title;
        this.extra = extra;
    }

    public TabWidgetImpl(String title, String extra, boolean isSelected) {
        this.title = title;
        this.extra = extra;
        this.isSelected = isSelected;
    }
}
