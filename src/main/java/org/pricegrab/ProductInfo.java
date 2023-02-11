package org.pricegrab;

public record ProductInfo(String sellerURL, String seller, String currency, String name, String price) {

    @Override
    public String toString() {
        return "Product{" + "sellerURL=" + sellerURL + ", seller='" + seller + '\'' + ", currency='"
                + currency + '\'' + ", name='" + name + '\'' + ", price=" + price + '}';
    }
}