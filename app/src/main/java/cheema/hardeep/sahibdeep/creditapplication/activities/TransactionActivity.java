package cheema.hardeep.sahibdeep.creditapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cheema.hardeep.sahibdeep.creditapplication.database.Database;
import cheema.hardeep.sahibdeep.creditapplication.R;
import cheema.hardeep.sahibdeep.creditapplication.model.Customer;
import cheema.hardeep.sahibdeep.creditapplication.model.Transaction;

public class TransactionActivity extends AppCompatActivity {

    public static final String CUSTOMER_ID_KEY = "customer-id";

    EditText amount, description;
    Button lendButton, receiveButton;

    Database database;
    Customer customer;

    public static Intent createIntent(Context context, String id) {
        Intent intent = new Intent(context, TransactionActivity.class);
        intent.putExtra(CUSTOMER_ID_KEY, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasaction);

        findViews();
        database = new Database(this);

        Intent intents = getIntent();
        String customerId = intents.getStringExtra(CUSTOMER_ID_KEY);
        Toast.makeText(this, customerId, Toast.LENGTH_SHORT).show();
        customer = database.getCustomer(customerId);

        lendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transaction transaction = createTransaction();
                database.insertCustomerTransaction(customer, transaction);
                resetViews();
                Toast.makeText(view.getContext(), "Money Lend Successful", Toast.LENGTH_SHORT).show();
            }
        });

        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int inputAmount = getInputAmount();
                if (inputAmount <= customer.getAmount()) {
                    Transaction transaction = createTransaction();
                    database.insertCustomerTransaction(customer, transaction);
                    resetViews();
                    Toast.makeText(view.getContext(), "Money Receive Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(view.getContext(), "Bitch WTF! I only owe you " + customer.getAmount(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Transaction createTransaction() {
        return new Transaction(
                Integer.parseInt(customer.getSerialNo()),
                Integer.parseInt(amount.getText().toString()),
                description.getText().toString(),
                ""
        );
    }

    private void resetViews() {
        amount.setText("");
        description.setText("");
    }

    private int getInputAmount() {
        return Integer.parseInt(amount.getText().toString());
    }

    private void findViews() {
        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);
        lendButton = findViewById(R.id.lendButton);
        receiveButton = findViewById(R.id.receiveButton);
    }
}
