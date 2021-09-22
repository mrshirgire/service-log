package com.creditsuisse.controller;

import com.creditsuisse.model.Event;
import com.creditsuisse.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping(value = "/api/event/publish",  consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> publishEvent(@Valid @RequestBody Event event, HttpServletRequest httpServletRequest) {

        log.info("In controller");
        return new ResponseEntity<Event>(eventService.publishEvent(event, httpServletRequest), CREATED);
    }

}
