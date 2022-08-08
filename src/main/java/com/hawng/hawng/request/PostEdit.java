package com.hawng.hawng.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter

public class PostEdit {
    @NotBlank(message = "title 입력해주세요")
    public String title;
    @NotBlank(message = "content 입력해주세요")
    public String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
