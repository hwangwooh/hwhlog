package com.hawng.hawng.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob
    private String content;


    private LocalDate dateTime;

    @JsonIgnore
    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE) // cascade = CascadeType.REMOVE 연관 관계 코멘트도 같이 삭제할수 있음
    private List<Comment> commentList = new ArrayList<>();


    @PrePersist
    public void DateTime(){
        this.dateTime = LocalDate.now();
    }

    @Builder
    public Post(String title, String content,LocalDateTime dateTime) {
        this.title = title;
        this.content = content;
    }
    public PostEditor.PostEditorBuilder toEditor(){
        PostEditor.PostEditorBuilder builder = PostEditor.builder().title(title).content(content);
        return builder;
    }
    public void edit(PostEditor build) {
        this.title = build.getTitle();
        this.content = build.getContent();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dateTime=" + dateTime +
               // ", commentList=" + commentList +
                '}';
    }
}
