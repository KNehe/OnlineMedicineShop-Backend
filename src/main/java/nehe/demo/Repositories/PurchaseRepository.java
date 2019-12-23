package nehe.demo.Repositories;



import nehe.demo.Modals.Purchase;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface PurchaseRepository  extends JpaRepository<Purchase,Integer> {

   @Modifying
   @Query(value = "delete from purchase where product_id=?1",nativeQuery = true)
   int deletePurchaseByProductId(int id);
   
   @Modifying
   @Query(value ="update purchase set status=? where user_id=? and date_paid=?", nativeQuery = true)
   int updatePurchaseStatus(String status,int user_id,Date date_paid);

   @Query(value = "SELECT COUNT(*) from purchase",nativeQuery = true)
   int numberOfAllPurchases();

   @Query(value = "SELECT COUNT(*) from purchase INNER JOIN products ON purchase.product_id = products.id WHERE products.user_id=?1",nativeQuery = true)
   int numberOfAllPurchaseOfProductsCreatedByParticularAdmin(int adminId);

   @Query(value = "SELECT COUNT(*) from purchase INNER JOIN products ON purchase.product_id = products.id WHERE products.user_id=?1 AND products.status=Not Delivered ",nativeQuery = true)
   int purchasedAndNotDelivered(int adminId);

   @Query(value = "SELECT COUNT(*) from purchase INNER JOIN products ON purchase.product_id = products.id WHERE products.user_id=?1 AND products.status=Delivered",nativeQuery = true)
   int purchasedAndDelivered(int adminId);

 
}
