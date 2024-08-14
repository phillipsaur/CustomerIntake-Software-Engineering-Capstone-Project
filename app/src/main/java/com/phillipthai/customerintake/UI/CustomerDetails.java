package com.phillipthai.customerintake.UI;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.phillipthai.customerintake.R;
import com.phillipthai.customerintake.database.Repository;
import com.phillipthai.customerintake.entities.Customer;
import com.phillipthai.customerintake.entities.Job;

import java.util.ArrayList;
import java.util.List;

public class CustomerDetails extends AppCompatActivity {

    String firstName;
    String lastName;
    int customerID;
    String phoneNumber;
    EditText editPhone;
    EditText editFirst;
    EditText editLast;
    Repository repository;
    Customer currentCustomer;
    int numJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_details);

        FloatingActionButton fab = findViewById(R.id.jobFAB);
        Button saveButton = findViewById(R.id.customersave);
        Button deleteButton = findViewById(R.id.customerdelete);
        editFirst = findViewById(R.id.customerfirst);
        editLast = findViewById(R.id.customerlast);
        editPhone = findViewById(R.id.phonenumber);
        repository = new Repository(getApplication());

        customerID = getIntent().getIntExtra("id",-1);
        firstName = getIntent().getStringExtra("first");
        lastName = getIntent().getStringExtra("last");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        editFirst.setText(firstName);
        editLast.setText(lastName);

        editPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            editPhone.setText(formatPhoneNumber(phoneNumber));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDetails.this, JobDetails.class);
                intent.putExtra("customerID", customerID);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.jobRV);
        final JobAdapter jobAdapter = new JobAdapter(this);
        recyclerView.setAdapter(jobAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Job> filteredJobs = new ArrayList<>();
        for (Job j : repository.getAllJobs()) {
            if (j.getCustomerID() == customerID) {
                filteredJobs.add(j);
            }
        }
        jobAdapter.setJobs(filteredJobs);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer customer;
                if (customerID == -1) {
                    if (repository.getmAllCustomers().isEmpty()) {
                        customerID = 1;
                    } else {
                        customerID = repository.getmAllCustomers()
                                .get(repository.getmAllCustomers().size() - 1)
                                .getCustomerID() + 1;
                    }
                    customer = new Customer(customerID,
                            editFirst.getText().toString(),
                            editLast.getText().toString(),
                            editPhone.getText().toString());
                    repository.insert(customer);
                } else {
                    customer = new Customer(customerID,
                            editFirst.getText().toString(),
                            editLast.getText().toString(),
                            editPhone.getText().toString());
                    repository.update(customer);
                }

                Intent intent = new Intent(CustomerDetails.this, CustomerList.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Customer cust : repository.getmAllCustomers()) {
                    if (cust.getCustomerID() == customerID) {
                        currentCustomer = cust;
                    }
                }

                numJobs = 0;
                for (Job job : repository.getAllJobs()) {
                    if (job.getCustomerID() == customerID) {
                        ++numJobs;
                    }
                }

                if (numJobs == 0) {
                    repository.delete(currentCustomer);
                    Toast.makeText(CustomerDetails.this, currentCustomer.getFirstName()
                            + " " + currentCustomer.getLastName() + " was deleted."
                            , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CustomerDetails.this, CustomerList.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CustomerDetails.this,"Cannot delete customer. Please delete associated job first.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String formatPhoneNumber(String phoneNumber) {
        String cleanNumber = phoneNumber.replaceAll("[^\\d]", "");
        if (cleanNumber.length() == 10) {
            return String.format("(%s) %s-%s",
                    cleanNumber.substring(0,3),
                    cleanNumber.substring(3,6),
                    cleanNumber.substring(6,10));
        } else {
            return phoneNumber;
        }
    }

    private class PhoneNumberFormattingTextWatcher implements TextWatcher {

        private boolean isFormatting;
        private boolean deletingHyphen;
        private int hyphenStart;
        private boolean deletingBackward;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Check if a hyphen is being deleted
            deletingHyphen = count == 1 && after == 0 && s.charAt(start) == '-';
            hyphenStart = start;
            deletingBackward = count > 0 && after == 0;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // No implementation needed
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (isFormatting) {
                return;
            }

            isFormatting = true;

            String phone = s.toString().replaceAll("[^\\d]", "");

            if (phone.length() == 0) {
                s.clear();
                isFormatting = false;
                return;
            }

            StringBuilder formatted = new StringBuilder();

            if (phone.length() > 3) {
                formatted.append("(").append(phone.substring(0, 3)).append(") ");
                phone = phone.substring(3);
            }
            if (phone.length() > 3) {
                formatted.append(phone.substring(0, 3)).append("-");
                phone = phone.substring(3);
            }
            formatted.append(phone);

            s.replace(0, s.length(), formatted.toString());

            isFormatting = false;
        }
    }

}