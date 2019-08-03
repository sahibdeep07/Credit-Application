package cheema.hardeep.sahibdeep.creditapplication.activities.transactionHistory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import cheema.hardeep.sahibdeep.creditapplication.R;
import cheema.hardeep.sahibdeep.creditapplication.adapters.CustomerAdapter;
import cheema.hardeep.sahibdeep.creditapplication.database.Database;
import cheema.hardeep.sahibdeep.creditapplication.model.CustomerAdapterClickType;

public class TransactionCustomersActivity extends AppCompatActivity {

    private static final String TRANSACTIONS_CUSTOMERS = "Transactions Customers";
    RecyclerView recyclerView;
    CustomerAdapter customerAdapter;

    Database database;

    public static Intent createIntent(Context context) {
        return new Intent(context, TransactionCustomersActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_customers);
        setTitle(TRANSACTIONS_CUSTOMERS);
        database =  new Database(this);

        recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        customerAdapter = new CustomerAdapter(CustomerAdapterClickType.TRANSACTION);
        recyclerView.setAdapter(customerAdapter);
        customerAdapter.updateCustomerList(database.fetchValuesWithAmount());
    }
}
