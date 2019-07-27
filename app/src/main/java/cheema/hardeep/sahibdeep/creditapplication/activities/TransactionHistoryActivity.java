package cheema.hardeep.sahibdeep.creditapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import cheema.hardeep.sahibdeep.creditapplication.R;
import cheema.hardeep.sahibdeep.creditapplication.adapters.CustomerAdapter;
import cheema.hardeep.sahibdeep.creditapplication.database.Database;
import cheema.hardeep.sahibdeep.creditapplication.model.ClickType;

public class TransactionHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomerAdapter customerAdapter;

    Database database;

    public static Intent createIntent(Context context) {
        return new Intent(context, TransactionHistoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        database =  new Database(this);

        recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        customerAdapter = new CustomerAdapter(ClickType.TRANSACTION);
        recyclerView.setAdapter(customerAdapter);
        customerAdapter.updateCustomerList(database.fetchValuesWithAmount());
    }
}
