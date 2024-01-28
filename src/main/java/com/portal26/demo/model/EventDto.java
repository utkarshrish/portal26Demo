package com.portal26.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    @JsonProperty(value = "user_id")
    private String userId;
    private String url;
    private String body;
    private String domain;
    private String category;

    @JsonProperty(value = "event_timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "GMT")
    private Date eventTimestamp;
}
