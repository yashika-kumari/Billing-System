# 💳 Billing System – Java OOP Billing Management System

## 📌 Project Overview
A Java-based Object-Oriented Billing Management System with a user-friendly Swing GUI for managing multiple items per bill. The system provides real-time item addition, discount calculation, GST processing, and automatic bill file generation.

This project demonstrates strong OOP principles, modular design, and scalable architecture suitable for retail and educational purposes.

---

## 🚀 Features
- Single-frame Swing GUI interface
- Multi-item billing using ArrayList<Item>
- Real-time item addition with live display
- Discount calculation based on total amount (10% for >5000, 5% for >2000)
- GST (18%) automatic calculation
- Running total preview before final bill
- Bill generation with formatted receipt
- Automatic save to bill.txt file
- Auto-open generated bill file
- Input validation with error messages (JOptionPane)
- Item details display (name, quantity, price, item total)

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
├── bill.txt (auto-generated)
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

A Swing GUI window will open. Use it to:
1. Enter customer name
2. Add items one by one (item name, price, quantity)
3. View items in the display area
4. Click "Generate Bill" to create formatted receipt
5. Bill automatically saves to bill.txt and opens


---

## 📸 Sample Output

### GUI Window
- Customer Name field
- Item fields (name, price, quantity)
- Add Item button
- Generate Bill button
- Display area showing items added

### Generated Bill (bill.txt)
```
===== BILL RECEIPT =====
Customer Name: John Doe

Items:
1. Laptop | Qty: 1 | Item Total: Rs 50000.00
2. Mouse | Qty: 2 | Item Total: Rs 2000.00
3. Keyboard | Qty: 1 | Item Total: Rs 5000.00

Total: Rs 57000.00
Discount: Rs 5700.00
GST (18%): Rs 9234.00
Final Amount: Rs 60534.00
========================
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
- Bill history management
- Customer profiles
- Inventory management
- Print functionality
- Multiple bill management in GUI
- JavaFX upgrade for modern UI
- Spring Boot REST API Version  

---

## 👨‍💻 Contributors
**Yashika Kumari**

---

## 📜 License
This project is for learning and academic purposes.
