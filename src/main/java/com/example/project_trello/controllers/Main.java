package com.example.project_trello.controllers;


import com.example.project_trello.entities.Folders;
import com.example.project_trello.entities.TaskCategories;
import com.example.project_trello.repositories.TaskCategoriesRepository;
import com.example.project_trello.services.FoldersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Main {
    @Autowired
    private FoldersService foldersService;

    @GetMapping(value = "/")
    public String mainPage(Model model) {
        model.addAttribute("folders", foldersService.getAllFolders());
        return "mainPage";
    }

    @GetMapping(value = "/folderDetails/{folder_id}")
    public String folderDetails(Model model,
                                @PathVariable(name = "folder_id") Long id) {
        Folders folder = foldersService.getFolder(id);

        if(folder != null) {
            model.addAttribute("folder", folder);
            model.addAttribute("folderCategories", foldersService.getFolderCategories(id));
        }

        return "folderDetails";
    }

    @PostMapping(value = "/addFolder")
    public String addFolder(@RequestParam(name = "folder_name") String folderName) {
        if (folderName != null) {
            Folders folder = Folders.builder()
                    .name(folderName)
                    .build();

            foldersService.addFolder(folder);
        }
        return "redirect:/";
    }

    @PostMapping(value = "/addFolderCategory")
    public String addFolderCategory(@RequestParam(name = "category_name") String categoryName,
                                    @RequestParam(name = "folderId") Long folderId) {
        TaskCategories taskCategory = TaskCategories.builder()
                                        .name(categoryName)
                                        .build();
        foldersService.addCategoryToFolder(taskCategory,folderId);
        return "redirect:/folderDetails/"+folderId;
    }

    @PostMapping(value = "/deleteCategoryFolder")
    public String deleteCategoryFolder(@RequestParam(name = "categoryId") Long categoryId,
                                    @RequestParam(name = "folderId") Long folderId) {
        foldersService.removeCategoryToFolder(categoryId, folderId);
        return "redirect:/folderDetails/"+folderId;
    }

}
