package nehe.demo.Modals;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "amount_paid")
    private int amount_paid;

    @Column(name = "date_paid")
    private Date date_paid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status")
    private String status;


    public Purchase() {
    }

    public Purchase(int id, int amount_paid, Date date_paid, int expectedUserId, int productId, byte image[], String status) {
        this.id = id;
        this.amount_paid = amount_paid;
        this.date_paid = date_paid;
        this.user = new User("", "", "", "", "", expectedUserId, "");
        this.product = new Product(productId, "", image, "", this.user.getId());
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(int amount_paid) {
        this.amount_paid = amount_paid;
    }

    public Date getDate_paid() {
        return date_paid;
    }

    public void setDate_paid(Date date_paid) {
        this.date_paid = date_paid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
