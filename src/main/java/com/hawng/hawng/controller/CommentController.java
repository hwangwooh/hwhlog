package com.hawng.hawng.controller;

import com.hawng.hawng.Repository.CommentRepository;
import com.hawng.hawng.Repository.PostRepository;
import com.hawng.hawng.domain.Comment;
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
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentController {



    private final CommentService commentService;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @PostMapping("/comment/{postId}") // 쓰기
    public void post(@PathVariable Long postId,
                     @RequestBody @Valid CommentCreate commentCreate) throws Exception {

        commentCreate.validate();//금지어
        commentService.write(postId,commentCreate);
    }
    @GetMapping("/comment/{postId}")
    public List<Comment> getList(@PathVariable Long postId){

        List<Comment> list = commentService.getList(postId);
        return list;
    }


    @DeleteMapping("/comment/{commentId}")// 삭제
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);

    }


    @PatchMapping("/comment/{commentId}")// 수정
    public void edit(@PathVariable("commentId") Long commentId ,
                     @RequestBody @Valid CommentEdit commentEdit) {

      //  System.out.println("commentId = " + commentEdit);
        commentService.edit(commentId, commentEdit.getComment_content());

    }






}
