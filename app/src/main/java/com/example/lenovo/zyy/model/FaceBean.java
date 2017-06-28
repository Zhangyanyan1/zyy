package com.example.lenovo.zyy.model;

/**
 * Created by lenovo on 2017/6/6.
 */

public class FaceBean {
    private String name;
    private int id;

    public FaceBean(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
