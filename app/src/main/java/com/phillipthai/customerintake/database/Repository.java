package com.phillipthai.customerintake.database;

import android.app.Application;

import com.phillipthai.customerintake.dao.CustomerDAO;
import com.phillipthai.customerintake.dao.JobDAO;
import com.phillipthai.customerintake.entities.Customer;
import com.phillipthai.customerintake.entities.Job;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private JobDAO mJobDAO;
    private CustomerDAO mCustomerDAO;

    private List<Customer> mAllCustomers;
    private List<Job> mAllJobs;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        CustomerDatabase db = CustomerDatabase.getDatabase(application);
        mCustomerDAO = db.customerDAO();
        mJobDAO = db.jobDAO();
    }

    public List<Customer> getmAllCustomers() {
        databaseExecutor.execute(() -> {
            mAllCustomers = mCustomerDAO.getAllCustomers();
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAllCustomers;
    }

    public void insert(Customer customer) {
        databaseExecutor.execute(() -> {
            mCustomerDAO.insert(customer);
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Customer customer) {
        databaseExecutor.execute(() -> {
            mCustomerDAO.update(customer);
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Customer customer) {
        databaseExecutor.execute(() -> {
            mCustomerDAO.delete(customer);
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Job> getAllJobs() {
        databaseExecutor.execute(() -> {
            mAllJobs = mJobDAO.getAllJobs();
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAllJobs;
    }

    public void insert(Job job) {
        databaseExecutor.execute(() -> {
            mJobDAO.insert(job);
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Job job) {
        databaseExecutor.execute(() -> {
            mJobDAO.update(job);
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Job job) {
        databaseExecutor.execute(() -> {
            mJobDAO.delete(job);
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer getCustomerById(int customerID) {
        final Customer[] customer = new Customer[1];
        databaseExecutor.execute(() -> customer[0] = mCustomerDAO.getCustomerById(customerID));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return customer[0];
    }

    public Job getJobById(int jobID) {
        final Job[] job = new Job[1];
        databaseExecutor.execute(() -> job[0] = mJobDAO.getJobById(jobID));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return job[0];
    }
}
