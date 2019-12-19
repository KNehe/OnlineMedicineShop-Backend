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
@RequestMapping("/api")
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

    @PostMapping("/purchase")
    public ResponseEntity<String> addPurchase(@RequestBody Purchase purchase,
     HttpServletRequest request) {

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
        Objects.requireNonNull(request);
        

        // stripe operations
        // createCustomer
        String userEmail = userRepository.findUserEmail(purchase.getUser().getId());
        try 
        {
            String customerId = stripeService.createCustomer(userEmail);
            
            if(stripeService.addCardToCustomer(customerId,
               request.getHeader("cardNumber"), request.getHeader("month"), 
               request.getHeader("year"), request.getHeader("cvc")
             ))
            {  
                //charge customer
               if(stripeService.chargeCustomer( request.getHeader("amount"), customerId))
               {
                   //save
                   purchaseService.addPurchase(purchase);
               }
               else
               {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(gson.toJson("Charge Customer Error")); 
               }

            }else
            {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(gson.toJson("Failed to add card")); 
            }

        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(gson.toJson("Stripe payment error"));

        }
        
        return ResponseEntity.ok(gson.toJson("Successfully bought !"));
      
    }
    
    
    //Fetch all purchase items including price,bought by,date and amount paid
    @GetMapping("/getAllPurchases")
    public  List<Orders> getAllPurchases()
    {
        return  purchaseService.getAllPurchases();

    }


}
