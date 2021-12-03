package OffLicense;


import java.io.Serializable;

public class Sales implements Serializable {
    private double amount;
    private double change;


    public Sales(){
        this(0.0,0.0);
    }

    public Sales(double amount, double change) {
        setAmount(amount);
        setChange(change);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public String toString(){
        return "The amount of money given was " + getAmount() + "The change given was " + getChange();
    }

}
