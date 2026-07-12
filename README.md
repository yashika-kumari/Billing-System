# 💳 Billing System – Java OOP Billing Management System

## 📌 Project Overview
A Java-based Object-Oriented Billing Management System with a user-friendly Swing GUI for managing multiple items per bill. The system provides real-time item addition, discount calculation, GST processing, and automatic bill file generation.

This project demonstrates strong OOP principles, modular design, and scalable architecture suitable for retail and educational purposes.

---

## 🚀 Features
- **Tabbed Interface** - Separate tabs for Current Bill and Past Bills
- Multi-item billing using ArrayList<Item>
- Real-time item addition with live display
- **Edit Item Details** - Modify price and quantity of added items
- **Delete Items** - Remove items from the bill before finalizing
- **View Past Bills** - Access all previously generated bills with timestamps
- Discount calculation based on total amount (10% for >5000, 5% for >2000)
- GST (18%) automatic calculation (Hardcoded for now)
- Professional invoice-style bill format with proper formatting
- Bill persistence - Bills saved with customer name and timestamp
- Input validation with error messages (JOptionPane)
- Item details display with edit/delete buttons
- Reset Bill functionality - Start fresh billing

---

## 🛠 Technologies Used
- Java
- Object-Oriented Programming (OOP)
- Java Collections (ArrayList)
- Java Swing (GUI Framework)
- File I/O (FileWriter)

---

## 📂 Project Structure
Billing-System/
│
├── Customer.java
├── Bill.java
├── Item.java
├── BillingService.java
├── BillingGUI.java
├── SmartBillingSystem.java
├── bills/                  (auto-generated directory for storing bills)
├── bill.txt               (deprecated - auto-generated single bill file)
├── ENHANCEMENTS.md        (detailed documentation of new features)
├── README.md
└── .gitignore


---

## ⚙️ How to Run

### Step 1: Clone Repository
```bash
git clone https://github.com/yashika-kumari/Billing-System.git
```

### Step 2: Navigate to Project Folder
```bash
cd Billing-System
```

### Step 3: Compile Java Files
```bash
javac *.java
```

### Step 4: Run Application
```bash
java SmartBillingSystem
```

A Swing GUI window will open with two tabs:

**Current Bill Tab:**
1. Enter customer name
2. Add items one by one (item name, price, quantity)
3. View items in the list panel with edit/delete options
4. Edit item details or delete items as needed
5. Click "Generate Bill" to create a professional receipt
6. Bill automatically saves to bills/ folder with timestamp

**Past Bills Tab:**
1. View all previously generated bills
2. Click any bill to view its complete details
3. Use "Refresh List" to update the list


---

## 📸 Sample Output

### GUI Window
- **Current Bill Tab**: Customer name field, item input fields, add item button, generate bill button, items list with edit/delete buttons
- **Past Bills Tab**: List of past bills, bill content viewer, refresh button

### Generated Bill (Professional Format)
```
╔════════════════════════════════════════════════════════════════╗
║                   SMART BILLING SYSTEM                        ║
║                      OFFICIAL RECEIPT                         ║
╚════════════════════════════════════════════════════════════════╝

Bill No: 1234567890               Date: 2026-04-27
─────────────────────────────────────────────────────────────────

SOLD TO:
Customer Name: John Doe
─────────────────────────────────────────────────────────────────

#    | ITEM NAME            |      PRICE |      QTY |       TOTAL
─────────────────────────────────────────────────────────────────
1    | Laptop               |   50000.00 |        1 |   50000.00
2    | Mouse                |    1000.00 |        2 |    2000.00
3    | Keyboard             |    5000.00 |        1 |    5000.00
─────────────────────────────────────────────────────────────────

Subtotal:                                            57000.00
Discount:                                   5700.00  (10%)
Subtotal After Discount:                              51300.00
GST (Goods & Service Tax):                             9234.00  (18%)
═════════════════════════════════════════════════════════════════
TOTAL AMOUNT DUE:                                     60534.00
═════════════════════════════════════════════════════════════════

Notes:
• Discount applies on orders > Rs 5000 (10%) or > Rs 2000 (5%)
• GST is calculated at 18% on discounted amount
• Please retain this receipt for your records

─────────────────────────────────────────────────────────────────
           Thank you for your business! Visit Again!              
─────────────────────────────────────────────────────────────────
```


---

## 🎯 Learning Outcomes
- Applied Object-Oriented Programming (OOP) principles
- Implemented modular class-based design
- Worked with Java Collections (ArrayList)
- Built GUI with Java Swing framework
- Handled file I/O operations
- Implemented input validation and error handling
- Structured multi-item billing logic  

---

## 🔮 Future Enhancements
- Database Integration (MySQL + JDBC)
- Customer profiles and history
- Inventory management
- Print to PDF functionality
- Search and filter bills
- Tax configuration settings
- Multi-currency support
- JavaFX upgrade for modern UI
- Spring Boot REST API Version  

---

## 👨‍💻 Contributors
**Yashika Kumari**

---

## 📜 License
This project is for learning and academic purposes.
