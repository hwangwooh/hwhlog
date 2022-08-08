package com.hawng.hawng.response;

import com.hawng.hawng.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collector;


@Getter
@ToString
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;

    public PostResponse(Post post) {


        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();

    }


    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0,Math.min(title.length(),10));
        this.content = content;
    }

}
