package com.example.project_trello.repositories;

import com.example.project_trello.entities.Folders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FoldersRepository extends JpaRepository<Folders, Long> {

}
