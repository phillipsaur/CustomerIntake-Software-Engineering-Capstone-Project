package com.phillipthai.customerintake.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.phillipthai.customerintake.entities.Customer;
import com.phillipthai.customerintake.entities.Job;

import java.util.List;

@Dao
public interface CustomerDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Customer customer);

    @Update
    void update(Customer customer);

    @Delete
    void delete(Customer customer);

    @Query("SELECT * FROM CUSTOMERS ORDER BY CUSTOMERID ASC")
    List<Customer> getAllCustomers();

    @Query("SELECT * FROM CUSTOMERS WHERE customerID = :customerID LIMIT 1")
    Customer getCustomerById(int customerID);
}
