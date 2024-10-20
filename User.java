package eMEDi;

class User {
    String first_name, last_name, e_mail, phone_no;

    public User(String first_name, String last_name, String e_mail, String phone_no) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.e_mail = e_mail;
        this.phone_no = phone_no;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getE_mail() {
        return e_mail;
    }

    public String getPhone_no() {
        return phone_no;
    }

}
