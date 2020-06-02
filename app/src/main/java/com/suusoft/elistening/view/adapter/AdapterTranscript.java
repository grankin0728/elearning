package com.suusoft.elistening.view.adapter;

import android.app.Activity;
import com.suusoft.elistening.R;
import android.app.Dialog;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.listener.IListenerSwitchDisplayType;
import com.suusoft.elistening.service.MusicService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ASUS on 3/28/2017.
 */

public class AdapterTranscript extends RecyclerView.Adapter<AdapterTranscript.MyViewHolder> implements TextToSpeech.OnInitListener {
    private final static String TAG  ="LOG";

    public void change() {
        notifyDataSetChanged();
        setSelectionPos(0);
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, "Languge not support", Toast.LENGTH_SHORT).show();
//                Intent installIntent = new Intent();
//                installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//                installIntent.setPackage("com.google.android.tts");
//                try {
//                    Log.v(TAG, "Installing voice data: " + installIntent.toUri(0));
//                    context.startActivity(installIntent);
//                } catch (ActivityNotFoundException ex) {
//                    Log.e(TAG, "Failed to install TTS data, no acitivty found for " + installIntent + ")");
//                }

            } else {
                speak();
            }
        }
    }

    public interface IClick {
        void click(int pos);
    }

    public interface IClickSeekbar {
        void onClickSeekbar(int currenProgress);
    }

    private TextToSpeech textToSpeech;
    private String text = "";

    private Activity context;
    private ArrayList<String> arr;
    private LayoutInflater inflater;
    private IClick iClick;
    private IListenerSwitchDisplayType iListenerSwitchDisplayType;

    public void setSelectionPos(int selectionPos) {
        this.selectionPos = selectionPos;
        notifyDataSetChanged();
    }

    private int selectionPos = 0;

    public AdapterTranscript(Activity context, ArrayList<String> arr, IClick iClick, IListenerSwitchDisplayType iListenerSwitchDisplayType) {
        this.context = context;
        this.arr = arr;
        this.iClick = iClick;
        this.iListenerSwitchDisplayType = iListenerSwitchDisplayType;
        inflater = LayoutInflater.from(context);
        textToSpeech = new TextToSpeech(context.getApplicationContext(), this);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recycle_transcript, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (arr.get(position)!=null){
            String text = arr.get(position);
                holder.txtSub.setText(Html.fromHtml(text));
                holder.txtSub.setMovementMethod(LinkMovementMethod.getInstance());
                holder.txtSub.setHighlightColor(Color.TRANSPARENT);

//                Spannable spannable = (Spannable) holder.txtSub.getText();
//                BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
//                iterator.setText(text);
//                int start = iterator.first();
//                for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
//                    String subText = text.substring(start, end);
//                    if (Character.isLetterOrDigit(subText.charAt(0))) {
//                        ClickableSpan clickableSpan = getClickAbleSpan(subText);
//                        spannable.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    }
//                }

            if (position == selectionPos){

                if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())){
                    holder.txtSub.setTextColor(Color.parseColor(GlobalValue.getTheme().getTextPlayHightLight_dark()));
                }else {
                    holder.txtSub.setTextColor(Color.parseColor(GlobalValue.getTheme().getTextPlayHightLight_light()));
                }

            } else {

                if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())){
                    holder.txtSub.setTextColor(Color.parseColor(GlobalValue.getTheme().getTextColorSecondaryDark()));
                }else {
                    holder.txtSub.setTextColor(Color.parseColor(GlobalValue.getTheme().getTextColorSecondaryLight()));
                }
            }
            holder.btnSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MusicService.playState != null &&
                            (MusicService.playState == MusicService.PlayState.Playing
                                    || MusicService.playState == MusicService.PlayState.Pause)) {
                        iClick.click(position);
                        setSelectionPos(position);
                    }
                }
            });
        }

    }

    private ClickableSpan getClickAbleSpan(final String subText) {
        return new ClickableSpan() {
            @Override
            public void onClick(View view) {
                translate(subText);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        };
    }

    private void translate(final String subText) {

        String[] language = context.getResources().getStringArray(R.array.arr_language_support);
        final String[] lang = context.getResources().getStringArray(R.array.arr_lang);
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dic_dialog);
        final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, language);
        spinner.setAdapter(adapter);
        spinner.setSelection(DataStoreManager.getLanguages());
        final EditText editText = (EditText) dialog.findViewById(R.id.edt_word);
        editText.setText(subText);
        final TextView txtMean = (TextView) dialog.findViewById(R.id.txt_translate);
        ImageButton btnSpeak = (ImageButton) dialog.findViewById(R.id.btn_speak);
        ImageButton btnSend = (ImageButton) dialog.findViewById(R.id.btn_send);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DataStoreManager.saveLanguages(i);
                translateText(subText, txtMean, lang[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translateText(editText.getText().toString().trim(), txtMean, lang[spinner.getSelectedItemPosition()]);
            }
        });
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = editText.getText().toString().trim();
                speak();
            }
        });

        dialog.show();
        translateText(subText, txtMean, lang[spinner.getSelectedItemPosition()]);
    }

    public void destroyTTS() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    private void translateText(String subText, final TextView txtMean, String lang) {
        String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="
                + context.getResources().getString(R.string.translate_key)
                + "&text=" + subText + "&lang=";
        JsonObjectRequest request = new JsonObjectRequest(url + lang, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("text");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String text = "";
                        text += jsonArray.get(i).toString() + "\n";
                        txtMean.setText(text);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Ve", error + "");
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private void speak() {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtSub;
        private ImageButton btnSub;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtSub = (TextView) itemView.findViewById(R.id.txt_sub);
            btnSub = (ImageButton) itemView.findViewById(R.id.btn_select_sub);
        }
    }

}
