package nehe.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nehe.demo.Modals.Orders;

public interface OrderRepository extends JpaRepository<Orders,Integer>
{
    @Query(
      value = 
      "SELECT myusers.firstname, myusers.lastname, GROUP_CONCAT(products.name) as products, products.price, purchase.id, purchase.product_id, purchase.user_id, purchase.date_paid, purchase.amount_paid ,purchase.status from purchase INNER JOIN myusers ON myusers.id = purchase.user_id INNER JOIN products ON products.id = purchase.product_id GROUP BY purchase.user_id",
      nativeQuery = true)
   List<Orders> getAllPurchases();
}