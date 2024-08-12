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
import com.phillipthai.customerintake.entities.Job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<Job> mJobs;
    private final Context context;
    private final LayoutInflater mInflater;

    public class JobViewHolder extends RecyclerView.ViewHolder {

        private final TextView jobItemView;
        private final TextView jobItemView2;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobItemView = itemView.findViewById(R.id.jobTextView);
            jobItemView2 = itemView.findViewById(R.id.jobTextView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Job current = mJobs.get(position);
                    Intent intent = new Intent(context, JobDetails.class);
                    intent.putExtra("id", current.getCustomerID());
                    intent.putExtra("name", current.getJobName());
                    intent.putExtra("price", current.getPrice());
                    intent.putExtra("customerID", current.getCustomerID());
                    context.startActivity(intent);
                }
            });
        }
    }

    public JobAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public JobAdapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.job_list_item, parent, false);
        return new JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdapter.JobViewHolder holder, int position) {
        if (mJobs != null) {
            Job current = mJobs.get(position);
            String name = current.getJobName();
            Date jobDate = current.getJobDate();

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
            String dateString = sdf.format(jobDate);

            holder.jobItemView.setText(name);
            holder.jobItemView2.setText(dateString);
        } else {
            holder.jobItemView.setText("No job name");
            holder.jobItemView2.setText("No date.");
        }
    }

    public void setJobs(List<Job> jobs) {
        mJobs = jobs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mJobs != null) {
            return mJobs.size();
        } else {
            return 0;
        }
    }
}
