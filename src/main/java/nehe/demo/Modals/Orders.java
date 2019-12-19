package nehe.demo.Modals;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Orders
{
    private String firstname;
    private String lastname;
    private String  products;
    private String price;
    @Id
    private int id;
    private int product_id;
    private int user_id;
    private Date date_paid;
    private String amount_paid;
    private String status;

    public Orders() {
    }

    public Orders(String firstname, String lastname, String products, String price, int id, int product_id, int user_id,
            Date date_paid, String amount_paid, String status) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.products = products;
        this.price = price;
        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.date_paid = date_paid;
        this.amount_paid = amount_paid;
        this.status = status;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getDate_paid() {
        return date_paid;
    }

    public void setDate_paid(Date date_paid) {
        this.date_paid = date_paid;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    



   
   
   

}