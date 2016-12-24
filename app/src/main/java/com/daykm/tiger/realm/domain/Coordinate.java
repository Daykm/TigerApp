package com.daykm.tiger.realm.domain;

import com.daykm.tiger.realm.wrappers.FloatWrapper;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Coordinate extends RealmObject {

    private int id;
    private RealmList<FloatWrapper> coordinates;
    private String type;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<FloatWrapper> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(RealmList<FloatWrapper> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
