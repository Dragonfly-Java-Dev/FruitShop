# Fruit Shop Management System

## Introduction
The **Fruit Shop Management System** is a simple Java-based desktop application for managing a fruit shop. It allows you to track stock, calculate bills, and email receipts to customers. 

## Techstack
- **VS Code**
- **MySQL Workbench**
- **Figma -Interface design**

---

## Key Features
- **Real-time Inventory Tracking**: Instantly view available fruit stock and prices.
- **Dynamic Billing System**: Calculate totals for multiple items with automatically updated unit prices.
- **Digitized Receipts**: Send bills directly to customer emails for a paperless experience.

---

## Using the Application

### 1. Inventory Management
The **Inventory** tab is your primary dashboard for checking stock.

<img width="1261" height="948" alt="inventory_tab" src="https://github.com/user-attachments/assets/8b554b8a-e9c5-48fd-ba24-176728bd4e06" />

- **View Catalog**: See all fruits currently in stock along with their unit prices and total kilograms available.
- **Clear Fruits**: Use the "Clear Fruits" button to reset the entire inventory catalog.
    > [!WARNING]
    > **Clear Fruits** will permanently delete all fruits from the system. You will be asked for confirmation before this happens.

### 2. Customer Billing
The **Billing** tab is used for processing customer sales.

<img width="1263" height="953" alt="billing_tab" src="https://github.com/user-attachments/assets/1425803e-d9e3-4a33-8089-1ef3cfc5b0e6" />

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
<img width="602" height="421" alt="email_receipt" src="https://github.com/user-attachments/assets/0d281ac7-7876-48e7-8d45-d7fd8ada8255" />

### 3. Stock Updates
The **Update** tab allows you to manage the fruit catalog.

<img width="1261" height="954" alt="update_tab" src="https://github.com/user-attachments/assets/fa41acf0-da4e-486b-b9cb-838aeee7e24f" />

-   **Add New Fruit**: Fill in the fruit details and click **Save** to add it to your inventory.
-   **Update Fruit**: Select a fruit to auto-fill its current price and amount, then modify as needed and click **Update**.
-   **Delete Fruit**: Select an item you no longer sell and click **Delete** to remove it from the system.

---
*Dragonfly Fruit Shop Management System - Empowering your business.*
