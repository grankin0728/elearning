package com.suusoft.elistening.handle;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.model.modelLesson.QuestionAnswer;

import java.util.ArrayList;

/**
 * Created by Suusoft on 10/19/2017.
 */

public class HandleQuestion {

    private final static String color_coal_purple = "#0f2132";
    private final static String color_indigo = "#5C6BC0";
    private final static String color_deep_green = "#66BB6A";
    private final static String color_deep_grey = "#282828";
    private final static String color_deep_orange = "#FF7043";
    private final static String color_deep_purple = "#734eb7";

    public static void showSuggestInView( boolean isLongAnswer ,ArrayList<TextView> arrTvAnswer, int numberOfSuggest, ArrayList<View> arrView, ArrayList<LinearLayout> arrScreenShortAnswer , QuestionAnswer questionAnswer ){
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

    public static void setColorTextByTheme(TextView[] textViews){

            for (int i =0; i < textViews.length; i ++){
                textViews[i].setTextColor(Color.parseColor(GlobalValue.getTheme().getBackgroundMainLight()));
            }
    }

    public static void setColorTextByTheme(Context context, TextView[] textViews, View bgCount){

        for (int i =0; i < textViews.length; i ++){
            if (textViews[i]!=null)
            textViews[i].setTextColor(Color.parseColor(GlobalValue.getTheme().getBackgroundMainDark()));
        }

        if (bgCount!=null)
            switch (GlobalValue.getTheme().getBackgroundMainDark()){
                case color_coal_purple: bgCount.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_radius_coalpurple));
                break;
                case color_indigo: bgCount.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_radius_indigo));
                break;
                case color_deep_green: bgCount.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_radius_deepgreen));
                break;
                case color_deep_grey: bgCount.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_radius_deepgrey));
                break;
                case color_deep_orange: bgCount.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_radius_deeporange));
                break;
                case color_deep_purple: bgCount.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_radius_deeppurple));
                break;
            }

    }

}
