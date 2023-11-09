package be.kdg.bakery.repository;

import be.kdg.bakery.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findByProductNumber(UUID productNumber);
    Optional<Product> findByProductName(String productName);
    Collection<Product> findAllBy();

    Collection<Product> findAllByPriceIsNull();

}
