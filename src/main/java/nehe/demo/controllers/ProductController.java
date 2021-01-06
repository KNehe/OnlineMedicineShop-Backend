package nehe.demo.controllers;



import java.io.IOException;

import nehe.demo.Modals.DataResponse;
import nehe.demo.Modals.GeneralResponse;
import nehe.demo.Modals.Product;
import nehe.demo.Services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/products")
public class ProductController {

	private ProductService productService;

	@Autowired
	public ProductController(ProductService productService)
	{
		this.productService = productService;
	}


	//get all the products from the database for users
	@GetMapping("/")
	public ResponseEntity<?> getAllProducts2(@RequestParam(defaultValue = "0") int page)
	{   
		return ResponseEntity.ok(new DataResponse("Success", productService.getAllProducts2(page)));
	}

	@DeleteMapping("products/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id)
	{
		productService.deleteProduct(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body( new GeneralResponse("Success", "Product deleted"));
	}

	@PostMapping("/")
	public ResponseEntity<?> addProduct(@RequestPart("file") MultipartFile file,
											 @RequestParam(required = true) String productName,
											 @RequestParam(required = true) String productPrice,
											 @RequestParam(required = true) int addedBy
	) throws IOException
	{
		if(productService.checkIfProductExists(productName))
		{
			return 	ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse("Fail", "Product exists !"));
		}

		Product product = productService.addProductOrUpdateProduct1(file,new Product(0,productName,file.getBytes(),productPrice,addedBy));

		return ResponseEntity.ok(new DataResponse("Success",product));
	}


	// get all the products for particular Admin
	@GetMapping("/products2")
	public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0") int page,
										@RequestParam(required = true) int id)
	{
		Page<Product> products = productService.getAllProducts(page,id);

		return ResponseEntity.ok(new DataResponse("Success",products));

	}


//    when request comes with a file
    @PostMapping("/edit-product1")
    public ResponseEntity<?> editProduct(@RequestPart("file") MultipartFile file,
											  @RequestParam(required = true) int ProductId,
											  @RequestParam(required = true) String ProductName,
											  @RequestParam(required = true) String ProductPrice,
											  @RequestParam(required = true) int AddedBy
	                                           ) throws IOException
    {
        productService.addProductOrUpdateProduct1(file,new Product(ProductId,ProductName,file.getBytes(),ProductPrice,AddedBy));

		return ResponseEntity.ok(new GeneralResponse("Success", "Changes made successfully"));

	}

    //edit product
    //when request doesn't come with a file
    //file is included as bytes in Product object
    @PostMapping("/edit-product2")
    public ResponseEntity<?> editProduct(@RequestBody Product product)
    {
        productService.addProductOrUpdateProduct2(product);

		return ResponseEntity.ok(new GeneralResponse("Success", "Changes made successfully"));

	}


}







