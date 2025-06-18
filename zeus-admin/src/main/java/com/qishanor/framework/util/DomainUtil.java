package com.qishanor.framework.util;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;

public class DomainUtil {

    /**
     * 比较两个域名是否是相同域名，子域名算是不同域名
     */
    public static boolean isSameDomain(String url1, String url2) {
        try {
            // 补全协议前缀
            url1 = ensureProtocol(url1);
            url2 = ensureProtocol(url2);

            // 解析第一个URL
            UrlBuilder builder1 = UrlBuilder.of(url1);
            String domain1 = builder1.getHost();

            // 解析第二个URL
            UrlBuilder builder2 = UrlBuilder.of(url2);
            String domain2 = builder2.getHost();

            // 比较域名（忽略大小写）
            return domain1.equalsIgnoreCase(domain2);
        } catch (Exception e) {
            // 解析失败，返回false
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 确保URL包含协议前缀，默认为http
     */
    public static String ensureProtocol(String url) {
        if (StrUtil.isBlank(url)) {
            return url;
        }

        // 如果不包含协议前缀，添加http://
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "http://" + url;
        }

        return url;
    }

    public static void main(String[] args) {
        String url1 = "https://www.baidu.com";
        String url2 = "http://www1.baidu.com";
        String url3 = "https://subdomain.example.com";

        System.out.println(isSameDomain(url1, url2)); // true (主域名相同)

    }
}
