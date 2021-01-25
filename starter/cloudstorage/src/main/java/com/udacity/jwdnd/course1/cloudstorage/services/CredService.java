package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredService {

    private UserMapper userMapper;
    private CredMapper credMapper;
    private EncryptionService encryptionService;

    public CredService(UserMapper userMapper, CredMapper credMapper, EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.credMapper = credMapper;
        this.encryptionService = encryptionService;
    }

    public String editCred(CredForm credForm, String username) {
        Integer userId = userMapper.getUserId(username);
        Integer credId = Integer.valueOf(credForm.getCredentialId());

        CredForm origCred = credMapper.getCred(userId, credId);
        String existingKey = origCred.getCredKey();

        String proposedUrl = credForm.getCredUrl();
        String proposedUsername = credForm.getCredUsername();
        String proposedPassword = credForm.getCredPassword();
        String proposedEncryptedPassword = encryptionService.encryptValue(proposedPassword, existingKey);

        credForm.setUserId(userId);
        credMapper.update(userId, credId, proposedUrl, proposedUsername, proposedEncryptedPassword);
        return null;
    }

    public String addCred(CredForm credForm, String username) {
        System.out.println("Adding cred");
        Integer userId = userMapper.getUserId(username);

        credForm.setUserId(userId);
        String newSalt = encryptionService.generateSalt();
        credForm.setCredKey(newSalt);
        String encryptedPassword = encryptionService.encryptValue(credForm.getCredPassword(), newSalt);
        credForm.setCredPassword(encryptedPassword);
        credMapper.insert(credForm);
        return null;
    }


    public void deleteCred(String username, Integer credId) {

        Integer userId = userMapper.getUserId(username);
        credMapper.deleteCred(userId, credId);
    }

    public List<CredForm> getCreds(String username) {
        Integer userId = userMapper.getUserId(username);
        List<CredForm> dbCreds = credMapper.getCreds(userId);
        return dbCreds;
    }
}
