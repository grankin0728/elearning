package com.suusoft.elistening.view.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suusoft.elistening.R;

import java.util.ArrayList;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mArrData;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        public void onClickItem(int position);
    }

    public SelectAdapter(Context ctx, ArrayList<String> arr, ItemClickListener listener) {
        this.mContext = ctx;
        this.mArrData = arr;
        this.itemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String strItem = mArrData.get(position);
        if (strItem != null) {
            holder.tvName.setText(strItem);
            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
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

        private TextView tvName;
        private RelativeLayout layoutItem;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            layoutItem = (RelativeLayout) view.findViewById(R.id.layoutItem);
        }
    }
}
