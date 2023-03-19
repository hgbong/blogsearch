package com.hgbong.blogsearch.model.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DocumentPageDto {

    private List<DocumentDto> documents;
    private Meta meta;

    @Getter
    @Setter
    @NoArgsConstructor
    static class Meta {
        private String is_end;
        private String pageable_count;
        private String total_count;
    }
}
