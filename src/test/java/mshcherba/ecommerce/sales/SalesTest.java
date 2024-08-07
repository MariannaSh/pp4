package mshcherba.ecommerce.sales;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import mshcherba.ecommerce.catalog.ProductCatalog;
import mshcherba.ecommerce.sales.cart.CartLine;
import mshcherba.ecommerce.sales.cart.InMemoryCartStorage;
import mshcherba.ecommerce.sales.offer.Offer;
import mshcherba.ecommerce.sales.offer.OfferCalculator;
import mshcherba.ecommerce.sales.reservation.ReservationRepository;
import mshcherba.ecommerce.sales.reservation.SpyPaymentGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class SalesTest {

    @Autowired
    ProductCatalog catalog;
    @Test
    void itShowsCurrentOffer() {
        //ARRANGE
        SalesFacade sales = thereIsSales();
        String customerId = thereIsCustomer("Mari");

        //ACT
        Offer offer = sales.getCurrentOffer(customerId);

        //ASSERT
        assertThat(offer.getTotal()).isEqualTo(BigDecimal.valueOf(0));
        assertThat(offer.getItemsCount()).isEqualTo(0);
    }

    private SalesFacade thereIsSales() {
        return new SalesFacade(
                new InMemoryCartStorage(),
                new OfferCalculator(catalog),
                new SpyPaymentGateway(),
                new ReservationRepository());
    }


    private String thereIsCustomer(String name) {

        return name;
    }

    @Test
    void itAddsProductToCart() {
        SalesFacade sales = thereIsSales();
        String customerId = thereIsCustomer("Mari");
        String productId = thereIsProduct("x","new",BigDecimal.valueOf(300), "https://example.com");

        sales.addToCart(customerId,productId);

        Offer offer = sales.getCurrentOffer(customerId);

        assertThat(offer.getTotal()).isEqualTo(BigDecimal.valueOf(300));
        assertThat(offer.getItemsCount()).isEqualTo(1);


    }

    private String thereIsProduct(String name, String desc, BigDecimal price, String url) {
        return catalog.addProduct(name, desc, price, url);
    }

    @Test
    void itAllowsToRemoveProductFromCart() {

    }

    @Test
    void itAllowsToAcceptOffer() {

    }


}