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

    @Test
    @DisplayName("글 조회시 코멘트 같이 조회")
    public void test2() throws Exception {
        // given

//        Post post = Post.builder().title("글 조회시 코멘트").content("태스트")
//                .build();
//        postRepository.save(post);
//        Comment comment = Comment.builder().post(post).comment_content("코멘트1").
//                build();
//        commentRepository.save(comment);
//
//        Comment comment2 = Comment.builder().post(post).comment_content("코멘트2").
//                build();
//        commentRepository.save(comment2);

        // when




        // when

        // then

        mockMvc.perform(MockMvcRequestBuilders.get("/comment/{postId}",33L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }



    @Test
    @DisplayName("해당 코멘트 삭제")
    public void test3() throws Exception {
        // given
        Post post = Post.builder().title("12345678").content("456")
                .build();
        postRepository.save(post);

        Comment comment = Comment.builder().post(post).comment_content("코멘트1 삭제한다").
                build();
        commentRepository.save(comment);




        // when

        mockMvc.perform(MockMvcRequestBuilders.delete("/comment/{commentId}",comment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("수정")
    public void test4() throws Exception {
        // given


        PostCategory postCategory = new PostCategory("자유");
        PostCategory save = postCategoryRepository.save(postCategory);

        Post post = Post.builder().title("12342222222225678").content("4522222222226").postCategory(save)
                .build();
        postRepository.save(post);
        Comment comment = Comment.builder().post(post).comment_content("코멘트1 수정한다").
                build();
        commentRepository.save(comment);
        CommentEdit commentEdit = CommentEdit.builder().comment_content("수정사항").build();
        String json = objectMapper.writeValueAsString(commentEdit);

        // when


        mockMvc.perform(MockMvcRequestBuilders.patch( "/comment/{commentId}",comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());



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

        String s = objectMapper.writeValueAsString(postEdit);
        System.out.println("@@@@@@@@ = " + s); //{"title":"황우현","content":"950105"}
        // when

        mockMvc.perform(MockMvcRequestBuilders.patch( "/posts/{postId}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then

    }


}
