package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<NoteForm> getNotes(Integer userId);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId} and noteid = #{noteid}")
    NoteForm getNote(Integer userId, Integer noteid);

    @Select("SELECT notetitle FROM NOTES WHERE userid = #{userid}")
    List<String> getNoteTitles(Integer userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES " +
            "(#{noteTitle}, #{noteText}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(NoteForm noteForm);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteText} WHERE userid=#{userId} and noteid=#{noteId}")
    int update(Integer userId, Integer noteId, String noteTitle, String noteText);

    @Delete("DELETE FROM NOTES where userid = #{userId} and noteid = #{noteId}")
    void deleteNote(Integer userId, Integer noteId);

}
