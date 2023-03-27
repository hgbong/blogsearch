package com.hgbong.blogsearch.event;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class QueryEvent extends ApplicationEvent {
    private String query;

    public QueryEvent(Object source, String query) {
        super(source);
        this.query = query;
    }
}
