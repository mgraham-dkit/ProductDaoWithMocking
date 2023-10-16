package daos;

import business.Product;

import java.util.List;

/**
 * @author Michelle
 */
public interface ProductDaoInterface 
{
    public Product getProductByCode(String code);
    public List<Product> getAllProducts();
    public List<Product> getProductsWithGreaterQuantity(int quantity);
    public int updateProductName(String productCode, String newProductName);
}
