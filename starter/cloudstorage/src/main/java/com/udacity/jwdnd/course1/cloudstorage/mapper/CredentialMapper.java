package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentials(Integer userid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredential(Integer credentialId);


    @Insert("INSERT INTO CREDENTIALS (url,username,key,password,userid) VALUES(#{url},#{username},#{key},#{password},#{userId})")
    //with the use of "useGeneratedKeys" the "Option" returns any id generated as integer
    //When we insert a new Object
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
        //it will return int as the primary key id of userId
    int insert(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId=#{credentialId}")
    int deleteCredential(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url=#{url},username=#{username},key=#{key},password=#{password} WHERE credentialId=#{credentialId}")
    void updateCredential(String url,String username,String key,String password, Integer credentialId);
}
