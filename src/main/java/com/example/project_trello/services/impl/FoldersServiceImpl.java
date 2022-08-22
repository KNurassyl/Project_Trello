package com.example.project_trello.services.impl;

import com.example.project_trello.entities.Folders;
import com.example.project_trello.entities.TaskCategories;
import com.example.project_trello.repositories.FoldersRepository;
import com.example.project_trello.repositories.TaskCategoriesRepository;
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
        return  folder.getCategories();
    }

    @Override
    public void addCategoryToFolder(TaskCategories category, Long folderId) {
        ArrayList<TaskCategories> allTaskCategories = (ArrayList<TaskCategories>) getAllTaskCategories();
        boolean check = true;

        Folders folder = getFolder(folderId);

        for (TaskCategories taskCategory : allTaskCategories) {
            if (taskCategory.getName().equals(category.getName())) {
                if (!folder.getCategories().contains(category)) {
                    category.setId(taskCategory.getId());
                    folder.getCategories().add(category);
                    updateFolder(folder);
                    break;
                }
            } else {
                check = false;
            }
        }

        if (!check) {
            taskCategoriesRepository.save(category);
            folder.getCategories().add(category);
            updateFolder(folder);
        }

    }

    @Override
    public void removeCategoryToFolder(Long categoryId, Long folderId) {
        TaskCategories taskCategory = getCategory(categoryId);

        Folders folder = getFolder(folderId);
        if(folder != null) {
            folder.getCategories().remove(taskCategory);
            updateFolder(folder);
        }
    }

    @Override
    public TaskCategories getCategory(Long id) {
        return taskCategoriesRepository.getReferenceById(id);
    }
}
