import java.util.ArrayList;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BillingService {

    private ArrayList<Bill> billHistory = new ArrayList<>();
    private static final String BILLS_DIR = "bills";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public BillingService() {
        // Create bills directory if it doesn't exist
        File directory = new File(BILLS_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }
        loadAllBills();
    }

    public void addBill(Bill bill) {
        billHistory.add(bill);
    }

    public void saveBill(Bill bill, String customerName) {
        String fileName = BILLS_DIR + File.separator + customerName + "_" + LocalDateTime.now().format(dateFormatter) + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(bill.generateBill(customerName));
        } catch (IOException e) {
            System.err.println("Error saving bill: " + e.getMessage());
        }
    }

    private void loadAllBills() {
        File directory = new File(BILLS_DIR);
        File[] files = directory.listFiles();
        
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    // We'll just track file existence, actual content loading happens when user views
                }
            }
        }
    }

    public ArrayList<String> getAllBillFiles() {
        ArrayList<String> billFiles = new ArrayList<>();
        File directory = new File(BILLS_DIR);
        File[] files = directory.listFiles();
        
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    billFiles.add(file.getName());
                }
            }
        }
        return billFiles;
    }

    public String readBillFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(BILLS_DIR + File.separator + fileName));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            return content.toString();
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    public void showAllBills() {

        if (billHistory.isEmpty()) {
            System.out.println("No Bills Found!");
            return;
        }

        for (Bill bill : billHistory) {
            bill.printBill();
        }
    }
}
