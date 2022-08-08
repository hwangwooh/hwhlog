package com.hawng.hawng.service;

import com.hawng.hawng.Repository.PostRepository;
import com.hawng.hawng.domain.Post;
import com.hawng.hawng.exception.PostNotFound;
import com.hawng.hawng.request.PostCreate;
import com.hawng.hawng.request.PostEdit;
import com.hawng.hawng.request.PostSearch;
import com.hawng.hawng.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hawng.hawng.domain.Post.builder;

@SpringBootTest
class PostServiceTest {


    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Test
    @DisplayName("글 작성")
    public void test1() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder().title("제목").content("내요용")
                .build();


        // when
        postService.write(postCreate);
        // then
        Assertions.assertEquals(1L,postRepository.count());
        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목", post.getTitle());
        Assertions.assertEquals("내요용", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    public void test2() throws Exception {
        // given
        Post post = Post.builder().title("123").content("456")
                .build();


        // when
        postRepository.save(post);
        Post post2 = postRepository.findAll().get(0);
        PostResponse post1 = postService.get(post2.getId());
        // then
        Assertions.assertNotNull(post1);

        Assertions.assertEquals("123", post1.getTitle());
        Assertions.assertEquals("456", post1.getContent());
    }


    @Test
    @DisplayName("글 1페이지 조회")
    public void test3() throws Exception {
        // given


        List<Post> postList = IntStream.range(1, 31).mapToObj(i -> Post.builder()
                .title("글 제 목 당"+ i)
                .content("내용입니당" + i)
                .build()).collect(Collectors.toList());
        postRepository.saveAll(postList);

//        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC,"id"));
        PostSearch postSearch = PostSearch.builder().page(1)
                .build();

        List<PostResponse> posts = postService.getList(postSearch);
        // then
        Assertions.assertEquals(10L,posts.size());
        Assertions.assertEquals("글 제 목 당30",posts.get(0).getTitle());
        Assertions.assertEquals("글 제 목 당27",posts.get(3).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    public void test4() throws Exception {
        // given
        Post post = Post.builder().title("123").content("456")
                .build();

        Post post1 = Post.builder().build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder().
        title("황우현")
        .content("950105")
        .build();

        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("글이 존제 하지 않습니다" + post.getId()));
        Assertions.assertEquals("황우현",changedPost.getTitle());
        Assertions.assertEquals("950105",changedPost.getContent());

    }




    @Test
    @DisplayName("글 1개 삭제")
    public void test6() throws Exception {
        // given
        Post post = Post.builder().title("123").content("456")
                .build();

        Post post1 = Post.builder().build();
        postRepository.save(post);

        postService.delete(post.getId());


        // then
   //     Post changedPost = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("글이 존제 하지 않습니다" + post.getId()));
       // Assertions.assertEquals("황우현",changedPost.getTitle());
        Assertions.assertEquals(0,postRepository.count());


    }

    @Test
    @DisplayName("존재 하지 않는 글")
    public void test5() throws Exception {
        // given
        Post post = Post.builder().title("황우현").content("현우황")
                .build();


        // when
        postRepository.save(post);
        //  Post post2 = postRepository.findAll().get(0);
        // PostResponse post1 = postService.get(post.getId()+1L);
        // then
        PostNotFound e = Assertions.assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });
        //  Assertions.assertEquals("존재하지 않는 글입니다", e.getMessage());

    }










}