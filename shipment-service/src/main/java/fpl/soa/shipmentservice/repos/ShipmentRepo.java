package fpl.soa.shipmentservice.repos;

import fpl.soa.shipmentservice.entities.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepo extends JpaRepository<ShipmentEntity, Long> {
}
