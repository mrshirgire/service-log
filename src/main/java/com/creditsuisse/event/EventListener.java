package com.creditsuisse.event;

import com.creditsuisse.model.Event;
import com.creditsuisse.repository.EventRepository;
import com.creditsuisse.util.EventUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class EventListener implements ApplicationListener<CustomEvent> {

    @Value("${logfile.name}")
    String filename;

    @Value("${logfile.location}")
    String location;

    @Autowired
    EventRepository eventRepository;

    @Override
    public void onApplicationEvent(CustomEvent customEvent) {

        LinkedHashMap<String, Object> eventData = customEvent.getEventData();
        Event event = (Event) eventData.get("event");
        
        long endTimestamp =  System.currentTimeMillis();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", event.getId());
        jsonObject.put("state", "FINISHED");
        jsonObject.put("type", event.getLogType());
        jsonObject.put("host", event.getHost());
        jsonObject.put("timestamp", endTimestamp);

        log.info("event details: ");
        log.info(jsonObject.toJSONString());

        
        Path path = Paths.get(location, filename);
        log.info("full-path: "+path.toString());

        long eventDuration = endTimestamp - (long)eventData.get("startTimestamp");

        /*write event to logfile*/
        EventUtil.writeToFile(path.toFile(), jsonObject.toString());
        

        event.setEventDuration(eventDuration);
        event.setAlert(false);

        if(eventDuration > 4)
            event.setAlert(true);

        eventRepository.save(event);

        log.info("end event..........");

    }
}