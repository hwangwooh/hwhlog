package com.hawng.hawng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawng.hawng.Repository.CommentRepository;
import com.hawng.hawng.Repository.PostCategoryRepository;
import com.hawng.hawng.Repository.PostRepository;
import com.hawng.hawng.domain.Comment;
import com.hawng.hawng.domain.Post;
import com.hawng.hawng.domain.PostCategory;
import com.hawng.hawng.request.CommentCreate;
import com.hawng.hawng.request.CommentEdit;
import com.hawng.hawng.request.PostCreate;
import com.hawng.hawng.request.PostEdit;
import com.hawng.hawng.service.CommentService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;
    @Autowired
    PostCategoryRepository postCategoryRepository;

    @Test
    @DisplayName("????????? ??????")
    public void test() throws Exception {
        PostCategory postCategory = postCategoryRepository.findById(1L).orElseThrow();

        Post post = Post.builder().title("?????? title").content("?????? content").postCategory(postCategory)
                .build();
        postRepository.save(post);


        CommentCreate create = CommentCreate.builder().post(post).content("?????? ?????????").build();
        String Json = objectMapper.writeValueAsString(create);

        // when


        // when

        mockMvc.perform(MockMvcRequestBuilders.post("/comment/{postId}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json)
                )
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("??? ????????? ????????? ?????? ??????")
    public void test2() throws Exception {
        // given

        PostCategory postCategory = postCategoryRepository.findById(1L).orElseThrow();

        Post post = Post.builder().title("?????? title").content("?????? content").postCategory(postCategory)
                .build();
        postRepository.save(post);

        Comment comment = Comment.builder().post(post).comment_content("?????????1").
                build();
        commentRepository.save(comment);

        Comment comment2 = Comment.builder().post(post).comment_content("?????????2").
                build();
        commentRepository.save(comment2);






        // when

        // then

        mockMvc.perform(MockMvcRequestBuilders.get("/comment/{postId}",33L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }



    @Test
    @DisplayName("?????? ????????? ??????")
    public void test3() throws Exception {
        // given
        Post post = Post.builder().title("12345678").content("456")
                .build();
        postRepository.save(post);

        Comment comment = Comment.builder().post(post).comment_content("?????????1 ????????????").
                build();
        commentRepository.save(comment);




        // when

        mockMvc.perform(MockMvcRequestBuilders.delete("/comment/{commentId}",comment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("??????")
    public void test4() throws Exception {
        // given


        PostCategory postCategory = new PostCategory("??????");
        PostCategory save = postCategoryRepository.save(postCategory);

        Post post = Post.builder().title("12342222222225678").content("4522222222226").postCategory(save)
                .build();
        postRepository.save(post);
        Comment comment = Comment.builder().post(post).comment_content("?????????1 ????????????").
                build();
        commentRepository.save(comment);
        CommentEdit commentEdit = CommentEdit.builder().comment_content("????????????").build();
        String json = objectMapper.writeValueAsString(commentEdit);

        // when


        mockMvc.perform(MockMvcRequestBuilders.patch( "/comment/{commentId}",comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());



    }



}
