package com.finalProject.contactsList.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.finalProject.contactsList.MyApplication;

@Database(entities = {Contact.class}, version = 1)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ContactDao contactDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.getContext(),
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}

