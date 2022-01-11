package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserMapper userMapper;

    public void saveFile(MultipartFile file, int userid){
        String  fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        String  fileSize = String.valueOf(file.getSize());
        try{
            File file2 = new File(fileName,contentType,fileSize,userid,file.getBytes());
            fileMapper.insert(file2);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public File getFile(Integer fileId){
        return  fileMapper.getFile(fileId);
    }

    public List<File> getFileContent(String fileName){
        return fileMapper.getFileContent(fileName);
    }

    public List<File> getFiles(Integer userid){
        return  fileMapper.getFiles(userid);
    }

    public int deleteFile(int fileId){
        return fileMapper.deleteFile(fileId);
    }
}
