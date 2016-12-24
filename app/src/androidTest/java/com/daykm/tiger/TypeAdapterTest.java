package com.daykm.tiger;

import android.support.test.runner.AndroidJUnit4;

import com.daykm.tiger.realm.domain.Entities;
import com.daykm.tiger.realm.GsonProvider;
import com.daykm.tiger.realm.domain.Status;
import com.daykm.tiger.util.StringUtil;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class TypeAdapterTest {

    @Test
    public void test() {
        Method[] methods = Status.class.getDeclaredMethods();
        Field[] fields = Status.class.getDeclaredFields();


        for(Field field : fields) {
            String fieldName = field.getName();
            Method matchingSetter = null;
            Method matchingGetter = null;

            for(int i = 0; (matchingSetter == null || matchingGetter == null) || i > methods.length; i++) {
                if(StringUtil.containsIgnoreCase(methods[i].getName(), fieldName)) {
                    if(methods[i].getReturnType().equals(Void.TYPE)) {
                        matchingSetter = methods[i];
                    } else {
                        matchingGetter = methods[i];
                    }
                }
            }
        }
    }

    @Test
    public void testFactory() {
        Gson gson = GsonProvider.getGson();

        Entities entities  = gson.fromJson(Data.TEST_ENTITIEES, Entities.class);
        assertEquals(entities.media.size(), 4);

    }

}
