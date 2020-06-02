package com.suusoft.elistening.view.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.model.GridInterface;

import java.util.ArrayList;

/**
 * Created by Suusoft on 10/25/2017.
 */

public class SelectGridColumnAdapter extends RecyclerView.Adapter<SelectGridColumnAdapter.ViewHolder>  {
    private Context mContext;
    private ArrayList<GridInterface> mArrData;
    private SelectGridColumnAdapter.ItemClickListener itemClickListener;

    public interface ItemClickListener {
        public void onClickItem(int position);
    }

    public SelectGridColumnAdapter(Context ctx, ArrayList<GridInterface> arr, SelectGridColumnAdapter.ItemClickListener listener) {
        this.mContext = ctx;
        this.mArrData = arr;
        this.itemClickListener = listener;
    }

    @Override
    public SelectGridColumnAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_list_type, parent, false);
        return new SelectGridColumnAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectGridColumnAdapter.ViewHolder holder, final int position) {
        final GridInterface gridInterface = mArrData.get(position);
        if (gridInterface != null) {
            holder.tvName.setText(gridInterface.getName());
            holder.imgTypeList.setVisibility(View.GONE);
//            if (Config.ID_GRID_3_COLUMN == gridInterface.getId()) {
//                holder.imgTypeList.setImageResource(R.drawable.ic_grid_black);
//            } else {
//                holder.imgTypeList.setImageResource(R.drawable.ic_grid_2_column);
//            }
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
        private ImageView imgTypeList;
        private RelativeLayout layoutItem;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            imgTypeList = (ImageView) view.findViewById(R.id.imgTypeList);
            layoutItem = (RelativeLayout) view.findViewById(R.id.layoutItem);
        }
    }
}
