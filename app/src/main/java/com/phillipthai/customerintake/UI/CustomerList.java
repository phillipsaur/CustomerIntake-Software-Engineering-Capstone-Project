package com.phillipthai.customerintake.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.phillipthai.customerintake.R;
import com.phillipthai.customerintake.database.Repository;
import com.phillipthai.customerintake.entities.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerList extends AppCompatActivity {

    private Repository repository;
    private SearchView searchView;
    private List<Customer> allCustomers;
    private CustomerAdapter customerAdapter;

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
        allCustomers = repository.getmAllCustomers();
        customerAdapter = new CustomerAdapter(this);
        recyclerView.setAdapter(customerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customerAdapter.setCustomers(allCustomers);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer_list, menu);

        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        return true;
    }

    private void filterList(String text) {
        List<Customer> filteredList = new ArrayList<>();
        for (Customer customer : allCustomers) {
            if (customer.getFirstName().toLowerCase().contains(text.toLowerCase()) ||
                    customer.getLastName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(customer);
            }
        }
        
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Customer not found", Toast.LENGTH_SHORT).show();
        } else {
            customerAdapter.setFilteredList(filteredList);
        }
    }
}