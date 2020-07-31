package com.example.roomdemo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface TextDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word entry);
    @Query("DELETE FROM word_table")
    void deleteAll();
    @Delete
    void deleteEntry(Word entry);
    @Query("SELECT * from word_table LIMIT 1")
    Word[] getEntry();
    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();
}
