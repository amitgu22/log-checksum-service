package com.spring.batch.log.processor.config;

import com.spring.batch.log.processor.entity.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class EventItemProcessor implements ItemProcessor<Event, Event> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventItemProcessor.class);
    private static long calculateTime(long l1, long l2) {
        return l1 > l2 ?  l1-l2 : l2-l1;
    }

    private final Map<String,Event> eventMap = new HashMap<>();
    @Value(value = "${max.log.file.duration}")
    private long threshold;

    private int count=0;
    @Override
    public Event process(Event event) {
        if(!eventMap.containsKey(event.getId())) {
            eventMap.put(event.getId(), event);
            return null;
        }else{
            long duration = calculateTime(eventMap.get(event.getId()).getTimestamp(),event.getTimestamp());
            if(duration > threshold){
                LOGGER.info("## {} Event found for maximum threshold limit < {} milliseocnds",++count,duration);
                event.setTimestamp(duration);
                event.setAlert(true);
                return event;
            }
        }
        return null;
    }
}
