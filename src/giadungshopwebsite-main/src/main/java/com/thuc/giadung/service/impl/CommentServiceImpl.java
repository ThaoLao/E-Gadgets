package com.thuc.giadung.service.impl;

import com.thuc.giadung.entity.Comment;
import com.thuc.giadung.repository.BlogRepository;
import com.thuc.giadung.repository.CommentRepository;
import com.thuc.giadung.service.CommentService;
import com.thuc.giadung.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;
    UserRepository userRepository;
    BlogRepository blogRepository;

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public Comment saveComment(String content, Long blogId, Long userId) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(userRepository.findById(userId).orElse(null));
        comment.setBlog(blogRepository.findById(blogId).orElse(null));
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByBlogIdOrderByCreatedAtDesc(Long blogId) {
        return commentRepository.findByBlogIdOrderByCreatedAtDesc(blogId);
    }
}
