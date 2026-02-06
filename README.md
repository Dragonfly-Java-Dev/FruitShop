# 🍎 Dragonfly Fruit Shop POS System

**A comprehensive Point of Sale (POS) and Inventory Management System built for small-scale fruit shops.**

This desktop application allows shop owners to manage stock, process sales, generate invoices, and automatically send digital receipts to customers via email. It is packaged as a standalone Windows executable (`.exe`) for easy deployment.

## 🛠️ Technology Stack

### Core Technologies
* **Language:** Java (JDK 17 / 1.8)
* **Build Tool:** Apache Maven
* **Database:** MySQL Server 8.0
* **IDE:** Visual Studio Code

### Frontend (Java Swing)
The Graphical User Interface is built using standard Swing components:
* **Containers:** `JFrame`, `JPanel`
* **Data Display:** `JTable`, `DefaultTableModel`
* **Inputs:** `JTextField`, `JComboBox`
* **Controls:** `JButton`, `JScrollPane`
* **Notifications:** `JOptionPane` (Custom alerts for Success/Error/Warning)

### Backend & Libraries
Managed via `pom.xml`:
* **Database Connectivity:** `mysql-connector-j` (v8.3.0)
* **Email Service:** `javax.mail` (v1.6.2) - *Powered by com.sun.mail*
* **Build Plugin:** `maven-shade-plugin` (v3.5.0) - *Used to create the Fat JAR*

### Deployment
* **Wrapper Tool:** **Launch4j** (v3.50)
* **Output:** Native Windows Executable (`.exe`)
* **Configuration:** Custom `.ico` integration and automatic JRE detection.

---

## 🚀 Features

* **🛒 Billing System:** Real-time bill calculation with a "Select & Add" interface.
* **📧 Digital Receipts:** Automatically sends a professional HTML-formatted receipt to the customer's email using **JavaMail API**.
* **📦 Inventory Management:** Full CRUD (Create, Read, Update, Delete) capabilities to manage fruit stock, prices, and quantities.
* **📊 Live Data:** Dynamic inventory tracking connected to a **MySQL** database.
* **🖥️ User-Friendly GUI:** Clean, tabbed interface built with **Java Swing**.
* **💿 Portable Deployment:** Distributed as a native Windows `.exe` file with a custom icon.

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
