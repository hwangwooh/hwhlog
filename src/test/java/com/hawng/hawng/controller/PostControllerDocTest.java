package com.hawng.hawng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawng.hawng.Repository.PostRepository;
import com.hawng.hawng.domain.Post;
import com.hawng.hawng.request.PostCreate;
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
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.lang.reflect.Array.get;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
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

//    @BeforeEach
//    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(restDocumentation))
//                .build();
//    }

    @Test
    @DisplayName("doc테스트")
    public void test() throws Exception {
        Post post = Post.builder().title("123456789012345678").content("456")
                .build();
        postRepository.save(post);

        // when



        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}",post.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-inquiry", RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("postId").description("게시글 ID")

                ), PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("id").description("게시글 ID"),
                        PayloadDocumentation.fieldWithPath("title").description("제목"),
                        PayloadDocumentation.fieldWithPath("content").description("내용")
                        )

                ));
    }


    @Test
    @DisplayName("글 등록")
    public void test2() throws Exception {

        PostCreate content = PostCreate.builder().title("나는").content("내용입니당.").build();



        String Json = objectMapper.writeValueAsString(content);

        // when



        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/posts").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(Json))
                .andExpect(status().isOk())
                .andDo(document("post-create",  PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("title").description("제목"),
                                PayloadDocumentation.fieldWithPath("content").description("내용")
                        )

                ));
    }
}
