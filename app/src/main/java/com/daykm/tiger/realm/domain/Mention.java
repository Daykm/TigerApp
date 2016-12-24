package com.daykm.tiger.realm.domain;

import com.daykm.tiger.realm.wrappers.IntegerWrapper;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Mention extends RealmObject {
    private String screen_name;
    private String name;
    private Long id;
    private String id_str;
    private RealmList<IntegerWrapper> indices;

    public Mention(String screen_name, String name, Long id, String id_str, RealmList<IntegerWrapper> indices) {
        this.screen_name = screen_name;
        this.name = name;
        this.id = id;
        this.id_str = id_str;
        this.indices = indices;
    }

    public Mention() {}

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }


    public RealmList<IntegerWrapper> getIndices() {
        return indices;
    }

    public void setIndices(RealmList<IntegerWrapper> indices) {
        this.indices = indices;
    }
}
