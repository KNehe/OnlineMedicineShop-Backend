package nehe.demo.Repositories;

import nehe.demo.Modals.Product;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Modifying
    @Query(value = "update products set image =? where user_id =? and id = ?",nativeQuery = true)
    void saveProductImage(String location, int userId, int productId);


    Product findByName(String productName);
    
    Page<Product> findByUserId(int user_id, Pageable pageable);
    
    @Query(value = "SELECT COUNT(*) from products",nativeQuery = true)
    int numberOfAllProducts();

    @Query(value = "SELECT COUNT(*) from products where user_id=?1",nativeQuery = true)
    int numberOfAllProductsCreatedByParticularAdmin(int adminId);
    
    

}
