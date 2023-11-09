package be.kdg.warehouse.repository;


import be.kdg.warehouse.domain.stock.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

}
