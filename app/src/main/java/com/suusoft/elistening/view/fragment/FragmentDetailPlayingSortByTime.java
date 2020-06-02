package com.suusoft.elistening.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suusoft.elistening.AppController;
import com.suusoft.elistening.DaoDownload.DownloadList;
import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseFragment;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.handle.HandleQuestion;
import com.suusoft.elistening.listener.IListenerLoadAudio;
import com.suusoft.elistening.listener.IListenerSwitchDisplayType;
import com.suusoft.elistening.model.modelLesson.Content;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.model.modelLesson.QuestionAnswer;
import com.suusoft.elistening.model.modelLesson.WatchLesson;
import com.suusoft.elistening.service.MusicService;
import com.suusoft.elistening.util.AppUtil;
import com.suusoft.elistening.view.activity.DetailActivity;
import com.suusoft.elistening.view.adapter.AdapterTranscript;
import com.suusoft.elistening.view.layout.CenterLayoutManager;
import com.suusoft.elistening.widgets.mycustomview.MyMediaPlayer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suusoft on 10/16/2017.
 */

public class FragmentDetailPlayingSortByTime extends BaseFragment implements AdapterTranscript.IClick,
        DetailActivity.IMove, DetailActivity.IChange, IListenerSwitchDisplayType, AdapterTranscript.IClickSeekbar,
        View.OnClickListener , Callback, TextView.OnEditorActionListener{

    //Screen item text
    private RelativeLayout screenItem;
    private TextView tvItem;

    //Screen question
    private View screenQuestionCard;
    private TextView tvQuestionCard, tvShowAnswerCorrect;
    private TextView tvCurrentQuestion, tvTotalQuestion , lblQuestion;
    private LinearLayout screenLongAnswer, screenShortAnswer , screenInputAnswer;
    private TextView tvLongAnswer1, tvLongAnswer2, tvLongAnswer3, tvLongAnswer4,
            tvLongAnswer5 , tvLongAnswer6 , tvLongAnswer7 , tvLongAnswer8;
    private LinearLayout screenShortAnswer12, screenShortAnswer34, screenShortAnswer56 ,screenShortAnswer78,
            llCurrentQuestion ;
    private TextView tvShortAnswer1, tvShortAnswer2, tvShortAnswer3, tvShortAnswer4,
            tvShortAnswer5, tvShortAnswer6, tvShortAnswer7, tvShortAnswer8;
    private EditText edtInputAnswer , tvShowAnswer;
    private ImageView imgQuestionCard;
    private TextView btnShowAnswer, btnAnswer, btnSkip;
    private View view12, view34, view56 , view78;

    private ArrayList<TextView> arrTvLongAnswer, arrTvShortAnswer ;
    private ArrayList<LinearLayout> arrScreenShortAnswer ;
    private ArrayList<View> arrView ;

    private QuestionAnswer questionAnswer;
    private String question;

    //Screen list tran
    private RecyclerView screenListTran;
    private CenterLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<String> arr;
    private ArrayList<QuestionAnswer > arrQuestionAnswer;
    private AdapterTranscript adapterTranscript;
    private Lesson lesson;
    private DownloadList downloadList;
    private WatchLesson watchLesson;
    private List<Content> arrtranscript;
    private int tmp = -1, posQuestion = -100;
    private boolean isAnswer = false , isQuestionSuggest = false , isLongAnswer = true ;
    private static MyMediaPlayer media;

    public static void setMediaPlayer(MyMediaPlayer mediaPlayer) {
        media = mediaPlayer;
    }

    public static FragmentDetailPlayingSortByTime newInstance(IListenerLoadAudio iListenerLoadAudio) {
        Bundle bundle = new Bundle();

        FragmentDetailPlayingSortByTime fragment = new FragmentDetailPlayingSortByTime();
        fragment.setArguments(bundle);
        fragment.iListenerLoadAudio = iListenerLoadAudio;
        return fragment;
    }


    @Override
    protected int getLayoutInflate() {
        ((DetailActivity) getActivity()).setiMove(this);
        ((DetailActivity) getActivity()).setChangePlay(this);
        ((DetailActivity) getActivity()).addListenerSwitchDisplayType(this);
        media.setIclickSeekbar(this);
        return R.layout.fragment_detail_playing;
    }

    @Override
    protected void init() {
        arr = new ArrayList<>();
        arrQuestionAnswer = new ArrayList<>();

        try {
            lesson = AppController.getInstance().getArrLessons().get(AppController.getInstance().getLessonIndex());
            if (lesson!=null){
                watchLesson = DataStoreManager.getWatchLesson(lesson.getId());
//                arrtranscript = ParserUtility.sortedByTime(lesson.getContent());
                Log.e("FragmentDetailPlayingSortByTime", arrtranscript.toString() + "");
                for (int i = 0; i < arrtranscript.size(); i++) {

                    if (arrtranscript.get(i).getText()!=null){
                        arr.add(arrtranscript.get(i).getText());
                    }

                    if (arrtranscript.get(i).getQuestionAnswer()!=null){
                        arrQuestionAnswer.add(arrtranscript.get(i).getQuestionAnswer());
                    }

                }
                Log.e("FragmentDetailPlayingSortByTime", arrQuestionAnswer.toString() + "");
            }
        } catch (NullPointerException e) {
            Log.e("LOI", e.getMessage());
        }
    }


    @Override
    protected void initView(View view) {
        Log.e("FragmentDetailPlayingSortByTime", " initView");

        lesson = AppController.getInstance().getLessonAt(AppController.getInstance().getLessonIndex());
        tvItem = view.findViewById(R.id.tv_item);
        tvItem.setMovementMethod(new ScrollingMovementMethod());
        if (lesson != null) {
            tvItem.setText(lesson.getContent());
        }


        initScreenItem(view);

        initScreenList(view);

        initScreenQuestionCard(view);

        showDataInScreen();


//        if (arrtranscript.size()>0)
//            processingShowDataInScreen(0, 0);

    }

    private void initScreenQuestionCard(View view) {
        screenQuestionCard = view.findViewById(R.id.screen_question);
        lblQuestion = (TextView) screenQuestionCard.findViewById(R.id.lblQuestion);
        llCurrentQuestion = screenQuestionCard.findViewById(R.id.llCurrentQuestion);
        tvCurrentQuestion = (TextView) screenQuestionCard.findViewById(R.id.tvCurrenQuestion);
        tvTotalQuestion = (TextView) screenQuestionCard.findViewById(R.id.tvTotalQuestion);
        lblQuestion = (TextView) screenQuestionCard.findViewById(R.id.lblQuestion);
        tvQuestionCard = (TextView) screenQuestionCard.findViewById(R.id.tvQuestion);
        tvShowAnswerCorrect = (TextView) screenQuestionCard.findViewById(R.id.tvShowAnserCorrect);
        llCurrentQuestion = (LinearLayout) screenQuestionCard.findViewById(R.id.llCurrentQuestion);
        screenLongAnswer = (LinearLayout) screenQuestionCard.findViewById(R.id.screenLongAnswer);
        screenShortAnswer = (LinearLayout) screenQuestionCard.findViewById(R.id.screenShortAnswer);
        screenInputAnswer = (LinearLayout) screenQuestionCard.findViewById(R.id.screenInputAnswer);
        screenShortAnswer12 = (LinearLayout) screenQuestionCard.findViewById(R.id.screenShortAnswer12);
        screenShortAnswer34 = (LinearLayout) screenQuestionCard.findViewById(R.id.screenShortAnswer34);
        screenShortAnswer56 = (LinearLayout) screenQuestionCard.findViewById(R.id.screenShortAnswer56);
        screenShortAnswer78 = (LinearLayout) screenQuestionCard.findViewById(R.id.screenShortAnswer78);

        tvShortAnswer1 = (TextView) screenQuestionCard.findViewById(R.id.tvShortAnswer1);
        tvShortAnswer2 = (TextView) screenQuestionCard.findViewById(R.id.tvShortAnswer2);
        tvShortAnswer3 = (TextView) screenQuestionCard.findViewById(R.id.tvShortAnswer3);
        tvShortAnswer4 = (TextView) screenQuestionCard.findViewById(R.id.tvShortAnswer4);
        tvShortAnswer5 = (TextView) screenQuestionCard.findViewById(R.id.tvShortAnswer5);
        tvShortAnswer6 = (TextView) screenQuestionCard.findViewById(R.id.tvShortAnswer6);
        tvShortAnswer7 = (TextView) screenQuestionCard.findViewById(R.id.tvShortAnswer7);
        tvShortAnswer8 = (TextView) screenQuestionCard.findViewById(R.id.tvShortAnswer8);

        tvLongAnswer1 = (TextView) screenQuestionCard.findViewById(R.id.tvLongAnswer1);
        tvLongAnswer2 = (TextView) screenQuestionCard.findViewById(R.id.tvLongAnswer2);
        tvLongAnswer3 = (TextView) screenQuestionCard.findViewById(R.id.tvLongAnswer3);
        tvLongAnswer4 = (TextView) screenQuestionCard.findViewById(R.id.tvLongAnswer4);
        tvLongAnswer5 = (TextView) screenQuestionCard.findViewById(R.id.tvLongAnswer5);
        tvLongAnswer6 = (TextView) screenQuestionCard.findViewById(R.id.tvLongAnswer6);
        tvLongAnswer7 = (TextView) screenQuestionCard.findViewById(R.id.tvLongAnswer7);
        tvLongAnswer8 = (TextView) screenQuestionCard.findViewById(R.id.tvLongAnswer8);

        view12 = (View) screenQuestionCard.findViewById(R.id.view12);
        view34 = (View) screenQuestionCard.findViewById(R.id.view34);
        view56 = (View) screenQuestionCard.findViewById(R.id.view56);
        view78 = (View) screenQuestionCard.findViewById(R.id.view78);

        arrTvLongAnswer = new ArrayList<>();
        arrTvLongAnswer.add(tvLongAnswer1);
        arrTvLongAnswer.add(tvLongAnswer2);
        arrTvLongAnswer.add(tvLongAnswer3);
        arrTvLongAnswer.add(tvLongAnswer4);
        arrTvLongAnswer.add(tvLongAnswer5);
        arrTvLongAnswer.add(tvLongAnswer6);
        arrTvLongAnswer.add(tvLongAnswer7);
        arrTvLongAnswer.add(tvLongAnswer8);

        arrTvShortAnswer = new ArrayList<>();
        arrTvShortAnswer.add(tvShortAnswer1);
        arrTvShortAnswer.add(tvShortAnswer2);
        arrTvShortAnswer.add(tvShortAnswer3);
        arrTvShortAnswer.add(tvShortAnswer4);
        arrTvShortAnswer.add(tvShortAnswer5);
        arrTvShortAnswer.add(tvShortAnswer6);
        arrTvShortAnswer.add(tvShortAnswer7);
        arrTvShortAnswer.add(tvShortAnswer8);

        arrView = new ArrayList<>();
        arrView.add(view12);
        arrView.add(view34);
        arrView.add(view56);
        arrView.add(view78);

        arrScreenShortAnswer = new ArrayList<>();
        arrScreenShortAnswer.add(screenShortAnswer12);
        arrScreenShortAnswer.add(screenShortAnswer34);
        arrScreenShortAnswer.add(screenShortAnswer56);
        arrScreenShortAnswer.add(screenShortAnswer78);

        edtInputAnswer = (EditText)screenQuestionCard.findViewById(R.id.edtInputAnswer);
        imgQuestionCard = (ImageView)screenQuestionCard.findViewById(R.id.imgQuestion);
        btnShowAnswer = (TextView) screenQuestionCard.findViewById(R.id.btnShowAnswer);
        btnAnswer = (TextView) screenQuestionCard.findViewById(R.id.btnAnswer);
        btnSkip = (TextView) screenQuestionCard.findViewById(R.id.btnNext);
        tvShowAnswer = (EditText) screenQuestionCard.findViewById(R.id.tvShowAnswer);
        llCurrentQuestion.setVisibility(View.GONE);

        setOnclickInScreenQuestion();

        changeThemeQuestion();

    }

    private void changeThemeQuestion() {
        HandleQuestion.setColorTextByTheme(self, new TextView[]{lblQuestion,tvShowAnswerCorrect }, llCurrentQuestion);
    }

    private void setOnclickInScreenQuestion() {
        for (int i = 0; i < arrTvLongAnswer.size(); i++){
            arrTvLongAnswer.get(i).setOnClickListener(this);
            arrTvShortAnswer.get(i).setOnClickListener(this);
        }
        btnShowAnswer.setOnClickListener(this);
        btnAnswer.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        edtInputAnswer.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            AppUtil.hideSoftKeyboard(getActivity());
            return true;
        }
        return false;
    }

    private void initScreenList(View view) {
        screenListTran = (RecyclerView) view.findViewById(R.id.rcv_playing);
        adapterTranscript = new AdapterTranscript(getActivity(), arr, this, this);
        layoutManager = new CenterLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        screenListTran.setLayoutManager(layoutManager );
        screenListTran.setAdapter(adapterTranscript);

    }

    private void initScreenItem(View view) {
        screenItem = (RelativeLayout)view.findViewById(R.id.screen_item);
        //tvItem = (TextView)view.findViewById(R.id.tv_item);
    }

    private void showDataInScreen() {
        screenQuestionCard.setVisibility(View.GONE);
        if(((DetailActivity)self).getTabLayout().getTabAt(Constant.TAB_SELECTED_PLAYING).isSelected())
            if (MusicService.getMediaPlayer().isPlaying()){
                DetailActivity.getPlayer().setVisibility(View.VISIBLE);
                //DetailActivity.btnPlay.setVisibility(View.GONE);
            }else {
                DetailActivity.getPlayer().setVisibility(View.VISIBLE);
                //DetailActivity.btnPlay.setVisibility(View.VISIBLE);
            }
        if (DataStoreManager.isDisplayTypeList()){
            screenListTran.setVisibility(View.VISIBLE);
            screenItem.setVisibility(View.GONE);
            Log.e("FragmentDetailPlayingSortByTime", "show list ");
        }else {
            screenListTran.setVisibility(View.GONE);
            screenItem.setVisibility(View.VISIBLE);
            Log.e("FragmentDetailPlayingSortByTime", "show grid ");
        }
    }


    @Override
    protected void getData() {
        Log.e("FragmentDetailPlayingSortByTime", " getData");
        if (AppController.isPausePlayerInPageQuestion){
            if(!MusicService.getMediaPlayer().isPlaying()){
                MusicService.intentStart(self,Constant.ACTION_PLAY );
                AppController.isPausePlayerInPageQuestion = false;
                Log.e("FragmentDetailPlayingSortByTime", "onResume isPausePlayerInPageQuestion false" );
            }
        }
    }


    /*Click button in item transcripAdapter*/
    @Override
    public void click(int pos) {
        Log.e("FragmentDetailPlayingSortByTime", " click");
        Intent intent = new Intent(getActivity(), MusicService.class);
        intent.setAction(Constant.ACTION_SEEK);
        Bundle b = new Bundle();
        int time = arrtranscript.get(pos).getTime() * 1000;
        b.putInt(Constant.K_SEEKTO, time);
        intent.putExtras(b);
        getActivity().startService(intent);

        processingShowDataInScreen(pos, arrtranscript.get(pos).getTime());

    }

    private IListenerLoadAudio iListenerLoadAudio;

    @Override
    public void move(int i) {
        if (i>0) this.iListenerLoadAudio.onLoadedAudio();
        Log.e("FragmentDetailPlayingSortByTime", " move time " + i );
//        int pos = lesson.getIdTranscriptSoftByTime(i/1000);
//        if (pos + 1 == arrtranscript.size()){
////            lesson.setView(true);
////            DataStoreManager.updateView(lesson);
//            watchLesson.setView(true);
//            DataStoreManager.updateViewLesson(watchLesson);
//        }
//        if (Config.TYPE_QUESTION_DISPLAY_WITH_TEXT == GlobalValue.getTypeDisplayQuestion().getId()) {
//            /*hiển thị câu hỏi cùng với text*/
//            if ( (posQuestion == (pos - 1)) && MusicService.getMediaPlayer().isPlaying() && !isAnswer) {
//                //pause mp3 while mp3 playing && khi chưa trả lời câu hỏi
//                MusicService.intentStart(self,Constant.ACTION_PLAY );
//                //Log.e("FragmentDetailPlayingSortByTime", "move posQuestion " + posQuestion + " move pos " + pos);
//            } else {
//                if (screenQuestionCard.getVisibility()==View.GONE)
//                    AppController.isShowQuestionInPagePlaying = false;
//                if (pos != -1 && pos != tmp && MusicService.getMediaPlayer().isPlaying()) {
//                    isAnswer = false;
//                    setAllTextBeforeAnswer();
//                    processingShowDataInScreen(pos, i);
//                    //Log.e("FragmentDetailPlayingSortByTime", "isAnswer = false" + " move time" + i + "s " + " -- move pos " + pos );
//                }
//            }
//            Log.e("FragmentDetailPlayingSortByTime", "question display with text ");
//        } else {
//            /*hiển thị câu hỏi ở cuối cùng*/
//            if (pos != -1 && pos != tmp && MusicService.getMediaPlayer().isPlaying()) {
//                processingShowDataInScreen(pos, i);
//                //Log.e("FragmentDetailPlayingSortByTime", "question display last "+"move pos " + i + "s , move time " + pos);
//            }
//        }

    }

    private void processingShowDataInScreen(int pos, int i){
        AppController.isShowQuestionInPagePlaying =false;
        AppController.position = pos;

        processingShowList(pos);
        if (Config.TYPE_LAST_QUESTION==GlobalValue.getTypeDisplayQuestion().getId()){
            processingTextAnimation(pos);

            if (pos==(arr.size()-1) && pos == media.getSeekBar().getMax() ) {

                processingDisplayQuestionLast();
            }

        }else {
            processingTextOrQuestionAnimation(pos);
        }
        tmp = pos;
    }

    private void processingTextAnimation(int pos) {
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())){
            tvItem.setTextColor(Color.parseColor(GlobalValue.getTheme().getTextPlayHightLight_dark()));
        }else {
            tvItem.setTextColor(Color.parseColor(GlobalValue.getTheme().getTextPlayHightLight_light()));
        }

        posQuestion = -1000;
        showDataInScreen();
        Animation moveRightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.moverighttoleft);
        tvItem.clearAnimation();
        //tvItem.setText(lesson.getContent());
