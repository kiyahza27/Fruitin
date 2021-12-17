package com.zakiyahhamidah.fruitinapp.RESTAPI;

import com.zakiyahhamidah.fruitinapp.Model.DataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("api/fruit/all")
    Call<List<DataResponse>> getAllData();
}
