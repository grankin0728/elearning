package com.suusoft.elistening.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.suusoft.elistening.AppController;
import com.suusoft.elistening.DaoFavorite.FavoriteDatabase;
import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseActivity;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.handle.HandleQuestion;
import com.suusoft.elistening.listener.IListenerChangeFragmentDetail;
import com.suusoft.elistening.listener.ILoadQuestion;
import com.suusoft.elistening.model.modelLesson.Content;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.model.modelLesson.Question;
import com.suusoft.elistening.model.modelLesson.QuestionAnswer;
import com.suusoft.elistening.retrofit.APIService;
import com.suusoft.elistening.retrofit.ApiUtils;
import com.suusoft.elistening.retrofit.respone.ResponeAnswer;
import com.suusoft.elistening.service.MusicService;
import com.suusoft.elistening.util.AppUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class QuestionActivity extends BaseActivity implements DetailActivity.IChange , Callback, ILoadQuestion , View.OnClickListener{

    final static String TAG = QuestionActivity.class.getSimpleName();
    public static int REQUEST_CODE = 10;
    public static int RESULT_CODE_REPLY_AGAIN = 11;
    public static int RESULT_CODE_BACK_TO_BOARD = 12;
    public static int RESULT_CODE_NEXT_LESSON = 13;
    public static String URL_BACKGROUND = "background";
    public static String ANSWER_CORRECT = "answer_correct";
    public static String TITLE_TOOLBAR = "title";
    public static String ARR_QUESTION = "arr_question";
    public static String SIZE_QUESTION = "size_question";

    //Screen question
    private Question questionAnswer;
    private boolean isQuestionSuggest = false , isShowQuestion = false , isLongAnswer = true;
    private ILoadQuestion iLoadQuestion;

    private View screenQuestionCard;
    private TextView tvQuestionCard, tvShowAnswerCorrect;
    private TextView lblQuestion, tvCurrentQuestion, tvTotalQuestion, tvTest;
    private LinearLayout screenLongAnswer, screenShortAnswer , screenInputAnswer;
    private TextView tvLongAnswer1, tvLongAnswer2, tvLongAnswer3, tvLongAnswer4,
            tvLongAnswer5 , tvLongAnswer6 , tvLongAnswer7 , tvLongAnswer8;
    private LinearLayout screenShortAnswer12, screenShortAnswer34, screenShortAnswer56 ,
            screenShortAnswer78,llCurrentQuestion ;
    private TextView tvShortAnswer1, tvShortAnswer2, tvShortAnswer3, tvShortAnswer4,
            tvShortAnswer5, tvShortAnswer6, tvShortAnswer7, tvShortAnswer8;
    private EditText edtInputAnswer, tvShowAnswer;
    private ImageView imgQuestionCard;
    private TextView btnShowAnswer, btnAnswer, btnSkip;
    private View view12, view34, view56 , view78;

    private ArrayList<TextView> arrTvLongAnswer, arrTvShortAnswer ;
    private ArrayList<LinearLayout> arrScreenShortAnswer ;
    private ArrayList<View> arrView ;
    private TextView tvNotifiQuestion, tvNotQuestion;
    private ArrayList<Question> arrQuestionAnswer;
    private int index , i = 0 , count = 0 , answerCorrect = 0;
    private Lesson lesson;
    private int id;

    private IListenerChangeFragmentDetail iListenerChangeFragmentDetail;

    public void setiListenerChangeFragmentDetail(IListenerChangeFragmentDetail iListenerChangeFragmentDetail) {
        this.iListenerChangeFragmentDetail = iListenerChangeFragmentDetail;
    }

    @Override
    protected ToolbarType getToolbarType() {
        return ToolbarType.NAVI;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.fragment_detail_question;
    }

    @Override
    protected void getExtraData(Intent intent) {
        lesson = AppController.getInstance().getLessonAt(AppController.getInstance().getLessonIndex());
    }

    @Override
    protected void initilize() {


    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.msg_grammar_quizz);

        //get arr question in lesson
//        index = AppController.getInstance().getLessonIndex();
//        lesson = AppController.getInstance().getLessonAt(index);
        //arrContent = lesson.getContent();


        getListQuestion();
        try {
            lesson = AppController.getInstance().getArrLessons().get(AppController.getInstance().getLessonIndex());

        } catch (NullPointerException e) {
            Log.e("LOI", e.getMessage());
        }

        iLoadQuestion = this;

        tvNotifiQuestion =  findViewById(R.id.tvNotifiQuestion);
        tvNotQuestion    =  findViewById(R.id.tvNotQuestion);
       // initScreenQuestionCard();
        screenQuestionCard = findViewById(R.id.screen_question_card);
        lblQuestion = (TextView) screenQuestionCard.findViewById(R.id.lblQuestion);
        llCurrentQuestion = screenQuestionCard.findViewById(R.id.llCurrentQuestion);
        tvCurrentQuestion = (TextView) screenQuestionCard.findViewById(R.id.tvCurrenQuestion);
        tvTotalQuestion = (TextView) screenQuestionCard.findViewById(R.id.tvTotalQuestion);
        tvQuestionCard = (TextView) screenQuestionCard.findViewById(R.id.tvQuestion);
        tvShowAnswerCorrect = (TextView) screenQuestionCard.findViewById(R.id.tvShowAnserCorrect);
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

        setOnclickInScreenQuestion();

        changeThemeQuestion();
        //getData();
    }

    @Override
    protected void onViewCreated() {

    }

    private void getListQuestion(){
        if (lesson != null) {
            id = lesson.getId();
            APIService apiService = ApiUtils.getAPIService();
            apiService.quizzById(DataStoreManager.getToken(), id, 1, 10).enqueue(new retrofit2.Callback<ResponeAnswer>() {
                @Override
                public void onResponse(Call<ResponeAnswer> call, Response<ResponeAnswer> response) {
                    arrQuestionAnswer = new ArrayList<>();
                    if (response.body().getData() != null) {
                        arrQuestionAnswer.addAll(response.body().getData());
                    }else {
                        Log.e(TAG, "onResponse: Data null" );
                    }
                    getData();
                }

                @Override
                public void onFailure(Call<ResponeAnswer> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage() );
                }
            });
        }
    }

    private void initScreenQuestionCard() {

    }

    private void changeThemeQuestion() {
        HandleQuestion.setColorTextByTheme(self, new TextView[]{lblQuestion,tvShowAnswerCorrect }, llCurrentQuestion);
    }

    private void setOnclickInScreenQuestion() {

        for (int i = 0; i < arrTvLongAnswer.size(); i++){
            arrTvLongAnswer.get(i).setOnClickListener(this);
            arrTvShortAnswer.get(i).setOnClickListener(this);
        }
//        tvShortAnswer1.setOnClickListener(this);
//        tvShortAnswer2.setOnClickListener(this);
//        tvShortAnswer3.setOnClickListener(this);
//        tvShortAnswer4.setOnClickListener(this);
//        tvShortAnswer5.setOnClickListener(this);
//        tvShortAnswer6.setOnClickListener(this);
//        tvShortAnswer7.setOnClickListener(this);
//        tvShortAnswer8.setOnClickListener(this);
//
//        tvLongAnswer1.setOnClickListener(this);
//        tvLongAnswer2.setOnClickListener(this);
//        tvLongAnswer3.setOnClickListener(this);
//        tvLongAnswer4.setOnClickListener(this);
//        tvLongAnswer5.setOnClickListener(this);
//        tvLongAnswer6.setOnClickListener(this);
//        tvLongAnswer7.setOnClickListener(this);
//        tvLongAnswer8.setOnClickListener(this);
        btnShowAnswer.setOnClickListener(this);
        btnAnswer.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
    }

    private void getData(){
        tvTotalQuestion.setText("/" + arrQuestionAnswer.size());
        if (arrQuestionAnswer.size()>0){
            showQuestion();
//            pausePlayerWhenItPlaying();
            Log.e(TAG, "getData" );
        } else showNotQuestion();
    }

    private void showQuestion() {
        if (i < arrQuestionAnswer.size()) {
            tvCurrentQuestion.setText((i + 1) + "");
            questionAnswer = arrQuestionAnswer.get(i);
            count = i;
            if (questionAnswer.getAnswerResponse().size() > 0) {
                showQuestionAnimation(i);
            } else {
                showQuestionInputAnimation(i);
            }
        }
    }

    private void showNotifiQuestion(){
        screenQuestionCard.setVisibility(View.GONE);
        tvNotifiQuestion.setVisibility(View.VISIBLE);
        tvNotQuestion.setVisibility(View.GONE);
    }

    private void showHaveQuestion(){
        screenQuestionCard.setVisibility(View.VISIBLE);
        tvNotifiQuestion.setVisibility(View.GONE);
        tvNotQuestion.setVisibility(View.GONE);
    }

    private void showNotQuestion(){
        screenQuestionCard.setVisibility(View.GONE);
        tvNotifiQuestion.setVisibility(View.GONE);
        tvNotQuestion.setVisibility(View.VISIBLE);
    }

    private void showQuestionInputAnimation(int pos) {
        Log.e(TAG, "showQuestionInputAnimation: check" );
        AppController.isShowQuestionInPagePlaying = true;
        isQuestionSuggest = false;
//        question = getString(R.string.question) +" <b><font color=\"#000000\">"+ " " + questionAnswer.getQuestion() + "</font></b>";
        tvQuestionCard.setText(questionAnswer.getQuestion());
        if (questionAnswer.getImage()!=null){
            if (!questionAnswer.getImage().isEmpty())
                Picasso.with(self).load( questionAnswer.getImage()).into(imgQuestionCard, this);
            else imgQuestionCard.setVisibility(View.GONE);
        }else imgQuestionCard.setVisibility(View.GONE);
        tvShowAnswerCorrect.setText(R.string.enter_your_answer);
        Log.e(TAG, " Question " + questionAnswer.getQuestion());
        screenInputAnswer.setVisibility(View.VISIBLE);
        tvShowAnswer.setVisibility(View.GONE);
        btnAnswer.setVisibility(View.VISIBLE);
        edtInputAnswer.setVisibility(View.VISIBLE);
        screenShortAnswer.setVisibility(View.GONE);
        screenLongAnswer.setVisibility(View.GONE);
        showHaveQuestion();
        Animation moveRightToLeft = AnimationUtils.loadAnimation(this, R.anim.moverighttoleft);
        screenQuestionCard.clearAnimation();
        screenQuestionCard.setAnimation(moveRightToLeft);
    }

    private void showQuestionAnimation(int pos) {
        AppController.isShowQuestionInPagePlaying = true;
        isQuestionSuggest = true;
//        question = getString(R.string.question) +" <b><font color=\"#000000\">"+ " " + questionAnswer.getQuestion() + "</font></b>";
        tvQuestionCard.setText(questionAnswer.getQuestion());
        if (questionAnswer.getImage()!=null){
            if (!questionAnswer.getImage().isEmpty())
                Picasso.with(self).load( questionAnswer.getImage()).into(imgQuestionCard, this);
            else imgQuestionCard.setVisibility(View.GONE);
        }else imgQuestionCard.setVisibility(View.GONE);
        tvShowAnswerCorrect.setText(R.string.choose_correct_answer);
        screenInputAnswer.setVisibility(View.GONE);
        btnAnswer.setVisibility(View.GONE);
        if (questionAnswer.getAnswer().length() > 30){
            Log.e(TAG, "showQuestionAnimation: true" );
            isLongAnswer = true;
            screenLongAnswer.setVisibility(View.VISIBLE);
            screenShortAnswer.setVisibility(View.GONE);
        }else {
            Log.e(TAG, "showQuestionAnimation: false" );
            Log.e(TAG, "showQuestionAnimation: " +questionAnswer.getAnswer());
            isLongAnswer = false;
            screenShortAnswer.setVisibility(View.VISIBLE);
            screenLongAnswer.setVisibility(View.GONE);
        }
        Log.e(TAG, " Question " + questionAnswer.getQuestion());
        showAnswerCard();
        showHaveQuestion();
        Animation moveRightToLeft = AnimationUtils.loadAnimation(this, R.anim.moverighttoleft);
        screenQuestionCard.clearAnimation();
        screenQuestionCard.setAnimation(moveRightToLeft);

    }

    private void showAnswerCard() {
        if (questionAnswer.getAnswerResponse()!=null){
            if (isLongAnswer){
                showLongAnswerSuggest();
            }else {
                showShortAnswerSuggest();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.video_menu, menu);

        if (FavoriteDatabase.getInstance(this).favoriteDao().isFavorite(lesson.getId()) == 1) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_fill_yellow));
        } else menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_border_white));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.btn_menu_favorite) {
            Lesson favoriteList = new Lesson();

            //get Data
            int id            = lesson.getId();
            String name       = lesson.getName();
            String image      = lesson.getImage();
            String overview   = lesson.getOverview();
            String conten     = lesson.getContent();
            String attachment = lesson.getAttachment();
            String url_link   = lesson.getLinkUrl();
            String type       = lesson.getType();
            String category   = lesson.getCategoryId();
            int isActive      = lesson.getIsActive();
            String code       = lesson.getCode();
            String subTitle   = lesson.getSubtitle();
            String auThor     = lesson.getAuthor();
            String sort       = lesson.getSortOrder();
            int isHot         = lesson.getIsHot();
            int top           = lesson.getIsTop();
            int rate          = lesson.getRate();
            int countRate     = lesson.getRateCount();
            int viewCount     = lesson.getViewCount();
            String level      = lesson.getLevel();
            String date       = lesson.getCreatedDate();
            String modifile   = lesson.getModifiedDate();
            String modifileUser = lesson.getModifiedUser();
            String crea_user    = lesson.getCreatedUser();
            String application  = lesson.getApplicationId();

            //set data to database
            favoriteList.setId(id);
            favoriteList.setName(name);
            favoriteList.setImage(image);
            favoriteList.setOverview(overview);
            favoriteList.setContent(conten);
            favoriteList.setAttachment(attachment);
            favoriteList.setLinkUrl(url_link);
            favoriteList.setType(type);
            favoriteList.setCategoryId(category);
            favoriteList.setIsActive(isActive);
            favoriteList.setCode(code);
            favoriteList.setSubtitle(subTitle);
            favoriteList.setAuthor(auThor);
            favoriteList.setSortOrder(sort);
            favoriteList.setIsHot(isHot);
            favoriteList.setIsTop(top);
            favoriteList.setRate(rate);
            favoriteList.setRateCount(countRate);
            favoriteList.setViewCount(viewCount);
            favoriteList.setLevel(level);
            favoriteList.setCreatedDate(date);
            favoriteList.setModifiedDate(modifile);
            favoriteList.setModifiedUser(modifileUser);
            favoriteList.setCreatedUser(crea_user);
            favoriteList.setApplicationId(application);

            if (FavoriteDatabase.getInstance(this).favoriteDao().isFavorite(id)!=1){
                item.setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_border_white));
                FavoriteDatabase.getInstance(this).favoriteDao().addData(favoriteList);

            }else {
                item.setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_fill_yellow));
                FavoriteDatabase.getInstance(this).favoriteDao().delete(favoriteList);

            }

        }
        return true;
    }

    private void showShortAnswerSuggest() {
        switch (questionAnswer.getAnswerResponse().size()) {
            case 2:
                showTextAnswerInView(arrTvShortAnswer, 2);
                break;

            case 3:
                showTextAnswerInView(arrTvShortAnswer, 3);
                break;

            case 4:
                showTextAnswerInView(arrTvShortAnswer, 4);
                break;

            case 5:
                showTextAnswerInView(arrTvShortAnswer, 5);
                break;

            case 6:
                showTextAnswerInView(arrTvShortAnswer, 6);
                break;

            case 7:
                showTextAnswerInView(arrTvShortAnswer, 7);
                break;

            case 8:
                showTextAnswerInView(arrTvShortAnswer, 8);
                break;
        }
    }


    private void showTextAnswerInView(ArrayList<TextView> arrTvAnswer, int numberOfSuggest) {

        for (int j = 0; j <arrTvAnswer.size(); j ++){
                //show text contain suggest
            if (j< numberOfSuggest){
                arrTvAnswer.get(0).setText(questionAnswer.getAnswerResponse().get(0).getA());
                arrTvAnswer.get(1).setText(questionAnswer.getAnswerResponse().get(1).getB());
                arrTvAnswer.get(2).setText(questionAnswer.getAnswerResponse().get(2).getC());
                arrTvAnswer.get(3).setText(questionAnswer.getAnswerResponse().get(3).getD());
//                arrTvAnswer.get(j).setText(questionAnswer.getAnswerResponse().size()+"");
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

    private void showLongAnswerSuggest() {
        switch (questionAnswer.getAnswerResponse().size()){
            case 2:
                showTextAnswerInView(arrTvLongAnswer , 2);
                break;

            case 3:
                showTextAnswerInView(arrTvLongAnswer , 3);
                break;

            case 4:
                showTextAnswerInView(arrTvLongAnswer , 4);
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

    @Override
    public void change() {
        Log.e(TAG, " change ");
        //get arr question when lesson change

        index = AppController.getInstance().getLessonIndex();
        lesson = AppController.getInstance().getLessonAt(index);
        //arrContent = lesson.getContent();
        if (arrQuestionAnswer!=null)
            arrQuestionAnswer.clear();
        else arrQuestionAnswer = new ArrayList<>();
        try {
            lesson = AppController.getInstance().getArrLessons().get(AppController.getInstance().getLessonIndex());
//            for (int i = 0; i < arrContent.size(); i++) {
//
//                if (arrContent.get(i).getQuestionAnswer()!=null){
//                    arrQuestionAnswer.add(arrContent.get(i).getQuestionAnswer());
//                }
//
//            }
            i= 0;
            answerCorrect =0;
            if (arrQuestionAnswer.size()>0){
                tvTotalQuestion.setText("/" + arrQuestionAnswer.size());
                showQuestion();
            } else showNotQuestion();

        } catch (NullPointerException e) {
            Log.e("LOI", e.getMessage());
        }
    }

    @Override
    public void onSuccess() {
        imgQuestionCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError() {
        imgQuestionCard.setVisibility(View.GONE);
    }

    @Override
    public void onLoadQuestion() {
        if (i < arrQuestionAnswer.size())
            showQuestion();
        else
            showResultAnswer();
    }

    private void showResultAnswer() {
        Intent intent = new Intent(self, QuestionAnswerSuccessActivity.class);
        intent.putExtra(URL_BACKGROUND, lesson.getImage());
        intent.putExtra(TITLE_TOOLBAR, lesson.getName());
        intent.putExtra(ANSWER_CORRECT, answerCorrect);
        intent.putExtra(ARR_QUESTION, arrQuestionAnswer);
        intent.putExtra(SIZE_QUESTION, arrQuestionAnswer.size());
        QuestionActivity.this.startActivityForResult(intent, REQUEST_CODE);

//        if (MusicService.getMediaPlayer().isPlaying()){
//            Intent it = new Intent(self, MusicService.class);
//            it.setAction(Constant.ACTION_PLAY);
//            self.startService(it);
//        }
    }

    //=-----------------------------------------------------------------------------=

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tvShortAnswer1:
                processingClickAnswer(tvShortAnswer1);
                Log.e(TAG, "onClick: tvShortAnswer1"  );
                break;

            case R.id.tvShortAnswer2:
                processingClickAnswer(tvShortAnswer2);
                Log.e(TAG, "onClick: tvShortAnswer2"  );
                break;

            case R.id.tvShortAnswer3:
                processingClickAnswer(tvShortAnswer3);
                Log.e(TAG, "onClick: tvShortAnswer3"  );
                break;

            case R.id.tvShortAnswer4:
                processingClickAnswer(tvShortAnswer4);
                Log.e(TAG, "onClick: tvShortAnswer4"  );
                break;

            case R.id.tvShortAnswer5:
                processingClickAnswer(tvShortAnswer5);
                Log.e(TAG, "onClick: tvShortAnswer5"  );
                break;

            case R.id.tvShortAnswer6:
                Log.e(TAG, "onClick: tvShortAnswer6"  );
                processingClickAnswer(tvShortAnswer6);
                break;

            case R.id.tvShortAnswer7:
                Log.e(TAG, "onClick: tvShortAnswer7"  );
                processingClickAnswer(tvShortAnswer7);
                break;

            case R.id.tvShortAnswer8:
                Log.e(TAG, "onClick: tvShortAnswer8"  );
                processingClickAnswer(tvShortAnswer8);
                break;

            case R.id.tvLongAnswer1:
                Log.e(TAG, "onClick: tvLongAnswer1"  );
                processingClickAnswer(tvLongAnswer1);
                break;

            case R.id.tvLongAnswer2:
                Log.e(TAG, "onClick: tvLongAnswer2"  );
                processingClickAnswer(tvLongAnswer2);
                break;

            case R.id.tvLongAnswer3:
                Log.e(TAG, "onClick: tvLongAnswer3"  );
                processingClickAnswer(tvLongAnswer3);
                break;

            case R.id.tvLongAnswer4:
                processingClickAnswer(tvLongAnswer4);
                break;

            case R.id.tvLongAnswer5:
                processingClickAnswer(tvLongAnswer5);
                break;

            case R.id.tvLongAnswer6:
                processingClickAnswer(tvLongAnswer6);
                break;

            case R.id.tvLongAnswer7:
                processingClickAnswer(tvLongAnswer7);
                break;

            case R.id.tvLongAnswer8:
                processingClickAnswer(tvLongAnswer8);
                break;

            case R.id.btnShowAnswer:
                Log.e(TAG, "onClick: showanswer" );
                AppUtil.hideSoftKeyboard(this);
                if (isQuestionSuggest){
                    processingShowAnswerIsQuestionSuggest();
                }else {
                    processingShowAnswer();
                }

                waitingTimeAfterShowAnswer();
                break;

            case R.id.btnAnswer:
                if (edtInputAnswer.getText().toString().isEmpty() || edtInputAnswer.getText().toString().length()==0 ){
                    AppUtil.showToast(self, getString(R.string.you_have_not_answered_the_question_yet));
                }else {
                    if (edtInputAnswer.getText().toString().equals(questionAnswer.getCorrectAnswer())) {
                        setTextColorAndBackGroundCorrectAnswer(edtInputAnswer);
                        answerCorrect ++;
                        //trả lời đúng mới được đi tiếp
                        AppUtil.hideSoftKeyboard(this);
                        arrQuestionAnswer.get(i).setCreatedUser(edtInputAnswer.getText().toString());
                        initLoadNextQuestion();

                    } else {
                        setTextColorAndBackGroundWrongAnswer(edtInputAnswer);
                        waitingTimeAfterWrongAnswer();
                    }


                }
                break;

            case R.id.btnNext:
                AppUtil.hideSoftKeyboard(this);
                initLoadNextQuestion();
                break;

        }

    }

    private void processingShowAnswer() {
        tvShowAnswer.setText(questionAnswer.getAnswer());
        setTextColorAndBackGroundCorrectAnswer(tvShowAnswer);
        edtInputAnswer.setVisibility(View.GONE);
        tvShowAnswer.setVisibility(View.VISIBLE);
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

    private void processingAnswerCorrect(TextView tv) {
//        if (tv.getText().equals(questionAnswer.getAnswer())){
//            setTextColorAndBackGroundCorrectAnswer(tv);
//        }

        if (questionAnswer.getAnswer().equalsIgnoreCase(tv.getText().toString())){

            setTextColorAndBackGroundCorrectAnswer(tv);

        }
    }

    private void processingClickAnswer(TextView tv) {
        if (questionAnswer.getAnswer().equalsIgnoreCase(tv.getText().toString())){
            answerCorrect ++;
            Log.e(TAG, "processingClickAnswer: " + tv.getText().toString() );
            setTextColorAndBackGroundCorrectAnswer(tv);
        }else {
            setTextColorAndBackGroundWrongAnswer(tv);
        }
        arrQuestionAnswer.get(i).setCreatedUser(tv.getText().toString());
        AppUtil.hideSoftKeyboard(this);
        initLoadNextQuestion();

    }


    private void setTextColorAndBackGroundCorrectAnswer(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.whitetext));
        tv.setBackgroundResource(R.drawable.bg_txt_answer_correct_radius);
    }

    private void setTextColorAndBackGroundCorrectAnswer(EditText tv) {
        tv.setTextColor(getResources().getColor(R.color.whitetext));
        tv.setBackgroundResource(R.drawable.bg_txt_answer_correct_radius);
    }

    private void setTextColorAndBackGroundBeforeAnswer(EditText tv) {
        tv.setText("");
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setBackgroundResource(R.drawable.bg_btn_question_green_radius_pale);
    }

    private void setTextColorAndBackGroundWrongAnswer(EditText tv) {
        tv.setTextColor(getResources().getColor(R.color.whitetext));
        tv.setBackgroundResource(R.drawable.bg_txt_answer_wrong_radius);
    }

    private void setTextColorAndBackGroundWrongAnswer(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.whitetext));
        tv.setBackgroundResource(R.drawable.bg_txt_answer_wrong_radius);
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

    private void setTextColorAndBackGroundBeforeAnswer(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setBackgroundResource(R.drawable.bg_txt_question_troke_radius);
    }

    private void initLoadNextQuestion() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i < count+1)
                    i++;
                iLoadQuestion.onLoadQuestion();
                setAllTextBeforeAnswer();
            }
        },1000);

    }

    private void waitingTimeAfterShowAnswer() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i < count+1)
                    i++;
                iLoadQuestion.onLoadQuestion();
                setAllTextBeforeAnswer();
            }
        },3000);

    }

    private void waitingTimeAfterWrongAnswer() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setTextColorAndBackGroundBeforeAnswer(edtInputAnswer);
            }
        },1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == QuestionActivity.REQUEST_CODE) {
            if (resultCode == QuestionActivity.RESULT_CODE_REPLY_AGAIN) {
                Log.e("MyDetailAvtivity", "on REPLY_AGAIN");
                reloadQuestion();
            }

            if (resultCode == QuestionActivity.RESULT_CODE_BACK_TO_BOARD) {
                Log.e(TAG, "on RESULT_CODE_BACK_TO_BOARD");
                setResult(QuestionActivity.RESULT_CODE_BACK_TO_BOARD);
                finish();
            }

            if (resultCode == QuestionActivity.RESULT_CODE_NEXT_LESSON) {
                Log.e(TAG, "on RESULT_CODE_NEXT_LESSON");
                //((DetailActivity) self).setTabsetlectQuestionFragment();
                onNextLesson();
            }
        }
    }

    public void reloadQuestion(){
        i=0;
        answerCorrect = 0;
        onLoadQuestion();
    }

    public void onNextLesson(){
        change();
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume" );
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.LISTEN_CHANGE_THEME);
        this.registerReceiver(settingReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(settingReceiver);
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
