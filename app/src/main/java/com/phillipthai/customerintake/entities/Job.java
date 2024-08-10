package com.phillipthai.customerintake.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "jobs")
public class Job {
    @PrimaryKey(autoGenerate = true)
    private int jobID;
    private String jobName;
    private double price;
    private int customerID;

    public Job(int jobID, String jobName, double price, int customerID) {
        this.jobID = jobID;
        this.jobName = jobName;
        this.price = price;
        this.customerID = customerID;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
