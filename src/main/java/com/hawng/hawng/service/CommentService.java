package com.hawng.hawng.service;


import com.hawng.hawng.Repository.CommentRepository;
import com.hawng.hawng.domain.Comment;
import com.hawng.hawng.domain.Post;
import com.hawng.hawng.domain.PostEditor;
import com.hawng.hawng.exception.PostNotFound;
import com.hawng.hawng.request.CommentCreate;
import com.hawng.hawng.request.PostCreate;
import com.hawng.hawng.request.PostEdit;
import com.hawng.hawng.request.PostSearch;
import com.hawng.hawng.response.PostResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final EntityManager em;



    public Comment write(CommentCreate commentCreate) {
        Comment comment = Comment.builder().post(commentCreate.getPost()).comment_content(commentCreate.getContent()).
                build();

        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> getList(Long postId) {

        List<Comment> commentList = em.createQuery("select c from Comment c" +
                        " join fetch c.post p" +
                        " where p.id = "+postId,
                Comment.class).getResultList();


        return commentList;

    }
//
    @Transactional
    public void edit(Long id, String comment_content) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new PostNotFound());


        comment.edit(comment_content);


    }
//

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("글이 존제 하지 않습니다" ));
        commentRepository.delete(comment);

    }




}
