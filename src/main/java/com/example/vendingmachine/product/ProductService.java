package com.example.vendingmachine.product;

import com.example.vendingmachine.stats.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    public static final String PRODUCT_UNAVAILABLE = "Sorry but this item is not available anymore.\n";
    public static final String RELATED_AVAILABLE_PRODUCTS = "Please find below a list of related products available\n%s";

    private final ProductRepository productRepo;
    private final StatService statService;

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    public Product buyProduct(String name) {
        return productRepo.findByNameIgnoreCase(name)
                .map(this::buyProductInternal)
                .orElseThrow(() -> new ProductNotFoundException(name));
    }

    public Product saveProduct(SaveProduct save) {
        return productRepo.findOne(Product.example(save))
                .map(product -> product.incrementQuantity(save.getQuantity()))
                .map(productRepo::save)
                .orElseGet(() -> productRepo.save(Product.from(save)));
    }

    private Product buyProductInternal(Product product) {
        try {
            product.decrementQuantity();
        } catch (ProductOutOfStockException ex) {
            List<Product> relatedProducts = productRepo.findByTypeAndQuantityGreaterThan(product.getType(), 0);
            String message = PRODUCT_UNAVAILABLE;
            if (!CollectionUtils.isEmpty(relatedProducts)) {
                String products = relatedProducts.stream()
                        .map(p -> String.format("-%s", p.getName()))
                        .collect(Collectors.joining("\n"));
                message = message.concat(String.format(RELATED_AVAILABLE_PRODUCTS, products));
            }
            ex.setReason(message);
            throw ex;
        }
        statService.incrementTotalAmountSold(product.getPrice());
        statService.incrementTotalProductsDelivered();
        return productRepo.save(product);
    }
}
