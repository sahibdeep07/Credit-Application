package cheema.hardeep.sahibdeep.creditapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cheema.hardeep.sahibdeep.creditapplication.R;
import cheema.hardeep.sahibdeep.creditapplication.database.Database;
import cheema.hardeep.sahibdeep.creditapplication.model.Customer;
import cheema.hardeep.sahibdeep.creditapplication.model.Transaction;

public class ProcessTransactionActivity extends AppCompatActivity {

    public static final String CUSTOMER_ID_KEY = "customer-id";
    public static final String PROCESS_TRANSACTIONS = "Process Transactions";
    public static final String DATE_PREFIX = "Date: ";

    EditText amount, description;
    TextView date;
    Button lendButton, receiveButton;

    Database database;
    Customer customer;


    public static Intent createIntent(Context context, String id) {
        Intent intent = new Intent(context, ProcessTransactionActivity.class);
        intent.putExtra(CUSTOMER_ID_KEY, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_transaction);
        setTitle(PROCESS_TRANSACTIONS);

        findViews();
        database = new Database(this);

        Intent intents = getIntent();
        String customerId = intents.getStringExtra(CUSTOMER_ID_KEY);
        customer = database.getCustomer(customerId);

        setClickListeners();
        setCurrentDate();
    }

    private void setClickListeners() {
        lendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLend(view);
            }
        });

        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleReceive(view);
            }
        });
    }

    private void handleLend(View view) {
        Transaction transaction = createTransaction();
        database.insertCustomerTransaction(customer, transaction);
        resetViews();
        Toast.makeText(view.getContext(), "Money Lend Successful", Toast.LENGTH_SHORT).show();
    }

    private void handleReceive(View view) {
        int inputAmount = getInputAmount();
        if (inputAmount <= customer.getAmount()) {
            Transaction transaction = createTransaction();
            transaction.createNegativeAmount();
            database.insertCustomerTransaction(customer, transaction);
            resetViews();
            Toast.makeText(view.getContext(), "Money Receive Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(view.getContext(), "Bitch WTF! I only owe you " + customer.getAmount(), Toast.LENGTH_SHORT).show();
        }
    }

    private Transaction createTransaction() {
        return new Transaction(
                Integer.parseInt(customer.getSerialNo()),
                Integer.parseInt(amount.getText().toString()),
                description.getText().toString(),
                date.getText().toString()
        );
    }

    private void setCurrentDate() {
        String myFormat = "MMM d, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(DATE_PREFIX + sdf.format(new Date()));
    }

    private void resetViews() {
        amount.setText("");
        description.setText("");
        setCurrentDate();
    }

    private int getInputAmount() {
        return Integer.parseInt(amount.getText().toString());
    }

    private void findViews() {
        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);
        lendButton = findViewById(R.id.lendButton);
        receiveButton = findViewById(R.id.receiveButton);
        date = findViewById(R.id.date);
    }
}
