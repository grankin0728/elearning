package com.suusoft.elistening.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suusoft.elistening.AppController;
import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseFragment;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.listener.IListenerExpand;
import com.suusoft.elistening.listener.IListenerSwitchDisplayType;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.model.modelLesson.Vocabulary;
import com.suusoft.elistening.service.MusicService;
import com.suusoft.elistening.view.activity.DetailActivity;
import com.suusoft.elistening.view.adapter.MyExpanableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 3/22/2017.
 */

public class FragmentDetailVocabulary extends BaseFragment implements DetailActivity.IChange ,
        IListenerSwitchDisplayType , View.OnClickListener , IListenerExpand{

    private ExpandableListView expl;
    private MyExpanableListAdapter adapter;
    private List<String> word;
    private ArrayList<String> mean;
    private ArrayList<String> image;
    private ArrayList<String> pronunciation;
    private ArrayList<Vocabulary> arrVocabularies;
    private int index;
    private Lesson lesson;

    private LinearLayout btnExpand;
    private ImageView imgExpand;
    private boolean isExpand = false;
    private IListenerExpand iListenerExpand;
    private TextView tvNotVoca;

    public static FragmentDetailVocabulary newInstance( ) {
        Bundle bundle = new Bundle();
        FragmentDetailVocabulary fragment = new FragmentDetailVocabulary();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.fragment_detail_word;
    }

    @Override
    protected void init() {
        index = AppController.getInstance().getLessonIndex();
        lesson = AppController.getInstance().getLessonAt(index);
        //arrVocabularies = lesson.getVocabularys();
        word = new ArrayList<>();
        mean = new ArrayList<>();
        image = new ArrayList<>();

        pronunciation = new ArrayList<>();
        if (arrVocabularies!=null){
            for (int i = 0; i < arrVocabularies.size(); i++) {
                word.add(arrVocabularies.get(i).getWord());
                mean.add(arrVocabularies.get(i).getMean());
                image.add(arrVocabularies.get(i).getImage());
                pronunciation.add(arrVocabularies.get(i).getPronunciation());
            }
        }

        iListenerExpand = this;
        ((DetailActivity) getActivity()).setChangeWord(this);
        ((DetailActivity) getActivity()).addListenerSwitchDisplayType(this);
    }

    @Override
    protected void initView(View view) {
        Log.e("FragmentDetailVocabulary", "initView");
        tvNotVoca = (TextView) view.findViewById(R.id.tvNotVoca) ;
        btnExpand = (LinearLayout)view.findViewById(R.id.btnExpand) ;
        imgExpand = (ImageView)view.findViewById(R.id.imgExpand);
        btnExpand.setOnClickListener(this);
        expl = (ExpandableListView) view.findViewById(R.id.elv);
        adapter = new MyExpanableListAdapter(getActivity(), word, mean, pronunciation ,  image);
        expl.setAdapter(adapter);
        expl.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if (expandableListView.isGroupExpanded(i)) {
                    expandableListView.collapseGroup(i);
                    adapter.setColapse(i);

                }else {
                    expandableListView.expandGroup(i , true);
                    adapter.setExpand(i);
                }
                return true;
            }
        });

    }

    @Override
    protected void getData() {
        if (AppController.isPausePlayerInPageQuestion){
            if(!MusicService.getMediaPlayer().isPlaying()){
                Intent intent = new Intent(self, MusicService.class);
                intent.setAction(Constant.ACTION_PLAY);
                self.startService(intent);
                AppController.isPausePlayerInPageQuestion = false;
            }
        }
        Log.e("FragmentDetailVocabulary", "getData");
    }

    @Override
    public void onResume() {
//        if (arrVocabularies.size() ==0)
//        {
//            tvNotVoca.setVisibility(View.VISIBLE);
//            expl.setVisibility(View.GONE);
//        }

        if (AppController.isPausePlayerInPageQuestion){
            if(!MusicService.getMediaPlayer().isPlaying()){
                Intent intent = new Intent(self, MusicService.class);
                intent.setAction(Constant.ACTION_PLAY);
                self.startService(intent);
                AppController.isPausePlayerInPageQuestion = false;
                Log.e("FragmentDetailPlaying", "onResume isPausePlayerInPageQuestion false" );
            }
        }
        Log.e("FragmentDetailVocabulary", "onResume");

        change();
        super.onResume();
    }

    @Override
    public void onStop() {
        Log.e("FragmentDetailVocabulary", "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.e("FragmentDetailVocabulary", "onDestroy");
        super.onDestroy();

    }

    @Override
    public void change() {
        index = AppController.getInstance().getLessonIndex();
        lesson = AppController.getInstance().getArrLessons().get(index);
        //arrVocabularies = lesson.getVocabularys();
        word.clear();
        mean.clear();
        if (arrVocabularies!=null){
            for (int i = 0; i < arrVocabularies.size(); i++) {
                word.add(arrVocabularies.get(i).getWord());
                mean.add(arrVocabularies.get(i).getMean());
                pronunciation.add(arrVocabularies.get(i).getPronunciation());
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChangeDisplayListOrItem() {
        if (!DataStoreManager.isExpandListWord()){
            for (int j = 0; j < word.size(); j++) {
                if (expl.isGroupExpanded(j)){
                    expl.collapseGroup(j);
                    adapter.setColapse(j);
                }

            }
        }else {
            for (int j = 0; j < word.size(); j++) {
                if (!expl.isGroupExpanded(j)){
                    expl.expandGroup(j, true);
                    adapter.setExpand(j);
                }
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnExpand:
                    isExpand= !isExpand;
                iListenerExpand.onChangExpandOrCollapse();

                break;
        }
    }

    @Override
    public void onChangExpandOrCollapse() {
        if (isExpand){
            imgExpand.setImageResource(R.drawable.ic_collapse);
            for (int j = 0; j < word.size(); j++) {
                if (!expl.isGroupExpanded(j)){
                    expl.expandGroup(j, true);
                    adapter.setExpand(j);
                }
            }
        }else {
            imgExpand.setImageResource(R.drawable.ic_expand);
            for (int j = 0; j < word.size(); j++) {
                if (expl.isGroupExpanded(j)){
                    expl.collapseGroup(j);
                    adapter.setColapse(j);
                }

            }
        }

    }
}
