package com.example.project_trello.repositories;

import com.example.project_trello.entities.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks, Long> {

    List<Tasks> findAllByFolder_Id(Long folderId);

}
