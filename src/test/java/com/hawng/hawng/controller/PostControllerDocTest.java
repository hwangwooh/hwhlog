package com.hawng.hawng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawng.hawng.Repository.PostCategoryRepository;
import com.hawng.hawng.Repository.PostRepository;
import com.hawng.hawng.domain.Post;
import com.hawng.hawng.domain.PostCategory;
import com.hawng.hawng.request.PostCreate;
import com.hawng.hawng.request.PostEdit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.reflect.Array.get;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https",uriHost = "api.test.com",uriPort = 443)
@AutoConfigureMockMvc
public class PostControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostCategoryRepository postCategoryRepository;

//    @BeforeEach
//    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(restDocumentation))
//                .build();
//    }

    @Test
    @DisplayName("??????")
    public void test() throws Exception {

        PostCategory postCategory = postCategoryRepository.findById(1L).orElseThrow();

        Post post = Post.builder().title("?????? title").content("?????? content").postCategory(postCategory)
                .build();
        postRepository.save(post);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}",post.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-inquiry", RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("postId").description("Post Id")

                ), PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("id").description("Post Id"),
                        PayloadDocumentation.fieldWithPath("title").description("Post ??????"),
                        PayloadDocumentation.fieldWithPath("content").description("Post ??????"),
                        PayloadDocumentation.fieldWithPath("dateTime").description("?????? ??????"),
                        PayloadDocumentation.fieldWithPath("category").description("????????????")
                        )
                ));
    }


    @Test
    @DisplayName("??? ??????")
    public void test2() throws Exception {


        PostCategory postCategory = postCategoryRepository.findById(1L).orElseThrow();

        Post post = Post.builder().title("?????? title").content("?????? content").postCategory(postCategory)
                .build();
        postRepository.save(post);
        PostCreate postCreate = PostCreate.builder().category(postCategory.getId().toString()).content(post.getContent()).title(post.getTitle())
                .build();
        String Json = objectMapper.writeValueAsString(postCreate);

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/posts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(Json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-create",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("Post ??????"),
                                PayloadDocumentation.fieldWithPath("content").description("Post ??????"),
                                PayloadDocumentation.fieldWithPath("category").description("????????????")
                        )));

    }

    @Test
    @DisplayName("??????")
    public void test3() throws Exception {

        Post post = Post.builder().title("?????? title").content("?????? content")
                .build();
        postRepository.save(post);

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/posts/{postId}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-delete", RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("postId").description("Post Id")

                )));

    }

    @Test
    @DisplayName("??????")
    public void test4() throws Exception {
        PostCategory postCategory = postCategoryRepository.findById(1L).orElseThrow();

        Post post = Post.builder().title("?????? title").content("?????? content").postCategory(postCategory)
                .build();
        postRepository.save(post);


        PostEdit postEdit = new PostEdit("????????????????????? ", "?????? ???????????????");


        mockMvc.perform(RestDocumentationRequestBuilders.patch( "/posts/{postId}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isOk())
                .andDo(document("post-revise", RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("postId").description("Post Id")
                        ),
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("Post ??????"),
                                PayloadDocumentation.fieldWithPath("content").description("Post ??????")
                        )
                ));

    }





}
