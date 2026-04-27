import java.util.ArrayList;

public class Bill {

    private Customer customer;
    private final ArrayList<Item> items;

    public Bill() {
        this.items = new ArrayList<>();
    }

    public Bill(Customer customer) {
        this();
        this.customer = customer;
    }

    // Backward-compatible constructor for older code paths
    public Bill(Customer customer, double purchaseAmount, String paymentMethod) {
        this(customer);
        addItem(new Item("Purchase", purchaseAmount, 1));
    }

    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void clearItems() {
        items.clear();
    }

    public double calculateTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getItemTotal();
        }
        return total;
    }

    public double calculateDiscount() {
        double total = calculateTotal();
        if (total > 5000) {
            return total * 0.10;
        }
        if (total > 2000) {
            return total * 0.05;
        }
        return 0;
    }

    public double calculateGST() {
        double totalAfterDiscount = calculateTotal() - calculateDiscount();
        return totalAfterDiscount * 0.18;
    }

    public double calculateFinalAmount() {
        double totalAfterDiscount = calculateTotal() - calculateDiscount();
        return totalAfterDiscount + calculateGST();
    }

    public String generateBill(String customerName) {
        StringBuilder billBuilder = new StringBuilder();
        double total = calculateTotal();
        double discount = calculateDiscount();
        double gst = calculateGST();
        double finalAmount = calculateFinalAmount();
        
        // Bill number - using timestamp as unique identifier
        String billNumber = String.valueOf(System.currentTimeMillis() / 1000);

        // Header
        billBuilder.append("╔════════════════════════════════════════════════════════════════╗\n");
        billBuilder.append("║                   DESKTOP BILLING SYSTEM                       ║\n");
        billBuilder.append("║                      OFFICIAL RECEIPT                          ║\n");
        billBuilder.append("╚════════════════════════════════════════════════════════════════╝\n\n");

        // Bill Number and Date
        billBuilder.append("Bill No: ").append(billNumber).append("                ");
        billBuilder.append("Date: ").append(java.time.LocalDate.now()).append("\n");
        billBuilder.append("─────────────────────────────────────────────────────────────────\n\n");

        // Customer Information
        billBuilder.append("SOLD TO:\n");
        billBuilder.append("Customer Name: ").append(customerName).append("\n");
        billBuilder.append("─────────────────────────────────────────────────────────────────\n\n");

        // Items Header
        billBuilder.append(String.format("%-4s | %-20s | %10s | %8s | %12s\n", 
                "#", "ITEM NAME", "PRICE", "QTY", "TOTAL"));
        billBuilder.append("─────────────────────────────────────────────────────────────────\n");

        // Items
        if (items.isEmpty()) {
            billBuilder.append("No items added.\n");
        } else {
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                billBuilder.append(String.format("%-4d | %-20s | %10.2f | %8d | %12.2f\n",
                        (i + 1),
                        item.getName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getItemTotal()));
            }
        }

        billBuilder.append("─────────────────────────────────────────────────────────────────\n\n");

        // Amounts Section
        billBuilder.append(String.format("%-45s %20.2f\n", "Subtotal:", total));
        
        if (discount > 0) {
            double discountPercent = total > 5000 ? 10 : 5;
            billBuilder.append(String.format("%-45s %20.2f  (%d%%)\n", 
                    "Discount:", discount, (int) discountPercent));
        }
        
        billBuilder.append(String.format("%-45s %20.2f\n", "Subtotal After Discount:", total - discount));
        billBuilder.append(String.format("%-45s %20.2f  (18%%)\n", "GST (Goods & Service Tax):", gst));
        billBuilder.append("═════════════════════════════════════════════════════════════════\n");
        billBuilder.append(String.format("%-45s %20.2f\n", "TOTAL AMOUNT DUE:", finalAmount));
        billBuilder.append("═════════════════════════════════════════════════════════════════\n\n");

        // Footer
        billBuilder.append("Notes:\n");
        billBuilder.append("• Discount applies on orders > Rs 5000 (10%) or > Rs 2000 (5%)\n");
        billBuilder.append("• GST is calculated at 18% on discounted amount\n");
        billBuilder.append("• Please retain this receipt for your records\n\n");
        
        billBuilder.append("─────────────────────────────────────────────────────────────────\n");
        billBuilder.append("           Thank you for your business! Visit Again!              \n");
        billBuilder.append("─────────────────────────────────────────────────────────────────\n");

        return billBuilder.toString();
    }

    public void printBill() {
        String customerName = customer != null ? customer.getName() : "Customer";
        System.out.println(generateBill(customerName));
    }
}

