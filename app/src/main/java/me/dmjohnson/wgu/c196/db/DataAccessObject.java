package me.dmjohnson.wgu.c196.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataAccessObject {
    @Query("SELECT * from Term")
    LiveData<List<Term>> getTerms();

    @Insert
    void addTerms(List<Term> terms);
}
