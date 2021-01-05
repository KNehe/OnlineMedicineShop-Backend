package nehe.demo.controllers;

import com.google.gson.Gson;

import nehe.demo.Modals.Orders;
import nehe.demo.Modals.Purchase;
import nehe.demo.Repositories.UserRepository;
import nehe.demo.Services.PurchaseService;
import nehe.demo.Services.StripeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

    private static final Gson gson = new Gson();

    private PurchaseService purchaseService;
    private StripeService stripeService;
    private UserRepository userRepository;


    @Autowired
    public PurchaseController(PurchaseService purchaseService, StripeService stripeService,
            UserRepository userRepository) {
        this.purchaseService = purchaseService;
        this.stripeService = stripeService;
        this.userRepository = userRepository;
    }

    @PostMapping("/")
    public ResponseEntity<String> addPurchase(@RequestBody Purchase purchase,@RequestParam String cardNumber,
                                              @RequestParam  String month,
                                              @RequestParam String year,
                                              @RequestParam String cvc,
                                              @RequestParam  String amount
                                              ) {

        // check internet connection
        try {

            URL stripeUrl = new URL("https://stripe.com/en-se");
            URLConnection urlConnection = stripeUrl.openConnection();
            urlConnection.connect();

        } catch (MalformedURLException e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(gson.toJson("No Internet Connection"));

        } catch (IOException e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(gson.toJson("No Internet Connection"));

        }

        Objects.requireNonNull(purchase);
        

        String userEmail = userRepository.findUserEmail(purchase.getUser().getId());
        try 
        {
            String customerId = stripeService.createCustomer(userEmail);
            
            if(stripeService.addCardToCustomer(customerId,cardNumber,month,year,cvc))
            {  
               if(stripeService.chargeCustomer(amount, customerId))
               {
                   purchaseService.addPurchase(purchase);
               }
               else
               {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(gson.toJson("Charge Customer Error")); 
               }

            }else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(gson.toJson("Failed to add card")); 
            }

        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(gson.toJson("Stripe payment error"));

        }
        
        return ResponseEntity.ok(gson.toJson("Successfully bought !"));
      
    }
    
    
    //Fetch all purchase items including price,bought by,date and amount paid
    @GetMapping("/")
    public  List<Orders> getAllPurchases(@RequestParam(required = true)int hash)
    {
        return  purchaseService.getAllPurchases(hash); //hash is admin id

    }

    @PutMapping("/")
    public ResponseEntity<String> updatePurchaseStatus(@RequestBody Orders orders)
    {
      Objects.requireNonNull(orders);
      if(purchaseService.updatePurchaseStatus(orders.getUser_id(), orders.getDate_paid(), orders.getStatus()) > 0)
      { 
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("Confirmed !"));
      }
      else
      {
          return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(gson.toJson("An error occurred while confirming"));
      }
    }


}
