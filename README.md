# Personal Financial Tracker

A Java-based console application that helps users manage their personal finances by tracking income and expenses, generating summaries, searching transactions, and maintaining secure backups.

## Features

### Authentication

* Password-protected access
* Change password functionality
* Limited login attempts for security

### Transaction Management

* Add transactions
* View all transactions
* Update transactions
* Delete transactions

### Financial Reports

* Balance calculation
* Category-wise expense summary
* Monthly financial summary
* Spending insights

### Search & Sort

* Search by date
* Search by category
* Sort by:

  * Date
  * Amount
  * Category

### Data Management

* Export transactions to CSV
* Import transactions from CSV
* Backup transaction records
* Restore from backup

### Budget Analysis

* Entertainment spending analysis
* Personalized budgeting tips

---

## Technologies Used

* Java
* Object-Oriented Programming (OOP)
* Collections Framework
* File Handling
* Exception Handling
* Regular Expressions
* LocalDate API

---

## Project Structure

```text
FinancialTracker/
│
├── PersonalFinacialTracker.java
├── data.csv
├── backup.csv
├── password.txt
└── README.md
```

---

## How It Works

### Login

The application starts with password authentication.

Default Password:

```text
admin123
```

Users can change their password after successful authentication.

---

### Add Transaction

Each transaction contains:

* Date
* Type (Income/Expense)
* Category
* Amount
* Note

Example:

```text
Date: 2026-08-01
Type: Expense
Category: Food
Amount: 250
Note: Lunch
```

---

### View Balance

The system automatically calculates:

```text
Balance = Total Income - Total Expense
```

---

### Export Data

All transactions can be exported to:

```text
data.csv
```

for future use and backup.

---

### Backup & Restore

Users can:

* Create backups
* Restore previous records
* Protect financial history

---

## Sample Menu

```text
1. Change Password
2. Add Transaction
3. View Transactions
4. View Balance
5. Category Summary
6. Monthly Summary
7. Search Transactions
8. Delete Transaction
9. Update Transaction
10. Export to CSV
11. Import from CSV
12. Backup Transactions
13. Restore Backup
14. Sort Transactions
15. Insights
16. Budgeting Tips
17. Exit
```

---

## Learning Outcomes

This project demonstrates:

* Java Programming Fundamentals
* File Handling
* Data Persistence
* Input Validation
* Collections Framework
* Exception Handling
* Financial Data Management
* Console-Based Application Development

---

## Future Enhancements

* MySQL Database Integration
* Spring Boot Migration
* REST API Development
* JWT Authentication
* Expense Categories Dashboard
* Monthly Budget Tracking
* Data Visualization Charts
* Email Reports
* User Profiles
* Cloud Backup Support

---

## Author

Developed as a Personal Finance Management System project to practice Java, OOP concepts, file handling, and real-world application development.
