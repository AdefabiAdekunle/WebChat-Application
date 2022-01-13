package com.example.WebChatApplication.mapper;

import com.example.WebChatApplication.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, salt,password, firstname, lastname) VALUES(#{username},#{salt},#{password},#{firstName},#{lastName})")
    //with the use of "useGeneratedKeys" the "Option" returns any id generated as integer
    //When we insert a new Object
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    //it will return int as the primary key id of userId
    int insert(User user);
}
