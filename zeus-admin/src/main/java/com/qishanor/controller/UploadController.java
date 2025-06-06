package com.qishanor.controller;

import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
public class UploadController {

//    private final FileTemplate fileTemplate;

//    private final SysFileConfig fileConfig;


//    @RequestMapping("/api/upload")
//    private R upload(@RequestParam("files[]") MultipartFile[] files) throws Exception {
//        List<String> uploadedFiles = new ArrayList<>();
//        List<String> failedFiles = new ArrayList<>();
//        Console.log(fileTemplate.getFileConfig());
//
//
//        for (MultipartFile file : files) {
//            String fileName=fileTemplate.putObject("image",file);
//            uploadedFiles.add(fileName);
//        }
//        List<Bucket> bucketList=fileTemplate.getAllBuckets();
//        bucketList.forEach(u->Console.log(u.getName()));
//
//        S3Object obj=fileTemplate.getObject("image",uploadedFiles.get(0));
//        Console.log(obj);
//        validateS3ObjectContent(obj);
//
//        List<S3ObjectSummary> objList=fileTemplate.getObjectsByPrefix("image","test",true);
//        for (S3ObjectSummary s3ObjectSummary : objList) {
//            Console.log(s3ObjectSummary);
//        }
//
//        fileTemplate.removeObject("image",uploadedFiles.get(1));
//        return R.ok();
//    }

    private static void validateS3ObjectContent(S3Object s3Object) {
        try (InputStream inputStream = s3Object.getObjectContent()) {
            // 检查输入流是否有数据
            long contentLength = inputStream.available(); // 获取对象大小（字节数）
            if (contentLength <= 0) {
                System.out.println("对象内容为空");
                return;
            }

            // 或通过读取流验证是否有数据（适用于流式处理场景）
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1) { // 流已结束且无数据
                System.out.println("对象内容为空");
            } else {
                System.out.println("对象内容有效，大小：" + contentLength + " 字节");
            }

        } catch (IOException e) {
            System.out.println("读取对象内容失败：" + e.getMessage());
        }
    }

}
