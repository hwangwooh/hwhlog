package com.hawng.hawng.service;

import com.hawng.hawng.Repository.CommentRepository;
import com.hawng.hawng.Repository.PostCategoryRepository;
import com.hawng.hawng.Repository.PostRepository;
import com.hawng.hawng.domain.Comment;
import com.hawng.hawng.domain.Post;
import com.hawng.hawng.domain.PostCategory;
import com.hawng.hawng.exception.CommentNotFound;
import com.hawng.hawng.exception.PostNotFound;
import com.hawng.hawng.request.CommentCreate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hawng.hawng.domain.QComment.comment;


@SpringBootTest
public class CommentServiceTest {
    @Autowired
    private  JPAQueryFactory jpaQueryFactory;

    @Autowired
    private  PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Autowired
    private EntityManager em;


    @Test
    @DisplayName("뎃글 입력")
    public void test1() throws Exception {


//        Post post = Post.builder().title("여기에").content("코멘트써라").
//                build();
//        postRepository.save(post);

        postRepository.findById(33L).orElseThrow();

        //CommentCreate commentCreate = CommentCreate.builder().content("코멘트").post(post).build();
      //  Comment write = commentService.write(commentCreate);
   //     Comment comment = commentRepository.findById(write.getId()).orElseThrow();
      //  System.out.println("comment = " + comment);

       // Assertions.assertEquals("코멘트",comment.getComment_content());



    }

    @Test
    @DisplayName("카테고리")
    public void test87() throws Exception
    {
        PostCategory postCategory = new PostCategory("개발");
        postCategoryRepository.save(postCategory);
        Post post = Post.builder().title("여기에").content("코멘트써라").postCategory(postCategory).
                build();
        postRepository.save(post);

        PostCategory postCategory1 = postCategoryRepository.findById(postCategory.getId()).orElseThrow();
        Post post1 = postRepository.findById(post.getId()).orElseThrow();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(postCategory1.toString());
        System.out.println(post.toString());
        System.out.println(post1.toString());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@");


        CommentCreate commentCreate = CommentCreate.builder().content("코멘트").post(post).build();
       // Comment write = commentService.write(commentCreate);








    }
    

    

    @Test
    @DisplayName("해당 글에 코멘트 조회")
    public void test() throws Exception {

        Post post = Post.builder().title("수정전 타이틀").content("수정전콘텐트").
                build();
        postRepository.save(post);


        Comment comment2 = Comment.builder().post(post).comment_content("코멘트2")
                .build();
        commentRepository.save(comment2);
        Comment comment3 = Comment.builder().post(post).comment_content("코멘트3")
                .build();
        commentRepository.save(comment3);
        Comment comment4 = Comment.builder().post(post).comment_content("코멘트4")
                .build();
        commentRepository.save(comment4);


        List<Comment> list = commentService.getList(post.getId());
        for (Comment comment1 : list) {

            System.out.println("comment = " + comment1);

        }

        Assertions.assertEquals("코멘트2",comment2.getComment_content());
        Assertions.assertEquals("코멘트3",comment3.getComment_content());
        Assertions.assertEquals("코멘트4",comment4.getComment_content());


    }

    @Test
    @DisplayName("수정")
    public void test2() throws Exception {
        // given
        Post post = Post.builder().title("수정전 타이틀").content("수정전콘텐트").
                build();
        postRepository.save(post);

        Comment comment = Comment.builder().comment_content("코멘트 삭제 용").post(post)
                .build();
        commentRepository.save(comment);

        commentService.edit(comment.getId(),"수정 수정");
        Comment comment2 = commentRepository.findById(comment.getId()).orElseThrow();
        // when


        // then

        Assertions.assertEquals("수정 수정",comment2.getComment_content());

    }

    @Test
    @DisplayName("코멘스 삭제")
    public void test4() throws Exception {
        // given

        Post post = Post.builder().title("삭제용22").content("삭제할거야22").
                build();

        postRepository.save(post);

        Comment comment = Comment.builder().comment_content("코멘트 삭제 용222222").post(post).build();

        commentRepository.save(comment);
        commentService.delete(comment.getId());


      //

        // when

        // then
    }


    @Test
    @DisplayName("코멘스 삭제")
    public void test666() throws Exception {
        // given
        PostCategory postCategory = new PostCategory("자유");
        postCategoryRepository.save(postCategory);
        PostCategory postCategory2 = new PostCategory("개발");
        postCategoryRepository.save(postCategory2);

        //

        // when

        // then
    }







}
