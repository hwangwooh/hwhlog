package com.hawng.hawng.controller;

import com.hawng.hawng.Repository.CommentRepository;
import com.hawng.hawng.Repository.PostRepository;
import com.hawng.hawng.request.CommentCreate;
import com.hawng.hawng.request.CommentEdit;
import com.hawng.hawng.request.PostCreate;
import com.hawng.hawng.request.PostEdit;
import com.hawng.hawng.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentController {



    private final CommentService commentService;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @PostMapping("/posts/{postId}")
    public void post(@PathVariable Long postId,
                     @RequestBody @Valid CommentCreate commentCreate) throws Exception {

        commentCreate.validate();//금지어
        commentService.write(commentCreate);
    }
    @DeleteMapping("/posts/{postId}/{commentId}")// 삭제 //수정해야 할듯 ???
    public String delete(@PathVariable Long postId,
                         @PathVariable Long commentId) {
        commentService.delete(commentId);
        return "/posts/" + postId;
    }


    @PatchMapping("/posts/{postId}/comment/{commentId}")// 아직 미완성
    public void edit(@PathVariable("postId") Long postId,
                     @PathVariable("commentId") Long commentId ,
                     @RequestBody @Valid CommentEdit commentEdit) {




        System.out.println("commentId = " + commentEdit);
        commentService.edit(commentId, commentEdit.getComment_content());

    }






}
