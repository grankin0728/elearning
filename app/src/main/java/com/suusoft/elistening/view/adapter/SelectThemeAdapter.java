package com.suusoft.elistening.view.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.model.Theme;

import java.util.ArrayList;

public class SelectThemeAdapter extends RecyclerView.Adapter<SelectThemeAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Theme> mArrData;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        public void onClickItem(int position);
    }

    public SelectThemeAdapter(Context ctx, ArrayList<Theme> arr, ItemClickListener listener) {
        this.mContext = ctx;
        this.mArrData = arr;
        this.itemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_theme, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Theme theme = mArrData.get(position);
        if (theme != null) {
            holder.getTvTitleTheme().setText(theme.getName());
            holder.getTvColor().setBackgroundColor(android.graphics.Color.parseColor(theme.getColorPrimary()));
            holder.getLayoutItem().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClickItem(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        try {
            return mArrData.size();
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitleTheme, tvColor;
        private RelativeLayout layoutItem;

        public ViewHolder(View view) {
            super(view);
            tvTitleTheme = (TextView) view.findViewById(R.id.tvTitleTheme);
            tvColor = (TextView) view.findViewById(R.id.tvColor);
            layoutItem = (RelativeLayout) view.findViewById(R.id.layoutItem);
        }

        public TextView getTvTitleTheme() {
            return tvTitleTheme;
        }

        public TextView getTvColor() {
            return tvColor;
        }

        public RelativeLayout getLayoutItem() {
            return layoutItem;
        }
    }
}
