package nehe.demo.Modals;

import javax.persistence.*;

//defines details of a drug/medicine
@Entity
@Table(name="products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="image")
	private byte[] image;
	@Column(name="price")
	private String price;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private  User user;


	public Product() {
		super();
	}

	//default id refers to user id
    public Product(int id,String name, byte[] image, String price, int default_id) {
	    this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.user = new User("","","","","",default_id,"");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
