package com.suusoft.elistening.view.fragment;

import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseFragmentBinding;
import com.suusoft.elistening.base.vm.BaseViewModel;
import com.suusoft.elistening.databinding.FragmentSettingsBinding;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.network.NetworkUtility;
import com.suusoft.elistening.util.AppUtil;
import com.suusoft.elistening.util.DialogUtil;
import com.suusoft.elistening.view.activity.LoginActivity;
import com.suusoft.elistening.view.activity.MainActivity;
import com.suusoft.elistening.viewmodel.fragment.SettingsVM;
import com.suusoft.elistening.widgets.dialog.MyProgressDialog;

/**
 * Created by Ha on 9/8/2016.
 */
public class FragmentSettings extends BaseFragmentBinding {
    private SettingsVM viewModel;
    private FragmentSettingsBinding binding;
    private TextView tvName, tvEmail, btnLogout;

    public static FragmentSettings newInstance() {
        Bundle args = new Bundle();
        FragmentSettings fragment = new FragmentSettings();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected BaseViewModel getViewModel() {
        viewModel = new SettingsVM(self);
        return viewModel;
    }

    @Override
    protected void setViewDataBinding(ViewDataBinding binding) {
        this.binding = (FragmentSettingsBinding) binding;
        this.binding.setViewModel(viewModel);
    }


    @Override
    protected void initView(View view) {
        tvName = view.findViewById(R.id.tv_username);
        tvEmail  = view.findViewById(R.id.tv_email);
        btnLogout = view.findViewById(R.id.logout);

        if (DataStoreManager.getUser() != null) {
            tvName.setText(DataStoreManager.getUser().getName());
            tvEmail.setText(DataStoreManager.getUser().getUsername());
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) self).setToolbarTitle(R.string.settings);
        ((MainActivity) self).showIconToolbar();
    }

    private void logout(){
        showDialogLogout();
    }

    private void showDialogLogout() {
        DialogUtil.showAlertDialog(self, R.string.msg_out, R.string.msg_logout, new DialogUtil.IDialogConfirm() {
            @Override
            public void onClickOk() {
                requestLogout();
            }
        }) ;

    }

    private void requestLogout() {
        if (NetworkUtility.isNetworkAvailable()) {
            final MyProgressDialog progressDialog = new MyProgressDialog(self);
            progressDialog.show();
            processBeforeLoggingOut(progressDialog);
        }else {
            AppUtil.showToast(self, getString(R.string.msg_no_network));
        }
    }

    private void processBeforeLoggingOut(MyProgressDialog progressDialog){
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        DataStoreManager.removeUser();
        Bundle bundle = new Bundle();
        bundle.putString("LOGOUT", "log out");
        tvName.setText(null);
        //imgAvatar.setImageDrawable(null);
        tvEmail.setText(null);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finishAffinity();
    }

}
