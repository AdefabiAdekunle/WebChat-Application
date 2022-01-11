package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getFiles(int userid);

    @Select("SELECT * FROM FILES WHERE fileId= #{fileid}")
    File getFile(int fileid);

    @Select("SELECT * FROM FILES WHERE filename= #{filename}")
    List<File> getFileContent(String filename);

    @Insert("INSERT INTO FILES (filename,contenttype,filesize,userid,filedata) VALUES(#{fileName},#{contentType},#{fileSize},#{userId},#{fileData})")
    //with the use of "useGeneratedKeys" the "Option" returns any id generated as integer
    //When we insert a new Object
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
        //it will return int as the primary key id of userId
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    int deleteFile(int fileId);
}
