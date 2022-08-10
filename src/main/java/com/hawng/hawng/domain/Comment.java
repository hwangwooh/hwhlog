package com.hawng.hawng.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Lob
    private String comment_content;

    public void addComment(String content){
        this.comment_content = content;
        post.getCommentList().add(this);
    }


    @Builder
    public Comment(Post post, String comment_content) {
        this.post = post;
        this.comment_content = comment_content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment_content='" + comment_content + '\'' +
                '}';
    }
}
