package com.portal26.demo.service;

import com.portal26.demo.document.Event;
import com.portal26.demo.exception.ApplicationException;
import com.portal26.demo.model.EventDto;
import com.portal26.demo.model.SearchQuery;
import com.portal26.demo.repository.EventRepository;
import com.portal26.demo.request.SearchReq;
import com.portal26.demo.utils.TranslationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventService {

    @Autowired
    public Map<String, String> categoryDomainMap;

    @Autowired
    private ShrinkerService shrinkerService;

    @Autowired
    private EventRepository eventRepository;

    public boolean createEvent(EventDto document, String tenant) {
        try{
            final String domain = TranslationUtils.getDomain(document.getUrl());
            final String category;
            if (categoryDomainMap.containsKey(domain)) {
                category = categoryDomainMap.get(domain);
            } else{
                category = shrinkerService.getCategory(domain);
                categoryDomainMap.put(domain, category);
            }
            eventRepository.save(TranslationUtils.toEvent(document, tenant, domain, category));
            return true;
        } catch (Exception e){
            log.error("Error saving event", e);
            return false;
        }
    }

    public List<EventDto> findByTenant(String tenant, int from, int size) {
        try{
            List<Event> events = eventRepository.findByTenant(tenant,
                    getPageReq(from, size)).getContent();
            return events.stream()
                    .map(TranslationUtils::toEventDto)
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<EventDto> findByUserId(String userId, String tenant, int from, int size) {
        try{
            List<Event> events = eventRepository.findByUserIdAndTenant(null, tenant,
                            getPageReq(from, size)).getContent();
            return events.stream()
                    .map(TranslationUtils::toEventDto)
                    .collect(Collectors.toList());
        } catch (Exception e){
            log.error("Error in search: ", e);
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<EventDto> search(SearchReq searchReq,
                                 String tenant,
                                 int from,
                                 int size){
        try{
            final SearchQuery searchQuery = TranslationUtils.toSearchQuery(searchReq, tenant);
            List<Event> events = eventRepository.findByUserIdAndTenantAndUrlDomainAndCategoryAndEventTimestampBetween(
                    searchQuery.getUserId(),
                    searchQuery.getTenant(),
                    searchQuery.getUrlDomain(),
                    searchQuery.getCategory(),
                    searchQuery.getFromDate(),
                    searchQuery.getToDate(),
                    getPageReq(from, size)).getContent();
            return events.stream()
                    .map(TranslationUtils::toEventDto)
                    .collect(Collectors.toList());
        } catch (Exception e){
            log.error("Error in search: ", e);
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<EventDto> findByUrlDomain(String urlDomain, int from, int size) {
        try{
            List<Event> events = eventRepository.findByUrlDomain(urlDomain, getPageReq(from, size))
                    .getContent();
            return events.stream()
                    .map(TranslationUtils::toEventDto)
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<EventDto> findEventsSince(String urlDomain, int from, int size) {
        try{
            List<Event> events = eventRepository.findByUrlDomain(urlDomain, getPageReq(from, size))
                    .getContent();
            return events.stream()
                    .map(TranslationUtils::toEventDto)
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private PageRequest getPageReq(int from, int size){
        return  PageRequest.of(from/size, size);
    }
}
