package com.suusoft.elistening.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.suusoft.elistening.R;
import com.suusoft.elistening.datastore.DataStoreManager;


/**
 * Created by Suusoft on 01/03/2018.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        // Check permissions
//        if (GlobalFunctions.isGranted(this, new String[]{
//                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_PERMISSIONS, ""))
//        {
//            onContinue();
//        }
        onContinue();

    }

    private void onContinue() {
//        if (AppControler.songList ==null || !(AppControler.songList.size()>0))
//            findMp3();

        //dung dc
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, SplashLoadActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 2000);

        if (DataStoreManager.getUser () != null) {
            Intent intent = new Intent (SplashActivity.this, MainActivity.class);
            startActivity (intent);
            finish ();
        } else {
            Intent intent = new Intent (SplashActivity.this, StartScreenActivity.class);
            startActivity (intent);
            finish ();
        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case RC_PERMISSIONS: {
//                if (grantResults.length > 0) {
//                    if (grantResults[0] == PackageManager.PERMISSION_DENIED
//                            || grantResults[1] == PackageManager.PERMISSION_DENIED) {
//                        showPermissionsReminder(RC_PERMISSIONS, true);
//                    } else {
//                        //permission đã được cấp phép
//                        onContinue();
//                    }
//                }
//                break;
//            }
//            default:
//                break;
//        }
//    }
//
//    protected void showPermissionsReminder(final int reqCode, final boolean flag) {
//        GlobalFunctions.showConfirmationDialog(this, getString(R.string.msg_remind_user_grants_permissions),
//                getString(R.string.allow), getString(R.string.no_thank), false, new IConfirmation() {
//                    @Override
//                    public void onPositive() {
//                        GlobalFunctions.isGranted(SplashActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, reqCode, null);
//                    }
//
//                    @Override
//                    public void onNegative() {
//                        if (flag) {
//                            finish();
//                        }
//                    }
//                });
//    }
}