//        if (arr.size()>0)
//            tvItem.setText(Html.fromHtml(arr.get(pos)));
        tvItem.setAnimation(moveRightToLeft);
    }

    private void processingDisplayQuestionLast() {
        AppUtil.showToast(self, "Show question");
    }

    /*xử lý hiển thị ở Recycleview*/
    private void processingShowList(int pos){

        if (layoutManager==null)
            layoutManager = new CenterLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.smoothScrollToPosition(screenListTran, new RecyclerView.State(), pos);
        if (adapterTranscript != null) {
            adapterTranscript.setSelectionPos(pos);
        }
    }

    /*Show the question after the text*/
    private void processingTextOrQuestionAnimation(int pos) {
        AppController.position = pos;

        if (pos>0){

            if (arrtranscript.get(pos-1).getQuestionAnswer()!=null){

                questionAnswer = arrtranscript.get(pos-1).getQuestionAnswer();
                if (questionAnswer.getSuggest().size()>0){
                    showQuestionAnimation(pos-1);
                } else {
                    Log.e("getQuestionAnswer", "arrSuggest null");
                    showQuestionInputAnimation(pos-1);
                }


            }else {
                processingTextAnimation(pos);
            }

        } else {
            processingTextAnimation(pos);
        }
    }

    /*Show the question over the text*/
