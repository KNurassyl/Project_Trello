package com.example.project_trello.controllers;


import com.example.project_trello.services.FoldersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Main {
    @Autowired
    private FoldersService foldersService;

    @GetMapping(value = "/")
    public String mainPage(Model model) {
        model.addAttribute("folders", foldersService.getAllFolders());
        return "mainPage";
    }
}
