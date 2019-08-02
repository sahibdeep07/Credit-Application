package cheema.hardeep.sahibdeep.creditapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import cheema.hardeep.sahibdeep.creditapplication.model.Customer;
import cheema.hardeep.sahibdeep.creditapplication.model.Transaction;

public class Database extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "customerinfo";
    public static final String CUSTOMER_TRANSACTION_TABLE = "customertransaction";

    SQLiteDatabase mydb;


    public Database(Context context) {
        super(context, "customerdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCustomerTable = "create table if not exists customerinfo (srno Integer Primary Key AUTOINCREMENT , " + " name text, phone text, address text, dob text, gender text, amount Integer)";
        String createTransactionTable = "create table if not exists customertransaction (customerserialno Integer, amount Integer, description text, date text)";
        try {
            db.execSQL(createCustomerTable);
            db.execSQL(createTransactionTable);
        } catch (Exception e) {
            Log.e("customerdatabase", "onCreate: " + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void mydbopen() {
        mydb = this.getReadableDatabase();
    }

    public void mydbclose() {
        mydb.close();
    }

    public void insertCustomer(Customer customerData) {
        mydbopen();
        ContentValues myValues = new ContentValues();
        myValues.put("name", String.valueOf(customerData.getName()));
        myValues.put("address", String.valueOf(customerData.getAddress()));
        myValues.put("phone", String.valueOf(customerData.getPhone()));
        myValues.put("dob", String.valueOf(customerData.getDOB()));
        myValues.put("gender", String.valueOf(customerData.getGender()));
        myValues.put("amount", customerData.getAmount());
        mydb.insert(CUSTOMER_TABLE, null, myValues);
        mydbclose();
    }

    public void insertCustomerTransaction(Customer customer, Transaction transaction) {
        mydbopen();
        ContentValues contentValues = new ContentValues();
        contentValues.put("customerserialno", customer.getSerialNo());
        contentValues.put("amount", transaction.getAmount());
        contentValues.put("description", transaction.getDescription());
        contentValues.put("date", transaction.getDate());
        mydb.insert(CUSTOMER_TRANSACTION_TABLE, null, contentValues);
        mydbclose();
        updateCustomerAmount(customer, transaction);
    }

    public ArrayList<Customer> fetchValues() {
        mydbopen();
        Cursor result = mydb.rawQuery("select * from customerinfo", null);
        ArrayList<Customer> results = new ArrayList<>();
        if (result.moveToFirst()) {
            do {
                String name = result.getString(result.getColumnIndex("name"));
                String serialNo = result.getString(result.getColumnIndex("srno"));
                results.add(new Customer(serialNo, name));
            } while (result.moveToNext());
        }
        mydbclose();
        return results;
    }

    public ArrayList<Customer> fetchValuesWithAmount() {
        mydbopen();
        Cursor result = mydb.rawQuery("select * from customerinfo where amount > 0", null);
        ArrayList<Customer> results = new ArrayList<>();
        if (result.moveToFirst()) {
            do {
                String name = result.getString(result.getColumnIndex("name"));
                String serialNo = result.getString(result.getColumnIndex("srno"));
                results.add(new Customer(serialNo, name));
            } while (result.moveToNext());
        }
        mydbclose();
        return results;
    }

    public ArrayList<Transaction> fetchAllTransactionWithCustomerId(String serialNo) {
        mydbopen();
        Cursor result = mydb.rawQuery("select * from customertransaction where customerserialno = ?", new String[]{serialNo});
        ArrayList<Transaction> results = new ArrayList<>();
        if (result.moveToFirst()) {
            do {
                int customerId = result.getInt(result.getColumnIndex("customerserialno"));
                int amount = result.getInt(result.getColumnIndex("amount"));
                String description = result.getString(result.getColumnIndex("description"));
                String date = result.getString(result.getColumnIndex("date"));
                results.add(new Transaction(customerId, amount, description, date));
            } while (result.moveToNext());
        }
        mydbclose();
        return results;
    }


    public Customer getCustomer(String serialNo) {
        mydbopen();
        Cursor result = mydb.rawQuery("select * from customerinfo WHERE srno = ?", new String[]{serialNo});
        result.moveToFirst();
        Customer customer = new Customer();
        customer.setSerialNo(serialNo);
        customer.setName(result.getString(result.getColumnIndex("name")));
        customer.setAddress(result.getString(result.getColumnIndex("address")));
        customer.setDOB(result.getString(result.getColumnIndex("dob")));
        customer.setPhone(result.getString(result.getColumnIndex("phone")));
        customer.setGender(result.getString(result.getColumnIndex("gender")));
        customer.setAmount(result.getInt(result.getColumnIndex("amount")));
        mydbclose();
        return customer;
    }

    public void updateCustomerAmount(Customer customer, Transaction transaction) {
        mydbopen();
        customer.updateAmount(transaction.getAmount());
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", customer.getAmount());
        mydb.update(CUSTOMER_TABLE, contentValues, "srno = ?", new String[]{customer.getSerialNo()});
        mydbclose();
    }

    public void deleteCustomer(String serialNo){
        mydbopen();
        mydb.delete(CUSTOMER_TABLE, "srno=?", new String[]{serialNo});
//        mydb.delete(CUSTOMER_TRANSACTION_TABLE, "srno=?", new String[]{serialNo});
        mydbclose();
    }

    public void updateCustomer(String serialNo, Customer customerData){
        mydbopen();
        ContentValues myValues = new ContentValues();
        myValues.put("name", String.valueOf(customerData.getName()));
        myValues.put("address", String.valueOf(customerData.getAddress()));
        myValues.put("phone", String.valueOf(customerData.getPhone()));
        myValues.put("dob", String.valueOf(customerData.getDOB()));
        myValues.put("gender", String.valueOf(customerData.getGender()));
        myValues.put("amount", customerData.getAmount());
        mydb.update(CUSTOMER_TABLE, myValues, "srno=?", new String[]{serialNo});
        mydbclose();
    }
}
