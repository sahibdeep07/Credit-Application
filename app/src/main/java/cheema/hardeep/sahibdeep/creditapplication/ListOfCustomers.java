package cheema.hardeep.sahibdeep.creditapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ListOfCustomers extends AppCompatActivity {
    ListView customerList;
    ArrayList<Customer> customers = new ArrayList<>();
    ArrayAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_customers);
        customerList = findViewById(R.id.customerList);
        Database obj = new Database(this);
        customers = obj.fetchValues();
        if (customers.isEmpty()) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            myAdapter = new ArrayAdapter(ListOfCustomers.this, android.R.layout.simple_list_item_1, getNames());
            customerList.setAdapter(myAdapter);
            customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = parent.getItemAtPosition(position).toString();
                    Intent intent = new Intent(ListOfCustomers.this, MainActivity.class);
                    intent.putExtra("srno", getSerialNo(name));
                    Toast.makeText(ListOfCustomers.this, name + "||" + getSerialNo(name), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });
        }

    }

    public String [] getNames() {
        String [] names = new String[customers.size()];
        for (int i = 0; i < customers.size(); i++) {
            names[i] = customers.get(i).getName();
        }
        return names;
    }

    public String getSerialNo(String name) {
        for (Customer customer : customers) {
            if(customer.name.equals(name)) return customer.serialNo;
        }
        return null;
    }
}
