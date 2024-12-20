package fpl.soa.shipmentservice.service;

import fpl.soa.shipmentservice.entities.ShipmentEntity;

import java.util.List;

public interface ShipmentService {
    ShipmentEntity createShipment(ShipmentEntity shipmentEntity);
    ShipmentEntity trackShipment(String trackingNumber);
    List<ShipmentEntity> getShipments();
    ShipmentEntity getShipmentById(Long id);
}
