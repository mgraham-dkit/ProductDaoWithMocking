package app;

import business.Product;
import daos.ProductDao;
import daos.ProductDaoInterface;

import java.util.List;
import java.util.Scanner;

/**
 * @author Michelle
 */
public class SampleProductApp {
    public static void main(String[] args) {
        System.out.println("This program selects the name and id of all products in database and displays them on the screen");
        System.out.println("The user can then enter a specific id to see all of its details");
        System.out.println("***************************************************************");
        
        Scanner input = new Scanner(System.in);
        
        ProductDaoInterface prodDao = new ProductDao("classicmodels");
        List<Product> products = prodDao.getAllProducts();
        
        for(Product p : products){
            System.out.println( "Product code = " + p.getCode() + " - Product name = " + p.getName()) ;
        }
        
        System.out.println("***************************************************************");
        System.out.println("Please enter a product code to see its full details:");
        String code = input.nextLine();
        
        Product p = prodDao.getProductByCode(code);
        if(p != null){
            System.out.println( "Product details: ") ;
            System.out.println(p);
        }
        else{
            System.out.println("No results found for that product code.");
        }
        
        // EXTRA EXAMPLE - This shows an example of UPDATING an entry in the database
        
        
        System.out.println("Please enter the code of the product to be updated: ");
        String prodCode = input.nextLine();
        
        System.out.println("Please enter the new name for this product: ");
        String newName = input.nextLine();
        
        int successful = prodDao.updateProductName(prodCode, newName);
        
        if(successful > 0){
            System.out.println("Product updated successfully, details are now: ");
            p = prodDao.getProductByCode(code);
            System.out.println(p);
        }
        else{
            System.out.println("The product code supplied could not be updated");
        }
    }
}
