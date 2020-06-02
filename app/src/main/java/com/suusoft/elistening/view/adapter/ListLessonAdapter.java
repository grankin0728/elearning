package com.suusoft.elistening.view.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.suusoft.elistening.AppController;
import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseAdapter;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.databinding.ItemLessonListVmBinding;
import com.suusoft.elistening.databinding.ItemLessonSuusoftVmBinding;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.util.AppUtil;
import com.suusoft.elistening.viewmodel.item.ItemLessonVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suusoft on 09/11/2017.
 */

public class ListLessonAdapter extends BaseAdapter{

    private final int height;
    private List<Lesson> listData;
    private int type;
    private Fragment fragment;
    private LinearLayout.LayoutParams layoutParamsGrid;
    private LinearLayout.LayoutParams layoutParamsList;
    private Activity activity;

    public ListLessonAdapter(Fragment fragment, List<?> datas, int type) {
        super(fragment.getActivity());
        this.fragment = fragment;
        this.activity = fragment.getActivity();
        this.listData = (List<Lesson>) datas;
        this.type = type;
        int withGird = ((AppUtil.getScreenWidth(activity)- AppUtil.convertDpToPixel(activity,fragment.getResources().getDimension(R.dimen.dimen_05x)))
                / GlobalValue.getGridColumn().getId());
        int withList = (AppUtil.getScreenWidth(activity)- AppUtil.convertDpToPixel(activity,fragment.getResources().getDimension(R.dimen.dimen_05x)));
        height = (int) (withGird *1.3);
        AppController.layoutParamsGrid = new LinearLayout.LayoutParams(withGird, height);
        AppController.layoutParamsList = new LinearLayout.LayoutParams(withList , height);
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void removeAll() {
        listData.clear();
        notifyDataSetChanged();
    }

    @Override
    public void setDatas(List<?> data) {
       // listData.clear();
        int size = listData.size();
        if (data!=null )
            listData.addAll((List<Lesson>) data);
        notifyItemRangeInserted(size,listData.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (type ==  Config.ID_TYPE_GRID) {
            ItemLessonSuusoftVmBinding binding = (ItemLessonSuusoftVmBinding) getViewBinding(parent, R.layout.item_lesson_suusoft_vm);
            return new ListLessonAdapter.ViewHolder(binding,  Config.ID_TYPE_GRID);
//        } else {
//            ItemLessonListVmBinding binding = (ItemLessonListVmBinding) getViewBinding(parent, R.layout.item_lesson_list_vm);
//            return new ListLessonAdapter.ViewHolder(binding, Config.ID_TYPE_LIST);
//        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        //if (type ==  Config.ID_TYPE_GRID) {
            ((ListLessonAdapter.ViewHolder) holder).bindViewModel(listData.get(position));
            ((ViewHolder) holder).itemView.getLayoutParams().height = height;
        if ("video".equals(listData.get(position).getType())) {
            ((ViewHolder) holder).imgVideo.setVisibility(View.VISIBLE);
        }else if ("audio".equals(listData.get(position).getType())) {
            ((ViewHolder) holder).imgMusic.setVisibility(View.VISIBLE);
        }else if ("quizz".equals(listData.get(position).getType())) {
            ((ViewHolder) holder).imgUnknow.setVisibility(View.VISIBLE);
        }

//        } else {
//            ((ListLessonAdapter.ViewHolder) holder).bindViewModel(listData.get(position));
//            ((ListLessonAdapter.ViewHolder) holder).itemView.setLayoutParams(AppController.layoutParamsList);
//        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class ViewHolder extends BaseViewHolder{

        private int type;
        private ItemLessonSuusoftVmBinding bindingGrid;
        private ItemLessonListVmBinding bindingList;
        private ImageView imgVideo, imgMusic, imgUnknow;

        public ViewHolder(ItemLessonSuusoftVmBinding binding, int type) {
            super(binding);
            this.bindingGrid = binding;
            this.type = type;
            imgMusic  = (ImageView) itemView.findViewById(R.id.img_music);
            imgVideo  = (ImageView) itemView.findViewById(R.id.img_video);
            imgUnknow  = (ImageView) itemView.findViewById(R.id.img_unknown);

        }

        public ViewHolder(ItemLessonListVmBinding binding, int type) {
            super(binding);
            this.bindingList = binding;
            this.type = type;
            imgMusic  = (ImageView) itemView.findViewById(R.id.img_music);
            imgVideo  = (ImageView) itemView.findViewById(R.id.img_video);
            imgUnknow  = (ImageView) itemView.findViewById(R.id.img_unknown);
        }

        public void bindViewModel(Lesson lesson) {
            if (type ==  Config.ID_TYPE_GRID) {
                if (bindingGrid.getViewModel() == null) {
                    bindingGrid.setViewModel(new ItemLessonVM(fragment, lesson , (ArrayList<Lesson>) listData));
                    Log.e("Listlesson", "bindViewModel: "+lesson.getId() );
                } else {

                    bindingGrid.getViewModel().setData(lesson);

                }
            } else {
                if (bindingList.getViewModel() == null) {
                    bindingList.setViewModel(new ItemLessonVM(fragment, lesson, (ArrayList<Lesson>) listData));
                    Log.e("Listlesson", "bindViewModel 1: "+lesson.getId() );
                } else {
                    bindingList.getViewModel().setData(lesson);

                }
            }

        }
    }
}
