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
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https",uriHost = "api.test.com",uriPort = 443)
@AutoConfigureMockMvc
public class CommentControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostCategoryRepository postCategoryRepository;

    @Autowired
    CommentRepository commentRepository;

//    @BeforeEach
//    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(restDocumentation))
//                .build();
//    }

    @Test
    @DisplayName("조회")
    public void test() throws Exception {

        PostCategory postCategory = postCategoryRepository.findById(1L).orElseThrow();

        Post post = Post.builder().title("조회 title").content("조회 content").postCategory(postCategory)
                .build();
        postRepository.save(post);

        Comment comment = Comment.builder().post(post).comment_content("test")
        .build();
        commentRepository.save(comment);
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/comment/{postId}",post.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("comment-inquiry", RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("postId").description("Post Id")

                ), PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("[].id").description("comment id"),
                        PayloadDocumentation.fieldWithPath("[].comment_content").description("comment 내용")
                        )

                ));
    }


    @Test
    @DisplayName("글 등록")
    public void test2() throws Exception {


        PostCategory postCategory = postCategoryRepository.findById(1L).orElseThrow();

        Post post = Post.builder().title("조회 title").content("조회 content").postCategory(postCategory)
                .build();
        postRepository.save(post);

        CommentCreate create = CommentCreate.builder().post(post).content("입력 테스트").build();
        String Json = objectMapper.writeValueAsString(create);


        // when
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/comment/{postId}",post.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(Json))
                .andDo(print())
                .andExpect(status().isOk())

                        .andDo(document("comment-create", RequestDocumentation.pathParameters(
                           RequestDocumentation.parameterWithName("postId").description("Post Id")
                        //RequestDocumentation.parameterWithName("commentId").description("comment Id")
                ),
                PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("content").description("comment 내용")
                )
        ));
    }

    @Test
    @DisplayName("삭제")
    public void test3() throws Exception {

        PostCategory postCategory = postCategoryRepository.findById(1L).orElseThrow();

        Post post = Post.builder().title("조회 title").content("조회 content").postCategory(postCategory)
                .build();
        postRepository.save(post);

        Comment comment = Comment.builder().post(post).comment_content("test")
                .build();
        commentRepository.save(comment);

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/comment/{commentId}",comment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("comment-delete", RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("commentId").description("comment Id")

                )));

    }

    @Test
    @DisplayName("수정")
    public void test4() throws Exception {
        PostCategory postCategory = postCategoryRepository.findById(1L).orElseThrow();

        Post post = Post.builder().title("조회 title").content("조회 content").postCategory(postCategory)
                .build();
        postRepository.save(post);

        Comment comment = Comment.builder().post(post).comment_content("test")
                .build();
        commentRepository.save(comment);



        CommentEdit commentEdit = new CommentEdit("수정 코멘트");

        mockMvc.perform(RestDocumentationRequestBuilders.patch( "/comment/{commentId}",comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentEdit))
                )
                .andExpect(status().isOk())


                .andDo(document("comment-revise", RequestDocumentation.pathParameters(
                             //   RequestDocumentation.parameterWithName("postId").description("Post Id"),
                                RequestDocumentation.parameterWithName("commentId").description("comment Id")
                        ),
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("comment_content").description("comment 내용")
                        )
                ));

    }





}
