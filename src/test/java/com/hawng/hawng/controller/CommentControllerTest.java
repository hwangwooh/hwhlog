package com.hawng.hawng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawng.hawng.Repository.PostRepository;
import com.hawng.hawng.domain.Post;
import com.hawng.hawng.request.CommentCreate;
import com.hawng.hawng.request.PostCreate;
import com.hawng.hawng.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https",uriHost = "api.test.com",uriPort = 443)
@AutoConfigureMockMvc
public class CommentControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("코멘트 입력")
    public void test() throws Exception {
        Post post = Post.builder().title("수정전 타이틀").content("수정전콘텐트").
                build();
        System.out.println("post = " + post);
        postRepository.save(post);


        CommentCreate create = CommentCreate.builder().post(post).content("입력 테스트").build();
        String Json = objectMapper.writeValueAsString(create);

        // when


        // when

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json)
                )
                .andExpect(status().isOk())
                .andDo(print());

    }

}
