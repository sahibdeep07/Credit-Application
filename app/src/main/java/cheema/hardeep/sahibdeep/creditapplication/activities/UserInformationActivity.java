package cheema.hardeep.sahibdeep.creditapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import cheema.hardeep.sahibdeep.creditapplication.model.Customer;
import cheema.hardeep.sahibdeep.creditapplication.database.Database;
import cheema.hardeep.sahibdeep.creditapplication.R;

public class UserInformationActivity extends AppCompatActivity {


    EditText name, phoneNumber, address, dob;
    ImageView dobButton;
    RadioButton male;
    Button save;

    Database database;

    public static Intent createIntent(Context context) {
        return new Intent(context, UserInformationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        findViews();
        database = new Database(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.insertCustomer(getCustomer());
                finish();
            }
        });
    }

    private void findViews() {
        name = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        dob = findViewById(R.id.dob);
        dobButton = findViewById(R.id.dobButton);
        male = findViewById(R.id.maleButton);
        save = findViewById(R.id.saveButton);
    }

    public Customer getCustomer(){
        return new Customer(name.getText().toString(),
                phoneNumber.getText().toString(),
                address.getText().toString(),
                dob.getText().toString(),
                male.isChecked());
    }
}
