package com.my.shishir.demoapp;

import android.support.annotation.NonNull;

import com.my.shishir.demoapp.api.RequestManager;
import com.my.shishir.demoapp.model.MainData;
import com.my.shishir.demoapp.utility.Utility;

public class MainPresenterImpl implements NewsListContract.MainPresenter {

    private final NewsListContract.MainView mainView;
    private final RequestManager requestManager;


    MainPresenterImpl(NewsListContract.MainView mainView) {
        this.mainView = mainView;
        RequestManager.ResponseListener<MainData> responseListener = this;
        requestManager = new RequestManager(responseListener);
    }

    @Override
    public void startProcess() {
        requestManager.callApi();
    }

    @Override
    public void initiate() {
        mainView.showProgress();
        startProcess();
    }

    @Override
    public void onRefresh() {
        startProcess();
    }

    @Override
    public void onClick(String url) {
        mainView.launchWeb(url);
    }

    @Override
    public void onResponse(@NonNull MainData mainData, boolean success, int errorType) {
        mainView.setAdapter(mainData);
        if (mainData.getResults() != null && mainData.getResults().size() > 0) {
            mainView.setTitle(mainData.getResults().get(0).getSource());
        }
        mainView.hideProgress();

        if (!success) {
            mainView.showMessage(errorType == Utility.OTHER_ERROR_CODE
                    ? R.string.download_failed
                    : R.string.no_internet);
        }
    }
}
