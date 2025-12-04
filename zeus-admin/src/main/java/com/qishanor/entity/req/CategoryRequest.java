package com.qishanor.entity.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotNull(message = "分类名不能为空")
    private String name;
    /**
     * 排序值，数值越小越靠前
     */
    private Integer sort;


}
