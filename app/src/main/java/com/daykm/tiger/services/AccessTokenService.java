package com.daykm.tiger.services;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccessTokenService {

	@POST("oauth/access_token") Call<ResponseBody> getAccessToken(@Body RequestBody verifier);
}
