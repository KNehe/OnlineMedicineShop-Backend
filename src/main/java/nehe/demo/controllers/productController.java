package nehe.demo.controllers;



import java.io.IOException;

import com.google.gson.Gson;
import nehe.demo.Modals.Product;
import nehe.demo.Services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class productController {

	private ProductService productService;

	private static final Gson gson = new Gson();

	@Autowired
	public productController(ProductService productService)
	{
		this.productService = productService;
	}

	// get all the products for particular Admin
	@GetMapping("/allProducts")
	public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") int page,
	@RequestParam(required = true) int id)
	{   
		return productService.getAllProducts(page,id);
	}

	//get all the products from the database for users
	@GetMapping("/allProducts2")
	public Page<Product> getAllProducts2(@RequestParam(defaultValue = "0") int page)
	{   
		return productService.getAllProducts2(page);
	}

	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestPart("file") MultipartFile file,
											 @RequestParam(required = true) String ProductName,
											 @RequestParam(required = true) String ProductPrice,
											 @RequestParam(required = true) int AddedBy
											 ) throws IOException
	{
		if(productService.checkIfProductExists(ProductName))
		{
		  return 	ResponseEntity.ok(gson.toJson("Product exists !"));
		}

         productService.addProductOrUpdateProduct1(file,new Product(0,ProductName,file.getBytes(),ProductPrice,AddedBy));

        return ResponseEntity.ok(gson.toJson("Product saved"));
	}

	@DeleteMapping("deleteProduct/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") int id)
	{
		productService.deleteProduct(id);
		return ResponseEntity.ok(gson.toJson("Product deleted"));
	}

//    when request comes with a file
    @PostMapping("/editProduct1")
    public ResponseEntity<String> editProduct(@RequestPart("file") MultipartFile file,
											  @RequestParam(required = true) int ProductId,
											  @RequestParam(required = true) String ProductName,
											  @RequestParam(required = true) String ProductPrice,
											  @RequestParam(required = true) int AddedBy
	                                           ) throws IOException
    {
        productService.addProductOrUpdateProduct1(file,new Product(ProductId,ProductName,file.getBytes(),ProductPrice,AddedBy));

        return ResponseEntity.ok(gson.toJson("Changes made successfully"));
    }

    //edit product
    //when request doesn't come with a file
    //file is included as bytes in Product object
    @PostMapping("/editProduct2")
    public ResponseEntity<String> editProduct(@RequestBody Product product)
    {
        productService.addProductOrUpdateProduct2(product);

        return ResponseEntity.ok(gson.toJson("Changes made successfully"));
	}


}







