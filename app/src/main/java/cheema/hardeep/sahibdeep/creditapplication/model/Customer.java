package cheema.hardeep.sahibdeep.creditapplication.model;

public class Customer {

    private String serialNo;
    private String name;
    private String phone;
    private String address;
    private String DOB;
    private String gender;
    private int amount = 0;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void updateAmount(int amount) {
        this.amount += amount;
    }

    public Customer(String name, String phone, String address, String DOB, boolean isMale) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.DOB = DOB;
        this.gender = isMale ? "Male" : "Female";
    }

    public Customer(String serialNo, String name) {
        this.serialNo = serialNo;
        this.name = name;
    }

    public Customer() {}
}


