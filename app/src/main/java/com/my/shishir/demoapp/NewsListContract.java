package com.my.shishir.demoapp;

import com.my.shishir.demoapp.api.RequestManager;
import com.my.shishir.demoapp.model.MainData;

interface NewsListContract {
     interface MainView {
        void showProgress();
        void hideProgress();
        void setAdapter(MainData mainData);
        void showMessage(int message);
        void setTitle(String title);
        void launchWeb(String url);
    }

     interface MainPresenter extends RequestManager.ResponseListener<MainData> {
        void startProcess();
        void initiate();
        void onRefresh();
        void onClick(String url);
     }
}
