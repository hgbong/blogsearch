package com.hgbong.blogsearch.model.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponse<T> {
    private List<T> contents;
    private int page;
    private int size;
    private int totalCount;
    private String sort;
}
