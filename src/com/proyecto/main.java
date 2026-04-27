package com.proyecto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class processes the generated CSV input files and creates
 * the final sales reports required by the project.
 * It reads products, salesmen, and sales files without requesting user input.
 */

public class main {

    private static final String INPUT_FOLDER = "generated_files";
    private static final String PRODUCTS_FILE = INPUT_FOLDER + File.separator + "products.csv";
    private static final String SALESMEN_FILE = INPUT_FOLDER + File.separator + "salesmen_info.csv";
    private static final String SALESMEN_REPORT_FILE = INPUT_FOLDER + File.separator + "salesmen_report.csv";
    private static final String PRODUCTS_REPORT_FILE = INPUT_FOLDER + File.separator + "products_report.csv";

    /**
     * Main method that loads input files, processes sales information,
     * and generates the salesmen and products reports.
     */ 
    
    public static void main(String[] args) {
        try {
            Map<String, Product> products = loadProducts(PRODUCTS_FILE);
            Map<String, Salesman> salesmen = loadSalesmen(SALESMEN_FILE);
            Map<String, Integer> soldProducts = new HashMap<String, Integer>();

            processSalesFiles(products, salesmen, soldProducts);

            writeSalesmenReport(salesmen);
            writeProductsReport(products, soldProducts);

            System.out.println("Process completed successfully.");

        } catch (Exception e) {
            System.out.println("Error while processing files: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads product information from the products CSV file.
     * @param filePath path of the products file
     * @return a map containing all products by product ID
     * @throws IOException if the file cannot be read
     */
    
    private static Map<String, Product> loadProducts(String filePath) throws IOException {
        Map<String, Product> products = new HashMap<String, Product>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(";");
            if (data.length >= 3) {
                String productId = data[0].trim();
                String productName = data[1].trim();
                double price = Double.parseDouble(data[2].trim());

                products.put(productId, new Product(productId, productName, price));
            }
        }

        reader.close();
        return products;
    }

    /**
     * Loads salesman information from the salesmen CSV file.
     * @param filePath path of the salesmen information file
     * @return a map containing all salesmen by document type and document number
     * @throws IOException if the file cannot be read
     */
    
    private static Map<String, Salesman> loadSalesmen(String filePath) throws IOException {
        Map<String, Salesman> salesmen = new HashMap<String, Salesman>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(";");
            if (data.length >= 4) {
                String documentType = data[0].trim();
                String documentNumber = data[1].trim();
                String firstName = data[2].trim();
                String lastName = data[3].trim();

                String salesmanKey = buildSalesmanKey(documentType, documentNumber);
                salesmen.put(salesmanKey, new Salesman(documentType, documentNumber, firstName, lastName));
            }
        }

        reader.close();
        return salesmen;
    }
    
    /**
     * Searches and processes all sales files located in the input folder.
     * @param products map of available products
     * @param salesmen map of registered salesmen
     * @param soldProducts map used to accumulate sold quantities by product
     * @throws IOException if the folder or any sales file cannot be read
     */

    private static void processSalesFiles(Map<String, Product> products, Map<String, Salesman> salesmen,
                                          Map<String, Integer> soldProducts) throws IOException {

        File folder = new File(INPUT_FOLDER);
        File[] files = folder.listFiles();

        if (files == null) {
            throw new IOException("The input folder could not be read.");
        }

        for (File file : files) {
            if (file.isFile() && file.getName().startsWith("sales_") && file.getName().endsWith(".csv")) {
                processSingleSalesFile(file, products, salesmen, soldProducts);
            }
        }
    }
    /**
     * Processes a single sales file and updates the total sales per salesman
     * and the total quantity sold per product.
     * @param file sales file to process
     * @param products map of available products
     * @param salesmen map of registered salesmen
     * @param soldProducts map used to accumulate sold quantities by product
     * @throws IOException if the sales file cannot be read
     */

    private static void processSingleSalesFile(File file, Map<String, Product> products, Map<String, Salesman> salesmen,
                                               Map<String, Integer> soldProducts) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String firstLine = reader.readLine();

        if (firstLine == null || firstLine.trim().isEmpty()) {
            reader.close();
            return;
        }

        String[] salesmanInfo = firstLine.split(";");
        if (salesmanInfo.length < 2) {
            reader.close();
            return;
        }

        String documentType = salesmanInfo[0].trim();
        String documentNumber = salesmanInfo[1].trim();
        String salesmanKey = buildSalesmanKey(documentType, documentNumber);

        Salesman salesman = salesmen.get(salesmanKey);

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] saleData = line.split(";");
            if (saleData.length >= 2) {
                String productId = saleData[0].trim();
                int quantity = Integer.parseInt(saleData[1].trim());

                Product product = products.get(productId);

                if (salesman != null && product != null) {
                    double totalValue = product.getPrice() * quantity;
                    salesman.addTotalSold(totalValue);

                    Integer currentQuantity = soldProducts.get(productId);
                    if (currentQuantity == null) {
                        currentQuantity = 0;
                    }

                    soldProducts.put(productId, currentQuantity + quantity);
                }
            }
        }

        reader.close();
    }
    
    /**
     * Writes the salesmen report sorted by total money collected in descending order.
     * @param salesmen map containing all salesmen with their accumulated sales
     * @throws IOException if the report file cannot be written
     */

    private static void writeSalesmenReport(Map<String, Salesman> salesmen) throws IOException {
        List<Salesman> salesmenList = new ArrayList<Salesman>(salesmen.values());

        Collections.sort(salesmenList, new Comparator<Salesman>() {
            @Override
            public int compare(Salesman first, Salesman second) {
                return Double.compare(second.getTotalSold(), first.getTotalSold());
            }
        });

        BufferedWriter writer = new BufferedWriter(new FileWriter(SALESMEN_REPORT_FILE));
        writer.write("DocumentType;DocumentNumber;FirstName;LastName;TotalSold");
        writer.newLine();

        for (Salesman salesman : salesmenList) {
            writer.write(salesman.getDocumentType() + ";"
                    + salesman.getDocumentNumber() + ";"
                    + salesman.getFirstName() + ";"
                    + salesman.getLastName() + ";"
                    + salesman.getTotalSold());
            writer.newLine();
        }

        writer.close();
    }
    
    /**
     * Writes the products report sorted by total quantity sold in descending order.
     * @param products map of available products
     * @param soldProducts map containing the total quantity sold by product
     * @throws IOException if the report file cannot be written
     */

    private static void writeProductsReport(Map<String, Product> products, Map<String, Integer> soldProducts)
            throws IOException {

        List<Map.Entry<String, Integer>> productsList = new ArrayList<Map.Entry<String, Integer>>(soldProducts.entrySet());

        Collections.sort(productsList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> first, Map.Entry<String, Integer> second) {
                return Integer.compare(second.getValue(), first.getValue());
            }
        });

        BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTS_REPORT_FILE));
        writer.write("ProductID;ProductName;Price;TotalQuantitySold");
        writer.newLine();

        for (Map.Entry<String, Integer> entry : productsList) {
            String productId = entry.getKey();
            int quantitySold = entry.getValue();
            Product product = products.get(productId);

            if (product != null) {
                writer.write(product.getProductId() + ";"
                        + product.getProductName() + ";"
                        + product.getPrice() + ";"
                        + quantitySold);
                writer.newLine();
            }
        }

        writer.close();
    }
    
    /**
     * Builds a unique key for identifying a salesman.
     * @param documentType salesman document type
     * @param documentNumber salesman document number
     * @return unique salesman key
     */

    private static String buildSalesmanKey(String documentType, String documentNumber) {
        return documentType + ";" + documentNumber;
    }

    private static class Product {
        private String productId;
        private String productName;
        private double price;

        public Product(String productId, String productName, double price) {
            this.productId = productId;
            this.productName = productName;
            this.price = price;
        }

        public String getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public double getPrice() {
            return price;
        }
    }

    private static class Salesman {
        private String documentType;
        private String documentNumber;
        private String firstName;
        private String lastName;
        private double totalSold;

        public Salesman(String documentType, String documentNumber, String firstName, String lastName) {
            this.documentType = documentType;
            this.documentNumber = documentNumber;
            this.firstName = firstName;
            this.lastName = lastName;
            this.totalSold = 0.0;
        }

        public void addTotalSold(double value) {
            totalSold += value;
        }

        public String getDocumentType() {
            return documentType;
        }

        public String getDocumentNumber() {
            return documentNumber;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public double getTotalSold() {
            return totalSold;
        }
    }
}