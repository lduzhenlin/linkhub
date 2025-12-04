package com.qishanor.entity.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LinkRequest {

    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "地址不能为空")
    private String url;
    private String description;
    private String icon;
    @NotBlank(message = "分类不能为空")
    private String categoryId;
}
