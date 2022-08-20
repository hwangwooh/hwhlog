package com.hawng.hawng.Repository;

import com.hawng.hawng.domain.Comment;
import com.hawng.hawng.domain.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository  extends JpaRepository<PostCategory, Long> {
}
