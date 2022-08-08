package com.hawng.hawng.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Lob;

@Getter
public class PostEditor {
    private final String title;
    private final String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
