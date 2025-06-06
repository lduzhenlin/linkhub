package com.qishanor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "*") // 允许跨域请求
public class FileUploadController {

    @RequestMapping("/page")
    public Object uploadPage(ModelAndView mv){

        mv.setViewName("front/upload");
        return mv;
    }

    // 文件上传目录
    private final String uploadDir = "uploads";

    public FileUploadController() {
        // 确保上传目录存在
        createUploadDirectory();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("files[]") MultipartFile[] files) {
        List<String> uploadedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                // 验证文件
                if (file.isEmpty()) {
                    failedFiles.add(file.getOriginalFilename() + " (empty file)");
                    continue;
                }

                // 生成唯一文件名
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFilename = UUID.randomUUID().toString() + extension;

                // 保存文件
                Path filePath = Paths.get(uploadDir, newFilename);
                Files.copy(file.getInputStream(), filePath);

                uploadedFiles.add(originalFilename);
                log.info("File uploaded successfully: {}", originalFilename);
            } catch (IOException e) {
                log.error("Failed to upload file: " + file.getOriginalFilename(), e);
                failedFiles.add(file.getOriginalFilename() + " (" + e.getMessage() + ")");
            }
        }

        // 构建响应
        if (failedFiles.isEmpty()) {
            return ResponseEntity.ok(new UploadResponse(true, "All files uploaded successfully", uploadedFiles));
        } else if (uploadedFiles.isEmpty()) {
            return ResponseEntity.badRequest().body(new UploadResponse(false, "All files failed to upload", failedFiles));
        } else {
            return ResponseEntity.ok(new UploadResponse(true, 
                "Some files uploaded successfully, some failed", 
                uploadedFiles, 
                failedFiles));
        }
    }

    private void createUploadDirectory() {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // 响应对象
    private static class UploadResponse {
        private boolean success;
        private String message;
        private List<String> uploadedFiles;
        private List<String> failedFiles;

        public UploadResponse(boolean success, String message, List<String> uploadedFiles) {
            this.success = success;
            this.message = message;
            this.uploadedFiles = uploadedFiles;
        }

        public UploadResponse(boolean success, String message, List<String> uploadedFiles, List<String> failedFiles) {
            this.success = success;
            this.message = message;
            this.uploadedFiles = uploadedFiles;
            this.failedFiles = failedFiles;
        }

        // Getters
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public List<String> getUploadedFiles() { return uploadedFiles; }
        public List<String> getFailedFiles() { return failedFiles; }
    }
} 