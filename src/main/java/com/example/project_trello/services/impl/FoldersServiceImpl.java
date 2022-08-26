package com.example.project_trello.services.impl;

import com.example.project_trello.entities.Comments;
import com.example.project_trello.entities.Folders;
import com.example.project_trello.entities.TaskCategories;
import com.example.project_trello.entities.Tasks;
import com.example.project_trello.repositories.CommentsRepository;
import com.example.project_trello.repositories.FoldersRepository;
import com.example.project_trello.repositories.TaskCategoriesRepository;
import com.example.project_trello.repositories.TasksRepository;
import com.example.project_trello.services.FoldersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoldersServiceImpl implements FoldersService {

    @Autowired
    private FoldersRepository foldersRepository;

    @Autowired
    private TaskCategoriesRepository taskCategoriesRepository;

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public List<Folders> getAllFolders() {
        return foldersRepository.findAll();
    }

    @Override
    public List<TaskCategories> getAllTaskCategories() {
        return taskCategoriesRepository.findAll();
    }

    @Override
    public Folders addFolder(Folders folder) {
        return foldersRepository.save(folder);
    }

    @Override
    public Folders updateFolder(Folders folder) {
        return foldersRepository.save(folder);
    }

    @Override
    public Folders getFolder(Long id) {
        return foldersRepository.getReferenceById(id);
    }

    @Override
    public List<TaskCategories> getFolderCategories(Long id) {
        Folders folder = getFolder(id);
        return folder.getCategories();
    }

    @Override
    public String addCategoryToFolder(TaskCategories category, Long folderId) {
        ArrayList<TaskCategories> allTaskCategories = (ArrayList<TaskCategories>) getAllTaskCategories();
        boolean check = false;
        String text = "";

        Folders folder = getFolder(folderId);

        for (TaskCategories taskCategory : allTaskCategories) {
            if (taskCategory.getName().equals(category.getName())) {
                check = true;
                category.setId(taskCategory.getId());
                if (folder.getCategories().contains(category)) {
                    text = "contains";
                    updateFolder(folder);
                } else {
                    category.setId(taskCategory.getId());
                    folder.getCategories().add(category);
                    updateFolder(folder);
                    break;
                }
            }
        }

        if (!check) {
            taskCategoriesRepository.save(category);
            folder.getCategories().add(category);
            updateFolder(folder);
        }

        return text;

    }

    @Override
    public void removeCategoryToFolder(Long categoryId, Long folderId) {
        TaskCategories taskCategory = getCategory(categoryId);

        Folders folder = getFolder(folderId);
        if (folder != null) {
            folder.getCategories().remove(taskCategory);
            updateFolder(folder);
        }
    }

    @Override
    public TaskCategories getCategory(Long id) {
        return taskCategoriesRepository.getReferenceById(id);
    }

    @Override
    public void deleteFolder(Long id) {
        Folders folder = getFolder(id);

        if (folder != null) {
            deleteTasksByFolderId(id);
            folder.getCategories().clear();
            updateFolder(folder);
        }

        if (folder != null && folder.getCategories().isEmpty()) {
            foldersRepository.deleteById(id);
        }
    }

    @Override
    public List<Tasks> getAllFolderTasks(Long folderId) {
        return tasksRepository.findAllByFolder_Id(folderId);
    }

    @Override
    public Tasks addTaskToFolder(Tasks task, Long folderId) {
        Folders folder = getFolder(folderId);

        if (folder != null) {
            task.setFolder(folder);
        }
        return tasksRepository.save(task);
    }

    @Override
    public void deleteTasksByFolderId(Long folderId) {
        List<Tasks> tasks = tasksRepository.findAllByFolder_Id(folderId);
        for (Tasks task : tasks) {
            deleteTask(task.getId());
        }
    }

    @Override
    public void deleteTask(Long taskId) {
        tasksRepository.deleteById(taskId);
    }

    @Override
    public Tasks getTask(Long id) {
        return tasksRepository.getReferenceById(id);
    }

    @Override
    public Tasks updateTask(Tasks task) {
        return tasksRepository.save(task);
    }

    @Override
    public List<Comments> getAllTaskComments(Long taskId) {
        return commentsRepository.findAllByTask_Id(taskId);
    }

    @Override
    public Comments addComment(Comments comment, Long taskId) {
        comment.setTask(getTask(taskId));
        return commentsRepository.save(comment);
    }
}
