package cheema.hardeep.sahibdeep.creditapplication.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cheema.hardeep.sahibdeep.creditapplication.adapters.CustomerAdapter;
import cheema.hardeep.sahibdeep.creditapplication.database.Database;
import cheema.hardeep.sahibdeep.creditapplication.R;
import cheema.hardeep.sahibdeep.creditapplication.model.ClickType;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addUser, trasactionHistory;

    CustomerAdapter customerAdapter;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(MainActivity.this);

        findViews();
        setUpRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        customerAdapter.updateCustomerList(db.fetchValues());
    }

    private void setUpRecyclerView() {
        customerAdapter = new CustomerAdapter(ClickType.USER_INFO);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(customerAdapter);
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recyclerView);
        addUser = findViewById(R.id.addUser);
        trasactionHistory = findViewById(R.id.transactionHistory);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserInformationActivity.createIntent(v.getContext()));
            }
        });

        trasactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(TransactionHistoryActivity.createIntent(view.getContext()));
            }
        });

    }
}
