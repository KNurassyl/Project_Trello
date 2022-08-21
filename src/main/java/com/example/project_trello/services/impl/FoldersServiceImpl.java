package com.example.project_trello.services.impl;

import com.example.project_trello.entities.Folders;
import com.example.project_trello.repositories.FoldersRepository;
import com.example.project_trello.services.FoldersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoldersServiceImpl implements FoldersService {

    @Autowired
    private FoldersRepository foldersRepository;

    @Override
    public List<Folders> getAllFolders() {
        return foldersRepository.findAll();
    }
}
