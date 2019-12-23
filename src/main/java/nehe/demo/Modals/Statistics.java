package nehe.demo.Modals;

public class Statistics {

    private int allProducts;
    private int allProductsById; //number of products created by a particular admin
    private int allPurchases;
    private int allPurchasesById;//number of purchases of products created by a particular admin
    private int allDeliveredProducts;
    private int allNotDeliveredProducts;

    public Statistics(int allProducts, int allProductsById, int allPurchases, int allPurchasesById,
            int allDeliveredProducts, int allNotDeliveredProducts) {
        this.allProducts = allProducts;
        this.allProductsById = allProductsById;
        this.allPurchases = allPurchases;
        this.allPurchasesById = allPurchasesById;
        this.allDeliveredProducts = allDeliveredProducts;
        this.allNotDeliveredProducts = allNotDeliveredProducts;
    }

    public int getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(int allProducts) {
        this.allProducts = allProducts;
    }

    public int getAllProductsById() {
        return allProductsById;
    }

    public void setAllProductsById(int allProductsById) {
        this.allProductsById = allProductsById;
    }

    public int getAllPurchases() {
        return allPurchases;
    }

    public void setAllPurchases(int allPurchases) {
        this.allPurchases = allPurchases;
    }

    public int getAllPurchasesById() {
        return allPurchasesById;
    }

    public void setAllPurchasesById(int allPurchasesById) {
        this.allPurchasesById = allPurchasesById;
    }

    public int getAllDeliveredProducts() {
        return allDeliveredProducts;
    }

    public void setAllDeliveredProducts(int allDeliveredProducts) {
        this.allDeliveredProducts = allDeliveredProducts;
    }

    public int getAllNotDeliveredProducts() {
        return allNotDeliveredProducts;
    }

    public void setAllNotDeliveredProducts(int allNotDeliveredProducts) {
        this.allNotDeliveredProducts = allNotDeliveredProducts;
    }

    

   
}