package OffLicense;

import java.io.Serializable;

public class Drink implements Serializable {
    private String name;
    private String type;
    private double price;
    private double size;

    public Drink() {
        this("Not specified","Not specified",0.0,0.0);
    }

    public Drink(String name, String type, double price, double size) {
        setName(name);
        setType(type);
        setPrice(price);
        setSize(size);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.equals("") || name == null)
            this.name = "Not specified";
        else
            this.name=name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type.equals("") || type==null)
            this.type = "Not specified";
        else
            this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price<0)
            this.price = 0;
        else
            this.price=price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        if (size<0)
            this.size = 0;
        else
            this.size=size;
    }

    @Override
    public String toString() {
        return "Name: " + getName() +
                "\nType: " + getType() +
                "\nPrice: " + getPrice() +
                "\nSize: " + getSize() + " ml";
    }
}
