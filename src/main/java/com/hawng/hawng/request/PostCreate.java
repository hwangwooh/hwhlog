package com.hawng.hawng.request;

import com.hawng.hawng.exception.InvalidRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString

public class PostCreate {

    @NotBlank(message = "title 입력해주세요")
    public String title;
    @NotBlank(message = "content 입력해주세요")
    public String content;
    @NotBlank(message = "Category 입력해주세요")
    public String category;

    @Builder
    public PostCreate(String title, String content,String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public void validate() {
        if(title.contains("바보")){
            throw new InvalidRequest("title","제목에 바보를 포함할수 없습니다");
        }

    }
}
