# Smart Billing System - GUI Enhancements

## Overview
The Smart Billing System GUI has been significantly enhanced with new features for managing bills, items, and viewing past bill history.

## New Features Implemented

### 1. **Tabbed Interface**
The GUI now uses a tabbed interface with two main sections:
- **Current Bill Tab**: For creating and managing the current bill
- **Past Bills Tab**: For viewing previously generated bills

### 2. **Item Management**

#### Add Items
- Enter item details (name, price, quantity)
- Click "Add Item" to add to the bill
- Items are displayed in a dedicated list panel with details

#### Edit Items
- Click the "Edit" button next to any item
- Modify the price and quantity
- Price and quantity are validated (must be > 0)
- Changes are immediately reflected in the bill

#### Delete Items
- Click the "Delete" button next to any item
- Confirm the deletion in the dialog box
- Item is removed from the current bill
- Bill totals update automatically

### 3. **Current Bill Management**
- **Real-time Item List Display**: Shows all items with their details (name, price, quantity, total)
- **Running Total**: Displays the current total of all items
- **Reset Bill**: Clear all items and start a new bill with one click
- **Generate Bill**: Creates a finalized bill with Discount and GST calculations

### 4. **Past Bills Viewer**
- **Bill List**: Displays all previously generated bills
- **View Details**: Click any bill to view its complete details
- **Refresh**: Update the list of past bills
- Bills are saved in the `bills/` directory with timestamps

### 5. **Bill Persistence**
- Bills are automatically saved when generated
- Bills are stored in the `bills/` folder with the format: `{CustomerName}_{Timestamp}.txt`
- Each bill file contains the complete bill details including:
  - Customer name
  - Items with details
  - Subtotal
  - Discount (if applicable)
  - GST (18%)
  - Final Amount

## File Changes

### 1. **Item.java**
- Changed `price` and `quantity` from `final` to mutable fields
- Added `setPrice()` method to update item price
- Added `setQuantity()` method to update item quantity

### 2. **Bill.java**
- Added `removeItem(int index)` method to delete items from the bill
- Added `clearItems()` method to clear all items from the bill

### 3. **BillingService.java**
- Added bill persistence functionality
- Created `bills/` directory for storing bill files
- Added `saveBill()` method to save bills with timestamp
- Added `getAllBillFiles()` method to retrieve past bills
- Added `readBillFile()` method to load bill content
- Automatic initialization of bill history on startup

### 4. **BillingGUI.java** (Complete Rewrite)
- Transformed from simple single-window layout to tabbed interface
- Implemented dynamic item list panel with edit/delete buttons
- Added edit dialog for modifying item details
- Added confirmation dialogs for destructive actions
- Integrated BillingService for bill persistence
- Enhanced validation with user-friendly messages
- Improved UX with better organization and layout

## How to Use

### Creating a Bill
1. Go to the **Current Bill** tab
2. Enter the customer name
3. Add items one by one by entering:
   - Item name
   - Price
   - Quantity
4. Click "Add Item" to add to the list
5. Edit or delete items as needed
6. Click "Generate Bill" to finalize

### Editing Items
1. View the item in the list
2. Click the "Edit" button
3. Modify the price and/or quantity
4. Click OK to save changes

### Deleting Items
1. Click the "Delete" button next to the item
2. Confirm the deletion
3. The item is removed and totals are updated

### Viewing Past Bills
1. Go to the **Past Bills** tab
2. Click any bill from the list to view its details
3. Click "Refresh List" to update the list of available bills

## Calculations
- **Subtotal**: Sum of all item totals (price × quantity)
- **Discount**: 
  - 10% if subtotal > Rs 5000
  - 5% if subtotal > Rs 2000
  - No discount if subtotal ≤ Rs 2000
- **GST**: 18% of (subtotal - discount)
- **Final Amount**: (subtotal - discount) + GST

## Technical Details
- Uses Java Swing for the GUI
- All data validation on the client side
- Bill files are stored as plain text for easy access
- Timestamp format: `yyyy-MM-dd_HH-mm-ss`
- All monetary values are formatted to 2 decimal places

---
**Version**: 2.0
**Last Updated**: April 2026

