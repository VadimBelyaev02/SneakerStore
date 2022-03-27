package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findAllByProductId(UUID productId);

}
