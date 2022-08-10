package com.hawng.hawng.service;

import com.hawng.hawng.Repository.CommentRepository;
import com.hawng.hawng.Repository.PostRepository;
import com.hawng.hawng.domain.Comment;
import com.hawng.hawng.domain.Post;
import com.hawng.hawng.exception.PostNotFound;
import com.hawng.hawng.request.CommentCreate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;


@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private  PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    @Autowired
    private EntityManager em;


    @Test
    @DisplayName("뎃글 입력")
    public void test1() throws Exception {


        Post post2 = postRepository.findById(168L).orElseThrow(() -> new PostNotFound());


        
        Comment comment = Comment.builder().comment_content("내용213123123s").post(post2).build();
        commentRepository.save(comment);
        Comment comment2 = Comment.builder().comment_content("내용23s").post(post2).build();
        commentRepository.save(comment2);
        Comment comment3 = Comment.builder().comment_content("내용2s").post(post2).build();
        commentRepository.save(comment3);



        List<Comment> commentList = em.createQuery("select c from Comment c" +
                " join fetch c.post p" +
                        " where p.id = "+post2.getId(),
                Comment.class).getResultList();
        for (Comment comment1 : commentList) {
            System.out.println("comment1 = " + comment1.toString());
            
        }


    }
}
