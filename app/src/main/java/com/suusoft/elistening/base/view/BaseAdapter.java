package com.suusoft.elistening.base.view;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Suusoft on 7/12/2016.
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    protected Context context;
    public abstract void setDatas(List<?> data);

    public BaseAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<?> data) {
        setDatas(data);
//        notifyDataSetChanged();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
        }
    }

    protected ViewDataBinding getViewBinding(ViewGroup parent, int layout){
        return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                layout, parent, false);

    }

}
