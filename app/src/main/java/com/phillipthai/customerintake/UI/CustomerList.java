package com.phillipthai.customerintake.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.phillipthai.customerintake.R;
import com.phillipthai.customerintake.database.Repository;
import com.phillipthai.customerintake.entities.Customer;

import java.util.List;

public class CustomerList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_list);

        FloatingActionButton fab = findViewById(R.id.customerFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerList.this, CustomerDetails.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.customerRV);
        repository = new Repository(getApplication());
        List<Customer> allCustomers = repository.getmAllCustomers();
        final CustomerAdapter customerAdapter = new CustomerAdapter(this);
        recyclerView.setAdapter(customerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customerAdapter.setCustomers(allCustomers);
    }
}