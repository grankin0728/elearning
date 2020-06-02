package com.suusoft.elistening.DaoFavorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.suusoft.elistening.R;
import com.suusoft.elistening.listener.IOnclickItem;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.view.adapter.ListLessonAdapter;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{

    private List<Lesson> favoriteLists;
    Context context;
    private IOnclickItem onclickItem;

    public void setOnclickItem(IOnclickItem onclickItem) {
        this.onclickItem = onclickItem;
    }

    public FavoriteAdapter(List<Lesson> favoriteLists, Context context) {
        this.favoriteLists = favoriteLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorive, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Lesson fl = favoriteLists.get(position);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(fl.getImage()).apply(options).into(holder.imgLesson);
        holder.tvName.setText(fl.getName());
        if ("video".equals(favoriteLists.get(position).getType())) {
            holder.imgVideo.setVisibility(View.VISIBLE);
        }else if ("audio".equals(favoriteLists.get(position).getType())) {
            holder.imgMusic.setVisibility(View.VISIBLE);
        }else if ("quizz".equals(favoriteLists.get(position).getType())) {
            holder.imgUnknow.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickItem.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteLists == null ? 0 : favoriteLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private ImageView imgVideo, imgMusic, imgUnknow, imgLesson;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMusic  =  itemView.findViewById(R.id.img_music);
            imgVideo  =  itemView.findViewById(R.id.img_video);
            imgUnknow =  itemView.findViewById(R.id.img_unknown);
            imgLesson = itemView.findViewById(R.id.img_lesson);
            tvName    = itemView.findViewById(R.id.txt_lessontitle);
        }
    }
}
