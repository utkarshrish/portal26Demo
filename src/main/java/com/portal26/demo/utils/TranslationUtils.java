package com.portal26.demo.utils;

import com.portal26.demo.document.Event;
import com.portal26.demo.model.EventDto;
import com.portal26.demo.model.SearchQuery;
import com.portal26.demo.request.SearchReq;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class TranslationUtils {

    public static SearchQuery toSearchQuery(SearchReq searchReq,
                                            String tenant){
        return SearchQuery.builder()
                .tenant(getSearchData(tenant))
                .userId(getSearchData(searchReq.getUserId()))
                .urlDomain(getSearchData(searchReq.getDomain()))
                .category(getSearchData(searchReq.getCategory()))
                .fromDate(getSearchDate(searchReq.getFromDate(), new Date(0)).getTime())
                .toDate(getSearchDate(searchReq.getToDate(), new Date()).getTime())
                .build();
    }

    public static Event toEvent(EventDto eventDto,
                                String tenant,
                                String domain,
                                String category){
        return Event.builder()
                .id(UUID.randomUUID().toString())
                .tenant(tenant)
                .userId(eventDto.getUserId())
                .body(eventDto.getBody())
                .url(eventDto.getUrl())
                .urlDomain(domain)
                .category(category)
                .eventTimestamp(eventDto.getEventTimestamp())
                .build();
    }

    public static EventDto toEventDto(Event event){
        return EventDto.builder()
                .userId(event.getUserId())
                .url(event.getUrl())
                .domain(event.getUrlDomain())
                .category(event.getCategory())
                .body(event.getBody())
                .eventTimestamp(event.getEventTimestamp())
                .build();
    }

    public static String getDomain(String url){
        Pattern pattern = Pattern.compile("(?i)(?:https?://)?(?:www\\.)?([^:/]+)");

        Matcher matcher = pattern.matcher(url);

        // Check if a match is found
        if (matcher.find()) {
            // Group 1 contains the matched domain
            String domain = matcher.group(1);
            String[] splits = domain.split("\\.");
            StringBuffer sb = new StringBuffer();
            sb.append(splits[splits.length-2]).append(".").append(splits[splits.length-1]);
            return sb.toString();
        } else {
            log.info("No domain found in the URL");
            return "";
        }
    }

    private static Date getSearchDate(Date d, Date defaultV){
        return d != null ? d: defaultV;
    }

    private static Date getSearchDate(String s, Date defaultV){
        try {
            if(isEmpty(s)){
                return defaultV;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            return formatter.parse(s);
        } catch (Exception e){
            log.error("Error in formatting date", e);
            return null;
        }
    }

    private static String getSearchData(String s){
        if(isEmpty(s)){
            return null;
        }
        return s;
    }

    private static boolean isEmpty(String s){
        return s==null || s.isEmpty();
    }
}
