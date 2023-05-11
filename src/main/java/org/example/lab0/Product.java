package org.example.lab0;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Product {

    private int id;
    private String name;
    private String description;
    private float price;
    private int amount;
    private Measure measure;

    public Product(){}

    public Product(int id, String name, String description, float price, int amount, Measure measure){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.measure = measure;
    }

    public Product(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.amount = product.getAmount();
        this.measure = product.getMeasure();
    }

    public Product clone(){
        return new Product(this);
    }

    @Override
    public boolean equals(Object obj){

        if (this == obj) return true;

        if (!(obj instanceof Product)) return false;

        Product prod = (Product) obj;

        return prod.id==this.id
                && prod.name.compareToIgnoreCase(this.name)==0
                && prod.description.compareToIgnoreCase(this.description)==0
                && Float.compare(prod.price, this.price)==0
                && prod.amount == this.amount
                && prod.measure == this.measure;
    }

    public void printProduct() {
        System.out.println(this.getId() + " | " + this.getName() + " | " +
                this.getDescription() + " | " + this.price + " | "
                + this.getAmount() + " | " + this.getMeasure()
                );
    }

    public static List<Product> readFromFile(String file) throws IOException {

        List<Product> productList = new ArrayList<>();

        // Create a factory for creating a JsonParser instance
        JsonFactory jsonFactory = new JsonFactory();
        // Create a JsonParser instance
        try (JsonParser jsonParser = jsonFactory.createParser(new FileInputStream(file))) {

            // Check the first token
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalStateException("Expected content to be an array");
            }

            // Iterate over the tokens until the end of the array
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                // Read a contact and do something with it
                Product prod = readProduct(jsonParser);
                productList.add(prod);
            }
        }
        return productList;
    }

    private static Product readProduct(JsonParser jsonParser) throws IOException {
        // Check the first token
        if (jsonParser.currentToken() != JsonToken.START_OBJECT) {
            throw new IllegalStateException("Expected content to be an object");
        }

        Product prod = new Product();

        // Iterate over the properties of the object
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            // Get the current property name
            String property = jsonParser.getCurrentName();

            // Move to the corresponding value
            jsonParser.nextToken();

            // Evaluate each property name and extract the value
            switch (property) {
                case "id":
                    prod.setId(jsonParser.getIntValue());
                    break;
                case "name":
                    prod.setName(jsonParser.getText());
                    break;
                case "description":
                    prod.setDescription(jsonParser.getText());
                    break;
                case "price":
                    String temp = jsonParser.getText();
                    prod.setPrice(Float.parseFloat(temp.substring(1, temp.length())));
                    break;
                case "amount":
                    prod.setAmount(Integer.parseInt(jsonParser.getText()));
                    break;
                case "measure":
                    String measure = jsonParser.getText();
                    if (!(measure.compareToIgnoreCase("null") ==0)){
                        prod.setMeasure(Measure.valueOf(jsonParser.getText()));
                    } else {
                        prod.setMeasure(null);
                    }
                    break;
                // Unknown properties are ignored
            }
        }
        return prod;
    }


    public void writeToFile(String file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);



        try {
            FileWriter out = new FileWriter(file, true);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            long len = randomAccessFile.length();
            randomAccessFile.seek(len-1); //to set pointer in the file (из-за запятых и ']')
            randomAccessFile.write(',');
            randomAccessFile.write('\n');

            objectMapper.writeValue(randomAccessFile, this);
            randomAccessFile.write(']');
            out.close();
            randomAccessFile.close();
            System.out.println("Object [ " + this + " ] was added to file [ " + file + " ]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shuffle(List<Product> productList){
        for (int i= productList.size()-1; i > 0; i--){
            int j = (int) Math.floor(Math.random() * (i+1));
            Product temp = productList.get(j);
            productList.set(j, productList.get(i));
            productList.set(i, temp);
        }
    }

    public static void writeProductsToNewFile(String filepath, List<Product> productList){

        try (PrintWriter out = new PrintWriter(new FileWriter(filepath))) {
            File myObj = new File("filename.txt");
            myObj.createNewFile();
            Gson gson = new Gson();
            String jsonString = gson.toJson(productList);
            out.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
