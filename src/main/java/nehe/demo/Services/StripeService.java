package nehe.demo.Services;

import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StripeService {

   

    @Autowired
    public  StripeService()
    {
        Stripe.apiKey = "sk_test_m7BvdxL5Nhm6ojJEtPtlPOLo00qDXssoGt";
    }

    //create  a stripe customer
    //returns a customer id
    public String createCustomer(String email) throws Exception
    {
        //create a customer
        Map<String,Object> customerParam = new HashMap<>();
        customerParam.put("email",email);
        Customer customer = Customer.create(customerParam);

        return customer.getId();
    }

    //create a card
    public boolean addCardToCustomer(String customerId,String number,String month,String year,String cvc) throws Exception
    {
        try
        {
            //create a card
        Customer a = Customer.retrieve(customerId);
        Map<String,Object> cardParam = new HashMap<>();
        cardParam.put("number",number);
        cardParam.put("exp_month",month);
        cardParam.put("exp_year",year);
        cardParam.put("cvc",cvc);

        Map<String,Object> tokenParam = new HashMap<>();
        tokenParam.put("card",cardParam);

        Token token = Token.create(tokenParam);

        Map<String,Object> source = new HashMap<>();
        source.put("source",token.getId());

        a.getSources().create(source);
        return true;
        
        }
        catch(Exception e)
        {
            System.out.println("Add Card To Customer Error: "+ e.getMessage());
            return false;
        }

    }

    //charge a customer
    public boolean chargeCustomer(String amount,String customerId) throws Exception
    {
        try
        {
        
        Map<String,Object> chargeParam = new HashMap<>();

        chargeParam.put("amount",amount+"00");
        chargeParam.put("currency","usd");
        chargeParam.put("customer",customerId);

        Charge.create(chargeParam);

        return true;

        }
        catch(Exception e)
        {
            System.out.println("Charge Customer Error: "+ e.getMessage());
            return false;
        }
    }



}
