package com.hawng.hawng.controller;

import com.hawng.hawng.exception.InvalidRequest;
import com.hawng.hawng.request.PostCreate;
import com.hawng.hawng.request.PostEdit;
import com.hawng.hawng.request.PostSearch;
import com.hawng.hawng.service.PostService;
import com.hawng.hawng.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate  request) throws Exception {

        request.validate();
        postService.write(request);


    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        PostResponse response = postService.get(postId);
        return response;
    }


    @GetMapping("/posts")// 전채 조회
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {

        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")//수정
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit) {
            postService.edit(postId, postEdit);
    }

    @DeleteMapping("/posts/{postId}")// 삭제
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }







}
