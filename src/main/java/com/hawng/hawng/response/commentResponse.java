package com.hawng.hawng.response;

import com.hawng.hawng.domain.Comment;
import com.hawng.hawng.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@ToString
public class commentResponse {
    private final Long id;
    private final String content;



    public commentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getComment_content();
    }


    @Builder
    public commentResponse(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
