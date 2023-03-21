package com.hgbong.blogsearch.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"query", "count"})
public class Query implements Persistable<String> {

    @Id
    private String query;

    @Column(nullable = false)
    private long count;

    public Query(String query, long count) {
        this.query = query;
        this.count = count;
    }

    @Transient
    @Override
    public String getId() {
        return this.query;
    }

    @Transient
    @Override
    public boolean isNew() {
        return this.count < 1;
    }

    public void addCount(long count) {
        this.count += count;
    }
}
