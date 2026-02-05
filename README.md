# Fruit Shop Management System

## Introduction
The **Fruit Shop Management System** is a simple Java-based desktop application for managing a fruit shop. It allows you to track stock, calculate bills, and email receipts to customers. 


---

## Key Features
- **Real-time Inventory Tracking**: Instantly view available fruit stock and prices.
- **Dynamic Billing System**: Calculate totals for multiple items with automatically updated unit prices.
- **Digitized Receipts**: Send bills directly to customer emails for a paperless experience.
- **Stock Management**: Easily add new fruits, update existing prices, or remove items from the catalog.
- **Global Synchronization**: Changes made in any part of the system are reflected across all tabs immediately.

---

## Using the Application

### 1. Inventory Management
The **Inventory** tab is your primary dashboard for checking stock.

![Inventory Tab](src/main/resources/images/docs/inventory_tab.png)

- **View Catalog**: See all fruits currently in stock along with their unit prices and total kilograms available.
- **Clear Fruits**: Use the "Clear Fruits" button to reset the entire inventory catalog.
    > [!WARNING]
    > **Clear Fruits** will permanently delete all fruits from the system. You will be asked for confirmation before this happens.

### 2. Customer Billing
The **Billing** tab is used for processing customer sales.

![Billing Tab & Success](src/main/resources/images/docs/billing_tab.png)

**To Create a Bill:**
1.  **Select Fruit**: Choose the fruit the customer is buying.
2.  **Enter Amount**: Type in the weight in kilograms (kg).
3.  **Add**: Click the **Add** button to add the item to the bill list.
4.  **Repeat**: Continue adding items until the purchase is complete.

**Finalizing the Bill:**
-   **Calculate**: Click **Calculate** to see the grand total of all items.
-   **Email Bill**: Enter the customer's email address and click **Send Bill** to deliver a digital receipt.
-   **Clear**: Use the **Clear** button to wipe the current bill and prepare for the next customer.

#### Email Receipt Example
![Email Receipt](src/main/resources/images/docs/email_receipt.png)

### 3. Stock Updates
The **Update** tab allows you to manage the fruit catalog.

![Update Tab](src/main/resources/images/docs/update_tab.png)

-   **Add New Fruit**: Fill in the fruit details and click **Save** to add it to your inventory.
-   **Update Fruit**: Select a fruit to auto-fill its current price and amount, then modify as needed and click **Update**.
-   **Delete Fruit**: Select an item you no longer sell and click **Delete** to remove it from the system.

---
*Dragonfly Fruit Shop Management System - Empowering your business.*
