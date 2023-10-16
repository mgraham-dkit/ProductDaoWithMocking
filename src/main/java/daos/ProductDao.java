package daos;

import business.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michelle
 */
public class ProductDao extends Dao implements ProductDaoInterface
{
    /**
     * Initialises a ProductDao to access the specified database name
     * 
     * @param databaseName The name of the MySQL database to be accessed (this database should 
     * be running on localhost and listening on port 3306).
     */
    
    public ProductDao(String databaseName)
    {
        super(databaseName);
    }

    /**
     * Initialises a ProductDao that will employ the supplied connection for its interactions
     *
     * @param con The connection to be used to carry out this Product table interaction.
     */
    public ProductDao(Connection con){
        super(con);
    }
    
    /**
     * Returns a list of {@code Product} objects based on information in the database. All 
     * product entries in the Products table are selected from the database and stored
     * as {@code Product} objects in a {@code List}.
     * 
     * @return The {@code List} of all product entries in the Product table. 
     * This {@code List} may be empty where no products are present in the database.
     */
    @Override
    public List<Product> getAllProducts()
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> products = new ArrayList();
        
        try{
            con = getConnection();

            String query = "Select * from Products";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(); 
            
            while(rs.next())
            {
                Product p = new Product(rs.getString("productCode"), rs.getString("productName"), rs.getString("productLine"), rs.getString("productScale"), rs.getString("productVendor"), rs.getString("productDescription"), rs.getInt("quantityInStock"), rs.getDouble("buyPrice"), rs.getDouble("MSRP"));
                products.add(p);
            }
        }catch (SQLException e) {
            System.out.println("Exception occured in the getAllProducts() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the getAllProducts() method: " + e.getMessage());
            }
        }
        
        return products;
    }
    
    /**
     * Searches for a product entry matching the code supplied as a parameter. 
     * 
     * @param code The id of the Product to be found in the database.
     * @return The {@code Product} contained in the database matching the supplied 
     * product code, or {@code null} otherwise.
     */
    @Override
    public Product getProductByCode(String code)
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Product p = null;
        
        try{
            con = getConnection();

            String query = "Select * from Products where productCode = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, code);
            // verify(ps).setString(1, "S12_1234");
            rs = ps.executeQuery(); 
            
            if(rs.next())
            {
                p = new Product(rs.getString("productCode"), rs.getString("productName"), rs.getString("productLine"), rs.getString("productScale"), rs.getString("productVendor"), rs.getString("productDescription"), rs.getInt("quantityInStock"), rs.getDouble("buyPrice"), rs.getDouble("MSRP"));
            }
        }catch (SQLException e) {
            System.out.println("Exception occured in the getProductByCode() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the getProductByCode() method: " + e.getMessage());
            }
        }
        return p;
    }
    
    
    /**
     * Searches for any product entries with a higher quantity in stock 
     * than the specified amount. 
     * 
     * @param quantity The minimum quantity in stock to be checked for.
     * @return The {@code List} of all product entries in the Product table 
     * This {@code List} may be empty where no products are present in the database
     * with a quantity greater than the specified value.
     */
    @Override
    public List<Product> getProductsWithGreaterQuantity(int quantity)
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> products = new ArrayList();
        
        try{
            con = getConnection();

            String query = "Select * from Products where quantityInStock > ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, quantity);
            rs = ps.executeQuery(); 
            
            while(rs.next())
            {
                Product p = new Product(rs.getString("productCode"), rs.getString("productName"), rs.getString("productLine"), rs.getString("productScale"), rs.getString("productVendor"), rs.getString("productDescription"), rs.getInt("quantityInStock"), rs.getDouble("buyPrice"), rs.getDouble("MSRP"));
                products.add(p);
            }
        }catch (SQLException e) {
            System.out.println("Exception occured in the getProductsWithGreaterQuantity() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the getProductsWithGreaterQuantity() method: " + e.getMessage());
            }
        }
        
        return products;
    }
    
    
    /**
     * Amends the name of any product in the database matching the specified code.
     * 
     * @param productCode The product code of the Product to be renamed (this is a unique value)
     * @param newProductName The new name to assign to the Product being changed
     * @return The number of product entries changed in the Product table.
     */
    @Override
    public int updateProductName(String productCode, String newProductName)
    {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;
        
        try{
            con = getConnection();

            String query = "UPDATE Products SET productName = ? WHERE productCode = ?";
            
            ps = con.prepareStatement(query);
            ps.setString(1, newProductName);
            ps.setString(2, productCode);
            
            rowsAffected = ps.executeUpdate(); 
            
        }catch (SQLException e) {
            System.out.println("Exception occured in the updateProductName() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the updateProductName() method");
                e.getMessage();
            }
        }
        
        return rowsAffected;
    }
}
