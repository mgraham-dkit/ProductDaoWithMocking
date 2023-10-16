package daos;

import business.Product;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductDaoIsolationTest {
    public ProductDaoIsolationTest()
    {
    }

    /**
     * Test of getAllProducts method, of class ProductDao.
     */
    @Test
    public void testGetAllProducts() throws SQLException
    {
        // Create expected results
        Product p1 = new Product("ProdCode1", "ProdName1", "ProdLine1", "ProdScale1", "ProdVendor1", "ProdDesc1", 1000, 99.99, 15.99);
        Product p2 = new Product("ProdCode2", "ProdName2", "ProdLine2", "ProdScale2", "ProdVendor2", "ProdDesc2", 2000, 299.99, 215.99);
        Product p3 = new Product("ProdCode3", "ProdName3", "ProdLine3", "ProdScale3", "ProdVendor3", "ProdDesc3", 3000, 399.99, 315.99);
        ArrayList<Product> expectedResults = new ArrayList();
        expectedResults.add(p1);
        expectedResults.add(p2);
        expectedResults.add(p3);

        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        // Fill mock objects with appropriatel dummy data
        when(dbConn.prepareStatement("Select * from Products")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        // Want 3 results in the resultset, so need true to be returned 3 times
        when(rs.next()).thenReturn(true, true, true, false);
        // Fill in the resultset
        when(rs.getString("productCode")).thenReturn(p1.getCode(), p2.getCode(), p3.getCode());
        when(rs.getString("productName")).thenReturn(p1.getName(), p2.getName(), p3.getName());
        when(rs.getString("productLine")).thenReturn(p1.getLine(), p2.getLine(), p3.getLine());
        when(rs.getString("productScale")).thenReturn(p1.getScale(), p2.getScale(), p3.getScale());
        when(rs.getString("productVendor")).thenReturn(p1.getVendor(), p2.getVendor(), p3.getVendor());
        when(rs.getString("productDescription")).thenReturn(p1.getDescription(), p2.getDescription(), p3.getDescription());
        when(rs.getInt("quantityInStock")).thenReturn(p1.getQtyInStock(), p2.getQtyInStock(), p3.getQtyInStock());
        when(rs.getDouble("buyPrice")).thenReturn(p1.getBuyPrice(),p2.getBuyPrice(),p3.getBuyPrice());
        when(rs.getDouble("MSRP")).thenReturn(p1.getMsrp(), p2.getMsrp(), p3.getMsrp());

        ProductDao prodDao = new ProductDao(dbConn);
        List<Product> result = prodDao.getAllProducts();

        // Check that the arraylist of Products we created as expected results at the start
        // equals the arraylist we got back from our method being tested.
        // If it does, then the method worked as expected
        assertEquals(expectedResults, result);

        System.out.println(result);
    }

    /**
     * Test of getProductByCode method, of class ProductDao.
     */
    @Test
    public void testGetProductByCode() throws SQLException
    {
        // Create expected results
        Product p1 = new Product("ProdCode1", "ProdName1", "ProdLine1", "ProdScale1", "ProdVendor1", "ProdDesc1", 1000, 99.99, 15.99);

        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        // Fill mock objects with appropriate dummy data
        when(dbConn.prepareStatement("Select * from Products where productCode = ?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        // Want 1 result in the resultset, so need true to be returned once
        when(rs.next()).thenReturn(true);
        // Fill in the resultset
        when(rs.getString("productCode")).thenReturn(p1.getCode());
        when(rs.getString("productName")).thenReturn(p1.getName());
        when(rs.getString("productLine")).thenReturn(p1.getLine());
        when(rs.getString("productScale")).thenReturn(p1.getScale());
        when(rs.getString("productVendor")).thenReturn(p1.getVendor());
        when(rs.getString("productDescription")).thenReturn(p1.getDescription());
        when(rs.getInt("quantityInStock")).thenReturn(p1.getQtyInStock());
        when(rs.getDouble("buyPrice")).thenReturn(p1.getBuyPrice());
        when(rs.getDouble("MSRP")).thenReturn(p1.getMsrp());

        ProductDao prodDao = new ProductDao(dbConn);
        Product result = prodDao.getProductByCode("ProdCode1");

        // Confirm that the SQL query used in the select actually uses the product code specified in the test
        verify(ps).setString(1, "ProdCode1");
        // Check that the Product we created as expected result at the start
        // equals the Product we got back from our method being tested.
        // If it does, then the method worked as expected
        assertEquals(p1, result);
    }
}
