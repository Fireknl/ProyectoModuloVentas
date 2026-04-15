# Sales Module Project - File Generator

##  Description

This project consists of a Java application designed to generate pseudo-random input files that simulate a sales environment. The generated data represents products, salesmen, and sales transactions.

These files serve as input for a second program that will process and analyze sales data.

---

## Technologies Used

- Java 8
- Eclipse IDE

---

## Project Structure

ProyectoModuloVentas  
├── src/  
│    └── com/proyecto/  
│         └── GenerateInfoFiles.java  
└── generated_files/  
     ├── products.csv  
     ├── salesmen_info.csv  
     └── sales_*.csv  

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

### Pending Improvements
- Improve JavaDoc documentation across all classes and methods.
- Add stronger validation for malformed input files.
- Detect inconsistent data such as invalid product IDs or negative values.
- Refine pseudo-random data generation for more realistic test data.
- Evaluate implementation of optional bonus features.

### Execution Order
1. Run `GenerateInfoFiles`
2. Run `main`

Generated files will be placed in the `generated_files` folder.
