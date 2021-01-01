package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileStorageService {

    private FileMapper fileMapper;
    private UserMapper userMapper;

    public FileStorageService(FileMapper fileMapper, UserMapper userMapper) {

        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public Integer uploadFile(File file) {

        return fileMapper.insert(file);
    }

    public List<File> getFiles(String username) {

        Integer userId = userMapper.getUserId(username);
        List<File> files = fileMapper.getFiles(userId);
        return files;
    }

    public File getFile(String username, String filename) {

        Integer userId = userMapper.getUserId(username);
        File file = fileMapper.getFile(userId, filename);
        return file;
    }

    public boolean isFilenameAvailable(String username, String filename) {

        Integer userId = userMapper.getUserId(username);
        File file = fileMapper.getFile(userId, filename);
        return file == null;
    }

    public void deleteFile(String username, String filename) {

        Integer userId = userMapper.getUserId(username);
        fileMapper.deleteFile(userId, filename);
    }

}
