package com.qishanor.entity.req;

import lombok.Data;

@Data
public class LinkRequest {

    private String title;
    private String url;
    private String description;
    private String icon;
    private String categoryId;
}
