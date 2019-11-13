package nehe.demo.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nehe.demo.Modals.Product;
import nehe.demo.Repositories.ProductRepository;
import nehe.demo.Repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


//this service is only for products
//will be used in the Product Controller
@Service
@Transactional
public class ProductService {
	

	private ProductRepository productRepository;
	private PurchaseRepository purchaseRepository;

	public ProductService(){}

	@Autowired
	public ProductService(ProductRepository productRepository,
						  PurchaseRepository purchaseRepository)
	{
		this.productRepository = productRepository;
		this.purchaseRepository = purchaseRepository;
	}
	
	//adding  a product to the database
	//when request has a file
    public void addProductOrUpdateProduct1(MultipartFile file, HttpServletRequest request) throws IOException
    {
        String productName =  request.getHeader("ProductName");
        String productPrice = request.getHeader("ProductPrice");
		int productId = request.getIntHeader("ProductId");
        int addedBy = request.getIntHeader("AddedBy");

        productRepository.saveAndFlush(new Product(productId,productName,file.getBytes(),productPrice,addedBy));
    }

	//adding  a product to the database
	//when no file is sent
	//file is included in Product object as bytes
	public void addProductOrUpdateProduct2(Product product)
	{
		productRepository.save(product);
	}

    //check if a product exists
	public boolean checkIfProductExists(String name)
	{
		Product product =  productRepository.findByName(name);
		if(product != null)
		{
			return true;
		}
		return false;
	}

	//deleting  a product from database
	public void deleteProduct(int id)
	{
		//delete from purchase if it exists
		purchaseRepository.deletePurchaseByProductId(id);
		//then delete product
		productRepository.deleteById(id);
	}
	
	//get all the products from the database
	public List<Product> getAllProducts()
    {
		List<Product> allProducts = new ArrayList<>();

		productRepository.findAll()
				.forEach(allProducts::add);

		return allProducts;
	}
	
	//get a particular product from the database
	public Product getOneProduct(int id)
	{
		return productRepository.findById(id).orElse(null);
	}




	
	

}

