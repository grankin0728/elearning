package com.suusoft.elistening.view.activity;

import android.content.Intent;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseActivity;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.model.modelLesson.Question;
import com.suusoft.elistening.model.modelLesson.QuestionAnswer;
import com.suusoft.elistening.service.MusicService;
import com.suusoft.elistening.util.AppUtil;

import java.util.ArrayList;

/**
 * Created by Suusoft on 09/11/2017.
 */

public class QuestionAnswerSuccessActivity extends BaseActivity implements View.OnClickListener {

    private String urlBackground, title;
    private int answerCorrect , sizeQuestion;
    private ArrayList<Question> questionAnswers;
    private Toolbar mToolbar;
    private ImageView imgBackground;
    private TextView tvCongratulation, tvComplete;
    private TextView btnNextLesson, btnBackToBoard, btnReplyAgain;

    @Override
    protected ToolbarType getToolbarType() {
        return ToolbarType.NONE;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.activity_question_answer_success;
    }

    @Override
    protected void getExtraData(Intent intent) {

        //Question
        urlBackground = intent.getStringExtra(QuestionActivity.URL_BACKGROUND);
        title = intent.getStringExtra(QuestionActivity.TITLE_TOOLBAR);
        answerCorrect = intent.getIntExtra(QuestionActivity.ANSWER_CORRECT, 0);
        sizeQuestion = intent.getIntExtra(QuestionActivity.SIZE_QUESTION, 0);
        questionAnswers = intent.getParcelableArrayListExtra(QuestionActivity.ARR_QUESTION);
    }

    @Override
    protected void initilize() {

    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar_success);
        imgBackground = (ImageView)findViewById(R.id.img_background);
        tvComplete = (TextView) findViewById(R.id.tvComplete);
        tvCongratulation = (TextView) findViewById(R.id.tvCongratulation);
        btnBackToBoard = (TextView) findViewById(R.id.btnBackToBoard);
        btnNextLesson = (TextView) findViewById(R.id.btnNextLesson);
        btnReplyAgain = (TextView) findViewById(R.id.btnReplyAgain);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.transparent));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        Glide.with(self).load(urlBackground).into(imgBackground);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Quetionhere
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(QuestionActivity.RESULT_CODE_REPLY_AGAIN);
                finish();
            }
        });
        btnNextLesson.setOnClickListener(this);
        btnBackToBoard.setOnClickListener(this);
        btnReplyAgain.setOnClickListener(this);

    }

    @Override
    protected void onViewCreated() {
        processingScoring();
    }

    private void processingScoring() {
        if (questionAnswers.size()!=0){
            float percent = answerCorrect *100 / questionAnswers.size();
            if (80 <= percent){
                tvCongratulation.setText(getResources().getString(R.string.excellent));
                tvCongratulation.setTextColor(getResources().getColor(R.color.green));
            }

            if (50 <= percent&& percent < 80){
                tvCongratulation.setText(getResources().getString(R.string.completed));
                tvCongratulation.setTextColor(getResources().getColor(R.color.green));
            }

            if (30 <= percent && percent < 50){
                tvCongratulation.setText(getResources().getString(R.string.reached));
                tvCongratulation.setTextColor(getResources().getColor(R.color.yellow));
            }

            if (percent < 30){
                tvCongratulation.setText(getResources().getString(R.string.not_achieved));
                tvCongratulation.setTextColor(getResources().getColor(R.color.red));
            }

            tvComplete.setText(getResources().getString(R.string.you_answered_correctly) + " "
                    + answerCorrect + "/" + questionAnswers.size() + getResources().getString(R.string.reaching) + " " + percent+ "%");
        }else {
            tvCongratulation.setText(getResources().getString(R.string.you_no_achievements_in_this_lesson));
            tvComplete.setText("");
        }

    }

    @Override
    public void onBackPressed() {
        setResult(QuestionActivity.RESULT_CODE_REPLY_AGAIN);
        super.onBackPressed();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNextLesson:
//                setResult(QuestionActivity.RESULT_CODE_NEXT_LESSON);
//                MusicService.intentStart(self,Constant.ACTION_NEXT );
//                finish();
                AppUtil.showToast(this, "No Lesson");
                break;

            case R.id.btnReplyAgain:
                setResult(QuestionActivity.RESULT_CODE_REPLY_AGAIN);
                finish();
                break;

            case R.id.btnBackToBoard:
                setResult(QuestionActivity.RESULT_CODE_BACK_TO_BOARD);
                finish();
                break;
        }
    }
}
