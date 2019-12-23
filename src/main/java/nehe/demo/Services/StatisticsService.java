package nehe.demo.Services;

import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nehe.demo.Modals.Statistics;
import nehe.demo.Repositories.ProductRepository;
import nehe.demo.Repositories.PurchaseRepository;

@Service
@Transactional
public class StatisticsService {
    
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;
    
    @Autowired
    public StatisticsService(ProductRepository productRepository, PurchaseRepository purchaseRepository) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }
    
    public Statistics getStatistics(int adminId)
    {   
        int allProducts = productRepository.numberOfAllProducts();
        int allProductsById = productRepository.numberOfAllProductsCreatedByParticularAdmin(adminId);
        int allPurchases = purchaseRepository.numberOfAllPurchases();
        int allPurchasesById = purchaseRepository.numberOfAllPurchaseOfProductsCreatedByParticularAdmin(adminId);
        int allDeliveredProducts = purchaseRepository.purchasedAndDelivered(adminId);
        int allNotDeliveredProducts = purchaseRepository.purchasedAndNotDelivered(adminId);
        
        return  new Statistics(allProducts, allProductsById, allPurchases, allPurchasesById, allDeliveredProducts, allNotDeliveredProducts);
    }

    
}