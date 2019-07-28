package cheema.hardeep.sahibdeep.creditapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cheema.hardeep.sahibdeep.creditapplication.activities.transactionHistory.TransactionsListActivity;
import cheema.hardeep.sahibdeep.creditapplication.model.ClickType;
import cheema.hardeep.sahibdeep.creditapplication.model.Customer;
import cheema.hardeep.sahibdeep.creditapplication.R;
import cheema.hardeep.sahibdeep.creditapplication.activities.ProcessTransactionActivity;

/**
 * @author Hardeep Singh (hardeep@cazisoft.com)
 * <p>
 * Copyright (c) 2019 Cazisoft, LLC
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    ArrayList<Customer> customers = new ArrayList<>();
    ClickType clickType;

    public CustomerAdapter(ClickType clickType) {
        this.clickType = clickType;
    }

    public void updateCustomerList(List<Customer> customerList) {
        customers.clear();
        customers.addAll(customerList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_item, viewGroup, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder customerViewHolder, int i) {
        final Customer customer = customers.get(i);
        customerViewHolder.name.setText(customer.getName());

        customerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickType.equals(ClickType.USER_INFO)) {
                    view.getContext().startActivity(ProcessTransactionActivity.createIntent(view.getContext(), customer.getSerialNo()));
                } else if(clickType.equals(ClickType.TRANSACTION)) {
                    view.getContext().startActivity(TransactionsListActivity.createIntent(view.getContext(), customer.getSerialNo()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.customerName);
        }
    }
}
