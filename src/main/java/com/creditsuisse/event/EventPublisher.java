package com.creditsuisse.event;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.creditsuisse.model.Event;
import com.creditsuisse.util.EventUtil;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Value("${logfile.name}")
    String filename;

    @Value("${logfile.location}")
    String location;

    public void publishCustomEvent(final Event event,  HttpServletRequest httpServletRequest){

        log.info("start event.......... ");

        /* Get event start time */
        long startTimestamp =  System.currentTimeMillis();

        /* Create json event object*/
        UUID uuid = UUID.randomUUID();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",uuid.toString());
        jsonObject.put("state","STARTED");
        jsonObject.put("type", event.getLogType());
        jsonObject.put("host", httpServletRequest.getRemoteHost());
        jsonObject.put("timestamp",startTimestamp);

        /* Write event to log file*/
        Path path = Paths.get(location, filename);
        EventUtil.writeToFile(path.toFile(), jsonObject.toString());

        /* Publish event*/
        event.setId(uuid.toString());
        event.setLogType(event.getLogType());
        event.setHost(httpServletRequest.getRemoteHost());

        CustomEvent customSpringEvent = new CustomEvent(this, event, startTimestamp);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}