package nehe.demo.Repositories;



import nehe.demo.Modals.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface PurchaseRepository  extends JpaRepository<Purchase,Integer> {

   @Modifying
   @Query(value = "delete from purchase where product_id=?1",nativeQuery = true)
   int deletePurchaseByProductId(int id);
}
