package com.example.project_trello.services;

import com.example.project_trello.entities.Folders;
import com.example.project_trello.entities.TaskCategories;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FoldersService {
    List<Folders> getAllFolders();

    List<TaskCategories> getAllTaskCategories();

    Folders addFolder(Folders folder);

    Folders updateFolder(Folders folder);

    void addCategoryToFolder(TaskCategories category, Long folderId);

    TaskCategories getCategory(Long id);

    void removeCategoryToFolder(Long categoryId, Long folderId);

    Folders getFolder(Long id);

    List<TaskCategories> getFolderCategories(Long id);

}
