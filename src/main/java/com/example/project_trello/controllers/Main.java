package com.example.project_trello.controllers;


import com.example.project_trello.entities.Comments;
import com.example.project_trello.entities.Folders;
import com.example.project_trello.entities.TaskCategories;
import com.example.project_trello.entities.Tasks;
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

    @GetMapping(value = "/allFolders")
    public String allFolders(Model model) {
        model.addAttribute("folders", foldersService.getAllFolders());
        return "allFolders";
    }

    @PostMapping(value = "/deleteFolder")
    public String deleteFolder(@RequestParam(name = "deleteFolderId") Long deleteFolderId) {
        foldersService.deleteFolder(deleteFolderId);
        return "redirect:/allFolders";
    }

    @GetMapping(value = "/folderDetails/{folder_id}")
    public String folderDetails(Model model,
                                @PathVariable(name = "folder_id") Long id) {
        Folders folder = foldersService.getFolder(id);

        if (folder != null) {
            model.addAttribute("folder", folder);
            model.addAttribute("folderCategories", foldersService.getFolderCategories(id));
            model.addAttribute("folderTasks", foldersService.getAllFolderTasks(id));
        }

        return "folderDetails";
    }

    @GetMapping(value = "/detailTask/{id}")
    public String detailTask(@PathVariable(name = "id") Long id,
                             Model model) {
        model.addAttribute("task", foldersService.getTask(id));
        model.addAttribute("comments", foldersService.getAllTaskComments(id));
        return "detailTask";
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
        foldersService.addCategoryToFolder(taskCategory, folderId);
        return "redirect:/folderDetails/" + folderId;
    }

    @PostMapping(value = "/deleteCategoryFolder")
    public String deleteCategoryFolder(@RequestParam(name = "categoryId") Long categoryId,
                                       @RequestParam(name = "folderId") Long folderId) {
        foldersService.removeCategoryToFolder(categoryId, folderId);
        return "redirect:/folderDetails/" + folderId;
    }


    @PostMapping(value = "/addTaskToFolder")
    public String addTaskToFolder(@RequestParam(name = "folderId") Long folderId,
                                  @RequestParam(name = "task_title") String task_title,
                                  @RequestParam(name = "task_description") String task_description) {
        if (folderId != null) {
            Tasks task = Tasks.builder()
                    .title(task_title)
                    .description(task_description)
                    .status(0)
                    .build();
            foldersService.addTaskToFolder(task, folderId);

            return "redirect:/folderDetails/" + folderId;
        }
        else {
            return "redirect:/";
        }
    }


    @PostMapping(value = "/editTask")
    public String editTask(@RequestParam(name = "editedTask_Id") Long editedTask_Id,
                           @RequestParam(name = "edited_title") String edited_title,
                           @RequestParam(name = "edited_description") String edited_description,
                           @RequestParam(name = "flexRadioDefault") int status){

        Folders folder = foldersService.getTask(editedTask_Id).getFolder();

        Tasks task = Tasks.builder()
                .id(editedTask_Id)
                .title(edited_title)
                .description(edited_description)
                .folder(folder)
                .status(status)
                .build();

        foldersService.updateTask(task);

        return "redirect:/folderDetails/" + folder.getId();
    }

    @PostMapping(value = "/deleteTask")
    public String deleteTask(@RequestParam(name = "deleteId") Long deleteId) {
        Folders folder = foldersService.getTask(deleteId).getFolder();

        foldersService.deleteTask(deleteId);

        return "redirect:/folderDetails/" + folder.getId();

    }

    @PostMapping(value = "/addComment")
    public String addComment(@RequestParam(name = "taskId") Long id ,
                             @RequestParam(name = "taskComment") String commentText) {
        Comments comment = new Comments();
        comment.setComment(commentText);
        foldersService.addComment(comment, id);

        return "redirect:/detailTask/" + id;
    }
}
