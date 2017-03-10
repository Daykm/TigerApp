package com.daykm.tiger.dagger;

import com.daykm.tiger.BuildConfig;
import com.daykm.tiger.services.TwitterApp;
import com.daykm.tiger.realm.GsonProvider;
import com.daykm.tiger.services.AccessTokenService;
import com.daykm.tiger.services.AuthenticationService;
import com.daykm.tiger.services.OAuth2Interceptor;
import com.daykm.tiger.services.TimelineService;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ServiceModule {

    private OkHttpClient httpBuilder = new OkHttpClient.Builder().build();

    @Provides
    @Singleton
    TimelineService timelineService(OAuth2Interceptor interceptor, Gson gson) {
        interceptor.isAuth = false;
        return buildService(TimelineService.class, interceptor, gson, new Retrofit.Builder(), httpBuilder.newBuilder(), TwitterApp.BASE_URL_1_1);
    }

    @Provides
    @Singleton
    AccessTokenService accessTokenService(OAuth2Interceptor interceptor, Gson gson) {
        interceptor.isAuth = false;
        return buildService(AccessTokenService.class, interceptor, gson, new Retrofit.Builder(), httpBuilder.newBuilder(), TwitterApp.BASE_URL);
    }

    @Provides
    @Singleton
    AuthenticationService authenticationService(OAuth2Interceptor interceptor, Gson gson) {
        interceptor.isAuth = true;
        return buildService(AuthenticationService.class, interceptor, gson, new Retrofit.Builder(), httpBuilder.newBuilder(), TwitterApp.BASE_URL);
    }


    @Provides
    @Singleton
    Gson getGson() {
        Gson gson = GsonProvider.getGson();
        return gson;
    }

    @Provides
    OAuth2Interceptor getInterceptor() {
        OAuth2Interceptor interceptor = new OAuth2Interceptor();
        return interceptor;
    }

    public static <S> S buildService(
            Class<S> serviceClass, OAuth2Interceptor oauthInterceptor, Gson gson,
            Retrofit.Builder retroBuilder, OkHttpClient.Builder httpBuilder, String baseUrl) {
        httpBuilder.interceptors().add(oauthInterceptor);
        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpBuilder.addInterceptor(interceptor);
        }
        OkHttpClient client = httpBuilder.build();
        Retrofit retrofit = retroBuilder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        return retrofit.create(serviceClass);
    }

}