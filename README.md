# ProyectoModuloVentas - Sales Processing System

## Description

This project is a Java 8 application developed in Eclipse IDE that simulates and processes a sales environment.

It consists of two main components:
1. A file generator that creates pseudo-random CSV files (products, salesmen, and sales data).
2. A processing module that reads these files, calculates results, and generates sales reports.

The system works automatically without requiring user input.

---

## Technologies Used

- Java 8
- Eclipse IDE

---

## Project Structure

ProyectoModuloVentas  
├── src/  
│    └── com/proyecto/  
│         ├── GenerateInfoFiles.java  
│         └── main.java  
├── generated_files/  
│     ├── products.csv  
│     ├── salesmen_info.csv  
│     ├── sales_*.csv  
│     ├── salesmen_report.csv  
│     └── products_report.csv  
├── conslusion.txt  
└── README.md  

---

## How to Run

1. Open the project in Eclipse IDE  
2. Locate the class: GenerateInfoFiles.java  
3. Run the program using: Run As → Java Application  
4. The program will automatically generate the files inside the folder: generated_files  

---

## Generated Files
### products.csv

Contains product information:

ProductID;ProductName;Price  

---
## Output Reports

### salesmen_report.csv

Contains:
DocumentType;DocumentNumber;FirstName;LastName;TotalSold

- Shows total money collected by each salesman
- Sorted in descending order (highest to lowest)

---

### products_report.csv

Contains:
ProductID;ProductName;Price;TotalQuantitySold

- Shows total quantity sold per product
- Sorted in descending order
### salesmen_info.csv

Contains salesman information:

DocumentType;DocumentNumber;FirstName;LastName  

---

### sales_*.csv

Each file represents the sales made by a single salesman:

DocumentType;DocumentNumber  
ProductID;Quantity;  

---

## Data Generation

All data is generated using pseudo-random values, including:

- Document types (CC, TI, CE)  
- Product IDs  
- Product prices  
- Sales quantities  

This allows realistic testing scenarios for the main application.

---

## Features

- No user input required  
- Automatically generates multiple files  
- Organized and readable code  
- Compatible with Java 8  
- Structured for future data processing  

---

## Author

Santiago Perez Farfan

## Delivery 2 Update

The project now includes a preliminary functional version of the complete sales processing module.

### Implemented Features

#### GenerateInfoFiles
- Generates `products.csv` with pseudo-random product data.
- Generates `salesmen_info.csv` with pseudo-random salesman information.
- Generates multiple `sales_*.csv` files with pseudo-random sales records.
- Ensures consistency between salesman information and sales files.
- Displays success or error messages after execution.

#### main
- Reads all generated input files automatically.
- Processes salesman sales data from all `sales_*.csv` files.
- Calculates total revenue per salesman.
- Calculates total quantity sold per product.
- Generates `salesmen_report.csv` ordered by total revenue (descending).
- Generates `products_report.csv` ordered by quantity sold (descending).
- Displays success or error messages after execution.


### Execution Order
1. Run `GenerateInfoFiles`
2. Run `main`

Generated files will be placed in the `generated_files` folder.

## Conclusion

A file named `conslusion.txt` is included in the project. It contains a summary of:
- Learning outcomes
- Professional applications
- Difficulties encountered during development
