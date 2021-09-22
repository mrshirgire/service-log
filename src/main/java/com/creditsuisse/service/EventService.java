package com.creditsuisse.service;

import com.creditsuisse.event.EventPublisher;
import com.creditsuisse.model.Event;
import com.creditsuisse.util.EventUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class EventService {

    @Autowired
    EventPublisher customSpringEventPublisher;

    public Event publishEvent(Event event, HttpServletRequest httpServletRequest){

        customSpringEventPublisher.publishCustomEvent(event, httpServletRequest);
        return event;
    }


}
