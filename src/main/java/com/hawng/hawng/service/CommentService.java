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
    private EntityManager em;
    public void write( CommentCreate commentCreate) {
        Comment comment = Comment.builder().post(commentCreate.getPost()).comment_content(commentCreate.getContent()).
                build();

        commentRepository.save(comment);
    }

    public List<Comment> getList(Long postId) {
        //return postRepository.findAll().stream().map(post -> new PostResponse(post)).collect(Collectors.toList());
        // Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC,"id"));


        List<Comment> commentList = em.createQuery("select c from Comment c" +
                        " join fetch c.post p" +
                        " where p.id = "+postId,
                Comment.class).getResultList();
        return commentList;

    }
//
//    @Transactional
//    public PostResponse edit(Long id, PostEdit postEdit) {
//        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFound());
//
//        PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();
//
//        PostEditor build = postEditorBuilder.title(postEdit.getTitle()).content(postEdit.getContent()).build();
//        post.edit(build);
//
//        PostResponse postResponse = new PostResponse(post);;
//        return postResponse;
//
//    }
//
//
//    public void delete(Long id) {
//
//        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFound());
//        postRepository.delete(post);
//
//    }




}
