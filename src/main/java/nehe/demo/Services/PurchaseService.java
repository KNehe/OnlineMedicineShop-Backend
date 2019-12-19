package nehe.demo.Services;

import nehe.demo.Modals.Orders;
import nehe.demo.Modals.Purchase;
import nehe.demo.Repositories.OrderRepository;
import nehe.demo.Repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public List<Orders> getAllPurchases()
    {
       List<Orders> purchases = new ArrayList<>();
       orderRepository.getAllPurchases()
               .forEach(purchases::add);

       return  purchases;
    }
}
