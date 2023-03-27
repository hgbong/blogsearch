package com.hgbong.blogsearch.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueryEventListener {

    @EventListener
    public void handleQueryEvent(QueryEvent event) {
        log.info("query event occur!. query: {}" + event.getQuery());

    }
}
