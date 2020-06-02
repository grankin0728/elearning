package com.suusoft.elistening.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;

import com.suusoft.elistening.R;

/**
 * Created by Suusoft on 6/29/2016.
 */
public class DialogUtil {

    public interface IDialogConfirm {
        void onClickOk();
    }

    public static void showAlertDialog(Context context, int title ,int message , final IDialogConfirm iDialogConfirm){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                iDialogConfirm.onClickOk();
            }
        });
        builder.setPositiveButton(context.getString(R.string.cancel),new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public static Dialog showDialogCustomLayout(Context context, int layout, int background, boolean isCancelable){
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(isCancelable);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(background);
        dialog.show();
        return dialog;
    }

}
