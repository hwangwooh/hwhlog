package com.hawng.hawng.service;

import com.hawng.hawng.Repository.PostCategoryRepository;
import com.hawng.hawng.Repository.PostRepository;
import com.hawng.hawng.domain.*;
import com.hawng.hawng.exception.PostNotFound;
import com.hawng.hawng.request.PostCreate;
import com.hawng.hawng.request.PostEdit;
import com.hawng.hawng.request.PostSearch;
import com.hawng.hawng.response.PostResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class PostService {


    private  final  PostRepository postRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final PostCategoryRepository postCategoryRepository;


    public void write(PostCreate postCreate) {
        //Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        List<PostCategory> byName = postCategoryRepository.findByName(postCreate.getContent());

        // 임시
        PostCategory postCategory;
        if (postCreate.getContent() == "개발") postCategory = postCategoryRepository.findById(1L).orElseThrow();
        else postCategory = postCategoryRepository.findById(2L).orElseThrow();


        Post post = Post.builder().title(postCreate
                .getTitle())
                .content(postCreate.getContent())
                .postCategory(postCategory)
                .build();

        postRepository.save(post);



    }


    public PostResponse get(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .dateTime(post.getDateTime())
                .category(post.getPostCategory().getName())
                .build();
        return response;
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        //return postRepository.findAll().stream().map(post -> new PostResponse(post)).collect(Collectors.toList());
       // Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC,"id"));

        return postRepository.getList(postSearch).stream().map(post -> PostResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .dateTime(post.getDateTime())
            .category(post.getPostCategory().getName())
            .build())
            .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFound());

        PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();

        PostEditor build = postEditorBuilder.title(postEdit.getTitle()).content(postEdit.getContent()).build();
        post.edit(build);

        PostResponse postResponse = new PostResponse(post);;
        return postResponse;

    }


    public void delete(Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFound());
        postRepository.deleteById(post.getId());
        //jpaQueryFactory.delete(comment).where(comment.post.id.eq(id)).execute();// c


    }
}
