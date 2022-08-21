package com.hawng.hawng.Repository;

import com.hawng.hawng.domain.Comment;
import com.hawng.hawng.domain.PostCategory;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCategoryRepository  extends JpaRepository<PostCategory, Long> {

    List<PostCategory> findByName(String name);
}

