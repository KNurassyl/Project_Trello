package com.example.project_trello.repositories;

import com.example.project_trello.entities.TaskCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCategoriesRepository extends JpaRepository<TaskCategories, Long> {
}
