package nehe.demo.Services;

import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;

import nehe.demo.Modals.Product;
import nehe.demo.Repositories.ProductRepository;
import nehe.demo.Repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


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
    public Product addProductOrUpdateProduct1(MultipartFile file, Product product) throws IOException
    {
       return productRepository.saveAndFlush(product);
    }

	//adding  a product to the database
	//when no file is sent
	//file is included in Product object as bytes
	public void addProductOrUpdateProduct2(Product product)
	{
		productRepository.save(product);
	}

	public boolean checkIfProductExists(String name)
	{
		Product product =  productRepository.findByName(name);
		if(product != null)
		{
			return true;
		}
		return false;
	}

	public void deleteProduct(int id)
	{
		purchaseRepository.deletePurchaseByProductId(id);
		productRepository.deleteById(id);
	}
	
	//get all the products from the database for particular Admins
	public Page<Product> getAllProducts(int page,int user_id)
    {
		// List<Product> allProducts = new ArrayList<>();

		// productRepository.findAll()
		// 		.forEach(allProducts::add);
        //  productRepository.getAllProducts(user_id)
        Pageable pageable = PageRequest.of(page, 4);
		return productRepository.findByUserId(user_id, pageable);
	}

	//get all the products from the database for users 
	public Page<Product> getAllProducts2(int page)
    {
		// List<Product> allProducts = new ArrayList<>();

		// productRepository.findAll()
		// 		.forEach(allProducts::add);
        //  productRepository.getAllProducts(user_id)

		return productRepository.findAll( PageRequest.of(page, 4));
	}
	
	public Product getOneProduct(int id)
	{
		return productRepository.findById(id).orElse(null);
	}




	
	

}

