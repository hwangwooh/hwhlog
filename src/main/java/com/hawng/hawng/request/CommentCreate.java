package com.hawng.hawng.request;


import com.hawng.hawng.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class CommentCreate {

    public Post Post;
    @NotBlank(message = "content 입력해주세요")
    public String content;

    @Builder
    public CommentCreate(com.hawng.hawng.domain.Post post, String content) {
        Post = post;
        this.content = content;
    }
}
