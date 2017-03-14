package com.daykm.tiger.features.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;

public interface AuthenticationService {

  @POST("oauth/request_token") Call<ResponseBody> getRequestToken();
}
