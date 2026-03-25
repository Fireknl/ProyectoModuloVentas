package com.proyecto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateInfoFiles {

    private static final String OUTPUT_FOLDER = "generated_files";
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {

        try {
            int productsCount = 10;
            int salesmenCount = 5;
            int salesPerSalesman = 8;

            createProductsFile(productsCount);
            createSalesManInfoFile(salesmenCount);

            for (int i = 0; i < salesmenCount; i++) {
                String docType = getRandomDocType();
                long id = 1000 + i;
                createSalesMenFile(salesPerSalesman, docType, id);
            }

            System.out.println("Generation completed successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createProductsFile(int count) throws IOException {
        createFolder();

        BufferedWriter writer = new BufferedWriter(
                new FileWriter(OUTPUT_FOLDER + "/products.csv"));

        for (int i = 0; i < count; i++) {
            String id = "P" + (i + 1);
            String name = "Producto" + (i + 1);
            double price = 1000 + RANDOM.nextInt(9000);

            writer.write(id + ";" + name + ";" + price);
            writer.newLine();
        }

        writer.close();
    }

    public static void createSalesManInfoFile(int count) throws IOException {
        createFolder();

        BufferedWriter writer = new BufferedWriter(
                new FileWriter(OUTPUT_FOLDER + "/salesmen_info.csv"));

        for (int i = 0; i < count; i++) {
            String type = getRandomDocType();
            long id = 1000 + i;
            String name = "Nombre" + i;
            String last = "Apellido" + i;

            writer.write(type + ";" + id + ";" + name + ";" + last);
            writer.newLine();
        }

        writer.close();
    }

    public static void createSalesMenFile(int count, String docType, long id) throws IOException {
        createFolder();

        BufferedWriter writer = new BufferedWriter(
                new FileWriter(OUTPUT_FOLDER + "/sales_" + id + ".csv"));

        writer.write(docType + ";" + id);
        writer.newLine();

        for (int i = 0; i < count; i++) {
            String productId = "P" + (RANDOM.nextInt(10) + 1);
            int quantity = RANDOM.nextInt(10) + 1;

            writer.write(productId + ";" + quantity + ";");
            writer.newLine();
        }

        writer.close();
    }

    private static void createFolder() {
        File folder = new File(OUTPUT_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    private static String getRandomDocType() {
        String[] types = {"CC", "TI", "CE"};
        return types[RANDOM.nextInt(types.length)];
    }
}