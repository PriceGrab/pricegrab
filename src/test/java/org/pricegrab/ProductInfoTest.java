package org.pricegrab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProductInfoTest {

    @Test
    public void toStringTest() {
        ProductInfo productInfo =
                new ProductInfo("sellerURLTest2", "sellerTest2", "currencyTest2", "nameTest2",
                        "priceTest2");
        String output = productInfo.toString();
        assertEquals(
                "Product{sellerURL=sellerURLTest2, seller='sellerTest2', currency='currencyTest2', name='nameTest2', price=priceTest2}",
                output);
    }
}