package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserMapper userMapper;

    public void saveFile(MultipartFile file, int userid) throws IOException {
        String  fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        String  fileSize = String.valueOf(file.getSize());
        InputStream fis = file.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = fis.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] fileData = buffer.toByteArray();
        //try{
            File file2 = new File(0,fileName,contentType,fileSize,userid,fileData);
            fileMapper.insert(file2);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }

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