//    private void processingTextOrQuestionAnimation(int pos) {
//
//        if (arrtranscript.get(pos).getQuestionAnswer()!=null){
//            QuestionAnswer = ParserUtility.getQuestion(arrtranscript.get(pos).getQuestionAnswer());
//            if (QuestionAnswer.getSuggest().size()>0){
//                showQuestionAnimation(pos);
//            } else {
//                Log.e("getQuestionAnswer", "arrSuggest null");
//                showQuestionInputAnimation(pos);
//            }
//
//        } else {
//            posQuestion = -1000;
//            showDataInScreen(DataStoreManager.isDisplayTypeList());
//            Animation moveRightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.moverighttoleft);
//            tvItem.clearAnimation();
//            tvItem.setText("");
//            tvItem.setText(arrtranscript.get(pos).getText());
//            tvItem.setAnimation(moveRightToLeft);
//        }
//    }

//    private void showQuestionInputAnimation(int pos) {
//        AppController.isShowQuestionInPagePlaying = true;
//        isQuestionSuggest = false;
//        posQuestion = pos;
//        if (QuestionAnswer.getImage()!=null){
//            if (!QuestionAnswer.getImage().isEmpty())
//                Picasso.with(self).load( QuestionAnswer.getImage()).into(imgQuestionCard, this);
//            else imgQuestionCard.setVisibility(View.GONE);
//        }else imgQuestionCard.setVisibility(View.GONE);
//
//        tvQuestion.setText(QuestionAnswer.getQuestion());
//        Log.e("FragmentDetailPlaying", " Question " + QuestionAnswer.getQuestion());
//        llAnswer.setVisibility(View.GONE);
//        edtAnswer.setVisibility(View.VISIBLE);
//        tvShowAnswer.setVisibility(View.GONE);
//        screenQuestion.setVisibility(View.VISIBLE);
//        screenItem.setVisibility(View.GONE);
//        screenListTran.setVisibility(View.GONE);
//        Animation moveRightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.moverighttoleft);
//        screenQuestion.clearAnimation();
//        screenQuestion.setAnimation(moveRightToLeft);
//    }

    private void showQuestionInputAnimation(int pos) {
        setVisiblePlayer(View.GONE);
        posQuestion = pos;
        AppController.isShowQuestionInPagePlaying = true;
        isQuestionSuggest = false;
        tvQuestionCard.setText(questionAnswer.getQuestion());
        if (questionAnswer.getImage()!=null){
            if (!questionAnswer.getImage().isEmpty())
                Picasso.with(self).load( questionAnswer.getImage()).into(imgQuestionCard, this);
            else imgQuestionCard.setVisibility(View.GONE);
        }else imgQuestionCard.setVisibility(View.GONE);
        tvShowAnswerCorrect.setText(R.string.enter_your_answer);
        Log.e("FragmentDetailQuestion", " Question " + questionAnswer.getQuestion());
        screenInputAnswer.setVisibility(View.VISIBLE);
        edtInputAnswer.setVisibility(View.VISIBLE);
        edtInputAnswer.setFocusable(true);
        tvShowAnswer.setVisibility(View.GONE);
        btnAnswer.setVisibility(View.VISIBLE);
        screenShortAnswer.setVisibility(View.GONE);
        screenLongAnswer.setVisibility(View.GONE);
        showHaveQuestion();
        Animation moveRightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.moverighttoleft);
        screenQuestionCard.clearAnimation();
        screenQuestionCard.setAnimation(moveRightToLeft);
    }

