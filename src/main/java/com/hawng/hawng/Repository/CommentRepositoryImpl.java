package com.hawng.hawng.Repository;

import com.hawng.hawng.domain.Comment;
import com.hawng.hawng.domain.Post;
import com.hawng.hawng.domain.QPost;
import com.hawng.hawng.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hawng.hawng.domain.QComment.comment;
import static com.hawng.hawng.domain.QPost.post;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> getList(Long postId) {
        List<Comment> commentList = jpaQueryFactory
                .selectFrom(comment)
                .join(comment.post, post).fetchJoin()
                .where(post.id.eq(postId))
                .fetch();
        return commentList;
    }







}
