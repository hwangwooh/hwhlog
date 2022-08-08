package com.hawng.hawng.Repository;

import com.hawng.hawng.domain.Post;
import com.hawng.hawng.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);


 }
