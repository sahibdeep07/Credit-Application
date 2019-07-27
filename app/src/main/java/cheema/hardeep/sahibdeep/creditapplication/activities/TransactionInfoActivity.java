package cheema.hardeep.sahibdeep.creditapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import cheema.hardeep.sahibdeep.creditapplication.R;
import cheema.hardeep.sahibdeep.creditapplication.adapters.TransactionAdapter;
import cheema.hardeep.sahibdeep.creditapplication.database.Database;

public class TransactionInfoActivity extends AppCompatActivity {

    public static final String TRANSACTION_ID_KEY = "transaction-id";


    RecyclerView recyclerView;
    TransactionAdapter transactionAdapter;
    Database database;

    public static Intent createIntent(Context context, String id) {
        Intent intent = new Intent(context, TransactionInfoActivity.class);
        intent.putExtra(TRANSACTION_ID_KEY, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_info);

        database = new Database(this);

        Intent intents = getIntent();
        String customerId = intents.getStringExtra(TRANSACTION_ID_KEY);

        recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        transactionAdapter = new TransactionAdapter();
        recyclerView.setAdapter(transactionAdapter);
        transactionAdapter.updateTransactionList(database.fetchAllTransactionWithCustomerId(customerId));
    }
}
