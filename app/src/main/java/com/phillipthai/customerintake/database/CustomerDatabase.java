package com.phillipthai.customerintake.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.phillipthai.customerintake.dao.CustomerDAO;
import com.phillipthai.customerintake.dao.JobDAO;
import com.phillipthai.customerintake.entities.Customer;
import com.phillipthai.customerintake.entities.Job;

@Database(entities = {Customer.class, Job.class}, version = 1, exportSchema = false)
public abstract class CustomerDatabase extends RoomDatabase {
    public abstract CustomerDAO customerDAO();

    public abstract JobDAO jobDAO();

    private static volatile CustomerDatabase INSTANCE;

    static CustomerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CustomerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CustomerDatabase.class, "MyCustomerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;

    }
}
