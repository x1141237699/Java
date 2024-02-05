package com.example.startingNovel.pojo;

import lombok.Data;

@Data
public class classification {

    private String classificationName;

    private int classificationId;

    public classification() {
    }

    public classification(String classificationName, int classificationId) {
        this.classificationName = classificationName;
        this.classificationId = classificationId;
    }

}
