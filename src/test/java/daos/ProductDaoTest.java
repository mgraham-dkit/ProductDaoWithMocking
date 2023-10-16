package daos;

import business.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author michelle
 */
public class ProductDaoTest {

    public ProductDaoTest() {
    }

    /**
     * Test of getAllProducts method, of class ProductDao.
     */
    @Test
    public void testGetAllProducts() {
        ProductDao prodDao = new ProductDao("testDatabase");
        Product p1 = new Product("S12_4473", "1957 Chevy Pickup", "Trucks and Buses", "1:12", "Exoto Designs", "1:12 scale die-cast", 6125, 55.7, 118.5);
        Product p2 = new Product("S18_3140", "1903 Ford Model A", "Vintage Cars", "1:18", "Unimax Art Galleries", "Features opening trunk", 3913, 68.3, 136.59);
        Product p3 = new Product("S18_4522", "1904 Buick Runabout", "Vintage Cars", "1:18", "Exoto Designs", "Features opening trunk", 8290, 52.66, 87.77);
        List<Product> expectedResult = new ArrayList();
        expectedResult.add(p1);
        expectedResult.add(p2);
        expectedResult.add(p3);

        List<Product> result = prodDao.getAllProducts();

        assertEquals(expectedResult, result);
    }

    /**
     * Test of getProductByCode method where there is no match present in the
     * database.
     */
    @Test
    public void testGetProductByCodeWhereNotPresent() {
        ProductDao prodDao = new ProductDao("testDatabase");
        String code = "";
        Product expResult = null;
        Product result = prodDao.getProductByCode(code);
        assertEquals(expResult, result);
    }

    /**
     * Test of getProductByCode method where there is a match present in the
     * database.
     */
    @Test
    public void testGetProductByCode() {
        ProductDao prodDao = new ProductDao("testDatabase");
        String code = "S18_3140";
        // Make a Product that matches the Product in the database
        Product expResult = new Product("S18_3140", "1903 Ford Model A", "Vintage Cars", "1:18", "Unimax Art Galleries", "Features opening trunk", 3913, 68.3, 136.59);
        // Get the product from the database
        Product result = prodDao.getProductByCode(code);
        // Confirm they are the same
        assertEquals(expResult, result);
    }

    /**
     * Test of getProductsWithGreaterQuantity method, of class ProductDao.
     */
    @Test
    public void testGetProductsWithGreaterQuantity() {
        ProductDao prodDao = new ProductDao("testDatabase");
        System.out.println("getProductsWithGreaterQuantity");
        int quantity = 6000;
        int numGreaterThanQuantity = 2;
        List<Product> result = prodDao.getProductsWithGreaterQuantity(quantity);
        assertEquals(numGreaterThanQuantity, result.size());
    }

    /**
     * Test of updateProductName method, of class ProductDao.
     */
    @Test
    public void testUpdateProductName() {
        ProductDao prodDao = new ProductDao("testDatabase");
        String productCode = "S18_3140";
        String newProductName = "Tester";

        int expResult = 1;
        int result = prodDao.updateProductName(productCode, newProductName);
        // Confirm that the method returns the appropriate value
        assertEquals(expResult, result);

        // If the update method returns an appropriate value, then confirm that 
        // the value was actually changed in the database
        if (expResult == result) {
            // Make a product matching the updated Product information for that code
            Product expectedProd = new Product("S18_3140", newProductName, "Vintage Cars", "1:18", "Unimax Art Galleries", "Features opening trunk", 3913, 68.3, 136.59);
            // Retrieve the updated product information from the database
            Product resultProd = prodDao.getProductByCode(productCode);
            // Confirm that the product was actually changed in the database
            assertEquals(resultProd, expectedProd);

            // Update the product in the database to revert it to the original value
            prodDao.updateProductName(productCode, "1903 Ford Model A");
        }
    }
}
