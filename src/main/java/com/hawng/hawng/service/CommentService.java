package com.hawng.hawng.service;


import com.hawng.hawng.Repository.CommentRepository;
import com.hawng.hawng.domain.*;
import com.hawng.hawng.exception.CommentNotFound;
import com.hawng.hawng.exception.PostNotFound;
import com.hawng.hawng.request.*;
import com.hawng.hawng.response.PostResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.hawng.hawng.domain.QComment.comment;
import static com.hawng.hawng.domain.QPost.post;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final EntityManager em;

    private final JPAQueryFactory jpaQueryFactory;

    public Comment write(CommentCreate commentCreate) {
        Comment comment = Comment.builder().post(commentCreate.getPost()).comment_content(commentCreate.getContent()).
                build();

        commentRepository.save(comment);

        return comment;
    }

    public List<Comment> getList(Long postId) {

//        List<Comment> commentList = em.createQuery("select c from Comment c" +
//                        " join fetch c.post p" +
//                        " where p.id = "+postId,
//                Comment.class).getResultList();

        List<Comment> commentList = jpaQueryFactory
                .selectFrom(comment)
                .join(comment.post, post).fetchJoin()
                .where(post.id.eq(postId))
                .fetch();



        return commentList;

    }
//
    @Transactional
    public void edit(Long commentId, String comment_content) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFound());
        comment.edit(comment_content);

    }
//

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFound());
        commentRepository.delete(comment);

    }






}
