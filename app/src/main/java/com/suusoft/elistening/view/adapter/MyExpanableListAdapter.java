package com.suusoft.elistening.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.util.AppUtil;
import com.squareup.picasso.Callback;

import java.util.List;

/**
 * Created by ASUS on 3/29/2017.
 */

public class MyExpanableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private List<String> word;
    private List<String> mean;
    private List<String> pronounciation;
    private List<String> image;
    private int posExpand, posColapse;

    public MyExpanableListAdapter(Activity context, List<String> word, List<String> mean, List<String> pronounciation , List<String> image ) {
        this.context = context;
        this.word = word;
        this.image = image;
        this.mean = mean;
        this.pronounciation = pronounciation;
    }

    public void setExpand(int pos){
        this.posExpand = pos;
    }

    public void setColapse(int pos){
        this.posColapse = pos;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return this.word.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return word.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mean.get(i);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.item_listexp_group, viewGroup, false);
        }
        TextView txtHead = (TextView) view.findViewById(R.id.txt_listgroup);
        txtHead.setText(headerTitle);

        ImageView imgIconGroup = (ImageView)view.findViewById(R.id.icon_group);
//        if (posExpand ==i){
//            imgIconGroup.setImageResource(R.drawable.ic_word_group_expand);
//        }else  imgIconGroup.setImageResource(R.drawable.ic_word_group);
//
//        if (posColapse==i)
//            imgIconGroup.setImageResource(R.drawable.ic_word_group);
//        else  imgIconGroup.setImageResource(R.drawable.ic_word_group_expand);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
//        String itemText = (String) getChild(i, i1);

        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.item_listexp, viewGroup, false);
        }
        TextView txtItemMean = (TextView) view.findViewById(R.id.txt_itemlist_mean);
        TextView txtItemPronounciation = (TextView) view.findViewById(R.id.txt_itemlist_pronounciation);
        final ImageView img = (ImageView) view.findViewById(R.id.img_word);
        txtItemMean.setText(mean.get(i));
        txtItemPronounciation.setText(pronounciation.get(i));
        AppUtil.setImage(img, image.get(i), new Callback() {
            @Override
            public void onSuccess() {
                img.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                img.setVisibility(View.GONE);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    class ViewHolder{
        private TextView tvMean, tvPronounciation;

        public ViewHolder(View itemView){
            tvMean = (TextView)itemView.findViewById(R.id.txt_itemlist_mean);
            tvPronounciation = (TextView)itemView.findViewById(R.id.txt_itemlist_pronounciation);
        }


        public void bindData(int pos){
            tvMean.setText(mean.get(pos));
            tvPronounciation.setText(pronounciation.get(pos));

        }

    }

}
