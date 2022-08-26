package com.hawng.hawng.Repository;

import com.hawng.hawng.domain.Comment;
import com.hawng.hawng.domain.Post;
import com.hawng.hawng.request.PostSearch;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> getList(Long postSearch);




 }
