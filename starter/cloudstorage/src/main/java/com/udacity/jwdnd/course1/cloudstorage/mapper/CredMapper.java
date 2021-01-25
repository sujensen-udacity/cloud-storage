package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.CredForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<CredForm> getCreds(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId} and credentialid = #{credid}")
    CredForm getCred(Integer userId, Integer credid);

    @Select("SELECT COUNT(*) FROM CREDENTIALS")
    int getCount();

    @Select("SELECT credentialid FROM CREDENTIALS")
    List<Integer> getCredIds();

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES " +
            "(#{credUrl}, #{credUsername}, #{credKey}, #{credPassword}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(CredForm credForm);

    @Update("UPDATE CREDENTIALS SET url=#{credUrl}, username=#{credUsername}, password=#{credPassword} WHERE userid=#{userId} and credentialid=#{credId}")
    int update(Integer userId, Integer credId, String credUrl, String credUsername, String credPassword);

    @Delete("DELETE FROM CREDENTIALS where userid = #{userId} and credentialid = #{credId}")
    void deleteCred(Integer userId, Integer credId);

}
