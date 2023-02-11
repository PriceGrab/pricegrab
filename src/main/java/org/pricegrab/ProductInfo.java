package org.pricegrab;

public class ProductInfo {
    private String sellerURL;
    private String seller;
    private String currency;
    private String name;
    private String price;


    public ProductInfo(String sellerURL, String seller, String currency, String name,
                       String price) {
        this.sellerURL = sellerURL;
        this.seller = seller;
        this.currency = currency;
        this.name = name;
        this.price = price;
    }

    public String getSellerURL() {
        return sellerURL;
    }

    public void setSellerURL(String sellerURL) {
        this.sellerURL = sellerURL;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" + "sellerURL=" + sellerURL + ", seller='" + seller + '\'' + ", currency='"
                + currency + '\'' + ", name='" + name + '\'' + ", price=" + price + '}';
    }
}