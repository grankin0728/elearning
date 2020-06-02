package com.suusoft.elistening.DaoDownload;

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

import java.util.List;

public class DownloadApdapter extends RecyclerView.Adapter<DownloadApdapter.ViewHolder> {

    private List<DownloadList> downloadLists;
    Context context;
    private IOnclickItem onclickItem;
    private ItemClickListener listener;

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnclickItem(IOnclickItem onclickItem) {
        this.onclickItem = onclickItem;
    }

    public DownloadApdapter(List<DownloadList> downloadLists, Context context) {
        this.downloadLists = downloadLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_download, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        DownloadList dl = downloadLists.get(position);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(dl.getImage()).apply(options).into(holder.imgLesson);
        holder.tvName.setText(dl.getName());
        if ("video".equals(downloadLists.get(position).getType())) {
            holder.imgVideo.setVisibility(View.VISIBLE);
        }else if ("audio".equals(downloadLists.get(position).getType())) {
            holder.imgMusic.setVisibility(View.VISIBLE);
        }else if ("quizz".equals(downloadLists.get(position).getType())) {
            holder.imgUnknow.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickItem.onItemClick(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemStudentLongClicked(position);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return downloadLists == null ? 0 : downloadLists.size();
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

    public interface ItemClickListener {
        void onItemStudentLongClicked(int position);
    }
}
