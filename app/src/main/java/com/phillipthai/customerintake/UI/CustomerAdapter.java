package com.phillipthai.customerintake.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phillipthai.customerintake.R;
import com.phillipthai.customerintake.entities.Customer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> mCustomers;
    private final Context context;
    private final LayoutInflater mInflater;

    public void setFilteredList(List<Customer> filteredList) {
        this.mCustomers = filteredList;
        notifyDataSetChanged();
    }

    public CustomerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {

        private final TextView customerItemView;
        private final TextView customerItemView2;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            customerItemView = itemView.findViewById(R.id.customerTextView);
            customerItemView2 = itemView.findViewById(R.id.customerTextView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Customer current = mCustomers.get(position);
                    Intent intent = new Intent(context, CustomerDetails.class);
                    intent.putExtra("id", current.getCustomerID());
                    intent.putExtra("first", current.getFirstName());
                    intent.putExtra("last", current.getLastName());
                    intent.putExtra("phoneNumber", current.getPhoneNumber());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public CustomerAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.customer_list_item, parent, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.CustomerViewHolder holder, int position) {
        if (mCustomers != null) {
            Customer current = mCustomers.get(position);
            String name = current.getFirstName() + " " + current.getLastName();
            String phone = current.getPhoneNumber();
            holder.customerItemView.setText(name);
            holder.customerItemView2.setText(phone);
        } else {
            holder.customerItemView.setText("No customer name.");
            holder.customerItemView2.setText("No phone number.");
        }
    }

    @Override
    public int getItemCount() {
        if (mCustomers != null) {
            return mCustomers.size();
        } else {
            return 0;
        }
    }

    public void setCustomers(List<Customer> customers) {
        mCustomers = customers;
        notifyDataSetChanged();
    }
}
