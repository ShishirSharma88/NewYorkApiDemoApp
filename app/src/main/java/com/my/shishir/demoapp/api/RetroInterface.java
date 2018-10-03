package com.my.shishir.demoapp.api;

import com.my.shishir.demoapp.model.MainData;

import retrofit2.http.GET;
import rx.Observable;

interface RetroInterface {

    @GET("svc/mostpopular/v2/mostviewed/all-sections/7.json?api-key=aca67b458faa43b69a58d238f116ae81")
    Observable<MainData> getNewsData();
}
