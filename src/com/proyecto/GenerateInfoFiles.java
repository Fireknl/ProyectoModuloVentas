package com.proyecto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class is responsible for generating all the input CSV files
 * required for the sales processing system.
 * It creates products, salesmen information, and sales files.
 */

public class GenerateInfoFiles {

    private static final String OUTPUT_FOLDER = "generated_files";
    private static final Random RANDOM = new Random();

    private static final String[] DOCUMENT_TYPES = { "CC", "TI", "CE" };

    private static final String[] FIRST_NAMES = {
            "Juan", "Maria", "Carlos", "Ana", "Luis",
            "Laura", "Pedro", "Sofia", "Andres", "Camila"
    };

    private static final String[] LAST_NAMES = {
            "Gomez", "Rodriguez", "Perez", "Lopez", "Martinez",
            "Garcia", "Ramirez", "Torres", "Diaz", "Moreno"
    };

    private static final Map<Long, String> SALESMAN_DOCUMENT_TYPES = new HashMap<Long, String>();
    private static final Map<Long, String> SALESMAN_NAMES = new HashMap<Long, String>();

    private static int generatedProductsCount = 0;
    
    /**
     * Main method that triggers the generation of all test files.
     * It creates products, salesmen, and sales data.
     */

    public static void main(String[] args) {
        try {
            int productsCount = 10;
            int salesmenCount = 5;
            int salesPerSalesman = 8;

            createFolder();
            createProductsFile(productsCount);
            createSalesManInfoFile(salesmenCount);

            for (int i = 0; i < salesmenCount; i++) {
                long id = 1000 + i;
                String name = SALESMAN_NAMES.get(id);
                createSalesMenFile(salesPerSalesman, name, id);
            }

            System.out.println("Generation completed successfully.");

        } catch (Exception e) {
            System.out.println("Error while generating files: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Generates a CSV file with product information.
     * @param productsCount number of products to generate
     * @throws IOException if file cannot be written
     */
    
    public static void createProductsFile(int productsCount) throws IOException {
        BufferedWriter writer = null;
        generatedProductsCount = productsCount;

        try {
            writer = new BufferedWriter(new FileWriter(OUTPUT_FOLDER + File.separator + "products.csv"));

            for (int i = 0; i < productsCount; i++) {
                String productId = "P" + (i + 1);
                String productName = "Producto" + (i + 1);
                double productPrice = 1000 + RANDOM.nextInt(9000);

                writer.write(productId + ";" + productName + ";" + productPrice);
                writer.newLine();
            }

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    /**
     * Generates a CSV file with salesmen information.
     * @param salesmanCount number of salesmen to generate
     * @throws IOException if file cannot be written
     */

    public static void createSalesManInfoFile(int salesmanCount) throws IOException {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(OUTPUT_FOLDER + File.separator + "salesmen_info.csv"));

            for (int i = 0; i < salesmanCount; i++) {
                long id = 1000 + i;
                String documentType = getRandomDocType();
                String firstName = getRandomFirstName();
                String lastName = getRandomLastName();

                SALESMAN_DOCUMENT_TYPES.put(id, documentType);
                SALESMAN_NAMES.put(id, firstName);

                writer.write(documentType + ";" + id + ";" + firstName + ";" + lastName);
                writer.newLine();
            }

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    /**
     * Generates a CSV file with sales for a specific salesman.
     * @param randomSalesCount number of sales records
     * @param name salesman name
     * @param id salesman identifier
     * @throws IOException if file cannot be written
     */

    public static void createSalesMenFile(int randomSalesCount, String name, long id) throws IOException {
        BufferedWriter writer = null;

        try {
            String documentType = SALESMAN_DOCUMENT_TYPES.get(id);

            if (documentType == null) {
                throw new IOException("No document type found for salesman with id: " + id);
            }

            if (generatedProductsCount <= 0) {
                throw new IOException("Products file must be generated before sales files.");
            }

            writer = new BufferedWriter(new FileWriter(OUTPUT_FOLDER + File.separator + "sales_" + id + ".csv"));

            writer.write(documentType + ";" + id);
            writer.newLine();

            for (int i = 0; i < randomSalesCount; i++) {
                String productId = "P" + (RANDOM.nextInt(generatedProductsCount) + 1);
                int quantitySold = RANDOM.nextInt(10) + 1;

                writer.write(productId + ";" + quantitySold + ";");
                writer.newLine();
            }

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private static void createFolder() {
        File folder = new File(OUTPUT_FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    private static String getRandomDocType() {
        return DOCUMENT_TYPES[RANDOM.nextInt(DOCUMENT_TYPES.length)];
    }

    private static String getRandomFirstName() {
        return FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
    }

    private static String getRandomLastName() {
        return LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
    }
}