package com.phillipthai.customerintake.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.phillipthai.customerintake.R;
import com.phillipthai.customerintake.database.Repository;
import com.phillipthai.customerintake.entities.Customer;
import com.phillipthai.customerintake.entities.Job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JobDetails extends AppCompatActivity {
    String job;
    double price;
    int customerID;
    int jobID;
    Job currentJob;
    EditText editName;
    EditText editPrice;
    Repository repository;
    TextView editJobDate;
    DatePickerDialog.OnDateSetListener jDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_job_details);
        repository = new Repository(getApplication());
        Button saveButton = findViewById(R.id.jobsave);
        Button deleteButton = findViewById(R.id.jobdelete);
        job = getIntent().getStringExtra("name");
        editName = findViewById(R.id.jobname);
        editName.setText(job);

        Log.d("JobDetails", "Price received: " + price);

        price = getIntent().getDoubleExtra("price", 0.00);

        Log.d("JobDetails", "Price received: " + price);

        editPrice = findViewById(R.id.jobprice);
        editPrice.setText(String.valueOf(price));
        editJobDate = findViewById(R.id.jobDate);
        customerID = getIntent().getIntExtra("customerID", -1);
        jobID = getIntent().getIntExtra("id", -1);

        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.addAll(repository.getmAllCustomers());
        ArrayList<Integer> customerIdList = new ArrayList<>();

        if (jobID != -1) {
            currentJob = repository.getJobById(jobID);
            editJobDate.setText(sdf.format(currentJob.getJobDate()));
        }

        jDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, day);

                updateLabelStart();
            }
        };

        editJobDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = editJobDate.getText().toString();
                if (info.equals("")) info = "08/01/24";

                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                new DatePickerDialog(JobDetails.this, jDate,
                        myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                List<Job> jobsForCustomer = repository.getJobsForCustomer(customerID);
                if (jobsForCustomer != null && !jobsForCustomer.isEmpty() && jobID == -1) {
                    Toast.makeText(JobDetails.this, "This customer already has a job. Cannot add another job.", Toast.LENGTH_LONG).show();
                    return;
                }
                String jobDateStr = editJobDate.getText().toString();
                Date jobStartDate = null;

                try {
                    jobStartDate = sdf.parse(jobDateStr);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                Job job;
                if (jobID == -1) {
                    if (repository.getAllJobs().size() == 0) {
                        jobID = 1;
                    } else {
                        jobID = repository.getAllJobs()
                                .get(repository.getAllJobs().size() - 1)
                                .getJobID() + 1;
                    }
                    job = new Job(jobID,
                            editName.getText().toString(),
                            Double.parseDouble(editPrice.getText().toString()),
                            jobStartDate,
                            customerID);
                    repository.insert(job);
                } else {
                    job = new Job(jobID,
                            editName.getText().toString(),
                            Double.parseDouble(editPrice.getText().toString()),
                            jobStartDate,
                            customerID);
                    repository.update(job);
                }

                Intent intent = new Intent(JobDetails.this, CustomerList.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(view -> {
            if (jobID != -1) {
                Job jobToDelete = repository.getJobById(jobID);

                if (jobToDelete != null) {
                    repository.delete(jobToDelete);
                    Toast.makeText(this, customerID + " " + editName.getText().toString() + " " + jobID + " deleted", Toast.LENGTH_SHORT).show();
                }
            }
            startActivity(new Intent(JobDetails.this, CustomerList.class));
        });

    }

    private void updateLabelStart() {
        editJobDate.setText(sdf.format(myCalendarStart.getTime()));
    }
}