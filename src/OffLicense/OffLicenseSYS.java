package OffLicense;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class OffLicenseSYS extends JFrame implements ActionListener {
    JMenu drinkMenu;
    JMenu staffMenu;
    JMenu salesMenu;

    ArrayList<Drink> drinks = new ArrayList<>();
    ArrayList<Staff> staffMembers = new ArrayList<>();
    ArrayList<Sales> sales = new ArrayList<>();
    private Drink drink;
    private Staff staff;
    private Sales sale;

    public OffLicenseSYS() {
        setTitle("Off License System");
        setLocationRelativeTo(null);

        setIconImage(new ImageIcon(getClass().getResource("offLicenseLogo.png")).getImage());

        close();
        createDrinkMenu();
        createStaffMenu();
        createSalesMenu();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.setBackground(Color.orange);
        menuBar.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        //add border
        /*
        Title: How to add border to Jmenu button
        Author: kswr
        Site owner/sponsor: stackoverflow.com
        Date: Jun 1 2018
        Availability: https://stackoverflow.com/questions/50642055/how-to-add-border-to-jmenu-button

        */
        menuBar.add(drinkMenu);
        menuBar.add(staffMenu);
        menuBar.add(salesMenu);

        setSize(450, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(250, 250);
        setLayout(new FlowLayout());
        setVisible(true);
        open();

    }

    public void save() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("drink.data"));
        os.writeObject(drinks);
        os.writeObject(staffMembers);
        os.writeObject(sales);
        os.close();
    }

    public void open() {
        try {
            File file = new File("drink.data");

            if (file.exists()) {
                ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
                drinks = (ArrayList<Drink>) is.readObject();
                staffMembers = (ArrayList<Staff>) is.readObject();
                is.close();

                JOptionPane.showMessageDialog(null, file.getName() + " file loaded into system", "Open", JOptionPane.INFORMATION_MESSAGE);
            } else {
                file.createNewFile();
                JOptionPane.showMessageDialog(null, "File just created", "Created " + file.getName() + " file", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (ClassNotFoundException cce) {
            JOptionPane.showMessageDialog(null, "Class of object deserialized not a match for anything used in this application", "Error", JOptionPane.ERROR_MESSAGE);
            cce.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(null, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Problem reading from the file", "Error", JOptionPane.ERROR_MESSAGE);
            ioe.printStackTrace();
        }

    }

    public void close() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    try {
                        save();
                        JOptionPane.showMessageDialog(null, "Data saved successfully", "Saved", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "Not able to save the file");
                        e1.printStackTrace();
                    }

                    System.exit(0);
                }
            }
        });
    }

    public void quit() {


        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                save();
                JOptionPane.showMessageDialog(null, "Data saved successfully", "Saved", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Not able to save the file");
                e1.printStackTrace();
            }

            System.exit(0);
        }

    }

    public static void main(String[] args) {
        OffLicenseSYS frame = new OffLicenseSYS();
        //new OffLicenseSYS();
    }

    public void actionPerformed(ActionEvent event) {
        String menuName = event.getActionCommand();

        if (menuName == "Add Drink")
            addDrink();
        else if (menuName == "View Drink")
            viewDrink();
        else if (menuName == "Delete Drink")
            deleteDrink();
        else if (menuName == "Quit")
            quit();
        else if (menuName == "Add Staff")
            addStaff();
        else if (menuName == "View Staff")
            viewStaff();
        else if (menuName == "Delete Staff")
            deleteStaff();
        else if (menuName == "Edit Drink")
            editDrink();
        else if (menuName == "Edit Staff")
            editStaff();
        else if (menuName == "Add Sale")
            addSale();
        else if (menuName == "View Sales")
            viewSales();

    }

    public void addDrink() {
        final String[] drinkList = {"Beer", "Cider", "Spirit", "Wine"};
        String name, type;
        double price;
        double size;

        type = (String) JOptionPane.showInputDialog(null, "Drink", "Drink", JOptionPane.QUESTION_MESSAGE, null, drinkList, drinkList[0]);
        name = JOptionPane.showInputDialog("Enter the name of the Drink");
        while (name.equals("") || name == null)
            name = JOptionPane.showInputDialog("Invalid! Cannot be Null, Please Re-Enter the name of the Drink");
        price = Double.parseDouble(JOptionPane.showInputDialog("Enter the price of the Drink"));
        while (price < 0)
            price = Double.parseDouble(JOptionPane.showInputDialog("Invalid! Cannot be negative, Please Re-Enter the price of the Drink"));
        size = Double.parseDouble(JOptionPane.showInputDialog("Enter the size of the drink"));
        while (size < 0)
            size = Double.parseDouble(JOptionPane.showInputDialog("Invalid! Cannot be Negative, Please Re-Enter the size of the Drink"));

        drink = new Drink(name, type, price, size);
        drinks.add(drink);

        JOptionPane.showMessageDialog(null, "Drink '" + name + "' added to the system");
    }

    public void viewDrink() {
        JComboBox drinkCombo = new JComboBox();
        String txt = "Drink Details:\n\n";

        if (drinks.size() < 1)
            JOptionPane.showMessageDialog(null, "No drinks have been added to the system yet", "Empty", JOptionPane.WARNING_MESSAGE);
        else {
            Iterator<Drink> iterator = drinks.iterator();

            while (iterator.hasNext())
                drinkCombo.addItem(iterator.next().getName() + "\n");


            JOptionPane.showMessageDialog(null, drinkCombo, "Select Drink to View", JOptionPane.PLAIN_MESSAGE);

            int selected = drinkCombo.getSelectedIndex();
            txt += drinks.get(selected).toString();

            JOptionPane.showMessageDialog(null, txt, "Drink details", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void deleteDrink() {
        JComboBox drinkList = new JComboBox();

        for (Drink d : drinks)
            drinkList.addItem(d.getName());

        JOptionPane.showMessageDialog(null, "Select drink you want removed", "Delete Drink", JOptionPane.INFORMATION_MESSAGE);

        JOptionPane.showMessageDialog(null, drinkList, "Delete Drink", JOptionPane.INFORMATION_MESSAGE);

        int selected = drinkList.getSelectedIndex();

        drinks.remove(selected);

        JOptionPane.showMessageDialog(null, "Drink Removed", "Deleted", JOptionPane.INFORMATION_MESSAGE);
    }

    public void editDrink() {
        JComboBox drinkCombo = new JComboBox();


        if (drinks.size() < 1)
            JOptionPane.showMessageDialog(null, "No drinks have been added to the system yet", "Empty", JOptionPane.WARNING_MESSAGE);
        else {
            Iterator<Drink> iterator = drinks.iterator();

            while (iterator.hasNext())
                drinkCombo.addItem(iterator.next().getName() + "\n");


            JOptionPane.showMessageDialog(null, drinkCombo, "Select Drink to Edit", JOptionPane.PLAIN_MESSAGE);

            int selected = drinkCombo.getSelectedIndex();

            final String[] drinkList = {"Beer", "Cider", "Spirit", "Wine"};

            String name = JOptionPane.showInputDialog("Enter new name of the drink");
            String type = (String) JOptionPane.showInputDialog(null, "Enter new type", "Drink type", JOptionPane.QUESTION_MESSAGE, null, drinkList, drinkList[0]);
            double price = Double.parseDouble(JOptionPane.showInputDialog("Enter new price for drink"));
            double size = Double.parseDouble(JOptionPane.showInputDialog("Enter new size for drink in ml"));
            drinks.set(selected, new Drink(name, type, price, size));

            JOptionPane.showMessageDialog(null, "Your drink has been updated successfully!", "Updated", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void createDrinkMenu() {
        JMenuItem item;

        drinkMenu = new JMenu("Drinks");

        item = new JMenuItem("Add Drink");
        item.addActionListener(this);
        drinkMenu.add(item);

        item = new JMenuItem("Edit Drink");
        item.addActionListener(this);
        drinkMenu.add(item);

        item = new JMenuItem("Delete Drink");
        item.addActionListener(this);
        drinkMenu.add(item);

        item = new JMenuItem("View Drink");
        item.addActionListener(this);
        drinkMenu.add(item);

        drinkMenu.addSeparator();

        item = new JMenuItem("Quit");
        item.addActionListener(this);
        drinkMenu.add(item);
    }

    public void createStaffMenu() {
        JMenuItem item;

        staffMenu = new JMenu("Staff");

        item = new JMenuItem("Add Staff");
        item.addActionListener(this);
        staffMenu.add(item);

        item = new JMenuItem("Edit Staff");
        item.addActionListener(this);
        staffMenu.add(item);

        item = new JMenuItem("Delete Staff");
        item.addActionListener(this);
        staffMenu.add(item);

        item = new JMenuItem("View Staff");
        item.addActionListener(this);
        staffMenu.add(item);

        staffMenu.addSeparator();

        item = new JMenuItem("Quit");
        item.addActionListener(this);
        staffMenu.add(item);
    }

    public void createSalesMenu() {
        JMenuItem item;

        salesMenu = new JMenu("Sales");

        item = new JMenuItem("Add Sale");
        item.addActionListener(this);
        salesMenu.add(item);

        item = new JMenuItem("View Sales");
        item.addActionListener(this);
        salesMenu.add(item);


        salesMenu.addSeparator();

        item = new JMenuItem("Quit");
        item.addActionListener(this);
        salesMenu.add(item);
    }

    public void addStaff() {
        String name, address, email;

        name = JOptionPane.showInputDialog("Enter the name of the staff");
        address = JOptionPane.showInputDialog("Enter the address of the staff");
        email = JOptionPane.showInputDialog("Enter the email of the staff");

        staff = new Staff(name, address, email);
        staffMembers.add(staff);

        JOptionPane.showMessageDialog(null, "Staff member '" + name + "' added to the system", "Staff Member Added", JOptionPane.INFORMATION_MESSAGE);


    }

    public void viewStaff() {
        JComboBox staffCombo = new JComboBox();
        String txt = "Staff Details:\n\n";

        if (staffMembers.size() < 1)
            JOptionPane.showMessageDialog(null, "No staff members have been added to the system yet", "Empty", JOptionPane.WARNING_MESSAGE);
        else {
            Iterator<Staff> iterator = staffMembers.iterator();

            while (iterator.hasNext())
                staffCombo.addItem(iterator.next().getName() + "\n");

            JOptionPane.showMessageDialog(null, staffCombo, "Select staff member to view", JOptionPane.PLAIN_MESSAGE);

            int selected = staffCombo.getSelectedIndex();
            txt += staffMembers.get(selected).toString();

            JOptionPane.showMessageDialog(null, txt, "Staff member details", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void deleteStaff() {
        JComboBox staffList = new JComboBox();

        for (Staff d : staffMembers)
            staffList.addItem(d.getName());

        JOptionPane.showMessageDialog(null, "Select staff member you want removed", "Delete Staff", JOptionPane.INFORMATION_MESSAGE);

        JOptionPane.showMessageDialog(null, staffList, "Delete Drink", JOptionPane.INFORMATION_MESSAGE);

        int selected = staffList.getSelectedIndex();

        staffMembers.remove(selected);

        JOptionPane.showMessageDialog(null, "Staff Member Removed", "Removed", JOptionPane.INFORMATION_MESSAGE);
    }

    public void editStaff() {
        JComboBox staffCombo = new JComboBox();
        String txt = "Drink Details:\n\n";

        if (staffMembers.size() < 1)
            JOptionPane.showMessageDialog(null, "No staff members have been added to the system yet", "Empty", JOptionPane.WARNING_MESSAGE);
        else {
            Iterator<Staff> iterator = staffMembers.iterator();

            while (iterator.hasNext())
                staffCombo.addItem(iterator.next().getName() + "\n");


            JOptionPane.showMessageDialog(null, staffCombo, "Select Staff Member to Edit", JOptionPane.PLAIN_MESSAGE);

            int selected = staffCombo.getSelectedIndex();


            String name = JOptionPane.showInputDialog("Enter new name of the staff member");
            String address = JOptionPane.showInputDialog("Enter new address of the staff member");
            String email = JOptionPane.showInputDialog("Enter new email of the staff member");

            staffMembers.set(selected, new Staff(name, address, email));

            JOptionPane.showMessageDialog(null, "Staff Member has been updated successfully!", "Updated", JOptionPane.INFORMATION_MESSAGE);
        }


    }

    public void addSale() {
        JComboBox drinkCombo = new JComboBox();
        Iterator<Drink> iterator = drinks.iterator();

        while (iterator.hasNext())
            drinkCombo.addItem(iterator.next().getName() + "\n");

        JOptionPane.showMessageDialog(null, drinkCombo, "Select Drink to View", JOptionPane.PLAIN_MESSAGE);

        int test = drinkCombo.getSelectedIndex();
        //Drink test = drinkCombo;


        double price = drinks.get(test).getPrice();

        String drinkName = drinks.get(test).getName();

        double amount = Double.parseDouble(JOptionPane.showInputDialog("You have chosen " + drinkName +
                "! The price for this is " + price + ". How much money has been given?"));

        while (amount <= price) {
            amount += Double.parseDouble(JOptionPane.showInputDialog("That is only " + amount + " the drink " +
                    drinkName + " cost " + price + " please give at least " + (price - amount) + " to purchase the drink"));
        }

        if (amount == price)
            JOptionPane.showMessageDialog(null, "You have given the exact amount", "Drink Purchased", JOptionPane.PLAIN_MESSAGE);

        if (amount > price)
            JOptionPane.showMessageDialog(null, "Here is your change of " + (amount - price), "Change", JOptionPane.PLAIN_MESSAGE);

        sale = new Sales(amount, sale.getChange());
        sales.add(sale);

    }

    public void viewSales() {
        JComboBox salesCombo = new JComboBox();
        String txt = "Sales:\n\n";

        if (sales.size() < 1)
            JOptionPane.showMessageDialog(null, "No sales have been made yet", "Empty", JOptionPane.WARNING_MESSAGE);
        else {
            Iterator<Sales> iterator = sales.iterator();

            while (iterator.hasNext())
                salesCombo.addItem(iterator.next().getAmount() + "\n");

            JOptionPane.showMessageDialog(null, salesCombo, "Select sale to view", JOptionPane.PLAIN_MESSAGE);

            int selected = salesCombo.getSelectedIndex();
            txt += sales.get(selected).toString();

            JOptionPane.showMessageDialog(null, txt, "Sales", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}