package nehe.demo.Services;

import nehe.demo.Modals.Orders;
import nehe.demo.Modals.Purchase;
import nehe.demo.Repositories.OrderRepository;
import nehe.demo.Repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class PurchaseService {

    private PurchaseRepository purchaseRepository;
    private OrderRepository orderRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository,OrderRepository orderRepository) {
        this.purchaseRepository = purchaseRepository;
        this.orderRepository = orderRepository;
    }

    public  void addPurchase(Purchase purchase)
    {
        this.purchaseRepository.save(purchase);
    }

    public List<Orders> getAllPurchases(int user_id)
    {
       List<Orders> purchases = new ArrayList<>();
       orderRepository.getAllPurchases(user_id)
               .forEach(purchases::add);

       return  purchases;
    }

    public int updatePurchaseStatus(int user_id,Date date_paid,String status)
    {
        return purchaseRepository.updatePurchaseStatus(status,user_id, date_paid);
    }
}
