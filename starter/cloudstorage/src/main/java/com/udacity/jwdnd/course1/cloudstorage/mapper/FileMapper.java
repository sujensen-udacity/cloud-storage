package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (fileId, filename, contenttype, filesize, userid, filedata) VALUES " +
            "(#{fileId}, #{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES where userid = #{userid}")
    List<File> getFiles(Integer userid);

    @Select("SELECT * FROM FILES where userid = #{userid} and filename = #{filename}")
    File getFile(Integer userid, String filename);

    @Delete("DELETE FROM FILES where userid = #{userId} and filename = #{filename}")
    void deleteFile(Integer userId, String filename);
}
