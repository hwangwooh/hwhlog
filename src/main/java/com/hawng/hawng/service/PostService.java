package com.hawng.hawng.service;

import com.hawng.hawng.Repository.PostRepository;
import com.hawng.hawng.domain.Post;
import com.hawng.hawng.domain.PostEditor;
import com.hawng.hawng.domain.QComment;
import com.hawng.hawng.domain.QPost;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.hawng.hawng.domain.QComment.comment;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class PostService {


    private  final  PostRepository postRepository;
    private final JPAQueryFactory jpaQueryFactory;


    public void write(PostCreate postCreate) {
        //Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        Post post = Post.builder().title(postCreate.getTitle()).content(postCreate.getContent()).build();

        postRepository.save(post);



    }


    public PostResponse get(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());
        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .dateTime(post.getDateTime())
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
