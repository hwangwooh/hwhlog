package com.hawng.hawng.request;


import com.hawng.hawng.domain.Post;
import com.hawng.hawng.exception.InvalidRequest;
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

    public void validate() {
        if(content.contains("바보")){
            throw new InvalidRequest("comment","코멘트에 바보를 포함할수 없습니다");
        }

    }
}
