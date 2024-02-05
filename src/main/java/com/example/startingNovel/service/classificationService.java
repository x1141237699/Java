package com.example.startingNovel.service;

import com.example.startingNovel.pojo.classification;

import java.util.List;

public interface classificationService {
    classification getClassificationByClassificationName(String classificationName);

    classification getClassificationByClassificationId(int classificationId);

    List<classification> getAllClassification();
}
