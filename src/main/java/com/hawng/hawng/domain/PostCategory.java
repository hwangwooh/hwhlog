package com.hawng.hawng.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postcategory_id")
    private Long id;

    private String name;

    public String getName() {
        if(this.name == null){
            this.name = "자유";
        }
        return name;
    }

    @OneToMany(mappedBy = "postCategory")
    private List<Post> postList = new ArrayList<>();

    public PostCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PostCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
