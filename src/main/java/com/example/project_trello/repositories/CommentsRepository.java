package com.example.project_trello.repositories;

import com.example.project_trello.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    List<Comments> findAllByTask_Id(Long taskId);
}
