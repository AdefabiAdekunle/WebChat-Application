package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotes(int userid);


    @Insert("INSERT INTO NOTES (notetitle,notedescription,userid) VALUES(#{noteTitle},#{noteDescription},#{userId})")
    //with the use of "useGeneratedKeys" the "Option" returns any id generated as integer
    //When we insert a new Object
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
        //it will return int as the primary key id of userId
    int insert(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId=#{noteId}")
    int deleteNote(int noteId);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{noteId}")
    void updateNote(String noteTitle,String noteDescription, int noteId);


}
