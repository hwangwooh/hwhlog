package com.hawng.hawng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawng.hawng.Repository.PostCategoryRepository;
import com.hawng.hawng.Repository.PostRepository;
import com.hawng.hawng.domain.Post;
import com.hawng.hawng.domain.PostCategory;
import com.hawng.hawng.request.PostCreate;
import com.hawng.hawng.request.PostEdit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostCategoryRepository postCategoryRepository;

//    @BeforeEach
//    void clean() {
//        postRepository.deleteAll();
//    }

    @Test
    @DisplayName("/post 요청시 Hello World를 출력한다")
    public void test() throws Exception {
        // given
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니당.\", \"content\": \"내용입니당.\"}")
                )
                .andExpect(status().isOk())
               // .andExpect(MockMvcResultMatchers.content().string("{}"))
                .andDo(print());
                // when

        // then
    }

    @Test
    @DisplayName("/post 요청시 title값은 필수다")
    public void test2() throws Exception {
        // given

        PostCreate content = PostCreate.builder().content("내용입니다").build();

        String Json = objectMapper.writeValueAsString(content);
        // when


        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json)
                )
                .andExpect(status().isBadRequest())
                // .andExpect(MockMvcResultMatchers.content().string("{}"))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못됨"))
                .andExpect(jsonPath("$.validation.title").value("title 입력해주세요"))
                .andDo(print());
        // then
    }

    @Test
    @DisplayName("/post 요청시 db에 값 저장")
    public void test3() throws Exception {
        // given

        PostCreate content = PostCreate.builder().title("제목입니당.").content("내용입니당.").build();



        String Json = objectMapper.writeValueAsString(content);

        // when

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then

//        Post post = postRepository.findAll().get(0);
//        Assertions.assertEquals("제목입니당.", post.getTitle());
//        Assertions.assertEquals("내용입니당.", post.getContent());
    }



    @Test
    @DisplayName("글 조회")
    public void test4() throws Exception {
        // given
        Post post = Post.builder().title("123456789012345678").content("456")
                .build();
        postRepository.save(post);

        // when

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}",post.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(print());

        // then

    }

    @Test
    @DisplayName("글 여러개 조회")
    public void test5() throws Exception {
        // given


        PostCategory category2 = new PostCategory("자유");
        PostCategory category = new PostCategory("개발");
        postCategoryRepository.save(category2);
        PostCategory save = postCategoryRepository.save(category);
        PostCategory postCategory = postCategoryRepository.findById(save.getId()).orElseThrow();


        List<Post> postList = IntStream.range(1, 31).mapToObj(i -> Post.builder()
                .title("글제목당"+ i)
                .content("내용입니당" + i).postCategory(postCategory)
                .build()).collect(Collectors.toList());
        postRepository.saveAll(postList);


//        postRepository.saveAll(List.of(Post.builder().title("123456789012345678").content("456")
//                .build(), Post.builder().title("111111111111111222").content("4444444")
//                .build()
//
//        ));

        // when

        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
             //   .andExpect(jsonPath("$[0].title").value("글제목당30"))
                .andDo(print());

        // then

    }


    @Test
    @DisplayName("글 수정 조회")
    public void test6() throws Exception {
        // given
        Post post = Post.builder().title("12345678").content("456")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder().
                title("황우현")
                .content("950105")
                .build();
        // when

        mockMvc.perform(MockMvcRequestBuilders.patch( "/posts/{postId}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then

    }


    @Test
    @DisplayName("글 삭제")
    public void test7() throws Exception {
        // given
        Post post = Post.builder().title("12345678").content("456")
                .build();
        postRepository.save(post);




        // when

        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}",post.getId(),post)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
//                .andExpect(jsonPath("$.id").value(post.getId()))
//                .andExpect(jsonPath("$.title").value("1234567890"))
//                .andExpect(jsonPath("$.content").value(post.getContent()));
        // then

    }

    @Test
    @DisplayName("존재 하지 않는 삭제")
    public void test8() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}",10000L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("수정")
    public void test9() throws Exception {
        // given
        PostEdit postEdit = PostEdit.builder().
                title("황우현")
                .content("950105")
                .build();
        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.patch( "/posts/{postId}",3429)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @DisplayName("/post 요청시 db에 값 저장 예외")
    public void test10() throws Exception {
        // given

        PostCreate content = PostCreate.builder().title("나는 바").content("내용입니당.").build();



        String Json = objectMapper.writeValueAsString(content);

        // when

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then

    }


}