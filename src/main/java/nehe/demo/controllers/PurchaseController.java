package nehe.demo.controllers;


import nehe.demo.Modals.DataResponse;
import nehe.demo.Modals.GeneralResponse;
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
import java.util.Objects;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

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
    public ResponseEntity<?> addPurchase(@RequestBody Purchase purchase,@RequestParam String cardNumber,
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
            .body(new GeneralResponse("Fail", "Wrong url"));

        } catch (IOException e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(new GeneralResponse("Fail", "No Internet Connection"));

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
                .body(new GeneralResponse("Fail", "Charge Customer Error"));
               }

            }else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GeneralResponse("Fail", "Failed to add card"));
            }

        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new GeneralResponse("Fail", "An error occurred while processing payment"));

        }
        
        return ResponseEntity.ok(new GeneralResponse("Fail", "Successfully bought !"));
      
    }
    
    
    @GetMapping("/")
    public   ResponseEntity<DataResponse> getAllPurchases(@RequestParam(required = true)int id)
    {
        return  ResponseEntity.ok( new DataResponse("Success",  purchaseService.getAllPurchases(id)));

    }

    @PutMapping("/")
    public ResponseEntity<?> updatePurchaseStatus(@RequestBody Orders orders)
    {
      Objects.requireNonNull(orders);
      if(purchaseService.updatePurchaseStatus(orders.getUser_id(), orders.getDate_paid(), orders.getStatus()) > 0)
      { 
        return ResponseEntity.status(HttpStatus.OK).body(new GeneralResponse("Success", "Update successfull"));
      }
      else
      {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new GeneralResponse("Fail", "An error occurred"));
      }
    }


}
