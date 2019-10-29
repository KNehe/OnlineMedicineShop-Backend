package nehe.demo.controllers;

import com.google.gson.Gson;
import nehe.demo.Modals.Purchase;
import nehe.demo.Services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PurchaseController {

    private static final Gson gson = new Gson();

    private PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> addPurchase(@RequestBody Purchase purchase)
    {
        purchaseService.addPurchase(purchase);
        return ResponseEntity.ok(gson.toJson("Successfully bought !"));
    }

    @GetMapping("/getAllPurchases")
    public  List<Purchase> getAllPurchases()
    {
        return  purchaseService.getAllPurchases();

    }


}
