package cheema.hardeep.sahibdeep.creditapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Database extends SQLiteOpenHelper {
    SQLiteDatabase mydb;
    public Database(Context context) { super(context, "customerdb", null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table if not exists customerinfo (srno Integer Primary Key AUTOINCREMENT , "+" name text, phone text, address text, dob text, gender text)";
        try {
            db.execSQL(query);
        }
        catch (Exception e){
            Log.e("customerdatabase", "onCreate: "+ e );
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void mydbopen(){ mydb = this.getReadableDatabase(); }

    public void mydbclose(){ mydb.close(); }

    public void insertValues(Customer customerData) {
        mydbopen();
        ContentValues myValues = new ContentValues();
        myValues.put("name",String.valueOf(customerData.name));
        myValues.put("address",String.valueOf(customerData.phone));
        myValues.put("phone",String.valueOf(customerData.address));
        myValues.put("dob",String.valueOf(customerData.DOB));
        myValues.put("gender",String.valueOf(customerData.gender));
        mydb.insert("customerinfo", null, myValues);
        mydbclose();
    }

    public ArrayList<Customer> fetchValues(){
        mydbopen();
        Cursor result = mydb.rawQuery("select * from customerinfo", null);
        ArrayList<Customer> results = new ArrayList<>();
        if(result.moveToFirst()) {
            do {
                String name = result.getString(result.getColumnIndex("name"));
                String serialNo = result.getString(result.getColumnIndex("srno"));
                results.add(new Customer(serialNo, name));
            } while (result.moveToNext());
        }
        mydbclose();
        return results;
    }


    public Customer getCustomer(String serialNo){
        mydbopen();
        Cursor result = mydb.rawQuery("select * from customerinfo WHERE srno = ?", new String[]{serialNo});
        Customer customer = new Customer();
        customer.setSerialNo(serialNo);
        customer.setName(result.getString(result.getColumnIndex("name")));
        customer.setAddress(result.getString(result.getColumnIndex("address")));
        customer.setDOB(result.getString(result.getColumnIndex("dob")));
        customer.setPhone(result.getString(result.getColumnIndex("phone")));
        customer.setGender(result.getString(result.getColumnIndex("gender")));
        mydbclose();
        return customer;
    }
}
