package org.example;

public class ProductInfo {
    private String productName;
    private double price;
    private String website;
    private String currency;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }






    @Override
    public String toString() {
        return "ProductInfo{" + "productName='" + productName + '\'' + ", price=" + price
                + ", website='" + website + '\'' + ", currency='" + currency + '\'' + '}';
    }
}