package com.portal26.demo.controller;

import com.portal26.demo.model.EventDto;
import com.portal26.demo.request.SearchReq;
import com.portal26.demo.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1")
public class MainController {

    private static final String FROM_DEFAULT_VALUE = "0";
    private static final String TO_DEFAULT_VALUE = "10";
    @Autowired
    private EventService service = null;

    @PostMapping(value = "/webhooks/{tenant}/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveEntity(@PathVariable String tenant,
                                        @RequestBody EventDto document) {
        boolean success = service.createEvent(document, tenant);
        return ResponseEntity.ok(success);
    }

    @GetMapping(value = "/{tenant}/search/userId/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDto>> findById(@PathVariable String tenant,
                                                   @PathVariable String userId,
                                                   @RequestParam(defaultValue = FROM_DEFAULT_VALUE) int from,
                                                   @RequestParam(defaultValue = TO_DEFAULT_VALUE) int size) {
        return new ResponseEntity<>(
                service.findByUserId(userId, tenant, from, size), HttpStatus.OK);
    }

    @PostMapping(value = "/{tenant}/query", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDto>> saveEntity(@PathVariable String tenant,
                                                    @RequestBody SearchReq searchReq,
                                                     @RequestParam(defaultValue = FROM_DEFAULT_VALUE) int from,
                                                     @RequestParam(defaultValue = TO_DEFAULT_VALUE) int size) {
        return new ResponseEntity<>(
                service.search(searchReq, tenant, from, size), HttpStatus.OK);
    }

    @GetMapping(value = "/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }
}
