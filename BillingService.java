import java.util.ArrayList;

public class BillingService {

    private ArrayList<Bill> billHistory = new ArrayList<>();

    public void addBill(Bill bill) {
        billHistory.add(bill);
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
