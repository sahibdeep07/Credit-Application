package cheema.hardeep.sahibdeep.creditapplication.activities.transactionHistory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import cheema.hardeep.sahibdeep.creditapplication.R;
import cheema.hardeep.sahibdeep.creditapplication.adapters.TransactionAdapter;
import cheema.hardeep.sahibdeep.creditapplication.database.Database;
import cheema.hardeep.sahibdeep.creditapplication.model.Customer;

public class TransactionsListActivity extends AppCompatActivity {

    public static final String TRANSACTION_ID_KEY = "transaction-id";
    public static final String TRANSACTIONS = "'s Transactions";
    public static final String RUPEES_PREFIX = "Rs. ";

    RecyclerView recyclerView;
    TextView pendingAmount;
    TransactionAdapter transactionAdapter;
    Database database;

    public static Intent createIntent(Context context, String id) {
        Intent intent = new Intent(context, TransactionsListActivity.class);
        intent.putExtra(TRANSACTION_ID_KEY, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
        database = new Database(this);

        findViews();

        Intent intents = getIntent();
        String customerId = intents.getStringExtra(TRANSACTION_ID_KEY);
        Customer customer = database.getCustomer(customerId);
        setTitle(customer.getName() + TRANSACTIONS);
        pendingAmount.setText(RUPEES_PREFIX + customer.getAmount());

        setUpRecyclerView(customerId);
    }

    private void setUpRecyclerView(String customerId) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        transactionAdapter = new TransactionAdapter();
        recyclerView.setAdapter(transactionAdapter);
        transactionAdapter.updateTransactionList(database.fetchAllTransactionWithCustomerId(customerId));
    }

    private void findViews() {
        recyclerView =  findViewById(R.id.recyclerView);
        pendingAmount = findViewById(R.id.pendingAmount);
    }
}
