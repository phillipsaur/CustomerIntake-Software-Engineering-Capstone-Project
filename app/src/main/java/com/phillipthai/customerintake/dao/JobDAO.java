package com.phillipthai.customerintake.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.phillipthai.customerintake.entities.Job;

import java.util.List;

@Dao
public interface JobDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Job job);

    @Update
    void update(Job job);

    @Delete
    void delete(Job job);

    @Query("SELECT * FROM JOBS ORDER BY JOBID ASC")
    List<Job> getAllJobs();

    @Query("SELECT * FROM JOBS WHERE CUSTOMERID= :cust ORDER BY JOBID ASC")
    List<Job> getAssociatedJobs(int cust);

    @Query("SELECT * FROM JOBS WHERE jobID = :jobID LIMIT 1")
    Job getJobById(int jobID);
}
