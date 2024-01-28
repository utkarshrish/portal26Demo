package com.portal26.demo.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchQuery {
    private String tenant;
    private long fromDate;
    private long toDate;
    private String userId;
    private String urlDomain;
    private String category;
}
