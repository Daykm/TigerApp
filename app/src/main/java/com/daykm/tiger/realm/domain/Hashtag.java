package com.daykm.tiger.realm.domain;

import io.realm.RealmObject;

public class Hashtag extends RealmObject {
    private int id;
    private Entities entities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }
}
