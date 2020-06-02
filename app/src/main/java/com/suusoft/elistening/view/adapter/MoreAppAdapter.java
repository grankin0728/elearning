package com.suusoft.elistening.view.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.model.OurApp;
import com.suusoft.elistening.util.AppUtil;

import java.util.List;

/**
 * Created by apptemplate.co on 6/19/17.
 */

public class MoreAppAdapter extends RecyclerView.Adapter<MoreAppAdapter.ViewHolder> {

    List<OurApp> mDatas;
    Context mContext;

    public MoreAppAdapter(Context context, List<OurApp> mDatas) {
        this.mDatas = mDatas;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OurApp item = mDatas.get(position);
        if (item != null){
            holder.bind(item);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtil.openWebView(mContext, item.getLinkSite());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct, btnStore, btnSite;
        TextView tvName, tvDescription;
        ViewGroup content;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDescription= (TextView) itemView.findViewById(R.id.tv_description);
            btnStore = (ImageView) itemView.findViewById(R.id.btn_demo);
            imgProduct = (ImageView) itemView.findViewById(R.id.img_product);
            content = (ViewGroup) itemView.findViewById(R.id.content);
            if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())) {
                tvName.setTextColor(Color.parseColor(GlobalValue.getTheme().getTextColorPrimaryDark()));
                tvDescription.setTextColor(Color.parseColor(GlobalValue.getTheme().getTextColorSecondaryDark()));
                AppUtil.setBackgroundRadius(content, GlobalValue.getTheme().getColorPrimaryDark());
            } else {
                tvName.setTextColor(Color.parseColor(GlobalValue.getTheme().getTextColorPrimaryLight()));
                tvDescription.setTextColor(Color.parseColor(GlobalValue.getTheme().getTextColorSecondaryLight()));
                AppUtil.setBackgroundRadiusWhite(mContext, content);
            }
        }

        public void bind(final OurApp item){
            tvName.setText(item.getName());
            tvDescription.setText(item.getDescription());
            imgProduct.setImageResource(mContext.getResources().getIdentifier(item.getImage(), "drawable", mContext.getPackageName()));

            btnStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtil.openWebView(mContext, item.getLinkStore());
                }
            });

        }
    }
}
