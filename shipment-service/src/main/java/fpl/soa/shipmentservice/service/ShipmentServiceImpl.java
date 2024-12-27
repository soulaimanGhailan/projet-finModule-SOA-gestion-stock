package fpl.soa.shipmentservice.service;

import fpl.soa.shipmentservice.entities.ShipmentEntity;
import fpl.soa.shipmentservice.repos.ShipmentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShipmentServiceImpl implements ShipmentService {

    private ShipmentRepo shipmentRepo ;

    public ShipmentServiceImpl(ShipmentRepo shipmentRepo) {
        this.shipmentRepo = shipmentRepo;
    }

    @Override
    public ShipmentEntity createShipment(ShipmentEntity shipmentEntity) {
        return shipmentRepo.save(shipmentEntity);
    }

    @Override
    public ShipmentEntity trackShipment(String trackingNumber) {
        return null;
    }

    @Override
    public List<ShipmentEntity> getShipments() {
        return List.of();
    }

    @Override
    public ShipmentEntity getShipmentById(Long id) {
        return null;
    }
}
