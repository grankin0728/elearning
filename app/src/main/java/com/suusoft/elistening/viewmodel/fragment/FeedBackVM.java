package com.suusoft.elistening.viewmodel.fragment;

import android.content.Context;
import android.view.View;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.AbstractActivity;
import com.suusoft.elistening.base.vm.BaseViewModel;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.modelmanager.RequestManager;
import com.suusoft.elistening.network.ApiResponse;
import com.suusoft.elistening.network.BaseRequest;
import com.suusoft.elistening.util.StringUtil;

/**
 * Created by Ha on 9/12/2016.
 */
public class FeedBackVM extends BaseViewModel {

    private String name;
    private String email;
    private String subject;
    private String content;

    public String getBackgroundMain() {
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())) {
            return GlobalValue.getTheme().getBackgroundMainDark();
        } else {
            return GlobalValue.getTheme().getBackgroundMainLight();
        }
    }

    public String getBackgroundRadius() {
        return GlobalValue.getTheme().getColorAccent();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void getData(int page) {

    }

    public FeedBackVM(Context self) {
        super(self);
    }

    public void onClickSendFeedBack(View view) {
        if (StringUtil.isEmpty(name)) {
            ((AbstractActivity) self).showSnackBar(R.string.message_name_empty);
            return;
        }
        if (StringUtil.isEmpty(email)) {
            ((AbstractActivity) self).showSnackBar(R.string.message_email_empty);
            return;
        }
        if (!StringUtil.isValidEmail(email)) {
            ((AbstractActivity) self).showSnackBar(R.string.message_email_wrong_format);
            return;
        }
        if (StringUtil.isEmpty(subject)) {
            ((AbstractActivity) self).showSnackBar(R.string.message_subject_empty);
            return;
        }
        if (StringUtil.isEmpty(content)) {
            ((AbstractActivity) self).showSnackBar(R.string.message_content_empty);
            return;
        }
        ((AbstractActivity) self).showProgress(true);
        RequestManager.getSendFeedBack(self, name, email, subject, content, new BaseRequest.CompleteListener() {
            @Override
            public void onSuccess(ApiResponse response) {
                ((AbstractActivity) self).showSnackBar(R.string.successfully);
                name = "";
                email = "";
                subject = "";
                content = "";
                ((AbstractActivity) self).showProgress(false);
                notifyChange();
            }

            @Override
            public void onError(String message) {
                ((AbstractActivity) self).showProgress(false);
                ((AbstractActivity) self).showSnackBar(message.toString());
            }
        });
    }

}