//    private void showQuestionAnimation(int pos) {
//        AppController.isShowQuestionInPagePlaying = true;
//        isQuestionSuggest = true;
//        posQuestion = pos;
//        if (QuestionAnswer.getImage()!=null){
//            if (!QuestionAnswer.getImage().isEmpty())
//                Picasso.with(self).load( QuestionAnswer.getImage()).into(imgQuestion, this);
//            else imgQuestion.setVisibility(View.GONE);
//        }else imgQuestion.setVisibility(View.GONE);
//
//        tvQuestion.setText(QuestionAnswer.getQuestion());
//        Log.e("FragmentDetailPlaying", " Question " + QuestionAnswer.getQuestion());
//        showAnswer();
//        edtAnswer.setVisibility(View.GONE);
//        tvShowAnswer.setVisibility(View.GONE);
//        llAnswer.setVisibility(View.VISIBLE);
//        screenQuestion.setVisibility(View.VISIBLE);
//        screenItem.setVisibility(View.GONE);
//        screenListTran.setVisibility(View.GONE);
//        Animation moveRightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.moverighttoleft);
//        screenQuestion.clearAnimation();
//        screenQuestion.setAnimation(moveRightToLeft);
//
//    }



    private void showQuestionAnimation(int pos) {
        setVisiblePlayer( View.GONE);
        AppController.isShowQuestionInPagePlaying = true;
        posQuestion = pos;
        isQuestionSuggest = true;
//        question = getString(R.string.question) +"<b><font color=\"#000000\">"+ " " + questionAnswer.getQuestion() + "</font></b>";
        tvQuestionCard.setText(questionAnswer.getQuestion());
        if (questionAnswer.getImage()!=null){
            if (!questionAnswer.getImage().isEmpty())
                Picasso.with(self).load( questionAnswer.getImage()).into(imgQuestionCard, this);
            else imgQuestionCard.setVisibility(View.GONE);
        }else imgQuestionCard.setVisibility(View.GONE);
        tvShowAnswerCorrect.setText(R.string.choose_correct_answer);
        screenInputAnswer.setVisibility(View.GONE);
        edtInputAnswer.setVisibility(View.GONE);
        btnAnswer.setVisibility(View.GONE);
        if (questionAnswer.getAnswer().length()>10){
            isLongAnswer = true;
            screenLongAnswer.setVisibility(View.VISIBLE);
            screenShortAnswer.setVisibility(View.GONE);
        }else {
            isLongAnswer = false;
            screenShortAnswer.setVisibility(View.VISIBLE);
            screenLongAnswer.setVisibility(View.GONE);
        }
        Log.e("FragmentDetailQuestion", " Question " + questionAnswer.getQuestion());
        showAnswerCard();
        showHaveQuestion();
        Animation moveRightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.moverighttoleft);
        screenQuestionCard.clearAnimation();
        screenQuestionCard.setAnimation(moveRightToLeft);

    }

    private void setVisiblePlayer(int visiblePlayer) {
        //DetailActivity.getPlayer().setVisibility(visiblePlayer);
        //DetailActivity.btnPlay.setVisibility(visiblePlayer);
    }

    private void showHaveQuestion() {
        screenQuestionCard.setVisibility(View.VISIBLE);
        screenListTran.setVisibility(View.GONE);
        screenItem.setVisibility(View.GONE);
    }


    private void showAnswerCard() {
        if (questionAnswer.getSuggest()!=null){
            if (isLongAnswer){
                showLongAnswerSuggest();
            }else {
                showShortAnswerSuggest();
            }
        }
    }

    private void showLongAnswerSuggest() {
        switch (questionAnswer.getSuggest().size()){
            case 2:
                showTextAnswerInView(arrTvLongAnswer , 2);
                break;

            case 3:
                showTextAnswerInView(arrTvLongAnswer , 3);
                break;

            case 4:
                showTextAnswerInView(arrTvLongAnswer , 4);
//                tvLongAnswer1.setText(questionAnswer.getSuggest().get(0).getQuestion());
//                tvLongAnswer2.setText(questionAnswer.getSuggest().get(1).getQuestion());
//                tvLongAnswer3.setText(questionAnswer.getSuggest().get(2).getQuestion());
//                tvLongAnswer4.setText(questionAnswer.getSuggest().get(3).getQuestion());
//                tvLongAnswer3.setVisibility(View.VISIBLE);
//                tvLongAnswer4.setVisibility(View.VISIBLE);
                break;

            case 5:
                showTextAnswerInView(arrTvLongAnswer , 5);
                break;

            case 6:
                showTextAnswerInView(arrTvLongAnswer , 6);
                break;

            case 7:
                showTextAnswerInView(arrTvLongAnswer , 7);
                break;
            case 8:
                showTextAnswerInView(arrTvLongAnswer , 8);
                break;
        }
    }

    private void showShortAnswerSuggest() {
        switch (questionAnswer.getSuggest().size()){
            case 2:
                showTextAnswerInView(arrTvShortAnswer , 2);
//                tvShortAnswer1.setText(questionAnswer.getSuggest().get(0).getQuestion());
//                tvShortAnswer2.setText(questionAnswer.getSuggest().get(1).getQuestion());
//                screenShortAnswer34.setVisibility(View.GONE);
//                screenShortAnswer56.setVisibility(View.GONE);
                break;

            case 3:
                showTextAnswerInView(arrTvShortAnswer , 3);
                break;

            case 4:
                showTextAnswerInView(arrTvShortAnswer , 4);
                break;

            case 5:
                showTextAnswerInView(arrTvShortAnswer , 5);
                break;

            case 6:
                showTextAnswerInView(arrTvShortAnswer , 6);
                break;

            case 7:
                showTextAnswerInView(arrTvShortAnswer , 7);
                break;

            case 8:
                showTextAnswerInView(arrTvShortAnswer , 8);
                break;
        }
    }


    private void showTextAnswerInView(ArrayList<TextView> arrTvAnswer, int numberOfSuggest) {

        for (int j = 0; j <arrTvAnswer.size(); j ++){
            if (j< numberOfSuggest){
                arrTvAnswer.get(j).setText(questionAnswer.getSuggest().get(j).getQuestion());
                arrTvAnswer.get(j).setVisibility(View.VISIBLE);
            } else arrTvAnswer.get(j).setVisibility(View.GONE);

            //Kiểm tra và hiển thị các view
            if (!isLongAnswer && j < arrView.size()){
                if (numberOfSuggest % 2 ==0) {
                    if (j < (numberOfSuggest /2) ){
                        arrView.get(j).setVisibility(View.VISIBLE);
                        arrScreenShortAnswer.get(j).setVisibility(View.VISIBLE);
                    }else {
                        arrScreenShortAnswer.get(j).setVisibility(View.GONE);
                        arrView.get(j).setVisibility(View.GONE);
                    }
                } else {
                    if (j <=(numberOfSuggest /2)){
                        arrView.get(j).setVisibility(View.VISIBLE);
                        if (j==(numberOfSuggest /2))
                            arrView.get(j).setVisibility(View.GONE);
                        arrScreenShortAnswer.get(j).setVisibility(View.VISIBLE);
                    }else  {
                        arrScreenShortAnswer.get(j).setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    @Override
    public void change() {
        Log.e("FragmentDetailPlayingSortByTime", " change");
        lesson = AppController.getInstance().getArrLessons().get(AppController.getInstance().getLessonIndex());
////        arrtranscript = ParserUtility.sortedByTime(lesson.getContent());
//
        arr.clear();
        arrQuestionAnswer.clear();
//        for (int i = 0; i < arrtranscript.size(); i++) {
//
//            if (arrtranscript.get(i).getText()!=null){
//                arr.add(arrtranscript.get(i).getText());
//            }
//
//            if (arrtranscript.get(i).getQuestionAnswer()!=null){
//                arrQuestionAnswer.add(arrtranscript.get(i).getQuestionAnswer());
//            }
//        }
//        Log.e("FragmentDetailPlayingSortByTime", arrtranscript.toString() + "");
//        adapterTranscript.change();
    }

    @Override
    public void onResume() {
        Log.e("FragmentDetailPlayingSortByTime", " onResume");
        if (questionAnswer!=null){
            if (AppController.isShowQuestionInPagePlaying){
                if (isQuestionSuggest)
                    showQuestionAnimation(AppController.position);
                else
                    showQuestionInputAnimation(AppController.position);
            }else
                onClickSeekbar(media.getSeekBar().getProgress());
        }
        super.onResume();
    }

    private void showQuestion() {
        screenItem.setVisibility(View.GONE);
        screenListTran.setVisibility(View.GONE);
        screenQuestionCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        Log.e("FragmentDetailPlayingSortByTime", "onPause ");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.e("FragmentDetailPlayingSortByTime", "onDestroy ");
        if (adapterTranscript != null) adapterTranscript.destroyTTS();
        super.onDestroy();
    }


    @Override
    public void onStop() {
        Log.e("FragmentDetailPlayingSortByTime", "onStop ");
        super.onStop();
    }


    /*Click on option menu switch display list trans*/
    @Override
    public void onChangeDisplayListOrItem() {
        Log.e("FragmentDetailPlayingSortByTime", " onChangeDisplayListOrItem list");
        showDataInScreen();
    }

    /*click on seekbar*/
    @Override
    public void onClickSeekbar(int currenProgress) {

        showDataInScreen();

        int time = currenProgress/1000;
        Log.e("FragmentDetailPlayingSortByTime", " onClickSeekbar " + time);
//        for (int i = 0; i < arrtranscript.size(); i++){
//            if (i + 1< arrtranscript.size()){
//                if (arrtranscript.get(i).getTime() <= time
//                        && arrtranscript.get(i+1).getTime() >= time){
//                    Log.e("FragmentDetailPlayingSortByTime", " onClickSeekbar " + arrtranscript.get(i).getText());
//                    processingShowDataInScreen(i, i);
//                }
//            }else {
//                if (arrtranscript.get(i).getTime() <= time ){
//                    Log.e("FragmentDetailPlayingSortByTime", " onClickSeekbar " + arrtranscript.get(i).getText());
//                    processingShowDataInScreen(i, i);
//                }
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvShortAnswer1:
                processingClickAnswer(tvShortAnswer1);
                break;

            case R.id.tvShortAnswer2:
                processingClickAnswer(tvShortAnswer2);
                break;

            case R.id.tvShortAnswer3:
                processingClickAnswer(tvShortAnswer3);
                break;

            case R.id.tvShortAnswer4:
                processingClickAnswer(tvShortAnswer4);
                break;

            case R.id.tvShortAnswer5:
                processingClickAnswer(tvShortAnswer5);
                break;

            case R.id.tvShortAnswer6:
                processingClickAnswer(tvShortAnswer6);
                break;

            case R.id.tvLongAnswer1:
                processingClickAnswer(tvLongAnswer1);
                break;

            case R.id.tvLongAnswer2:
                processingClickAnswer(tvLongAnswer2);
                break;

            case R.id.tvLongAnswer3:
                processingClickAnswer(tvLongAnswer3);
                break;

            case R.id.tvLongAnswer4:
                processingClickAnswer(tvLongAnswer4);
                break;

            case R.id.btnAnswer:
                if (edtInputAnswer.getText().toString().isEmpty() || edtInputAnswer.getText().toString().length()==0 ){
                    AppUtil.showToast(self, getString(R.string.you_have_not_answered_the_question_yet));
                }else {
                    if (edtInputAnswer.getText().toString().equals(questionAnswer.getAnswer())){
                        setTextColorAndBackGroundCorrectAnswer(edtInputAnswer);
                        //trả lời đúng mới được đi tiếp
                        AppUtil.hideSoftKeyboard(getActivity());
                        playMusicService();
                    }else {
                        setTextColorAndBackGroundWrongAnswer(edtInputAnswer);
                        waitingTimeAfterWrongAnswer();
                    }

                }
                break;

            case R.id.btnNext:
                AppUtil.hideSoftKeyboard(getActivity());
                playMusicService();
                break;

            case R.id.btnShowAnswer:
                AppUtil.hideSoftKeyboard(getActivity());
                if (isQuestionSuggest){
                    processingShowAnswerIsQuestionSuggest();
                }else {
                    processingShowAnswer();
                }
                playMusicServiceDelay();
                break;
        }
    }

    private void waitingTimeAfterWrongAnswer() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setTextColorAndBackGroundBeforeAnswer(edtInputAnswer);
            }
        },700);

    }

    private void waitingTimeAfterShowAnswer() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setAllTextBeforeAnswer();
            }
        },3000);

    }



    private void processingClickAnswer(TextView tv) {
        if (tv.getText().equals(questionAnswer.getAnswer())) {
            setTextColorAndBackGroundCorrectAnswer(tv);
        } else {
            setTextColorAndBackGroundWrongAnswer(tv);
        }
        playMusicService();
    }

    private void processingAnswerCorrect(TextView tv) {
        if (tv.getText().equals(questionAnswer.getAnswer())){
            setTextColorAndBackGroundCorrectAnswer(tv);
        }
    }

    private void processingShowAnswerIsQuestionSuggest() {
        if (isLongAnswer){
            processingAnswerCorrect(tvLongAnswer1);
            processingAnswerCorrect(tvLongAnswer2);
            processingAnswerCorrect(tvLongAnswer3);
            processingAnswerCorrect(tvLongAnswer4);

        }else {
            processingAnswerCorrect(tvShortAnswer1);
            processingAnswerCorrect(tvShortAnswer2);
            processingAnswerCorrect(tvShortAnswer3);
            processingAnswerCorrect(tvShortAnswer4);
            processingAnswerCorrect(tvShortAnswer5);
            processingAnswerCorrect(tvShortAnswer6);
        }

    }

    private void processingShowAnswer() {
        tvShowAnswer.setText(questionAnswer.getAnswer());
        setTextColorAndBackGroundCorrectAnswer(tvShowAnswer);
        edtInputAnswer.setVisibility(View.GONE);
        tvShowAnswer.setVisibility(View.VISIBLE);
    }

    private void setTextColorAndBackGroundCorrectAnswer(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.whitetext));
        tv.setBackgroundResource(R.drawable.bg_txt_answer_correct_radius);
    }

    private void setTextColorAndBackGroundCorrectAnswer(EditText tv) {
        tv.setTextColor(getResources().getColor(R.color.whitetext));
        tv.setBackgroundResource(R.drawable.bg_txt_answer_correct_radius);
    }

    private void setTextColorAndBackGroundWrongAnswer(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.whitetext));
        tv.setBackgroundResource(R.drawable.bg_txt_answer_wrong_radius);
    }

    private void setTextColorAndBackGroundWrongAnswer(EditText tv) {
        tv.setTextColor(getResources().getColor(R.color.whitetext));
        tv.setBackgroundResource(R.drawable.bg_txt_answer_wrong_radius);
    }

    private void playMusicServiceDelay(){
        if (!MusicService.getMediaPlayer().isPlaying()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    playMusicService();
                }
            },10000);
        }
    }

    private void playMusicService() {
        if (!MusicService.getMediaPlayer().isPlaying()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MusicService.intentStart(self,Constant.ACTION_PLAY );
                    processingTextAnimation(AppController.position);
                    showDataInScreen();
                    isAnswer = true;
                }
            },500);
        }
    }

    private void setAllTextBeforeAnswer(){
        if (isLongAnswer){
            setTextColorAndBackGroundBeforeAnswer(tvLongAnswer1);
            setTextColorAndBackGroundBeforeAnswer(tvLongAnswer2);
            setTextColorAndBackGroundBeforeAnswer(tvLongAnswer3);
            setTextColorAndBackGroundBeforeAnswer(tvLongAnswer4);
        }else {
            setTextColorAndBackGroundBeforeAnswer(tvShortAnswer1);
            setTextColorAndBackGroundBeforeAnswer(tvShortAnswer2);
            setTextColorAndBackGroundBeforeAnswer(tvShortAnswer3);
            setTextColorAndBackGroundBeforeAnswer(tvShortAnswer4);
            setTextColorAndBackGroundBeforeAnswer(tvShortAnswer5);
            setTextColorAndBackGroundBeforeAnswer(tvShortAnswer6);
        }

        if (!isQuestionSuggest) {
            setTextColorAndBackGroundBeforeAnswer(edtInputAnswer);
        }
    }

    private void setTextColorAndBackGroundBeforeAnswer(EditText tv) {
        tv.setText("");
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setBackgroundResource(R.drawable.bg_btn_question_green_radius_pale);
    }

    private void setTextColorAndBackGroundBeforeAnswer(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setBackgroundResource(R.drawable.bg_txt_question_troke_radius);
    }

    //Listener load image errorr or success
    @Override
    public void onSuccess() {
        imgQuestionCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError() {
        imgQuestionCard.setVisibility(View.GONE);
    }

    private BroadcastReceiver settingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String actionChange = intent.getAction();
            switch (actionChange){
                case Constant.LISTEN_CHANGE_THEME:
                    changeThemeQuestion();
            }
        }
    };
}
