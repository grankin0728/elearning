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
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.model.ListType;

import java.util.ArrayList;

public class SelectListTypeAdapter extends RecyclerView.Adapter<SelectListTypeAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ListType> mArrData;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        public void onClickItem(int position);
    }

    public SelectListTypeAdapter(Context ctx, ArrayList<ListType> arr, ItemClickListener listener) {
        this.mContext = ctx;
        this.mArrData = arr;
        this.itemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_list_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ListType listType = mArrData.get(position);
        if (listType != null) {
            holder.tvName.setText(listType.getName());
            if (Config.ID_TYPE_GRID == listType.getId()) {
                holder.imgTypeList.setImageResource(R.drawable.ic_grid_black);
            } else {
                holder.imgTypeList.setImageResource(R.drawable.ic_list_black);
            }
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
