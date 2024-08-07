package mshcherba.ecommerce.catalog;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.math.BigDecimal;
import java.util.List;


public class ProductCatalogTest {
    @Test
    void itListAvailableProducts(){
        ProductCatalog catalog = createProductCatalog();

        List<Product> products = catalog.allProducts();

        assert products.isEmpty();
    }

    private static ProductCatalog createProductCatalog() {
        return new ProductCatalog(new ArrayListProductStorage());
    }

    @Test
    void itAllowsToAddProduct(){
        ProductCatalog catalog = createProductCatalog();

        catalog.addProduct("Lego set 8083", "Nice one", BigDecimal.valueOf(100), "https://example.com/images/lego8083.jpg");

        List<Product> products = catalog.allProducts();

        assertThat(products)
                .hasSize(1);
    }

    @Test
    void itLoadsSingleProductById(){
        ProductCatalog catalog = createProductCatalog();
        String id = catalog.addProduct("Lego set 8083", "Nice one", BigDecimal.valueOf(100), "https://example.com/images/lego8083.jpg");

        Product loaded = catalog.getProductBy(id);

        assertThat(id).isEqualTo(loaded.getId());
    }

    @Test
    void itAllowsChangePrice(){
        ProductCatalog catalog = createProductCatalog();
        String id = catalog.addProduct("Lego set 8083", "Nice one", BigDecimal.valueOf(100), "https://example.com/images/lego8083.jpg");

        catalog.changePrice(id, BigDecimal.valueOf(10.10));
        Product loaded = catalog.getProductBy(id);

        assertThat(BigDecimal.valueOf(10.10)).isEqualTo(loaded.getPrice());
    }

    private static ProductCatalog ProductCatalog() {
        return createProductCatalog();
    }
}