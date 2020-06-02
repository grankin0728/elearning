package com.suusoft.elistening.configs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


import com.suusoft.elistening.R;
import com.suusoft.elistening.listener.IConfirmation;
import com.suusoft.elistening.listener.IOnMenuItemClick;

/**
 * Created by suusoft.com on 11/20/17.
 */

public class Global {


    public static void showPopupMenu(Context context, View view, int layout, final IOnMenuItemClick listener){
        android.widget.PopupMenu popup = new android.widget.PopupMenu(context, view);
        popup.getMenuInflater()
                .inflate(layout, popup.getMenu());
        popup.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                listener.onMenuItemClick(item);
                return true;
            }
        });
        popup.show(); //showing popup menu
    }


    public static void showConfirmationDialog(Context context, String msg, String positive, String negative,
                                              boolean isCancelable, final IConfirmation iConfirmation) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_confirm);

        TextView lblMsg = dialog.findViewById(R.id.lbl_msg);
        TextView lblNegative = dialog.findViewById(R.id.lbl_negative);
        TextView lblPositive = dialog.findViewById(R.id.lbl_positive);

        lblMsg.setText(msg);
        lblNegative.setText(negative);
        lblPositive.setText(positive);

        lblNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                iConfirmation.onNegative();
            }
        });

        lblPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                iConfirmation.onPositive();
            }
        });

        dialog.setCancelable(isCancelable);

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public static boolean isGranted(Activity activity, String[] permissions, int reqCode, String notification) {
        boolean granted = true;

        if (isMarshmallow()) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];

                granted = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
                if (!granted) {
                    if (notification != null && notification.length() > 0) {
                        Toast.makeText(activity, notification, Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }

            // Ask permissions
            if (!granted) {
                ActivityCompat.requestPermissions(activity, permissions, reqCode);
            }
        }

        return granted;
    }

    public static boolean isMarshmallow() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M;
    }

    public static void startActivityWithoutAnimation(Context act, Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(act, clz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtras(bundle);
        act.startActivity(intent);
    }
}
