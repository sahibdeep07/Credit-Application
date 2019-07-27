package cheema.hardeep.sahibdeep.creditapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cheema.hardeep.sahibdeep.creditapplication.R;
import cheema.hardeep.sahibdeep.creditapplication.model.Transaction;

/**
 * @author Hardeep Singh (hardeep@cazisoft.com)
 * <p>
 * Copyright (c) 2019 Cazisoft, LLC
 */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    ArrayList<Transaction> transactions = new ArrayList<>();

    public void updateTransactionList(List<Transaction> transactionList) {
        transactions.clear();
        transactions.addAll(transactionList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_item, viewGroup, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder transactionViewHolder, int i) {
        final Transaction transaction = transactions.get(i);
        Log.d("HSING", transaction.getAmount() + " | " + transaction.getDescription());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {


        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
