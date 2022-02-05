package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<>();

    public void addProduct(Product product, Integer quantity) {
        if (quantity == 0) throw new IllegalArgumentException("Quantity cannot be 0");

        this.products.put(product, quantity);
    }

    public void addProduct(Product product) {
        addProduct(product,1);
    }

    public BigDecimal getNetTotal() {

        BigDecimal sum = BigDecimal.ZERO;
        for (Product product : this.products.keySet()) {
            Integer quantity = this.products.get(product);
            BigDecimal quantityAsBigDecimal = BigDecimal.valueOf(quantity);
            BigDecimal priceOfThisItem = product.getPrice().multiply(quantityAsBigDecimal);
            sum = sum.add(priceOfThisItem);
        }
        return sum;
    }

    public BigDecimal getTax() {
        return this.getTotal().subtract(this.getNetTotal());
    }

    public BigDecimal getTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = BigDecimal.valueOf(products.get(product));
            sum = sum.add(product.getPriceWithTax().multiply(quantity));
        }

        return sum;
    }
}
