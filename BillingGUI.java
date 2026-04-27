import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BillingGUI extends JFrame {

    private JTextField customerNameField;
    private JTextField itemNameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextArea billArea;
    private JPanel itemListPanel;
    private JScrollPane itemScrollPane;
    private final Bill bill;
    private final BillingService billingService;

    public BillingGUI() {
        bill = new Bill();
        billingService = new BillingService();

        setTitle("Desktop Billing System");
        setSize(900, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Current Bill
        JPanel currentBillPanel = createCurrentBillPanel();
        tabbedPane.addTab("Current Bill", currentBillPanel);

        // Tab 2: Past Bills
        JPanel pastBillsPanel = createPastBillsPanel();
        tabbedPane.addTab("Past Bills", pastBillsPanel);

        add(tabbedPane, BorderLayout.CENTER);
        
        setVisible(true);
    }

    private JPanel createCurrentBillPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Items to Bill"));
        inputPanel.setPreferredSize(new Dimension(900, 150));

        // Customer Name
        inputPanel.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField();
        inputPanel.add(customerNameField);

        // Item Name
        inputPanel.add(new JLabel("Item Name:"));
        itemNameField = new JTextField();
        inputPanel.add(itemNameField);

        // Price
        inputPanel.add(new JLabel("Price (Rs):"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        // Quantity
        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton addItemButton = new JButton("Add Item");
        JButton generateBillButton = new JButton("Generate Bill");
        JButton resetButton = new JButton("Reset Bill");

        buttonPanel.add(addItemButton);
        buttonPanel.add(generateBillButton);
        buttonPanel.add(resetButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Items List Panel
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsPanel.setBorder(BorderFactory.createTitledBorder("Items in Current Bill"));
        
        itemListPanel = new JPanel();
        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
        
        itemScrollPane = new JScrollPane(itemListPanel);
        itemScrollPane.setPreferredSize(new Dimension(900, 250));
        itemsPanel.add(itemScrollPane, BorderLayout.CENTER);

        mainPanel.add(itemsPanel, BorderLayout.CENTER);

        // Bill Display Area
        billArea = new JTextArea();
        billArea.setEditable(false);
        billArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        JScrollPane billScrollPane = new JScrollPane(billArea);
        billScrollPane.setPreferredSize(new Dimension(900, 200));

        mainPanel.add(billScrollPane, BorderLayout.SOUTH);

        // Button Actions
        addItemButton.addActionListener(e -> addItemAction());
        generateBillButton.addActionListener(e -> generateBillAction());
        resetButton.addActionListener(e -> resetBillAction());

        return mainPanel;
    }

    private JPanel createPastBillsPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Bills List Panel
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Past Bills"));

        JPanel billsListPanel = new JPanel();
        billsListPanel.setLayout(new BoxLayout(billsListPanel, BoxLayout.Y_AXIS));
        
        JScrollPane billsScrollPane = new JScrollPane(billsListPanel);
        listPanel.add(billsScrollPane, BorderLayout.CENTER);

        mainPanel.add(listPanel, BorderLayout.WEST);

        // Bill Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createTitledBorder("Bill Content"));

        JTextArea billContentArea = new JTextArea();
        billContentArea.setEditable(false);
        billContentArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        JScrollPane contentScrollPane = new JScrollPane(billContentArea);
        contentPanel.add(contentScrollPane, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Load and display bills
        ArrayList<String> billFiles = billingService.getAllBillFiles();
        if (billFiles.isEmpty()) {
            JLabel noBillsLabel = new JLabel("No past bills found.");
            billsListPanel.add(noBillsLabel);
        } else {
            for (String billFile : billFiles) {
                JButton billButton = new JButton(billFile);
                billButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                billButton.setHorizontalAlignment(SwingConstants.LEFT);
                billButton.addActionListener(e -> {
                    String content = billingService.readBillFile(billFile);
                    billContentArea.setText(content);
                });
                billsListPanel.add(billButton);
            }
        }

        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(e -> {
            billsListPanel.removeAll();
            ArrayList<String> updatedBillFiles = billingService.getAllBillFiles();
            if (updatedBillFiles.isEmpty()) {
                JLabel noBillsLabel = new JLabel("No past bills found.");
                billsListPanel.add(noBillsLabel);
            } else {
                for (String billFile : updatedBillFiles) {
                    JButton billButton = new JButton(billFile);
                    billButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                    billButton.setHorizontalAlignment(SwingConstants.LEFT);
                    billButton.addActionListener(e2 -> {
                        String content = billingService.readBillFile(billFile);
                        billContentArea.setText(content);
                    });
                    billsListPanel.add(billButton);
                }
            }
            billsListPanel.revalidate();
            billsListPanel.repaint();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(refreshButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
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

        // Update displays
        updateItemDisplay();
        updateItemListPanel();

        // Clear fields
        itemNameField.setText("");
        priceField.setText("");
        quantityField.setText("");
        itemNameField.requestFocus();
    }

    private void updateItemListPanel() {
        itemListPanel.removeAll();

        ArrayList<Item> items = bill.getItems();
        if (items.isEmpty()) {
            JLabel emptyLabel = new JLabel("No items added yet.");
            itemListPanel.add(emptyLabel);
        } else {
            for (int i = 0; i < items.size(); i++) {
                itemListPanel.add(createItemRow(i));
            }
        }

        itemListPanel.revalidate();
        itemListPanel.repaint();
    }

    private JPanel createItemRow(int index) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        rowPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        Item item = bill.getItems().get(index);

        JLabel itemLabel = new JLabel(String.format("%d. %s | Price: Rs %.2f | Qty: %d | Total: Rs %.2f",
                index + 1, item.getName(), item.getPrice(), item.getQuantity(), item.getItemTotal()));
        itemLabel.setPreferredSize(new Dimension(500, 30));

        JButton editButton = new JButton("Edit");
        editButton.setPreferredSize(new Dimension(70, 30));
        editButton.addActionListener(e -> editItemAction(index));

        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(70, 30));
        deleteButton.addActionListener(e -> deleteItemAction(index));

        rowPanel.add(itemLabel);
        rowPanel.add(editButton);
        rowPanel.add(deleteButton);

        return rowPanel;
    }

    private void editItemAction(int index) {
        Item item = bill.getItems().get(index);

        JPanel editPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        editPanel.add(new JLabel("Item Name:"));
        JTextField nameField = new JTextField(item.getName());
        nameField.setEditable(false);
        editPanel.add(nameField);

        editPanel.add(new JLabel("Price (Rs):"));
        JTextField priceField = new JTextField(String.valueOf(item.getPrice()));
        editPanel.add(priceField);

        editPanel.add(new JLabel("Quantity:"));
        JTextField quantityField = new JTextField(String.valueOf(item.getQuantity()));
        editPanel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(this, editPanel, "Edit Item", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double newPrice = Double.parseDouble(priceField.getText().trim());
                int newQuantity = Integer.parseInt(quantityField.getText().trim());

                if (newPrice <= 0 || newQuantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Price and Quantity must be greater than zero.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                item.setPrice(newPrice);
                item.setQuantity(newQuantity);

                updateItemDisplay();
                updateItemListPanel();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Price and Quantity must be valid numbers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteItemAction(int index) {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            bill.removeItem(index);
            updateItemDisplay();
            updateItemListPanel();
        }
    }

    private void resetBillAction() {
        int confirm = JOptionPane.showConfirmDialog(this, "Clear all items and start a new bill?", "Reset Bill", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            bill.clearItems();
            customerNameField.setText("");
            itemNameField.setText("");
            priceField.setText("");
            quantityField.setText("");
            billArea.setText("");
            updateItemListPanel();
        }
    }

    private void updateItemDisplay() {
        StringBuilder display = new StringBuilder();
        display.append("===== ITEMS IN CURRENT BILL =====\n\n");

        ArrayList<Item> items = bill.getItems();
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

        if (bill.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one item.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Generate bill
        String billText = bill.generateBill(customerName);
        billArea.setText(billText);

        // Save bill
        billingService.saveBill(bill, customerName);

        JOptionPane.showMessageDialog(this, "Bill generated and saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BillingGUI::new);
    }
}

