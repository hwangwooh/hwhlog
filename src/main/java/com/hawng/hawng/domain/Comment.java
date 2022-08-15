package com.hawng.hawng.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hawng.hawng.request.CommentEdit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Lob
    private String comment_content;

    public void addComment(String content){
        this.comment_content = content;
        post.getCommentList().add(this);
    }

    public void edit(String comment_content) {

        this.comment_content = comment_content;
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

    public CommentEdit.CommentEditBuilder toEditor() {
        CommentEdit.CommentEditBuilder commentEditBuilder = CommentEdit.builder().comment_content(comment_content);
        return commentEditBuilder;

    }
}
