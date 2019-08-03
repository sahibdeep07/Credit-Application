package cheema.hardeep.sahibdeep.creditapplication.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cheema.hardeep.sahibdeep.creditapplication.activities.UserInformationActivity;
import cheema.hardeep.sahibdeep.creditapplication.activities.transactionHistory.TransactionsListActivity;
import cheema.hardeep.sahibdeep.creditapplication.model.CustomerAdapterClickType;
import cheema.hardeep.sahibdeep.creditapplication.model.Customer;
import cheema.hardeep.sahibdeep.creditapplication.R;
import cheema.hardeep.sahibdeep.creditapplication.activities.ProcessTransactionActivity;
import cheema.hardeep.sahibdeep.creditapplication.model.UserInformationClickType;


public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    ArrayList<Customer> customers = new ArrayList<>();
    CustomerAdapterClickType customerAdapterClickType;

    public CustomerAdapter(CustomerAdapterClickType customerAdapterClickType) {
        this.customerAdapterClickType = customerAdapterClickType;
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
                if (customerAdapterClickType.equals(CustomerAdapterClickType.USER_INFO)) {
                    view.getContext().startActivity(ProcessTransactionActivity.createIntent(view.getContext(), customer.getSerialNo()));
                } else if (customerAdapterClickType.equals(CustomerAdapterClickType.TRANSACTION)) {
                    view.getContext().startActivity(TransactionsListActivity.createIntent(view.getContext(), customer.getSerialNo()));
                }
            }
        });
        customerViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.getContext().startActivity(UserInformationActivity.createIntent(v.getContext(), customer.getSerialNo(), UserInformationClickType.UPDATE));
                return false;
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
