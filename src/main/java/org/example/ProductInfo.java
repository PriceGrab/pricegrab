package org.example;

import java.math.BigDecimal;
import java.net.URL;

public class ProductInfo {
    private URL sellerURL;
    private String seller;
    private String currency;
    private String name;
    private BigDecimal price;

    public ProductInfo(URL sellerURL, String seller, String currency, String name,
            BigDecimal price) {
        this.sellerURL = sellerURL;
        this.seller = seller;
        this.currency = currency;
        this.name = name;
        this.price = price;
    }

    public URL getSellerURL() {
        return sellerURL;
    }

    public void setSellerURL(URL sellerURL) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" + "sellerURL=" + sellerURL + ", seller='" + seller + '\'' + ", currency='"
                + currency + '\'' + ", name='" + name + '\'' + ", price=" + price + '}';
    }
}