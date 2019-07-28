package cheema.hardeep.sahibdeep.creditapplication.model;

public class Transaction {

    private int customerSerialNo;
    private int amount;
    private String description;
    private String date;

    public Transaction(int customerSerialNo, int amount, String description, String date) {
        this.customerSerialNo = customerSerialNo;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public int getCustomerSerialNo() {
        return customerSerialNo;
    }

    public void setCustomerSerialNo(int customerSerialNo) {
        this.customerSerialNo = customerSerialNo;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void createNegativeAmount() {
        this.amount = -1 * this.amount;
    }
}
