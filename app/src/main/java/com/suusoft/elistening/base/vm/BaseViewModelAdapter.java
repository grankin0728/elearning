package com.suusoft.elistening.base.vm;

import android.content.Context;
import androidx.databinding.BaseObservable;

/**
 * Created by phamv on 7/18/2016.
 */
public abstract class BaseViewModelAdapter extends BaseObservable {

    public Context self;

    public abstract void setData(Object object);

    public BaseViewModelAdapter(Context self) {
        this.self = self;
    }
}
