package cheema.hardeep.sahibdeep.creditapplication.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import cheema.hardeep.sahibdeep.creditapplication.activities.transactionHistory.TransactionCustomersActivity;
import cheema.hardeep.sahibdeep.creditapplication.adapters.CustomerAdapter;
import cheema.hardeep.sahibdeep.creditapplication.database.Database;
import cheema.hardeep.sahibdeep.creditapplication.R;
import cheema.hardeep.sahibdeep.creditapplication.model.CustomerAdapterClickType;
import cheema.hardeep.sahibdeep.creditapplication.model.Customer;
import cheema.hardeep.sahibdeep.creditapplication.model.UserInformationClickType;

public class MainActivity extends AppCompatActivity {

    static RecyclerView recyclerView;
    FloatingActionButton addUser, trasactionHistory;
    static ImageView noRecord;

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
        ArrayList<Customer> checkRecordList = db.fetchValues();
        if(checkRecordList.isEmpty()){
            noRecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else {
            noRecord.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            customerAdapter.updateCustomerList(db.fetchValues());
        }
    }

    private void setUpRecyclerView() {
        customerAdapter = new CustomerAdapter(CustomerAdapterClickType.USER_INFO);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(customerAdapter);
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recyclerView);
        addUser = findViewById(R.id.addUser);
        trasactionHistory = findViewById(R.id.transactionHistory);
        noRecord = findViewById(R.id.noRecord);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserInformationActivity.createIntent(v.getContext(), null, UserInformationClickType.ADD));
            }
        });

        trasactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(TransactionCustomersActivity.createIntent(view.getContext()));
            }
        });

    }

}
