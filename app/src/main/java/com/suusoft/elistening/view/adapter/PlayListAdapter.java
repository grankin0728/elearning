package com.suusoft.elistening.view.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.suusoft.elistening.AppController;
import com.suusoft.elistening.R;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.service.MusicService;

import java.util.ArrayList;

/**
 * Created by ASUS on 4/27/2017.
 */

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> lessons;
    private LayoutInflater inflater;

    public PlayListAdapter(Context context, ArrayList<String> arr) {
        this.mContext = context;
        this.lessons = arr;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recycle_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtTitle.setText(lessons.get(position));
        if (AppController.getInstance().getLessonIndex() == position) {
            holder.llStatus.setVisibility(View.VISIBLE);
        } else holder.llStatus.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().setLessonIndex(position);
                MusicService.intentStart(mContext,Constant.ACTION_OPEN );
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public void change() {
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private LinearLayout llStatus;

        ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title_playlist);
            llStatus = (LinearLayout) itemView.findViewById(R.id.ll_status);
        }
    }
}
