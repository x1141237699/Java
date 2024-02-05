package com.example.startingNovel.controller;

import com.example.startingNovel.pojo.classification;
import com.example.startingNovel.result.result;
import com.example.startingNovel.service.impl.classificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/classification")
public class classificationController {

    @Autowired
    private classificationServiceImpl classificationserviceimpl;

    @GetMapping("/getClassificationByClassificationName")
    public result<classification> getClassificationByClassificationName(@RequestParam String classificationName) {
        if(classificationserviceimpl.getClassificationByClassificationName(classificationName) == null)
            return result.fail(classificationserviceimpl.getClassificationByClassificationName(classificationName));
        return result.success(classificationserviceimpl.getClassificationByClassificationName(classificationName));
    }

    @GetMapping("/getClassificationByClassificationId")
    public result<classification> getClassificationByClassificationId(@RequestParam int classificationId) {
        if(classificationserviceimpl.getClassificationByClassificationId(classificationId) == null)
            return result.fail(classificationserviceimpl.getClassificationByClassificationId(classificationId));
        return result.success(classificationserviceimpl.getClassificationByClassificationId(classificationId));
    }

    @GetMapping("/list")
    public result<List<classification>> getAllClassification() {
        if(classificationserviceimpl.getAllClassification().isEmpty())
            return result.fail(classificationserviceimpl.getAllClassification());
        return result.success(classificationserviceimpl.getAllClassification());
    }
}
