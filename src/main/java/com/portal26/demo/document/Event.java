package com.portal26.demo.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "events", type = "_doc", createIndex = false)
public class Event {
    @Id
    private String id;
    private String tenant;
    private String userId;
    private String url;
    private String body;
    private String urlDomain;
    private String category;
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Date eventTimestamp;

}
