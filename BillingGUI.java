import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BillingGUI extends JFrame {

    private final JTextField customerNameField;
    private final JTextField itemNameField;
    private final JTextField priceField;
    private final JTextField quantityField;
    private final JTextArea billArea;
    private final Bill bill;

    public BillingGUI() {
        bill = new Bill();

        setTitle("Smart Billing System");
        setSize(700, 550);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Customer Name Label and Field
        JLabel customerLabel = new JLabel("Customer Name:");
        customerLabel.setBounds(30, 20, 120, 25);
        add(customerLabel);

        customerNameField = new JTextField();
        customerNameField.setBounds(160, 20, 200, 25);
        add(customerNameField);

        // Item Name Label and Field
        JLabel itemLabel = new JLabel("Item Name:");
        itemLabel.setBounds(30, 60, 120, 25);
        add(itemLabel);

        itemNameField = new JTextField();
        itemNameField.setBounds(160, 60, 200, 25);
        add(itemNameField);

        // Price Label and Field
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(30, 100, 120, 25);
        add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(160, 100, 200, 25);
        add(priceField);

        // Quantity Label and Field
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(30, 140, 120, 25);
        add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(160, 140, 200, 25);
        add(quantityField);

        // Add Item Button
        JButton addItemButton = new JButton("Add Item");
        addItemButton.setBounds(390, 60, 120, 30);
        add(addItemButton);

        // Generate Bill Button
        JButton generateBillButton = new JButton("Generate Bill");
        generateBillButton.setBounds(390, 100, 120, 30);
        add(generateBillButton);

        // Bill Display Area
        billArea = new JTextArea();
        billArea.setEditable(false);
        billArea.setFont(new Font("Courier New", Font.PLAIN, 11));

        JScrollPane scrollPane = new JScrollPane(billArea);
        scrollPane.setBounds(30, 190, 620, 300);
        add(scrollPane);

        // Button Actions
        addItemButton.addActionListener(e -> addItemAction());
        generateBillButton.addActionListener(e -> generateBillAction());

        setVisible(true);
    }

    private void addItemAction() {
        String itemName = itemNameField.getText().trim();
        String priceText = priceField.getText().trim();
        String quantityText = quantityField.getText().trim();

        // Validation
        if (itemName.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all item fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price;
        int quantity;

        try {
            price = Double.parseDouble(priceText);
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price and Quantity must be valid numbers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (price <= 0 || quantity <= 0) {
            JOptionPane.showMessageDialog(this, "Price and Quantity must be greater than zero.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add item
        Item newItem = new Item(itemName, price, quantity);
        bill.addItem(newItem);

        // Update display with current items
        updateItemDisplay();

        // Clear fields
        itemNameField.setText("");
        priceField.setText("");
        quantityField.setText("");
        itemNameField.requestFocus();
    }

    private void updateItemDisplay() {
        StringBuilder display = new StringBuilder();
        display.append("===== ITEMS ADDED =====\n\n");

        java.util.ArrayList<Item> items = bill.getItems();
        if (items.isEmpty()) {
            display.append("No items added yet.\n");
        } else {
            double runningTotal = 0;
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                double itemTotal = item.getItemTotal();
                runningTotal += itemTotal;
                display.append(i + 1).append(". ").append(item.getName())
                        .append(" | Price: Rs ").append(String.format("%.2f", item.getPrice()))
                        .append(" | Qty: ").append(item.getQuantity())
                        .append(" | Total: Rs ").append(String.format("%.2f", itemTotal))
                        .append("\n");
            }
            display.append("\n--- Current Total: Rs ").append(String.format("%.2f", runningTotal)).append(" ---\n");
        }

        billArea.setText(display.toString());
    }

    private void generateBillAction() {
        String customerName = customerNameField.getText().trim();

        // Validation
        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Customer name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Generate bill
        String billText = bill.generateBill(customerName);
        billArea.setText(billText);

        // Save to file
        File file = new File("bill.txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(billText);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not save bill.txt: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Open file
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Bill saved to bill.txt, but could not open automatically.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BillingGUI::new);
    }
}

