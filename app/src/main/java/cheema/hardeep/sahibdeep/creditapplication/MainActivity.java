package cheema.hardeep.sahibdeep.creditapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText name,phone,address,dob;
    ImageButton date;
    Button save,update;
    RadioButton male,female;
    Customer customerData;

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(MainActivity.this);
        findViews();
    }

    private void findViews() {
        name = findViewById(R.id.nameBox);
        phone = findViewById(R.id.phoneBox);
        address = findViewById(R.id.addressBox);
        dob = findViewById(R.id.dobBox);
        date = findViewById(R.id.dateButton);
        male = findViewById(R.id.maleButton);
        female = findViewById(R.id.femaleButton);
        save = findViewById(R.id.saveButton);
        update = findViewById(R.id.updateButton);

        //click-listener
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertValues(getValues());
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent =  new Intent (MainActivity.this, ListOfCustomers.class);
               startActivity(intent);
            }
        });
    }


    public Customer getValues(){
       customerData = new Customer(name.getText().toString(),
               phone.getText().toString(),
               address.getText().toString(),
               dob.getText().toString(),
               male.isChecked());
       return customerData;
    }
}
