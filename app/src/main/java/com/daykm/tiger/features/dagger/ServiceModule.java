package com.daykm.tiger.features.dagger;

import com.daykm.tiger.BuildConfig;
import com.daykm.tiger.features.data.realm.GsonProvider;
import com.daykm.tiger.features.services.AccessTokenService;
import com.daykm.tiger.features.services.AuthenticationService;
import com.daykm.tiger.features.services.OAuth2Interceptor;
import com.daykm.tiger.features.services.TimelineService;
import com.daykm.tiger.features.services.TwitterApp;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public class ServiceModule {

	private OkHttpClient httpBuilder = new OkHttpClient.Builder().build();

	@Provides @DebugLog TimelineService timelineService(OAuth2Interceptor interceptor, Gson gson) {
		interceptor.isAuth = false;
		return buildService(TimelineService.class, interceptor, gson, new Retrofit.Builder(),
				httpBuilder.newBuilder(), TwitterApp.BASE_URL_1_1);
	}

	@Provides @DebugLog AccessTokenService accessTokenService(OAuth2Interceptor interceptor,
			Gson gson) {
		interceptor.isAuth = false;
		return buildService(AccessTokenService.class, interceptor, gson, new Retrofit.Builder(),
				httpBuilder.newBuilder(), TwitterApp.BASE_URL);
	}

	@Provides @DebugLog AuthenticationService authenticationService(OAuth2Interceptor interceptor,
			Gson gson) {
		interceptor.isAuth = true;
		return buildService(AuthenticationService.class, interceptor, gson, new Retrofit.Builder(),
				httpBuilder.newBuilder(), TwitterApp.BASE_URL);
	}

	@Provides @DebugLog Gson getGson() {
		Gson gson = GsonProvider.getGson();
		return gson;
	}

	@Provides @DebugLog OAuth2Interceptor getInterceptor() {
		OAuth2Interceptor interceptor = new OAuth2Interceptor();
		return interceptor;
	}

	public static <S> S buildService(Class<S> serviceClass, OAuth2Interceptor oauthInterceptor,
			Gson gson, Retrofit.Builder retroBuilder, OkHttpClient.Builder httpBuilder, String baseUrl) {
		httpBuilder.interceptors().add(oauthInterceptor);
		if (BuildConfig.DEBUG) {
			HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
			interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
			httpBuilder.addInterceptor(interceptor);
		}
		OkHttpClient client = httpBuilder.build();
		Retrofit retrofit = retroBuilder.baseUrl(baseUrl)
				.addConverterFactory(GsonConverterFactory.create(gson))
				.client(client)
				.build();
		return retrofit.create(serviceClass);
	}
}
