package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;

    public int saveFile(Note note){
        try{
            System.out.println(note.getNoteTitle());
            System.out.println(note.getNoteDescription());
            return noteMapper.insert(note);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public List<Note> getNotes(int userid){
        return noteMapper.getNotes(userid);
    }

    public void deleteNote(int noteId){
        noteMapper.deleteNote(noteId);
    }

    public void updateNote(String noteTitle,String noteDescription, int noteId){
        noteMapper.updateNote(noteTitle,noteDescription,noteId);
    }


}
