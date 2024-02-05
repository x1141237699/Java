package com.example.startingNovel.service.impl;

import com.example.startingNovel.mapper.classificationMapper;
import com.example.startingNovel.pojo.classification;
import com.example.startingNovel.service.classificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class classificationServiceImpl implements classificationService {

    @Autowired
    private classificationMapper classificationmapper;

    @Override
    public classification getClassificationByClassificationName(String classificationName) {
        return classificationmapper.getClassificationByClassificationName(classificationName);
    }

    @Override
    public classification getClassificationByClassificationId(int classificationId) {
        return classificationmapper.getClassificationByClassificationId(classificationId);
    }

    @Override
    public List<classification> getAllClassification() {
        return classificationmapper.getAllClassification();
    }
}
