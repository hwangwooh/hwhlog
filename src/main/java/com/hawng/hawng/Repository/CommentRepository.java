package com.hawng.hawng.Repository;

import com.hawng.hawng.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentRepository extends JpaRepository<Comment, Long> {
}
