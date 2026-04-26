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

    public ArrayList<Item> getItems() {
        return items;
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

        billBuilder.append("===== BILL RECEIPT =====\n");
        billBuilder.append("Customer Name: ").append(customerName).append("\n\n");
        billBuilder.append("Items:\n");

        if (items.isEmpty()) {
            billBuilder.append("No items added.\n");
        } else {
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                billBuilder.append(i + 1)
                        .append(". ")
                        .append(item.getName())
                        .append(" | Qty: ")
                        .append(item.getQuantity())
                        .append(" | Item Total: Rs ")
                        .append(String.format("%.2f", item.getItemTotal()))
                        .append("\n");
            }
        }

        billBuilder.append("\nTotal: Rs ").append(String.format("%.2f", total)).append("\n");
        billBuilder.append("Discount: Rs ").append(String.format("%.2f", discount)).append("\n");
        billBuilder.append("GST (18%): Rs ").append(String.format("%.2f", gst)).append("\n");
        billBuilder.append("Final Amount: Rs ").append(String.format("%.2f", finalAmount)).append("\n");
        billBuilder.append("========================\n");

        return billBuilder.toString();
    }

    public void printBill() {
        String customerName = customer != null ? customer.getName() : "Customer";
        System.out.println(generateBill(customerName));
    }
}

