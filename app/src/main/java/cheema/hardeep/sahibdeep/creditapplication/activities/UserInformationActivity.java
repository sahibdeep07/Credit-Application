package cheema.hardeep.sahibdeep.creditapplication.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Random;

import cheema.hardeep.sahibdeep.creditapplication.model.Customer;
import cheema.hardeep.sahibdeep.creditapplication.database.Database;
import cheema.hardeep.sahibdeep.creditapplication.R;
import cheema.hardeep.sahibdeep.creditapplication.model.UserInformationClickType;

public class UserInformationActivity extends AppCompatActivity {

    public static final String CREATE_CUSTOMER = "Create Customer";
    public static final String KEY_TYPE = "type";
    public static final String KEY_SERIAL_NO = "serialNo";


    EditText name, phoneNumber, address, dob;
    ImageButton dobButton;
    RadioButton male, female;
    Button save, update, delete;
    int otp;

    Database database;

    public static Intent createIntent(Context context, String serialNo, UserInformationClickType userInformationClickType) {
        Intent intent = new Intent(context, UserInformationActivity.class);
        intent.putExtra(KEY_SERIAL_NO, serialNo);
        intent.putExtra(KEY_TYPE, userInformationClickType);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        setTitle(CREATE_CUSTOMER);

        database = new Database(this);
        findViews();

        Intent intent = getIntent();
        UserInformationClickType userInformationClickType = (UserInformationClickType) intent.getSerializableExtra(KEY_TYPE);
        String serialNo = intent.getStringExtra(KEY_SERIAL_NO);

        fillingFields(serialNo);
        handleClickListeners(serialNo, userInformationClickType);
    }

    private void findViews() {
        name = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        dob = findViewById(R.id.dob);
        dobButton = findViewById(R.id.dobButton);
        male = findViewById(R.id.maleButton);
        female = findViewById(R.id.femaleButton);
        save = findViewById(R.id.saveButton);
        update = findViewById(R.id.updateButton);
        delete = findViewById(R.id.deleteButton);
    }

    private void handleClickListeners(final String serialNumber, UserInformationClickType userInformationClickType) {
        dobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        if (userInformationClickType.equals(UserInformationClickType.ADD)) {
            update.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
            save.setVisibility(View.VISIBLE);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkEntries()) {
                        Random obj = new Random();
                        otp = obj.nextInt(10000);
                        if (ActivityCompat.checkSelfPermission(UserInformationActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(UserInformationActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                        } else {
                            sendMessage();
                            createOptDialog();
                        }
                    } else {
                        Toast.makeText(UserInformationActivity.this, "Please Fill All The Fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (userInformationClickType.equals(UserInformationClickType.UPDATE)) {
            update.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            save.setVisibility(View.GONE);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database.updateCustomer(serialNumber, getCustomer());
                    Toast.makeText(v.getContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database.deleteCustomer(serialNumber);
                    Toast.makeText(v.getContext(), "Deletion Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

    public Customer getCustomer() {
        return new Customer(name.getText().toString(),
                phoneNumber.getText().toString(),
                address.getText().toString(),
                dob.getText().toString(),
                male.isChecked());
    }

    private void fillingFields(String serialNo) {
        if (serialNo != null) {
            Customer customer = database.getCustomer(serialNo);
            name.setText(customer.getName());
            address.setText(customer.getAddress());
            phoneNumber.setText(customer.getPhone());
            dob.setText(customer.getDOB());
            boolean isMale = customer.getGender().equalsIgnoreCase("male");
            male.setChecked(isMale);
            female.setChecked(!isMale);
        }
    }

    private void showDatePicker() {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dob.setText(String.format("%02d-%02d-%04d", dayOfMonth, month + 1, year));
            }
        }, 1996, 00, 01).show();
    }

    private boolean checkEntries() {
        if (!name.getText().toString().trim().isEmpty()
                && !phoneNumber.getText().toString().isEmpty()
                && !address.getText().toString().isEmpty()
                && !dob.getText().toString().isEmpty()
                && (male.isChecked() || female.isChecked())) return true;
        else return false;
    }

    void sendMessage() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(getCustomer().getPhone(), null, String.valueOf(otp), null, null);
            Toast.makeText(getApplicationContext(), "Your Message Sent " + otp,
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void createOptDialog() {
        final Dialog mydialog;
        mydialog = new Dialog(UserInformationActivity.this);
        mydialog.setContentView(R.layout.otp_layout);
        mydialog.show();
        Button confirmbtn = mydialog.findViewById(R.id.otpButton);
        final EditText otpbox = mydialog.findViewById(R.id.enterOTP);
        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDialogResponse(otpbox.getText().toString());
            }
        });
    }

    private void handleDialogResponse(String value) {
        if (value.equals(String.valueOf(otp))) {
            database.insertCustomer(getCustomer());
            finish();
        } else {
            Toast.makeText(UserInformationActivity.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendMessage();
                createOptDialog();
            } else {
                Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
