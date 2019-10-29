package nehe.demo.Repositories;



import nehe.demo.Modals.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PurchaseRepository  extends JpaRepository<Purchase,Integer> {


}
