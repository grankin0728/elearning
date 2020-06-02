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
import com.suusoft.elistening.model.DisplayQuestionType;

import java.util.ArrayList;

/**
 * Created by Suusoft on 09/08/2017.
 */

public class SelectListTypeDisplayQuestion  extends RecyclerView.Adapter<SelectListTypeDisplayQuestion.ViewHolder>  {
    private Context mContext;
    private ArrayList<DisplayQuestionType> mArrData;
    private SelectListTypeDisplayQuestion.ItemClickListener itemClickListener;

    public interface ItemClickListener {
        public void onClickItem(int position);
    }

    public SelectListTypeDisplayQuestion(Context ctx, ArrayList<DisplayQuestionType> arr, SelectListTypeDisplayQuestion.ItemClickListener listener) {
        this.mContext = ctx;
        this.mArrData = arr;
        this.itemClickListener = listener;
    }

    @Override
    public SelectListTypeDisplayQuestion.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_list_type, parent, false);
        return new SelectListTypeDisplayQuestion.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectListTypeDisplayQuestion.ViewHolder holder, final int position) {
        final DisplayQuestionType displayQuestionType = mArrData.get(position);
        if (displayQuestionType != null) {
            holder.tvName.setText(displayQuestionType.getName());
//            if (Config.TYPE_GRID == listType.getId()) {
//                holder.imgTypeList.setImageResource(R.drawable.ic_grid_black);
//            } else {
//                holder.imgTypeList.setImageResource(R.drawable.ic_list_black);
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
            imgTypeList.setVisibility(View.GONE);
            layoutItem = (RelativeLayout) view.findViewById(R.id.layoutItem);
        }
    }
}
