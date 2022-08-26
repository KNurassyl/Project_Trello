package com.example.project_trello.services;

import com.example.project_trello.entities.Comments;
import com.example.project_trello.entities.Folders;
import com.example.project_trello.entities.TaskCategories;
import com.example.project_trello.entities.Tasks;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FoldersService {
    List<Folders> getAllFolders();

    List<TaskCategories> getAllTaskCategories();

    Folders addFolder(Folders folder);

    Folders updateFolder(Folders folder);

    String addCategoryToFolder(TaskCategories category, Long folderId);

    void deleteFolder(Long id);

    TaskCategories getCategory(Long id);

    void removeCategoryToFolder(Long categoryId, Long folderId);

    Folders getFolder(Long id);

    List<TaskCategories> getFolderCategories(Long id);

    List<Tasks> getAllFolderTasks(Long folderId);

    Tasks addTaskToFolder(Tasks task, Long folderId);

    Tasks getTask(Long id);

    void deleteTasksByFolderId(Long folderId);

    void deleteTask(Long taskId);

    Tasks updateTask(Tasks task);

    List<Comments> getAllTaskComments(Long taskId);

    Comments addComment(Comments comment, Long taskId);
}
