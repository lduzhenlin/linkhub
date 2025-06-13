package com.qishanor.framework.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProxyUrlToMultipartFile {

    /**
     * 从代理URL获取图片并转换为MultipartFile
     */
    public static MultipartFile convert(String proxyUrl) throws IOException {
        // 发送请求到代理URL
        HttpRequest httpRequest = HttpRequest.get(proxyUrl);
        HttpResponse response = httpRequest.execute();

        if (!response.isOk()) {
            throw new IOException("无法从代理URL获取内容: " + proxyUrl);
        }

        // 获取响应内容（可能是重定向地址或图片数据）
        String content = response.body();
        String contentType = response.header("Content-Type");

        // 检查是否是重定向响应（可能返回HTML或JSON）
        if (contentType != null && contentType.contains("text")) {
            // 从响应内容中提取实际图片URL
            String realImageUrl = extractImageUrl(content);
            if (realImageUrl != null) {
                // 递归调用，处理实际图片URL
                return convert(realImageUrl);
            } else {
                throw new IOException("无法从响应中提取图片URL");
            }
        } else {
            // 直接是图片数据
            return createMultipartFile(response, proxyUrl);
        }
    }

    /**
     * 创建MultipartFile对象
     */
    private static MultipartFile createMultipartFile(HttpResponse response, String originalUrl) throws IOException {
        // 获取图片数据
        byte[] imageBytes = response.bodyBytes();

        // 从原始URL中提取文件名（如果有）
        String fileName = extractFileName(originalUrl);

        // 获取或检测Content-Type
        String contentType = response.header("Content-Type");
        if (contentType == null || contentType.startsWith("application/octet-stream")) {
            contentType = detectContentType(imageBytes);
        }

        // 创建MultipartFile
        return new MockMultipartFile(
                "file",
                fileName,
                contentType,
                new ByteArrayInputStream(imageBytes)
        );
    }

    /**
     * 从URL中提取文件名
     */
    private static String extractFileName(String url) {
        if (url == null) {
            return "image" + getRandomSuffix();
        }

        try {
            UrlBuilder urlBuilder = UrlBuilder.of(url);
            String path = String.valueOf(urlBuilder.getPath());

            if (path != null && !path.isEmpty()) {
                String[] parts = path.split("/");
                String fileName = parts[parts.length - 1];

                // 检查是否有扩展名
                if (fileName.contains(".")) {
                    return fileName;
                } else {
                    return fileName + getRandomSuffix();
                }
            }
        } catch (Exception e) {
            // 解析失败时返回随机文件名
        }

        return "image" + getRandomSuffix();
    }

    // 其余方法（extractImageUrl, getRandomSuffix, detectContentType）保持不变...

    /**
     /**
     * 从响应内容中提取图片URL
     */
    private static String extractImageUrl(String content) {
        // 简单正则匹配常见的URL格式
        String regex = "https?://[^\\s\"']+\\.(jpg|jpeg|png|gif|bmp|webp)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.group();
        }

        // 尝试提取JSON中的URL
        regex = "\"(https?://[^\\s\"']+\\.(jpg|jpeg|png|gif|bmp|webp))\"";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    /**
     * 获取随机文件后缀
     */
    private static String getRandomSuffix() {
        String[] suffixes = {".jpg", ".png", ".gif", ".webp"};
        int index = (int) (Math.random() * suffixes.length);
        return suffixes[index];
    }

    /**
     * 检测文件内容类型
     */
    private static String detectContentType(byte[] data) {
        // 简单检测文件头
        if (data.length >= 2) {
            // JPEG
            if (data[0] == (byte)0xFF && data[1] == (byte)0xD8) {
                return "image/jpeg";
            }
            // PNG
            if (data[0] == (byte)0x89 && data[1] == (byte)0x50) {
                return "image/png";
            }
            // GIF
            if (data[0] == 'G' && data[1] == 'I') {
                return "image/gif";
            }
            // BMP
            if (data[0] == 'B' && data[1] == 'M') {
                return "image/bmp";
            }
        }
        return "image/jpeg"; // 默认返回JPEG
    }

}