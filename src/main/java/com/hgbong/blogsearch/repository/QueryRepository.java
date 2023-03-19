package com.hgbong.blogsearch.repository;

import com.hgbong.blogsearch.entity.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryRepository extends JpaRepository<Query, String> {
    List<Query> findTop10ByOrderByCountDesc();
}
