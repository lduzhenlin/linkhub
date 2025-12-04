package com.qishanor.framework.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;
import java.util.Set;

/**
 * Utility to ensure uploaded files are common image formats only.
 */
public final class ImageTypeValidator {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "png", "jpg", "jpeg", "gif", "bmp", "webp", "svg", "ico"
    );

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/png",
            "image/jpeg",
            "image/gif",
            "image/bmp",
            "image/webp",
//            "image/svg+xml",
            "image/svg",
            "image/x-icon",
            "image/vnd.microsoft.icon"
    );

    private static final String ALLOWED_FORMATS_TEXT = "png/jpg/jpeg/gif/bmp/webp/svg/ico";

    private ImageTypeValidator() {
    }

    /**
     * Checks whether the current MultipartFile is one of the allowed image formats.
     */
    public static boolean isCommonImage(MultipartFile file) {
        if (file == null) {
            return false;
        }
        String contentType = file.getContentType();
        if (contentType != null) {
            String normalizedType = contentType.toLowerCase(Locale.ROOT);
            if (ALLOWED_CONTENT_TYPES.contains(normalizedType)) {
                return true;
            }
        }
        String filename = file.getOriginalFilename();
        if (StrUtil.isNotBlank(filename)) {
            String ext = FileUtil.extName(filename);
            if (StrUtil.isNotBlank(ext)) {
                String normalizedExt = ext.toLowerCase(Locale.ROOT);
                if (ALLOWED_EXTENSIONS.contains(normalizedExt)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getAllowedFormatsText() {
        return ALLOWED_FORMATS_TEXT;
    }
}

