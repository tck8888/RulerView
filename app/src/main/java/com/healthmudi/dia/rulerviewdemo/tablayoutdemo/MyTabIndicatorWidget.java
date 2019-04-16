package com.healthmudi.dia.rulerviewdemo.tablayoutdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthmudi.dia.rulerviewdemo.R;

import java.util.List;

/**
 * <p>description:</p>
 * <p>created on: 2019/4/16 20:04</p>
 *
 * @author tck
 * @version 3.3
 */
public class MyTabIndicatorWidget extends RecyclerView.Adapter<MyTabIndicatorWidget.MyTabIndicatorViewHolder> {

    private Context context;
    private List<TabWidgetImpl> tabs;

    private int extraSelectedColor;
    private int extraUnSelectedColor;

    private int normalSelectedColor;
    private int normalUnSelectedColor;

    public MyTabIndicatorWidget(Context context, List<TabWidgetImpl> tabs) {
        this.context = context;
        this.tabs = tabs;

        extraSelectedColor = Color.parseColor("#FFFF6152");
        extraUnSelectedColor = Color.parseColor("#FFD9D9D9");
        normalSelectedColor = Color.parseColor("#FF2C2C2C");
        normalUnSelectedColor = Color.parseColor("#FF9F9F9F");
    }

    @NonNull
    @Override
    public MyTabIndicatorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyTabIndicatorViewHolder(LayoutInflater.from(context).inflate(R.layout.tabs_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyTabIndicatorViewHolder holder, int pos) {
        TabWidgetImpl data = tabs.get(pos);
        String title = data.title;
        String extra = data.extra;
        boolean selected = data.isSelected;
        if (selected) {
            holder.tvTabName.setTextColor(normalSelectedColor);
            holder.tvTabName.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    null,
                    ContextCompat.getDrawable(context, R.drawable.selected_indicator)
            );
        } else {
            holder.tvTabName.setTextColor(normalUnSelectedColor);
            holder.tvTabName.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    null,
                    ContextCompat.getDrawable(context, R.drawable.unselected_indicator));
        }

        if (!TextUtils.isEmpty(extra)) {
            String titles = title + " " + extra;
            holder.tvTabName.setText(
                    getColorStr(titles,
                            title.length() + 1,
                            titles.length(),
                            selected ? extraSelectedColor : extraUnSelectedColor));
        }
    }

    @Override
    public int getItemCount() {
        return tabs == null ? 0 : tabs.size();
    }


    class MyTabIndicatorViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTabName;

        public MyTabIndicatorViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvTabName = (TextView) itemView.findViewById(R.id.tv_tab_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SpannableString getColorStr(String str, int start, int end, @ColorInt int targetColor) {
        SpannableString spannableString = new SpannableString(str);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(targetColor);
        spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(17f / 16f);
        spannableString.setSpan(sizeSpan01, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}
