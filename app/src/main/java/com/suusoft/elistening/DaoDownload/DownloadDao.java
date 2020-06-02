package com.suusoft.elistening.DaoDownload;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DownloadDao {

    @Insert
    public void addDataDownload(DownloadList downloadList);

    @Query("select * from download")
    public List<DownloadList> getDownloadData();

    @Query("SELECT EXISTS (SELECT * FROM download WHERE id=:id)")
    public int isDownload(int id);

    @Delete
    public void deleteDownload(DownloadList downloadList);

}
