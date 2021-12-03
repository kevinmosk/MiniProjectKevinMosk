package OffLicense;

import java.io.Serializable;

public class Staff implements Serializable {
    private String name;
    private String address;
    private String email;

    public Staff(){
        this("Not specified","Not specified","Not specified");
    }

    public Staff(String name, String address, String email) {
        setName(name);
        setAddress(address);
        setEmail(email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.equals("") || name == null)
            this.name = "Not specified";
        else
            this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address.equals("") || address == null)
            this.address = "Not specified";
        else this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.equals("") || email == null)
            this.email = "Not specified";
        else
            this.email=email;
    }

    public String toString(){
        return "Name: " + getName() + "\nAddress: " + getAddress() +
                "\nEmail: " + getEmail();
    }
}
