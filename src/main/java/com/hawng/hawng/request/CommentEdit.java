package com.hawng.hawng.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class CommentEdit {


    @NotBlank(message = "comment_content 입력해주세요")
    public String comment_content;

    @Builder
    public CommentEdit(String comment_content) {
        this.comment_content = comment_content;
    }
}
