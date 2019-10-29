package nehe.demo.Services;

import nehe.demo.Modals.Purchase;
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

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public  void addPurchase(Purchase purchase)
    {
        this.purchaseRepository.save(purchase);
    }

    public List<Purchase> getAllPurchases()
    {
       List<Purchase> purchases = new ArrayList<>();
       purchaseRepository.findAll()
               .forEach(purchases::add);

       return  purchases;
    }
}
