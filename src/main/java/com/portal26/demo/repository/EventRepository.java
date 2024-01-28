package com.portal26.demo.repository;

import com.portal26.demo.document.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends ElasticsearchRepository<Event, String> {

    Page<Event> findByTenant(String tenant,
                             Pageable pageable);

    Page<Event> findByUserIdAndTenant(String userId,
                                      String tenant,
                                      Pageable pageable);

    Page<Event> findByUrlDomain(String urlDomain,
                                Pageable pageable);

    Page<Event> findByUserIdAndTenantAndUrlDomainAndCategoryAndEventTimestampBetween(
            String userId,
            String tenant,
            String urlDomain,
            String category,
            long fromTimeStamp,
            long toTimeStamp,
            Pageable pageable);
}
