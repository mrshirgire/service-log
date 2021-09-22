package com.creditsuisse.event;

import com.creditsuisse.model.Event;
import org.springframework.context.ApplicationEvent;
import java.util.LinkedHashMap;


public class CustomEvent extends ApplicationEvent {

    private  Event event;
    private long startTimestamp;

    public CustomEvent(Object source, Event event, long startTimestamp) {
        super(source);
        this.event = event;
        this.startTimestamp = startTimestamp;

    }
    
    public LinkedHashMap<String, Object> getEventData() {

        LinkedHashMap<String, Object> eventData = new LinkedHashMap<String, Object>();
        eventData.put("event", this.event);
        eventData.put("startTimestamp", startTimestamp);
        
        return eventData;
    }
}