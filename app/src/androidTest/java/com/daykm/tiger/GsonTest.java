package com.daykm.tiger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.daykm.tiger.features.data.realm.GsonProvider;
import com.daykm.tiger.features.data.realm.domain.Entities;
import com.daykm.tiger.features.data.realm.domain.Mention;
import com.daykm.tiger.features.data.realm.domain.Status;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class) public class GsonTest {

	Context context;

	@Before public void setup() {
		context = InstrumentationRegistry.getContext();
	}

	@Test public void testGetGson() {
		Gson gson = GsonProvider.getGson();
		assertNotNull(gson);
	}

	@Test public void testParse() {
		Gson gson = GsonProvider.getGson();

		// TODO fix test resources not being found and move this out of the source resources
		Mention[] status = gson.fromJson(Data.DATA_USER_MENTION, Mention[].class);
	}

	@Test public void testMentionParse() {

		Gson gson = GsonProvider.getGson();

		// TODO fix test resources not being found and move this out of the source resources
		Mention[] status = gson.fromJson(Data.DATA_USER_MENTION, Mention[].class);
		assertEquals(status.length, 2);
	}

	@Test public void testEntitiesParse() {
		Gson gson = GsonProvider.getGson();
		Entities entities = gson.fromJson(Data.DATA_ENTITIES, Entities.class);
	}

	@Test public void testStatusParse() {
		Gson gson = GsonProvider.getGson();
		Status[] statuses = gson.fromJson(Data.DATA, Status[].class);
		assertTrue(true);
	}
}
