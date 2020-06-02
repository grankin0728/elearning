package com.suusoft.elistening.base.vm;

import androidx.databinding.ObservableInt;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.suusoft.elistening.base.view.BaseActivityBinding;
import com.suusoft.elistening.base.view.BaseFragmentBinding;
import com.suusoft.elistening.base.view.BaseListActivityBinding;
import com.suusoft.elistening.base.view.BaseListFragmentBinding;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.listener.IDataChangedListener;
import com.suusoft.elistening.widgets.recyclerview.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suusoft on 7/11/2016.
 */
public abstract class BaseViewModelList extends BaseViewModel implements EndlessRecyclerOnScrollListener.OnLoadMoreListener {

    public String backgroundMain;
    /**
     * show hide textview  No Data
     *
     */
    public ObservableInt isDataAvailable;

    /**
     * list data
     *
     */
    protected List<?> mDatas;

    /**
     * listener when data is changed
     *
     */
    private IDataChangedListener dataListener;

    protected EndlessRecyclerOnScrollListener mOnScrollListener;
    public abstract RecyclerView.LayoutManager getLayoutManager();
    public abstract void getData(int page);

    /**
     * Constructor with fragment
     * @param binding BaseListFragmentBinding
     *
     */
    public BaseViewModelList(BaseListFragmentBinding binding) {
        super(binding.getContext());
        initialize();
    }

    public BaseViewModelList(BaseFragmentBinding binding) {
        super(binding.getContext());
        initialize();
    }

    public BaseViewModelList(BaseActivityBinding binding) {
        super(binding);
        initialize();
    }

    /**
     * Constructor with activity
     * @param binding BaseListActivityBinding
     *
     */
    public BaseViewModelList(BaseListActivityBinding binding) {
        super(binding);
        initialize();
    }

    /**
     * initialize all component
     *
     */
    private void initialize(){
        mOnScrollListener = new EndlessRecyclerOnScrollListener(this,getLayoutManager());
        mDatas = new ArrayList<>();
        isDataAvailable = new ObservableInt();
        isDataAvailable.set(View.GONE);
    }

    public EndlessRecyclerOnScrollListener getOnScrollListener() {
        return mOnScrollListener;
    }

    @Override
    public void onLoadMore(int page) {
        getData(page);
    }

    @Override
    public void destroy() {
        dataListener = null;
        self = null;
    }

    /**
     * Notify that the view is needed to update
     *
     * */
    protected void notifyDataChanged(){
        if (dataListener != null){
            dataListener.onListDataChanged(mDatas);
        }
    }


    public List<?> getListData() {
        if (mDatas == null)
            mDatas = new ArrayList<>();
        return mDatas;
    }

    /**
     * add to list data and notify that need update
     * @param data list data
     *
     */
    public void addListData(List<?> data) {
        this.mDatas = data;
        notifyDataChanged();
        if (data!=null)
            if (data.size() > 0)
                isDataAvailable.set(View.GONE);
            else isDataAvailable.set(View.VISIBLE);

        Log.e("ListLessonVM addListData", " isDataAvailable"  + isDataAvailable.get());
    }

    /**
     *  Check loading more. Using this when call recyclerView.addOnScrollListener
     *  @param totalPage get in json
     *
     */
    protected void checkLoadingMoreComplete(int totalPage){
        // set status_hot loadmore
        mOnScrollListener.setEnded(mOnScrollListener.getCurrent_page() >= totalPage);
        mOnScrollListener.onLoadMoreComplete();
    }

    public IDataChangedListener getDataListener() {
        return dataListener;
    }

    public void setDataListener(IDataChangedListener listener) {
        this.dataListener = listener;
    }

    public String getBackgroundMain() {
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())) {
            return GlobalValue.getTheme().getBackgroundMainDark();
        } else {
            return GlobalValue.getTheme().getBackgroundMainLight();
        }
    }
}
