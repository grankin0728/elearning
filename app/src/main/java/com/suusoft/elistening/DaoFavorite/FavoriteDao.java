package com.suusoft.elistening.DaoFavorite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.suusoft.elistening.model.modelLesson.Lesson;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FavoriteDao {


    @Insert
    public void addData(Lesson favoriteList);

    @Query("select * from favorite")
    public List<Lesson> getFavoriteData();

    @Query("SELECT EXISTS (SELECT * FROM favorite WHERE id=:id)")
    public int isFavorite(int id);

    @Delete
    public void delete(Lesson favoriteList);




}
