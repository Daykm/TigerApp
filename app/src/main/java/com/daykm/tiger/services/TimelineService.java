package com.daykm.tiger.services;

import com.daykm.tiger.realm.domain.Status;
import com.daykm.tiger.realm.domain.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TimelineService {
	@POST("statuses/update.json") Call<Void> postStatus(
			@Query(value = "status", encoded = true) String status);

	/**
	 * @param count 3200
	 */
	@GET("statuses/user_timeline.json") Call<List<Status>> getUserTimeline(
			@Query("user_id") String user, @Query("since_id") String sinceId, @Query("count") int count);

	/**
	 * @param count maximum 800
	 */
	@GET("statuses/mentions_timeline.json") Call<List<Status>> getMentions(
			@Query("user_id") String user, @Query("since_id") String sinceId, @Query("count") int count);

	/**
	 * @param count maximum 200
	 */
	@GET("statuses/home_timeline.json") Call<List<Status>> getTimeline(
			@Query("since_id") String sinceId, @Query("count") int count);

	@GET("users/show.json") Call<User> getUser(@Query("user_id") String userId,
			@Query("screen_name") String screenName);
}
