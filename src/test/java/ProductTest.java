import org.example.lab0.Measure;
import org.example.lab0.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;

//import org.testng.reporters.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;
//import org.testng.annotations.Test;

import java.lang.reflect.Field;

public class ProductTest {

    @Test
    public void testGettersAndSetters() {
        Product product = new Product();

        Field[] fields = product.getClass().getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            String capitalizedFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try {
                // Test getter
                String getterMethodName = "get" + capitalizedFieldName;
                Assertions.assertNotNull(product.getClass().getMethod(getterMethodName));

                // Test setter
                String setterMethodName = "set" + capitalizedFieldName;
                Assertions.assertNotNull(product.getClass().getMethod(setterMethodName, field.getType()));
            } catch (NoSuchMethodException e) {
                Assertions.fail("Getter or setter not found for field: " + fieldName);
            }
        }
    }



    @Test
    public void testClone() {
        Product product = new Product(1, "Product 1", "Description", 10.99f, 5, Measure.L);

        Product clonedProduct = product.clone();

        // Check if the cloned object is not the same reference
        Assertions.assertNotSame(product, clonedProduct);

        // Check if the cloned object is equal to the original object
        Assertions.assertEquals(product, clonedProduct);
    }

    @Test
    public void testEquals() {
        Product product1 = new Product(1, "Product 1", "Description", 10.99f, 5, Measure.L);
        Product product2 = new Product(1, "Product 1", "Description", 10.99f, 5, Measure.M);
        Product product3 = new Product(2, "Product 2", "Description", 20.99f, 10, Measure.XL);

        // Check if the same object is equal to itself
        Assertions.assertEquals(product1, product1);

        // Check if two objects with the same properties are equal
        Assertions.assertNotEquals(product1, product2);

        // Check if two objects with different properties are not equal
        Assertions.assertNotEquals(product1, product3);
    }


    @Test
    public void testReadFromFile() throws IOException {
        // Create a temporary test file with sample JSON data
        String testFileContent = "[{\"id\": 1, \"name\": \"Product 1\", \"description\": \"Description 1\", \"price\": 10.99, \"amount\": 5, \"measure\": \"L\"}," +
                "{\"id\": 2, \"name\": \"Product 2\", \"description\": \"Description 2\", \"price\": 20.99, \"amount\": 10, \"measure\": \"M\"}]";
        Path testFilePath = Files.createTempFile("test", ".json");
        Files.write(testFilePath, testFileContent.getBytes(), StandardOpenOption.WRITE);

        // Call the readFromFile method with the test file path
        List<Product> productList = Product.readFromFile(testFilePath.toString());

        // Verify the expected list of products
        Assertions.assertEquals(2, productList.size());

        Product product1 = new Product(1, "Product 1", "Description 1", 10.99f, 5, Measure.L);
        Product product2 = new Product(2, "Product 2", "Description 2", 20.99f, 10, Measure.M);

        Assertions.assertEquals(product1, productList.get(0));
        Assertions.assertEquals(product2, productList.get(1));

        // Clean up the test file
        Files.deleteIfExists(testFilePath);
    }




    @Test
    public void testWriteToFile() throws IOException {
        // Create a temporary test file
        Path testFilePath = Files.createTempFile("test", ".json");
        String testFile = testFilePath.toString();

        // Create a sample Product object
        Product product = new Product(1, "Test Product", "Description", 9.99f, 10, Measure.L);

        // Invoke the writeToFile method
        product.writeToFile(testFile);

        // Read the contents of the test file
        byte[] fileBytes = Files.readAllBytes(Paths.get(testFile));
        String fileContent = new String(fileBytes, StandardCharsets.UTF_8);

        // Verify the expected file content
        String expectedContent = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Test Product\",\n" +
                "    \"description\": \"Description\",\n" +
                "    \"price\": 9.99,\n" +
                "    \"amount\": 10,\n" +
                "    \"measure\": \"L\"\n" +
                "  }\n" +
                "]";
        Assertions.assertEquals(expectedContent, fileContent);

        // Clean up the test file
        Files.deleteIfExists(testFilePath);
    }


}
