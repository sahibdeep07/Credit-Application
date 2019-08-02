package cheema.hardeep.sahibdeep.creditapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import cheema.hardeep.sahibdeep.creditapplication.activities.MainActivity;
import cheema.hardeep.sahibdeep.creditapplication.activities.UserInformationActivity;
import cheema.hardeep.sahibdeep.creditapplication.database.Database;
import cheema.hardeep.sahibdeep.creditapplication.model.Customer;

public class UserInformationUpdate extends AppCompatActivity {

    private static String serialNumber = new String();

//    public static Intent createIntent(Context context, String id) {
//        serialNumber = id;
//        return new Intent(context, UserInformationActivity.class);
//    }

    Database db;
    EditText name, phone, address, dob;
    RadioButton male, female;
    Customer customer, newCustomer;
    Button update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information_update);
        findViews();
        db = new Database(this);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            serialNumber = (String) bd.get("id");
        }
        customer = new Customer();
        customer = db.getCustomer(serialNumber);
        fillingFields();
    }
    void findViews(){
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        dob = findViewById(R.id.dob);
        male = findViewById(R.id.maleButton);
        female = findViewById(R.id.femaleButton);
        update = findViewById(R.id.updateButton);
        delete = findViewById(R.id.deleteButton);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateCustomer(serialNumber, updateData());
                Toast.makeText(v.getContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteCustomer(serialNumber);
                startActivity(new Intent(UserInformationUpdate.this, MainActivity.class));
                Toast.makeText(UserInformationUpdate.this, "Deletion Successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    void fillingFields(){
        name.setText(customer.getName());
        address.setText(customer.getAddress());
        phone.setText(customer.getPhone());
        dob.setText(customer.getDOB());
        if(customer.getGender().equalsIgnoreCase("male")){
            male.setChecked(true);
        }
        else female.setChecked(true);
    }

    Customer updateData(){
        return new Customer(name.getText().toString(),
                phone.getText().toString(),
                address.getText().toString(),
                dob.getText().toString(),
                male.isChecked());
    }
}
