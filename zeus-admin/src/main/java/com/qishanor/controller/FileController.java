package com.qishanor.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.io.IoUtil;
import com.amazonaws.services.s3.model.S3Object;
import com.qishanor.common.core.util.R;
import com.qishanor.common.file.FileTemplate;
import com.qishanor.framework.util.ImageTypeValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final FileTemplate fileTemplate;


    @RequestMapping("/upload")
    private R<List<String>> upload(@RequestParam("files[]") MultipartFile[] files) throws Exception {
//        Map<String, String> resultMap = new HashMap<>(4);
        List<String> uploadFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if(!ImageTypeValidator.isCommonImage(file)){
                return R.failed("仅支持常用图片格式: "+ImageTypeValidator.getAllowedFormatsText());
            }
            String fileName=fileTemplate.uploadFile(file);
            uploadFiles.add(fileName);
        }
        return R.ok(uploadFiles);
    }

    /**
     * 获取本地（resources）文件
     * @param fileName 文件名称
     * @param response 本地文件
     */
    @SaIgnore
    @GetMapping("/{fileName}")
    public void localFile(@PathVariable String fileName, HttpServletResponse response){
        try{
            S3Object s3Object=fileTemplate.getFile(fileName);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IoUtil.copy(s3Object.getObjectContent(),response.getOutputStream());
        }catch (Exception e){}

    }


}
