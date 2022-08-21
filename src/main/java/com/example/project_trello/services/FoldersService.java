package com.example.project_trello.services;

import com.example.project_trello.entities.Folders;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FoldersService {
    List<Folders> getAllFolders();
}
