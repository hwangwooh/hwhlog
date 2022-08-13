package com.hawng.hawng.controller;

import com.hawng.hawng.request.CommentCreate;
import com.hawng.hawng.request.PostCreate;
import com.hawng.hawng.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentController {



    private final CommentService commentService;

    @PostMapping("/posts/{postId}")
    public void post(@PathVariable Long postId,@RequestBody @Valid CommentCreate commentCreate) throws Exception {

        commentCreate.validate();//금지어
        commentService.write(commentCreate);
    }

}
