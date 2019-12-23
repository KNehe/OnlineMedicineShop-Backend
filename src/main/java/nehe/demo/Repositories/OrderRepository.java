package nehe.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nehe.demo.Modals.Orders;

public interface OrderRepository extends JpaRepository<Orders,Integer>
{
    @Query(
      value = 
      "SELECT myusers.firstname, myusers.lastname, myusers.phone, GROUP_CONCAT(products.name) as products, products.price, purchase.id, purchase.product_id, purchase.user_id, purchase.date_paid, GROUP_CONCAT(purchase.amount_paid) as amount_paid ,purchase.status,myusers.id, products.user_id as created_by_id from purchase INNER JOIN myusers ON myusers.id = purchase.user_id INNER JOIN products ON products.id = purchase.product_id  GROUP BY purchase.user_id , created_by_id HAVING created_by_id = ?1 ",
      nativeQuery = true)
   List<Orders> getAllPurchases(int user_id);
}