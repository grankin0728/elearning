package com.suusoft.elistening.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.suusoft.elistening.R;

public class MyProgressDialog extends Dialog {
    private TextView mLblMsg;

    public MyProgressDialog(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(
                R.drawable.bg_dialog_progress);
        setContentView(R.layout.layout_progress_dialog);
        mLblMsg = findViewById(R.id.lbl_progress_msg);

    }

    public void setMessage(String msg) {
        if (msg != null && !msg.equals("")) {
            mLblMsg.setVisibility(View.VISIBLE);
            mLblMsg.setText(msg);
        } else {
            mLblMsg.setVisibility(View.GONE);
        }
    }
}
