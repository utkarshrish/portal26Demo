package com.portal26.demo.response;

import lombok.Data;

import java.util.List;

@Data
public class ShrinkerData {
    private String url;
    private List<Category> categories;
}
