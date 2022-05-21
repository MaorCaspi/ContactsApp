package com.finalProject.contactsList.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ContactDao {

    @Query("select * from Contact where userId=:userId")
    List<Contact> getAll(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Contact... contacts);

    @Delete
    void delete(Contact contact);
}
