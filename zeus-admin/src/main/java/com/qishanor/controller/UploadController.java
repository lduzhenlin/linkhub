package com.qishanor.controller;

import cn.hutool.core.lang.Console;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.qishanor.common.core.util.R;
import com.qishanor.common.file.FileTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final FileTemplate fileTemplate;



    @RequestMapping("/api/upload")
    private R upload(@RequestParam("files[]") MultipartFile[] files) throws Exception {
        List<String> uploadedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();
        Console.log(fileTemplate.getFileConfig());


        for (MultipartFile file : files) {
            String fileName=fileTemplate.putObject("image",file);
            uploadedFiles.add(fileName);
        }
        List<Bucket> bucketList=fileTemplate.getAllBuckets();
        bucketList.forEach(u->Console.log(u.getName()));

        S3Object obj=fileTemplate.getObject("image",uploadedFiles.get(0));
        Console.log(obj);
//        validateS3ObjectContent(obj);

        List<S3ObjectSummary> objList=fileTemplate.getObjectsByPrefix("image","test",true);
        for (S3ObjectSummary s3ObjectSummary : objList) {
            Console.log(s3ObjectSummary);
        }

        fileTemplate.removeObject("image",uploadedFiles.get(1));
        return R.ok();
    }


}
